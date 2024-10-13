package back.ailion.exception.custom;

import back.ailion.exception.BaseExceptionCode;
import lombok.Getter;

@Getter
public class AlreadyExecutedException extends RuntimeException {

    private final BaseExceptionCode baseExceptionCode;

    public AlreadyExecutedException(BaseExceptionCode baseExceptionCode) {
        this.baseExceptionCode = baseExceptionCode;
    }

}
