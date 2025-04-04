package com.hanzai.app.dto;

import lombok.Data;

@Data
public class GithubOauthResponse {

    private String scope;

    private String token_type;

    private String access_token;

}
