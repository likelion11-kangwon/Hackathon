package back.ailion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPageDto {

    private Page<PostDto> posts;
    private List<AiInfoResponseDto> aiInfos;
    List<PostDto> bestPosts;
}