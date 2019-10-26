package org.psc.playground;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandlerAutoConfiguration {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception, HttpServletRequest httpServletRequest) {
        log.error("an exception occurred with the following message: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
