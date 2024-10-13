package back.ailion.model.dto;

import back.ailion.model.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {

    private Long userId;
    private Long replyId;

    private String content;
    private String writer;

    private boolean delCheck;

    private LocalDateTime createdDate;

    public ReplyDto(Reply reply) {
        this.userId = reply.getUser().getId();
        this.replyId = reply.getId();
        this.createdDate = reply.getCreatedDate();
        this.content = reply.getContent();
        this.writer = reply.getWriter();
        this.delCheck = reply.isDelCheck();
    }
}