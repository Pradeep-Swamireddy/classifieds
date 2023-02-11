package com.deloitte.classifieds.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static java.lang.String.format;

@Log4j2
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    // Exception method for Id Not Found with Status code 404.
    @ExceptionHandler(ClassifiedNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(ClassifiedNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        final List<String> validationList = ex.getBindingResult().getAllErrors().stream().map(o -> format("%s field %s", ((FieldError) o).getField(), o.getDefaultMessage())).toList();
        return new ResponseEntity<>(validationList, status);
    }
}
