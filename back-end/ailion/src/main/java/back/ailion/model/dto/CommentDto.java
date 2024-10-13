package back.ailion.model.dto;

import back.ailion.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long userId;
    private Long commentId;

    private String content;
    private String writer;

    private boolean delCheck;

    private LocalDateTime createdDate;

    private List<ReplyDto> replies;

    public CommentDto(Comment comment) {
        this.userId = comment.getUser().getId();
        this.commentId = comment.getId();
        this.createdDate = comment.getCreatedDate();
        this.content = comment.getContent();
        this.writer = comment.getWriter();
        this.delCheck = comment.isDelCheck();
    }
}