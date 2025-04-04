package com.hanzai.app.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hanzai.app.entity.GithubOauthEntity;
import com.hanzai.app.dao.GithubOauthMapper;
import com.hanzai.app.service.IGithubOauthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Han Zai
 * @since 2025-04-04
 */
@Service
public class GithubOauthServiceImpl extends ServiceImpl<GithubOauthMapper, GithubOauthEntity> implements IGithubOauthService {

    private final GithubOauthMapper githubOauthMapper;

    public GithubOauthServiceImpl(GithubOauthMapper githubOauthMapper) {
        this.githubOauthMapper = githubOauthMapper;
    }

    @Override
    public Optional<GithubOauthEntity> getGithubOauthByLogin(String login) {
        GithubOauthEntity githubOauthEntity = githubOauthMapper.selectOne(Wrappers.<GithubOauthEntity>lambdaQuery()
                .eq(GithubOauthEntity::getLogin, login));
        return Optional.ofNullable(githubOauthEntity);
    }

}
