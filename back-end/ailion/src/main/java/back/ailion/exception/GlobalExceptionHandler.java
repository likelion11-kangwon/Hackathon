package back.ailion.exception;

import back.ailion.exception.custom.AlreadyExecutedException;
import back.ailion.exception.custom.FileException;
import back.ailion.exception.custom.NonPositiveException;
import back.ailion.exception.custom.NotFoundException;
import com.nimbusds.oauth2.sdk.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> baseExceptionHandler(BaseException e) {
        return ResponseEntity
                .status(e.getBaseExceptionCode().getHttpStatusCode())
                .body(new ExceptionResponse(e.getBaseExceptionCode().getHttpStatusCode(), e.getBaseExceptionCode().getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundExceptionHandler(NotFoundException e) {
        return ResponseEntity
                .status(e.getBaseExceptionCode().getHttpStatusCode())
                .body(new ExceptionResponse(e.getBaseExceptionCode().getHttpStatusCode(), e.getBaseExceptionCode().getMessage()));
    }

    @ExceptionHandler(AlreadyExecutedException.class)
    public ResponseEntity<ExceptionResponse> alreadyExecutedExceptionHandler(AlreadyExecutedException e) {
        return ResponseEntity
                .status(e.getBaseExceptionCode().getHttpStatusCode())
                .body(new ExceptionResponse(e.getBaseExceptionCode().getHttpStatusCode(), e.getBaseExceptionCode().getMessage()));
    }

    /**
     * 파일 업로드 용량 초과시 발생
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<ExceptionResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {

        log.info("handleMaxUploadSizeExceededException", e);

        return ResponseEntity
                .status(BaseExceptionCode.FILE_SIZE_EXCEED.getHttpStatusCode())
                .body(new ExceptionResponse(BaseExceptionCode.FILE_SIZE_EXCEED.getHttpStatusCode(), BaseExceptionCode.FILE_SIZE_EXCEED.getMessage()));
    }

    /**
     * 파일 업로드 관련 예외 처리
     */
    @ExceptionHandler(FileException.class)
    protected ResponseEntity<ExceptionResponse> handleFileException(FileException e) {

        log.info("handleFileException", e);

        return ResponseEntity
                .status(e.getBaseExceptionCode().getHttpStatusCode())
                .body(new ExceptionResponse(e.getBaseExceptionCode().getHttpStatusCode(), e.getBaseExceptionCode().getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(NonPositiveException.class)
    public ResponseEntity<ExceptionResponse> handleNonPositiveException(NonPositiveException e){
        return ResponseEntity
                .status(e.getBaseExceptionCode().getHttpStatusCode())
                .body(new ExceptionResponse(e.getBaseExceptionCode().getHttpStatusCode(), e.getBaseExceptionCode().getMessage()));
    }
}
