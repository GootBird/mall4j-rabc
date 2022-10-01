package com.xixi.mall.rabc.manage;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xixi.mall.rabc.entity.RoleEntity;
import com.xixi.mall.rabc.entity.RoleMenuEntity;
import com.xixi.mall.rabc.entity.UserRoleEntity;
import com.xixi.mall.rabc.mapper.RoleMapper;
import com.xixi.mall.rabc.mapper.RoleMenuMapper;
import com.xixi.mall.rabc.mapper.UserRoleMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class RoleManage {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    public void saveRole(RoleEntity newEntity, List<Long> menuIds, List<Long> menuPermissionIds) {
        roleMapper.insert(newEntity);
        initMenuAndPermission(newEntity.getRoleId(), menuIds, menuPermissionIds);
    }

    public void initMenuAndPermission(Long roleId, List<Long> menuIds, List<Long> menuPermissionIds) {

        List<RoleMenuEntity> insertRoleMenus = new ArrayList<>();

        Objects.requireNonNullElse(menuIds, Collections.emptyList())
                .forEach(menuId -> {
                    RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
                    roleMenuEntity.setRoleId(roleId);
                    roleMenuEntity.setMenuId((Long) menuId);
                    insertRoleMenus.add(roleMenuEntity);
                });

        Objects.requireNonNullElse(menuPermissionIds, Collections.emptyList())
                .forEach(menuPermissionId -> {
                    RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
                    roleMenuEntity.setMenuPermissionId((Long) menuPermissionId);
                    roleMenuEntity.setRoleId(roleId);
                    insertRoleMenus.add(roleMenuEntity);
                });

        roleMenuMapper.batchInsert(insertRoleMenus);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(RoleEntity roleEntity, List<Long> menuIds, List<Long> menuPermissionIds) {

        roleMapper.updateById(roleEntity);

        roleMenuMapper.delete(
                Wrappers.<RoleMenuEntity>lambdaQuery()
                        .eq(RoleMenuEntity::getRoleId, roleEntity.getRoleId())
        );

        initMenuAndPermission(roleEntity.getRoleId(), menuIds, menuPermissionIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long roleId, Integer sysType) {
        roleMapper.delete(
                Wrappers.<RoleEntity>lambdaQuery()
                        .eq(RoleEntity::getRoleId, roleId)
                        .eq(RoleEntity::getBizType, sysType)
        );

        roleMenuMapper.delete(
                Wrappers.<RoleMenuEntity>lambdaQuery()
                        .eq(RoleMenuEntity::getRoleId, roleId)
        );

        userRoleMapper.delete(
                Wrappers.<UserRoleEntity>lambdaQuery()
                        .eq(UserRoleEntity::getRoleId, roleId)
        );
    }
}
