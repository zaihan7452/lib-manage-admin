package com.hanzai.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanzai.app.entity.UserEntity;

import java.util.Optional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Han Zai
 * @since 2025-03-23
 */
public interface IUserService extends IService<UserEntity> {

    /**
     * Retrieve user by username.
     *
     * @param username the username to search for
     * @return user entity optional
     */
    Optional<UserEntity> getUserByUsername(String username);

}
