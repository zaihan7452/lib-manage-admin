package com.hanzai.app.exception;

import com.hanzai.app.model.ErrorCodeEnum;
import com.hanzai.app.model.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle general system exceptions (unexpected errors).
     *
     * @param ex Exception thrown
     * @return a Result with generic error details
     */
    @ExceptionHandler(Exception.class)
    public Result<Object> handleSystemException(Exception ex) {
        log.error("System exception occurred: {}", ex.getMessage(), ex);
        return Result.error(ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMessage());
    }

    /**
     * Handle custom BusinessException and return a custom response.
     *
     * @param ex BusinessException thrown by business logic
     * @return a Result with error details
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Object> handleBusinessException(BusinessException ex) {
        log.error("Business exception occurred: {}", ex.getMessage(), ex);
        return Result.error(ex.getCode(), ex.getMessage());
    }

    /**
     * Handle validation exceptions (invalid method arguments, such as incorrect request data).
     * This exception is thrown when a method parameter fails validation (e.g., using @Valid or @Validated).
     *
     * @param ex MethodArgumentNotValidException
     * @return a Result with validation error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("Method argument not valid exception occurred: {}", message, ex);
        return Result.error(ErrorCodeEnum.BAD_REQUEST.getCode(), "Method argument not valid failed: " + message);
    }

    /**
     * Handle constraint violation exceptions (e.g., for invalid values in object fields).
     * This exception occurs when the constraints set by JSR-303 annotations like @NotNull, @Size, etc., are violated.
     *
     * @param ex ConstraintViolationException
     * @return a Result with constraint violation error details
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        log.error("Constraint violation exception occurred: {}", message, ex);
        return Result.error(ErrorCodeEnum.BAD_REQUEST.getCode(), "Constraint violation failed: " + message);
    }

    /**
     * Handle binding exceptions (e.g., when request parameters cannot be bound to method parameters).
     * This happens when the request body or parameters can't be correctly mapped to a method's parameters.
     *
     * @param ex BindException
     * @return a Result with binding error details
     */
    @ExceptionHandler(BindException.class)
    public Result<Object> handleBindException(BindException ex) {
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("Bind exception occurred: {}", message, ex);
        return Result.error(ErrorCodeEnum.BAD_REQUEST.getCode(), "Bind failed: " + message);
    }

}
