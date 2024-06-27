package lecture.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> bindException(BindException e){
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();

        Map<String, String> collect = allErrors.stream()
                .map(error -> (FieldError) error)
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return ApiResponse.of(HttpStatus.BAD_REQUEST,"바인딩 오류",collect);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<Object> runTimeException(RuntimeException e){
        return ApiResponse.of(HttpStatus.BAD_REQUEST,e.getMessage(),null);
    }
}
