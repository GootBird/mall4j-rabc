package com.xixi.mall.rabc.service.web;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xixi.mall.api.auth.bo.UserInfoInTokenBo;
import com.xixi.mall.common.core.utils.BooleanUtil;
import com.xixi.mall.common.core.utils.ThrowUtils;
import com.xixi.mall.common.core.webbase.vo.BasePageReqBodyVo;
import com.xixi.mall.common.core.webbase.vo.BasePageRespBodyVo;
import com.xixi.mall.common.security.context.AuthUserContext;
import com.xixi.mall.rabc.entity.MenuPermissionEntity;
import com.xixi.mall.rabc.manage.MenuPermissionManage;
import com.xixi.mall.rabc.mapper.MenuPermissionMapper;
import com.xixi.mall.rabc.vo.MenuPermissionVo;
import com.xixi.mall.rabc.vo.request.MenuPermissionEditReq;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.xixi.mall.common.core.constant.Constant.VOID;

@Service
public class MenuPermissionService {

    @Resource
    private MenuPermissionMapper menuPermissionMapper;

    @Resource
    private MenuPermissionManage menuPermissionManage;


    public List<MenuPermissionVo> listByMenuId(Long menuId) {

        return menuPermissionMapper.selectList(
                Wrappers.<MenuPermissionEntity>lambdaQuery()
                        .eq(MenuPermissionEntity::getMenuId, menuId)
        )
                .stream()
                .map(val -> BeanUtil.toBean(val, MenuPermissionVo.class))
                .collect(Collectors.toList());
    }

    public MenuPermissionVo getByMenuPermissionId(Long menuPermissionId) {
        return BeanUtil.toBean(
                menuPermissionMapper.selectOne(
                        Wrappers.<MenuPermissionEntity>lambdaQuery()
                                .eq(MenuPermissionEntity::getMenuPermissionId, menuPermissionId)
                ),
                MenuPermissionVo.class
        );
    }

    public Void save(MenuPermissionEditReq req) {
        Integer sysType = AuthUserContext.get().getSysType();

        ThrowUtils.checkIsTrue(
                Objects.isNull(
                        menuPermissionManage.queryByPermission(req.getPermission(), sysType)
                ),
                "权限编码已存在，请勿重复添加"
        );

        MenuPermissionEntity newMenu = BeanUtil.toBean(req, MenuPermissionEntity.class);
        UserInfoInTokenBo userInfoInTokenBO = AuthUserContext.get();

        newMenu.setMenuPermissionId(null);
        newMenu.setBizType(userInfoInTokenBO.getSysType());

        menuPermissionManage.save(newMenu);
        return VOID;
    }

    public Void update(MenuPermissionEditReq req) {
        Integer sysType = AuthUserContext.get().getSysType();
        String permission = req.getPermission();

        ThrowUtils.checkIsTrue(
                menuPermissionMapper.selectCount(
                        Wrappers.<MenuPermissionEntity>lambdaQuery()
                                .eq(MenuPermissionEntity::getPermission, permission)
                                .eq(Objects.nonNull(sysType), MenuPermissionEntity::getBizType, sysType)
                                .ne(MenuPermissionEntity::getMenuPermissionId, req.getMenuPermissionId())
                ) < 1,
                "权限编码已存在，请勿重复添加"
        );

        MenuPermissionEntity newEntity = BeanUtil.toBean(req, MenuPermissionEntity.class);
        newEntity.setBizType(sysType);

        menuPermissionManage.updateById(newEntity);
        return VOID;
    }

    public Void delete(Long menuPermissionId) {
        Integer sysType = AuthUserContext.get().getSysType();
        menuPermissionManage.delete(menuPermissionId, sysType);
        return VOID;
    }

    public List<String> list() {
        UserInfoInTokenBo curUser = AuthUserContext.get();
        Integer sysType = curUser.getSysType();

        return BooleanUtil.isTrue(curUser.getIsAdmin())
                ? menuPermissionManage.listAll(sysType)
                : menuPermissionManage.listByUserIdAndSysType(curUser.getUserId(), sysType);
    }

    public BasePageRespBodyVo<MenuPermissionVo> page(BasePageReqBodyVo<?> req) {
        return null;
    }
}
