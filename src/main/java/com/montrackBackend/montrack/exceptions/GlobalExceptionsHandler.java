package com.montrackBackend.montrack.exceptions;

import com.montrackBackend.montrack.response.Response;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.UnknownHostException;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionsHandler {

    @ExceptionHandler(NotExistException.class)
    public final ResponseEntity<Response<String>>handleNotExistException(NotExistException ex){
        log.error(ex.getMessage(), ex);
        return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Response<String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        log.error(ex.getMessage(), ex);
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Unable to process the request", errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Response<String>> handleConstraintViolationExceptions(ConstraintViolationException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        String errorMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
        return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errorMessage);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public final ResponseEntity<Response<String>> handleTransactionSystemException(TransactionSystemException ex, WebRequest request) {
        log.error("TransactionSystemException: " + ex.getMessage(), ex);

        Throwable cause = ex.getRootCause();
        if (cause instanceof ConstraintViolationException) {
            return handleConstraintViolationExceptions((ConstraintViolationException) cause, request);
        }
        // Add more specific handling for other root causes if needed

        return Response.failedResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Transaction failed", cause != null ? cause.getLocalizedMessage() : ex.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response<String>> handleAllExceptions(Exception ex) {

        log.error(ex.getMessage(), ex.getCause(), ex);

        if (ex.getCause() instanceof UnknownHostException) {
            return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Unable to process the unknownhostexception", ex.getLocalizedMessage());
        }

        return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Unable to process " + ex.getMessage(), ex.getClass().getName());
    }
}
