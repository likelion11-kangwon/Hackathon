package back.ailion.repository;

import back.ailion.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments c WHERE p.id = :postId")
    Post findByIdWithComments(@Param("postId") Long postId);

    Page<Post> findByCategory(String category, Pageable pageable);

    Page<Post> findByDelCheckFalse(Pageable pageable);

    @Query("SELECT p FROM Post p ORDER BY p.likeCount DESC")
    List<Post> findBestPostsByLike(Pageable pageable);

    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount + 1 WHERE p = :selectedPost")
    void addLikeCount(@Param("selectedPost") Post post);

    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount - 1 WHERE p = :selectedPost")
    void subLikeCount(@Param("selectedPost") Post post);

    Page<Post> findByTitleContainingAndWriterContainingAndDelCheckFalse(String title, String writer, Pageable pageable);
}