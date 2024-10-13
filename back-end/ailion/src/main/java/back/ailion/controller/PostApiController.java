package back.ailion.controller;

import back.ailion.exception.BaseExceptionCode;
import back.ailion.exception.custom.NonPositiveException;
import back.ailion.exception.custom.NotFoundException;
import back.ailion.model.dto.*;
import back.ailion.model.dto.request.*;
import back.ailion.model.entity.Post;
import back.ailion.service.AwsS3Service;
import back.ailion.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ailion")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final AwsS3Service awsS3Service;

    @PostMapping("/posts")
    public MediaPost savePostWithFile(@Valid @RequestPart PostRequestDto postRequestDto,
                              @RequestPart(value = "attachFile", required = false) MultipartFile attachFile,
                              @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles) throws IOException {

        PostDto postDto = postService.savePostWithFile(postRequestDto);
        FileUploadResponse fileUploadResponse = new FileUploadResponse();

        if (attachFile != null || imageFiles != null) {
            fileUploadResponse = awsS3Service.uploadFile(new FileUploadRequest(postDto.getPostId(), attachFile, imageFiles));
        }

        return new MediaPost(postDto, fileUploadResponse);
    }

    @PatchMapping("/posts")
    public PostDto updatePost(@Valid @RequestBody PostUpdateDto postUpdateDto) {

        return postService.updatePost(postUpdateDto);
    }

    @DeleteMapping("/posts/{id}")
    public boolean deletePost(@PathVariable("id") Long postId) {

        return postService.deletePost(postId);
    }

    // 상세 게시글
    @GetMapping("/api/posts/{postId}/{userId}")
    public PostLikeDto getPost(@PathVariable("postId") Long postId,
                               @PathVariable("userId") Long userId,
                               HttpServletRequest req, HttpServletResponse res) {


        viewCountUp(postId, req, res);
        return postService.getPost(postId, userId);
    }

    // 카테고리 게시글
    @GetMapping("/api/posts/{category}/list")
    public Page<PostDto> getCategoryPosts(
            @PathVariable("category") String category,
            @RequestParam(value="page", defaultValue="0") int page) {

        Page<Post> paging = postService.getCategoryPosts(category, page);
        return paging.map(post -> new PostDto(post));
    }

    // 인기 게시글
    @GetMapping("/api/posts/best/list")
    public Result getBestPosts() {

        return postService.getBestPosts();
    }

    // 복수 게시글
    @GetMapping("/api/posts/list")
    public Page<PostDto> getPosts(@RequestParam(value="page", defaultValue="0") int page) {

        Page<Post> paging = postService.getPosts(page);
        return paging.map(post -> new PostDto(post));
    }

    private void viewCountUp(Long id, HttpServletRequest req, HttpServletResponse res) {

        Cookie oldCookie = null;

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            // 요청에 Cookie가 있고 글을 조회한 기록이 있다면 pass 없다면 Cookie에 [게시글ID] 붙이기
            if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                postService.viewCountUp(id);
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                res.addCookie(oldCookie);
            }
        } else {
            // 요청에 Cookie가 없고 글을 조회한다면 [게시글ID]의 값을 추가하여 Cookie생성 (기간은 하루로 설정)
            postService.viewCountUp(id);
            Cookie newCookie = new Cookie("postView","[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            res.addCookie(newCookie);
        }

    }

    @GetMapping("/api/posts/search")
    public Page<PostDto> searchPosts(@RequestBody SearchPostDto searchPost,
                                     @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {

        return postService.searchPosts(searchPost, pageable);
    }
}