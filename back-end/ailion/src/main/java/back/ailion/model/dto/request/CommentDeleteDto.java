package back.ailion.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDeleteDto {

    @Positive(message = "null")
    private Long postId;

    @Positive(message = "null")
    private Long commentId;
}