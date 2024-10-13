package back.ailion.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
    private final BaseExceptionCode baseExceptionCode;

    public BaseException(BaseExceptionCode baseExceptionCode) {
        this.baseExceptionCode = baseExceptionCode;
    }

}
