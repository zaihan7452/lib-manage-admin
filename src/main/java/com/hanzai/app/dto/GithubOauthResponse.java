package com.hanzai.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "GitHub OAuth Response")
public class GithubOauthResponse {

    @Schema(description = "Scope")
    private String scope;

    @Schema(description = "Token Type")
    private String token_type;

    @Schema(description = "Access Token")
    private String access_token;

}
