package back.ailion.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDeleteDto {

    @Positive(message = "null")
    private Long favoriteId;

    @Positive(message = "null")
    private Long aiInfoId;
}