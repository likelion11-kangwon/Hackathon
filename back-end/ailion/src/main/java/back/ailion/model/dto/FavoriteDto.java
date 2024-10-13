package back.ailion.model.dto;

import back.ailion.model.entity.Favorite;
import back.ailion.model.entity.Heart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDto {

    private Long favoriteId;
    private Long userId;
    private Long aiInfoId;

    public FavoriteDto(Favorite favorite) {
        this.favoriteId = favorite.getId();
        this.userId = favorite.getUser().getId();
        this.aiInfoId = favorite.getAiInfo().getId();
    }
}
