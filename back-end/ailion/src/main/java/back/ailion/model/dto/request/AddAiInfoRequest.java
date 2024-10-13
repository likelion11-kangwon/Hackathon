package back.ailion.model.dto.request;

import back.ailion.model.entity.AiInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddAiInfoRequest {
    private String name;
    private String content;
    private String url;
    private String category;

    public AiInfo toEntity() {
        return AiInfo.builder()
                .name(name)
                .content(content)
                .url(url)
                .category(category)
                .build();
    }
}

