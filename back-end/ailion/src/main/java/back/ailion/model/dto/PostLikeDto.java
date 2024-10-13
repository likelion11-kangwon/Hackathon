package back.ailion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostLikeDto<T> {

    private boolean likeCheck;
    private T data;
}
