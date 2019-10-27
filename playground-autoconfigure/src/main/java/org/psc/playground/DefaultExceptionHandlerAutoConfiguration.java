package org.psc.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@ControllerAdvice
@ConditionalOnProperty(prefix = "playground", name = "enable-exception-handler", havingValue = "true", matchIfMissing = true)
public class DefaultExceptionHandlerAutoConfiguration {

    private final PlaygroundAutoConfigurationProperties playgroundAutoConfigurationProperties;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception exception,
                                                               HttpServletRequest httpServletRequest) {
        log.error("{}", playgroundAutoConfigurationProperties.isEnableExceptionHandler());
        log.error("an exception occurred with the following message: {}", exception.getMessage());
        return new ResponseEntity<>(Map.of("error", exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
