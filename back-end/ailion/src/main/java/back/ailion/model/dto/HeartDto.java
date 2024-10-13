package back.ailion.model.dto;

import back.ailion.model.entity.Heart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeartDto {

    private Long userId;
    private Long postId;

    public HeartDto(Heart heart) {
        this.userId = heart.getUser().getId();
        this.postId = heart.getPost().getId();
    }
}