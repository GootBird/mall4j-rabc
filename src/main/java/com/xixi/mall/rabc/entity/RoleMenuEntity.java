package com.xixi.mall.rabc.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.xixi.mall.common.core.webbase.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色与菜单对应关系
 */
@Getter
@Setter
@ToString
@TableName("role_menu")
public class RoleMenuEntity extends BaseEntity {

    /**
     * 关联id
     */
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 菜单资源用户id
     */
    private Long menuPermissionId;

}
