package back.ailion.repository;

import back.ailion.model.entity.Heart;
import back.ailion.model.entity.Post;
import back.ailion.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByUserAndPost(User user, Post post);

    Optional<Heart> findByPostIdAndUserId(Long postId, Long userId);
}
