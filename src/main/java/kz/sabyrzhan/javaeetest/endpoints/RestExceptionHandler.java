package kz.sabyrzhan.javaeetest.endpoints;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "kz.sabyrzhan.javaeetest")
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Error: {}", e.toString(), e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception exc) {
        log.error("Generic exception thrown: {}", exc.toString(), exc);
        return new ErrorResponse(exc.getMessage());
    }

    @Data
    @AllArgsConstructor
    public static class ErrorResponse {
        private String message;
    }
}