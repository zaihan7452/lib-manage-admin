package com.hanzai.app.model;

import com.hanzai.app.constant.LibManageAdminConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * Generic result wrapper for API responses.
 * Contains status code, message, and optional data payload.
 *
 * @param <T> type of the data payload
 */
@Getter
@Setter
public class Result<T> {
    private int code;
    private String message;
    private T data;

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * Create a successful result with data.
     *
     * @param data data payload
     * @param <T>  type of the data
     * @return Result with success status and data
     */
    private static <T> Result<T> ofSuccess(T data) {
        return new Result<>(LibManageAdminConstant.SUCCESS_CODE, LibManageAdminConstant.SUCCESS_MESSAGE, data);
    }

    /**
     * Create a successful result without data.
     *
     * @param <T> type of the data
     * @return Result with success status and no data
     */
    public static <T> Result<T> success() {
        return ofSuccess(null);
    }

    /**
     * Alias for {@link #ofSuccess(Object)}.
     *
     * @param data data payload
     * @param <T>  type of the data
     * @return Result with success status and data
     */
    public static <T> Result<T> success(T data) {
        return ofSuccess(data);
    }

    /**
     * Create an error result with code and message.
     *
     * @param code    error code
     * @param message error message
     * @param <T>     type of the data
     * @return Result with error status
     */
    private static <T> Result<T> ofError(int code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * Create an error result using predefined ErrorCodeEnum.
     *
     * @param errorCode predefined error code enum
     * @param <T>       type of the data
     * @return Result with error status
     */
    private static <T> Result<T> ofError(ErrorCodeEnum errorCode) {
        return new Result<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * Alias for {@link #ofError(int, String)}.
     *
     * @param code    error code
     * @param message error message
     * @param <T>     type of the data
     * @return Result with error status
     */
    public static <T> Result<T> error(int code, String message) {
        return ofError(code, message);
    }

    /**
     * Alias for {@link #ofError(ErrorCodeEnum)}.
     *
     * @param errorCode predefined error code enum
     * @param <T>       type of the data
     * @return Result with error status
     */
    public static <T> Result<T> error(ErrorCodeEnum errorCode) {
        return ofError(errorCode);
    }
}
