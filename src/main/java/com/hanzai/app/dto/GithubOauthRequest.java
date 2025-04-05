package com.hanzai.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "GitHub OAuth request parameters")
public class GithubOauthRequest {

    @Schema(description = "Client ID for GitHub OAuth")
    private String client_id;

    @Schema(description = "Client secret for GitHub OAuth")
    private String client_secret;

    @Schema(description = "Authorization code received from GitHub")
    private String code;

    @Schema(description = "Redirect URI for GitHub OAuth")
    private String redirect_uri;

}
