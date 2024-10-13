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
public class PostRequestDto {

    @Positive(message = "null")
    private Long userId;

    @Size(min = 2, max = 30, message = "제목을 2~30자 사이로 입력해주세요.")
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @Size(min = 2, max = 100, message = "내용을 2~100자 사이로 입력해주세요.")
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotBlank(message = "카테고리를 설정해주세요.")
    private String category;
}