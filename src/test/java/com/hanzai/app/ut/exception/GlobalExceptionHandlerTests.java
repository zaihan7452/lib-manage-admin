package com.hanzai.app.ut.exception;

import com.hanzai.app.exception.BusinessException;
import com.hanzai.app.exception.GlobalExceptionHandler;
import com.hanzai.app.model.ErrorCodeEnum;
import com.hanzai.app.model.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTests {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleSystemException() {
        Exception ex = new Exception();

        Result<Object> result = globalExceptionHandler.handleSystemException(ex);

        assertEquals(ErrorCodeEnum.SYSTEM_ERROR.getCode(), result.getCode());
        assertEquals(ErrorCodeEnum.SYSTEM_ERROR.getMessage(), result.getMessage());
    }

    @Test
    public void testHandleBusinessException() {
        BusinessException ex = new BusinessException(ErrorCodeEnum.BUSINESS_ERROR);

        Result<Object> result = globalExceptionHandler.handleBusinessException(ex);

        assertEquals(ErrorCodeEnum.BUSINESS_ERROR.getCode(), result.getCode());
        assertEquals(ErrorCodeEnum.BUSINESS_ERROR.getMessage(), result.getMessage());
    }

    @Test
    public void testHandleMethodArgumentNotValidException() {
        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(ex.getBindingResult()).thenReturn(bindingResult);
        Mockito.when(bindingResult.getAllErrors())
                .thenReturn(List.of(new FieldError("object", "field", "Field cannot be empty")));

        Result<Object> result = globalExceptionHandler.handleMethodArgumentNotValidException(ex);

        assertEquals(ErrorCodeEnum.BAD_REQUEST.getCode(), result.getCode());
        assertEquals("Method argument not valid failed: Field cannot be empty", result.getMessage());
    }

    @Test
    public void testHandleConstraintViolationException() {
        ConstraintViolationException ex = Mockito.mock(ConstraintViolationException.class);
        ConstraintViolation<Object> violation = Mockito.mock(ConstraintViolation.class);
        Mockito.when(violation.getMessage()).thenReturn("Field must not be null");
        Mockito.when(ex.getConstraintViolations()).thenReturn(Set.of(violation));

        Result<Object> result = globalExceptionHandler.handleConstraintViolationException(ex);

        assertEquals(ErrorCodeEnum.BAD_REQUEST.getCode(), result.getCode());
        assertEquals("Constraint violation failed: Field must not be null", result.getMessage());
    }

    @Test
    public void testHandleBindException() {
        BindException ex = Mockito.mock(BindException.class);
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(ex.getBindingResult()).thenReturn(bindingResult);
        Mockito.when(bindingResult.getAllErrors())
                .thenReturn(List.of(new FieldError("object", "field", "bind failed")));

        Result<Object> result = globalExceptionHandler.handleBindException(ex);

        assertEquals(ErrorCodeEnum.BAD_REQUEST.getCode(), result.getCode());
        assertEquals("Bind failed: bind failed", result.getMessage());
    }

}
