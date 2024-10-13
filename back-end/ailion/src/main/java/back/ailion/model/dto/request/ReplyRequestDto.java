package back.ailion.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRequestDto {

    @Positive(message = "null")
    private Long userId;
    @Positive(message = "null")
    private Long commentId;
    @Positive(message = "null")
    private Long postId;

    @Size(min = 2, max = 50, message = "내용을 2~50자 사이로 입력해주세요.")
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
