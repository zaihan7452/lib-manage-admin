package com.hanzai.app.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Enumeration of standard error codes and messages.
 * Provides lookup utilities to retrieve enum by code.
 */
@Getter
public enum ErrorCodeEnum {

    SYSTEM_ERROR(500, "Internal Server Error"),
    BAD_REQUEST(400, "Bad Request"),
    BUSINESS_ERROR(1001, "Business Exception");

    /**
     * -- GETTER --
     *  Retrieve the numeric code of the error.
     *
     * @return error code as int
     */
    private final int code;
    /**
     * -- GETTER --
     *  Retrieve the human-readable message of the error.
     *
     * @return error message as String
     */
    private final String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // ======= Static Lookup Map =======
    private static final Map<Integer, ErrorCodeEnum> codeMap = new HashMap<>();

    static {
        for (ErrorCodeEnum value : values()) {
            codeMap.put(value.code, value);
        }
    }

    // ======= Lookup Methods =======

    /**
     * Retrieve ErrorCodeEnum by code.
     * Returns Optional.empty() if not found.
     *
     * @param code error code
     * @return Optional of ErrorCodeEnum
     */
    public static Optional<ErrorCodeEnum> fromCode(int code) {
        return Optional.ofNullable(codeMap.get(code));
    }

    /**
     * Retrieve ErrorCodeEnum by code, or return the specified default if not found.
     *
     * @param code         error code
     * @param defaultEnum  default enum to return if code is not found
     * @return ErrorCodeEnum
     */
    public static ErrorCodeEnum fromCodeOrDefault(int code, ErrorCodeEnum defaultEnum) {
        return codeMap.getOrDefault(code, defaultEnum);
    }

    /**
     * Retrieve ErrorCodeEnum by code, or throw exception if not found.
     *
     * @param code error code
     * @return ErrorCodeEnum
     * @throws IllegalArgumentException if code is invalid
     */
    public static ErrorCodeEnum fromCodeOrThrow(int code) {
        ErrorCodeEnum result = codeMap.get(code);
        if (result == null) {
            throw new IllegalArgumentException("Invalid ErrorCode: " + code);
        }
        return result;
    }
}
