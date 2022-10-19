package com.xixi.mall.rabc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xixi.mall.rabc.entity.MenuPermissionEntity;
import com.xixi.mall.rabc.vo.MenuPermissionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuPermissionMapper extends BaseMapper<MenuPermissionEntity> {

    List<String> listByUserIdAndSysType(Long userId, Integer sysType);

    IPage<MenuPermissionVo> pageQueryMenu(IPage<MenuPermissionVo> queryPage, @Param("sysType") Integer sysType);

}
