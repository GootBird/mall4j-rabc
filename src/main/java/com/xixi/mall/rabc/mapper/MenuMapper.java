package com.xixi.mall.rabc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xixi.mall.rabc.entity.MenuEntity;
import com.xixi.mall.rabc.vo.MenuSimpleVo;
import feign.Param;

import java.util.List;

/**
 * @author FrozenWatermelon
 */
public interface MenuMapper extends BaseMapper<MenuEntity> {

    List<MenuSimpleVo> listWithPermissions(@Param("sysType") Integer sysType);

    List<Long> listMenuIds(@Param("userId") Long userId);

}
