package back.ailion.service;

import back.ailion.exception.BaseExceptionCode;
import back.ailion.exception.custom.NotFoundException;
import back.ailion.model.dto.*;
import back.ailion.model.dto.request.PostRequestDto;
import back.ailion.model.dto.request.PostUpdateDto;
import back.ailion.model.dto.request.SearchPostDto;
import back.ailion.model.entity.Comment;
import back.ailion.model.entity.Reply;
import back.ailion.model.entity.User;
import back.ailion.model.entity.Post;
import back.ailion.repository.HeartRepository;
import back.ailion.repository.UserRepository;
import back.ailion.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;

    private PostDto PostToPostDto(Post post) {
        return new PostDto(post);
    }

    @Transactional
    public PostDto savePostWithFile(PostRequestDto postRequestDto) {

        User user = userRepository.findById(postRequestDto.getUserId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.USER_NOT_FOUND));

        Post post = Post.builder()
                .user(user)
                .content(postRequestDto.getContent())
                .title(postRequestDto.getTitle())
                .writer(user.getNickname())
                .commentCount(0)
                .likeCount(0)
                .viewCount(0)
                .delCheck(false)
                .category(postRequestDto.getCategory())
                .build();

        return PostToPostDto(postRepository.save(post));
    }

    @Transactional
    public PostDto updatePost(PostUpdateDto updateDto) {

        Post post = postRepository.findById(updateDto.getPostId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.POST_NOT_FOUND));

        post.setTitle(updateDto.getTitle());
        post.setContent(updateDto.getContent());
        post.setCategory(updateDto.getCategory());

        return PostToPostDto(postRepository.findById(updateDto.getPostId()).get());
    }

    @Transactional
    public boolean deletePost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.POST_NOT_FOUND));

        post.delete();
        return true;
    }

    public Page<PostDto> searchPosts(SearchPostDto searchPost, Pageable pageable) {

        Page<Post> searchResult = postRepository.findByTitleContainingAndWriterContainingAndDelCheckFalse(searchPost.getTitle(), searchPost.getWriter(), pageable);
        return searchResult.map(post -> new PostDto(post));
    }

    @Transactional
    public void viewCountUp(Long postId) {
        Post post = findById(postId);
        post.viewCountUp(post);
    }

    @Transactional(readOnly = true)
    public Post findById(Long postId) {
//        checkNotNull(postId, "postId must be provided");
        return postRepository.findById(postId).orElseThrow(() -> new NotFoundException(BaseExceptionCode.POST_NOT_FOUND));
    }

    public Page<Post> getCategoryPosts(String category, int page) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return postRepository.findByCategory(category, pageable);
    }

    public Result getBestPosts() {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));

        Pageable pageable = PageRequest.of(0, 5, Sort.by(sorts));

        List<Post> posts = postRepository.findBestPostsByLike(pageable);
        List<PostDto> collect = posts.stream()
                .map(post -> new PostDto(post))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    // 페이징 처리
    public Page<Post> getPosts(int page) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return postRepository.findByDelCheckFalse(pageable);
    }

    public PostLikeDto getPost(Long postId, Long userId) {

        Post post = postRepository.findByIdWithComments(postId);

        if (heartRepository.findByPostIdAndUserId(postId, userId).isPresent()) {
            return new PostLikeDto(true, convertPostToDTO(post));
        }

        return new PostLikeDto(false, convertPostToDTO(post));
    }

    private PostDto convertPostToDTO(Post post) {
        PostDto postDTO = new PostDto();
        postDTO.setPostId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setWriter(post.getWriter());
        postDTO.setCommentCount(post.getCommentCount());
        postDTO.setLikeCount(post.getLikeCount());
        postDTO.setViewCount(post.getViewCount());
        postDTO.setCreatedDate(post.getCreatedDate());
        postDTO.setDelCheck(post.isDelCheck());
        postDTO.setComments(convertCommentsToDTOs(post.getComments()));
        postDTO.setUserId(post.getUser().getId());
        return postDTO;
    }

    private List<CommentDto> convertCommentsToDTOs(List<Comment> comments) {
        List<CommentDto> commentDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto commentDTO = new CommentDto();
            commentDTO.setUserId(comment.getUser().getId());
            commentDTO.setCommentId(comment.getId());
            commentDTO.setContent(comment.getContent());
            commentDTO.setWriter(comment.getWriter());
            commentDTO.setCreatedDate(comment.getCreatedDate());
            commentDTO.setDelCheck(comment.isDelCheck());
            commentDTO.setReplies(convertRepliesToDTOs(comment.getReplies()));
            commentDTOs.add(commentDTO);
        }
        return commentDTOs;
    }

    private List<ReplyDto> convertRepliesToDTOs(List<Reply> replies) {
        List<ReplyDto> replyDTOs = new ArrayList<>();
        for (Reply reply : replies) {
            ReplyDto replyDTO = new ReplyDto();
            replyDTO.setUserId(reply.getUser().getId());
            replyDTO.setReplyId(reply.getId());
            replyDTO.setContent(reply.getContent());
            replyDTO.setWriter(reply.getWriter());
            replyDTO.setDelCheck(reply.isDelCheck());
            replyDTO.setCreatedDate(reply.getCreatedDate());
            replyDTOs.add(replyDTO);
        }
        return replyDTOs;
    }
}
