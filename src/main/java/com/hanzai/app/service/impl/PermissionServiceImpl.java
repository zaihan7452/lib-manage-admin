package com.hanzai.app.service.impl;

import com.hanzai.app.entity.PermissionEntity;
import com.hanzai.app.dao.PermissionMapper;
import com.hanzai.app.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Han Zai
 * @since 2025-03-23
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements IPermissionService {

    private final PermissionMapper permissionMapper;

    public PermissionServiceImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<PermissionEntity> getUserPermissionsByUserId(Long userId) {
        return permissionMapper.getUserPermissionByUserId(userId);
    }

}
