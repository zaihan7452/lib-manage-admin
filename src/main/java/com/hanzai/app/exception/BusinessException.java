package com.hanzai.app.exception;

import com.hanzai.app.model.ErrorCodeEnum;
import lombok.Getter;

/**
 * Custom business exception class.
 * Used to throw business-specific errors.
 */
@Getter
public class BusinessException extends RuntimeException {

    private final int code;
    private final String message;

    /**
     * Constructor that accepts an ErrorCodeEnum.
     *
     * @param errorCode The ErrorCodeEnum representing the error
     */
    public BusinessException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    /**
     * Constructor that accepts custom error code and message.
     *
     * @param code    Custom error code
     * @param message Custom error message
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
