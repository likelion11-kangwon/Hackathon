package back.ailion.exception.custom;

import back.ailion.exception.BaseExceptionCode;
import lombok.Getter;

@Getter
public class FileException extends RuntimeException{

    private final BaseExceptionCode baseExceptionCode;

    public FileException(BaseExceptionCode baseExceptionCode) {
        this.baseExceptionCode = baseExceptionCode;
    }

    public FileException(BaseExceptionCode baseExceptionCode, Throwable cause) {
        super(cause);
        this.baseExceptionCode = baseExceptionCode;
    }
}