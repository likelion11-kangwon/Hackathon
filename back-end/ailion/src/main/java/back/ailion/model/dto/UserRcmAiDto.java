package back.ailion.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserRcmAiDto{
    private String username;
    private List<AiInfoResponseDto> aiInfoResponseDtos;

    public UserRcmAiDto(String username, List<AiInfoResponseDto> aiInfoResponseDtos) {
        this.username = username;
        this.aiInfoResponseDtos = aiInfoResponseDtos;
    }
}
