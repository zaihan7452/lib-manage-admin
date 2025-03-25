package com.hanzai.app.ut.util;

import com.hanzai.app.security.PasswordUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilTests {

    private static final String RAW_PASSWORD = "123456";
    private static final String WRONG_RAW_PASSWORD = "654321";

    @Test
    public void testEncodePassword() {
        String encodedPassword = PasswordUtil.encode(RAW_PASSWORD);

        assertNotNull(encodedPassword);
        assertNotEquals(RAW_PASSWORD, encodedPassword);
        assertTrue(encodedPassword.startsWith("$2a$"));
    }

    @Test
    public void testMatchPassword() {
        String encodedPassword = PasswordUtil.encode(RAW_PASSWORD);

        assertTrue(PasswordUtil.match(RAW_PASSWORD, encodedPassword));
        assertFalse(PasswordUtil.match(WRONG_RAW_PASSWORD, encodedPassword));
    }
}
