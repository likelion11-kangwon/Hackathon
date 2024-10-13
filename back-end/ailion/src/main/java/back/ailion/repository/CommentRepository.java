package back.ailion.repository;

import back.ailion.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT COUNT(r) FROM Reply r WHERE r.comment.id = :commentId")
    int countRepliesByCommentId(@Param("commentId") Long commentId);
}
