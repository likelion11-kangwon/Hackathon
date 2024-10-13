package back.ailion.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class SingleResponseDto<T> {
    private T data;

    public SingleResponseDto(T data){
        this.data = data;
    }
}
