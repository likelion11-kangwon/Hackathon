package back.ailion.exception.custom;

import back.ailion.exception.BaseExceptionCode;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final BaseExceptionCode baseExceptionCode;

    public NotFoundException(BaseExceptionCode baseExceptionCode) {
        this.baseExceptionCode = baseExceptionCode;
    }

}
