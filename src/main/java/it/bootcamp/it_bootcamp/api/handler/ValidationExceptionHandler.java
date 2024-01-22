package it.bootcamp.it_bootcamp.api.handler;

import it.bootcamp.it_bootcamp.dto.error.ApiError;
import it.bootcamp.it_bootcamp.service.exception.UserCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice(basePackages = "it.bootcamp.it_bootcamp.api.controller")
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> errors = getErrorMessages(ex);
        ApiError apiError = ApiError.builder()
                .httpStatus(BAD_REQUEST)
                .message(ex.getLocalizedMessage())
                .errors(errors)
                .build();

        log.info(apiError.toString());

        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    private static List<String> getErrorMessages(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for(FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return errors;
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<Object> handleUserCreationException(UserCreationException ex,
                                                              HttpHeaders headers,
                                                              HttpStatusCode status,
                                                              WebRequest request) {

        ApiError apiError = ApiError.builder()
                .httpStatus(INTERNAL_SERVER_ERROR)
                .errors(List.of(ex.getMessage()))
                .message(ex.getLocalizedMessage())
                .build();

        log.info(apiError.toString());

        return handleExceptionInternal(ex, apiError, headers, status, request);
    }
}
