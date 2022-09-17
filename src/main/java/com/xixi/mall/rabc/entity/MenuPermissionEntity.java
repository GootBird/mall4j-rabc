package com.xixi.mall.rabc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xixi.mall.common.core.webbase.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 菜单资源
 */
@Getter
@Setter
@ToString
@TableName("menu_permission")
public class MenuPermissionEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单资源用户id
     */
    @TableId
    private Long menuPermissionId;

    /**
     * 资源关联菜单
     */
    private Long menuId;

    /**
     * 业务类型 0平台菜单 1 店铺菜单
     */
    private Integer bizType;

    /**
     * 权限对应的编码
     */
    private String permission;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源对应服务器路径
     */
    private String uri;

    /**
     * 请求方法 1.GET 2.POST 3.PUT 4.DELETE
     */
    private Integer method;
}
