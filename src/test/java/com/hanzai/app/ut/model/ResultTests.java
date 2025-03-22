package com.hanzai.app.ut.model;

import com.hanzai.app.constant.LibManageAdminConstant;
import com.hanzai.app.model.ErrorCodeEnum;
import com.hanzai.app.model.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ResultTests {

    @Test
    public void testSuccessWithData() {
        String expectedData = "testSuccessWithData";
        Result<String> result = Result.success(expectedData);

        assertEquals(LibManageAdminConstant.SUCCESS_CODE, result.getCode());
        assertEquals(LibManageAdminConstant.SUCCESS_MESSAGE, result.getMessage());
        assertEquals(expectedData, result.getData());
    }

    @Test
    public void testSuccessWithoutData() {
        Result<Object> result = Result.success();

        assertEquals(LibManageAdminConstant.SUCCESS_CODE, result.getCode());
        assertEquals(LibManageAdminConstant.SUCCESS_MESSAGE, result.getMessage());
        assertNull(result.getData());
    }

    @Test
    public void testSystemErrorWithCodeAndMessage() {
        int exceptedErrorCode = ErrorCodeEnum.SYSTEM_ERROR.getCode();
        String exceptedErrorMessage = ErrorCodeEnum.SYSTEM_ERROR.getMessage();
        Result<Object> result = Result.error(exceptedErrorCode, exceptedErrorMessage);

        assertEquals(ErrorCodeEnum.SYSTEM_ERROR.getCode(), result.getCode());
        assertEquals(ErrorCodeEnum.SYSTEM_ERROR.getMessage(), result.getMessage());
        assertNull(result.getData());
    }

    @Test
    public void testSystemErrorWithEnum() {
        Result<Object> result = Result.error(ErrorCodeEnum.SYSTEM_ERROR);

        assertEquals(ErrorCodeEnum.SYSTEM_ERROR.getCode(), result.getCode());
        assertEquals(ErrorCodeEnum.SYSTEM_ERROR.getMessage(), result.getMessage());
        assertNull(result.getData());
    }
}
