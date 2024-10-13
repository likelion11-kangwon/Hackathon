package back.ailion.repository;

import back.ailion.model.entity.User;
import back.ailion.model.entity.Post;
import back.ailion.model.entity.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;


    @Test
    public void crudTest() {

        User user = User.builder()
                .username("username")
                .name("an")
                .nickname("ph")
                .password("123")
                .phone("010123")
                .activated(true)
                .id(1L)
                .providerId("")
                .build();

        User savedUser = userRepository.save(user);

        Post post = Post.builder()
                .user(savedUser)
                .content("test")
                .title("hi")
                .writer(savedUser.getNickname())
                .build();

        Post savedPost = postRepository.save(post);

        assertThat(savedUser.getNickname()).isEqualTo(savedPost.getUser().getNickname());
    }

}