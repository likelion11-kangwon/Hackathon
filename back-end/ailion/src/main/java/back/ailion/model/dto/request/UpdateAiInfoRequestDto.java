package back.ailion.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateAiInfoRequestDto {
    private String name;
    private String content;
    private String url;
    private String category;


}
