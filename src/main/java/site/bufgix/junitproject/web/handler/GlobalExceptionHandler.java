package site.bufgix.junitproject.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import site.bufgix.junitproject.web.dto.response.CommonRespDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> apiExeption(RuntimeException e) {

        return new ResponseEntity<>(CommonRespDto.builder().code(-1).msg(e.getMessage()).build(),
                HttpStatus.BAD_REQUEST);
    }
}
