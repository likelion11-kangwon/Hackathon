package back.ailion.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDeleteDto {

    @Positive(message = "null")
    private Long replyId;

    @Positive(message = "null")
    private Long postId;
}