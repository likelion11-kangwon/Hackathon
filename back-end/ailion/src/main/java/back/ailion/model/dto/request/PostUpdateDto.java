package back.ailion.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateDto {

    @Positive(message = "null")
    private Long postId;

    @Size(min = 2, max = 30, message = "제목을 2~30자 사이로 입력해주세요.")
    private String title;

    @Size(min = 2, max = 100, message = "내용을 2~100자 사이로 입력해주세요.")
    private String content;

    private String category;
}