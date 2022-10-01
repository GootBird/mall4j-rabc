package com.xixi.mall.rabc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xixi.mall.rabc.entity.RoleMenuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenuEntity> {

    void batchInsert(@Param("list") List<RoleMenuEntity> roleMenuEntities);

}
