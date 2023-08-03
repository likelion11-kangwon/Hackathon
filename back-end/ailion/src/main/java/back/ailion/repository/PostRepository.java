package back.ailion.repository;

import back.ailion.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p JOIN FETCH p.comments c WHERE p.id = :postId")
    Post findByIdWithComments(@Param("postId") Long postId);

    Page<Post> findByDelCheckFalse(Pageable pageable);
}