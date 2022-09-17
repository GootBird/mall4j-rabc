package com.xixi.mall.rabc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xixi.mall.rabc.entity.MenuPermissionEntity;

import java.util.List;

public interface MenuPermissionMapper extends BaseMapper<MenuPermissionEntity> {

    List<String> listByUserIdAndSysType(Long userId, Integer sysType);

}
