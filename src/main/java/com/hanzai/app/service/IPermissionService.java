package com.hanzai.app.service;

import com.hanzai.app.entity.PermissionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Han Zai
 * @since 2025-03-23
 */
public interface IPermissionService extends IService<PermissionEntity> {

    /**
     * Retrieve user's permission by userId.
     *
     * @param userId the userId to search for
     * @return permission entity list
     */
    List<PermissionEntity> getUserPermissionsByUserId(Long userId);

}
