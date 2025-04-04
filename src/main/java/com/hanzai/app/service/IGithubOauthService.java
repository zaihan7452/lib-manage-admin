package com.hanzai.app.service;

import com.hanzai.app.entity.GithubOauthEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Optional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Han Zai
 * @since 2025-04-04
 */
public interface IGithubOauthService extends IService<GithubOauthEntity> {

    /**
     * Retrieve the GitHub OAuth entity by login.
     *
     * @param login The login to search for.
     * @return An optional containing the GithubOauthEntity if successful, otherwise empty.
     */
    Optional<GithubOauthEntity> getGithubOauthByLogin(String login);

}
