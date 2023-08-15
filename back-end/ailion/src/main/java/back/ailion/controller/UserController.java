package back.ailion.controller;

import back.ailion.config.jwt.GetIdFromToken;
import back.ailion.model.dto.AiInfoResponseDto;
import back.ailion.model.dto.FileUploadResponse;
import back.ailion.model.dto.PostDto;
import back.ailion.model.dto.UserDto;
import back.ailion.model.entity.User;
import back.ailion.service.AwsS3Service;
import back.ailion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ailion")
public class UserController {
    private final UserService userService;
    private final AwsS3Service awsS3Service;

    @PostMapping("/api/signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }

    @PostMapping("/signup/validId")
    public ResponseEntity<Boolean> isValidId(@RequestBody String id) {
        return ResponseEntity.ok(userService.isValidId(id));
    }

    @GetMapping("/my/posts")
    public List<PostDto> myPosts(@GetIdFromToken String username) {

        return userService.myPosts(username);
    }

    @GetMapping("/my/like/posts")
    public List<PostDto> myLikePosts(@GetIdFromToken String username) {

        return userService.myLikePosts(username);
    }

    @GetMapping("/my/favorite/ai")
    public List<AiInfoResponseDto> myFavoriteAi(@GetIdFromToken String username) {

        return userService.myFavoriteAi(username);
    }

    @GetMapping("/my/profile")
    public UserDto getMyProfile(@GetIdFromToken String username) {

        return userService.getMyProfile(username);
    }

    @PostMapping("/profile/image")
    public FileUploadResponse changeProfileImage(@RequestPart(value = "profileImage") MultipartFile profileImage) throws IOException {

        return awsS3Service.changeProfileImage(profileImage);
    }
}