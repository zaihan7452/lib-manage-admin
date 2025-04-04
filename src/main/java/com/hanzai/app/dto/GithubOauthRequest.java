package com.hanzai.app.dto;

import lombok.Data;

@Data
public class GithubOauthRequest {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;

}
