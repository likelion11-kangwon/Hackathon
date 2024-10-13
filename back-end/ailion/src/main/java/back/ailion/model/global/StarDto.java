package back.ailion.model.global;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class StarDto implements Star{

    public Long id;
    public Integer stars;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Integer stars() {
        return stars;
    }

    public StarDto(Long id, Integer stars) {
        this.id = id;
        this.stars = stars;
    }
}
