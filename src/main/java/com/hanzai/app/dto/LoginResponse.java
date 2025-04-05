package com.hanzai.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Login response")
public class LoginResponse {

    @Schema(description = "JWT token")
    private String token;
}
