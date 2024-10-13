package back.ailion.exception.custom;

import back.ailion.exception.BaseExceptionCode;
import lombok.Getter;

@Getter
public class NonPositiveException extends RuntimeException {

    private final BaseExceptionCode baseExceptionCode;

    public NonPositiveException(BaseExceptionCode baseExceptionCode) {
        this.baseExceptionCode = baseExceptionCode;
    }
}
