package com.hanzai.app.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 * 
 * </p>
 *
 * @author Han Zai
 * @since 2025-04-04
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("github_oauth")
public class GithubOauthEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("github_id")
    private Long githubId;

    @TableField("login")
    private String login;

    @TableField("login_type")
    private String loginType;

    @TableField("access_token")
    private String accessToken;

    @TableField("scope")
    private String scope;

    @TableField("token_type")
    private String tokenType;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField("deleted")
    private Boolean deleted;
}
