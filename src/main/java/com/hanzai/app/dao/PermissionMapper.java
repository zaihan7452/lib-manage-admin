package com.hanzai.app.dao;

import com.hanzai.app.entity.PermissionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Han Zai
 * @since 2025-03-23
 */
public interface PermissionMapper extends BaseMapper<PermissionEntity> {

    List<PermissionEntity> getUserPermissionByUserId(Long userId);

}

