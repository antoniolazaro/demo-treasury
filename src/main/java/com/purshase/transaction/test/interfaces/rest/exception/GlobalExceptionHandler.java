package com.purshase.transaction.test.interfaces.rest.exception;

import com.purshase.transaction.test.domain.exceptions.AbstractPurshaseException;
import com.purshase.transaction.test.domain.exceptions.badgateway.AbstractBadGatewayException;
import com.purshase.transaction.test.domain.exceptions.badrequest.AbstractBadRequestException;
import com.purshase.transaction.test.domain.exceptions.notfound.AbstractNotFoundException;
import com.purshase.transaction.test.domain.model.dto.ExceptionResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {AbstractBadGatewayException.class})
    public final ResponseEntity<Object> handleAbstractBadGatewayException(AbstractBadGatewayException ex, WebRequest request) {
        return handleExceptions(ex.getMessage(), HttpStatus.BAD_GATEWAY);
    }
    @ExceptionHandler(value = {AbstractBadRequestException.class})
    public final ResponseEntity<Object> handleAbstractBadRequestException(AbstractBadRequestException ex, WebRequest request) {
        return handleExceptions(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {AbstractNotFoundException.class})
    public final ResponseEntity<Object> handleAbstractNotFoundException(AbstractNotFoundException ex, WebRequest request) {
        return handleExceptions(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
                                                                           WebRequest request) {
        return handleExceptions(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {AbstractPurshaseException.class})
    public final ResponseEntity<Object> handleAbstractPurshaseException(AbstractPurshaseException ex, WebRequest request) {
        return handleExceptions(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = {RuntimeException.class})
    public final ResponseEntity<Object> handleRuntimeExceptions(RuntimeException ex, WebRequest request) {
        return handleExceptions(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private ResponseEntity<Object> handleExceptions(String message, HttpStatus httpStatus) {
        var exceptionResponse = ExceptionResponse.builder()
                .error(httpStatus.getReasonPhrase())
                .time(LocalDateTime.now())
                .description(message)
                .build();
        log.error(exceptionResponse.toString(), message);
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }
}
