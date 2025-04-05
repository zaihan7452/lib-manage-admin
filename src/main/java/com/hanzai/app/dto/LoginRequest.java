package com.hanzai.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Login request")
public class LoginRequest {

    @Schema(description = "Username", example = "username")
    private String username;

    @Schema(description = "Password", example = "password")
    private String password;
}
