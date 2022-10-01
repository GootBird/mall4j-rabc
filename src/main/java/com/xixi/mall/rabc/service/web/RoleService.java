package com.xixi.mall.rabc.service.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xixi.mall.api.auth.bo.UserInfoInTokenBo;
import com.xixi.mall.common.core.enums.ResponseEnum;
import com.xixi.mall.common.core.utils.ThrowUtils;
import com.xixi.mall.common.core.webbase.vo.BasePageReqBodyVo;
import com.xixi.mall.common.core.webbase.vo.BasePageRespBodyVo;
import com.xixi.mall.common.database.utils.PageObjConvertUtil;
import com.xixi.mall.common.security.context.AuthUserContext;
import com.xixi.mall.rabc.entity.RoleEntity;
import com.xixi.mall.rabc.entity.RoleMenuEntity;
import com.xixi.mall.rabc.manage.RoleManage;
import com.xixi.mall.rabc.mapper.RoleMapper;
import com.xixi.mall.rabc.mapper.RoleMenuMapper;
import com.xixi.mall.rabc.vo.RoleVo;
import com.xixi.mall.rabc.vo.request.RoleEditReq;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.xixi.mall.common.core.constant.Constant.VOID;

@Service
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleManage roleManage;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    private static final SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();

    public BasePageRespBodyVo<RoleVo> page(BasePageReqBodyVo<?> req) {

        UserInfoInTokenBo curUser = AuthUserContext.get();
        IPage<RoleEntity> queryPage = PageObjConvertUtil.getIPageByPageVo(req.getPageVo());
        IPage<RoleEntity> resPage = roleMapper.selectPage(
                queryPage,
                Wrappers.<RoleEntity>lambdaQuery()
                        .eq(RoleEntity::getTenantId, curUser.getTenantId())
                        .eq(RoleEntity::getBizType, curUser.getSysType())
                        .orderByDesc(RoleEntity::getRoleId)
        );

        BasePageRespBodyVo<RoleVo> resp = new BasePageRespBodyVo<>();
        resp.setPageInfo(PageObjConvertUtil.getPageVoByIPage(resPage));
        resp.setResult(
                resPage.getRecords()
                        .stream()
                        .map(roleEntity -> BeanUtil.toBean(roleEntity, RoleVo.class))
                        .collect(Collectors.toList())
        );

        return resp;
    }

    public List<RoleVo> list() {

        UserInfoInTokenBo curUser = AuthUserContext.get();
        return roleMapper.selectList(
                Wrappers.<RoleEntity>lambdaQuery()
                        .eq(RoleEntity::getTenantId, curUser.getTenantId())
                        .eq(RoleEntity::getBizType, curUser.getSysType())
                        .orderByDesc(RoleEntity::getRoleId)
        )
                .stream()
                .map(roleEntity -> BeanUtil.toBean(roleEntity, RoleVo.class))
                .collect(Collectors.toList());
    }

    public RoleVo getByRoleId(Long roleId) {

        RoleVo roleVo = BeanUtil.toBean(
                roleMapper.selectOne(
                        Wrappers.<RoleEntity>lambdaQuery()
                                .eq(RoleEntity::getRoleId, roleId)
                ),
                RoleVo.class);

        List<RoleMenuEntity> roleMenuEntities = roleMenuMapper.selectList(
                Wrappers.<RoleMenuEntity>lambdaQuery()
                        .eq(RoleMenuEntity::getRoleId, roleId)
        );

        List<Long> menuIds = new ArrayList<>(),
                menuPermissionIds = new ArrayList<>();

        roleMenuEntities.stream()
                .filter(roleMenu -> Objects.nonNull(roleMenu.getMenuId()))
                .forEach(roleMenuEntity -> {
                    menuIds.add(roleMenuEntity.getMenuId());
                    menuPermissionIds.add(roleMenuEntity.getMenuPermissionId());
                });

        roleVo.setMenuIds(menuIds);
        roleVo.setMenuPermissionIds(menuPermissionIds);

        return roleVo;
    }

    public Void save(RoleEditReq req) {
        UserInfoInTokenBo curUser = AuthUserContext.get();
        RoleEntity roleEntity = BeanUtil.toBean(req, RoleEntity.class);

        roleEntity.setRoleId(snowflakeGenerator.next());

        roleEntity.setCreateUserId(curUser.getUserId());
        roleEntity.setBizType(curUser.getSysType());
        roleEntity.setTenantId(curUser.getTenantId());

        roleManage.saveRole(roleEntity, req.getMenuIds(), req.getMenuPermissionIds());
        return VOID;
    }

    public Void update(RoleEditReq req) {

        UserInfoInTokenBo curUser = new UserInfoInTokenBo();
        RoleEntity dbRole = roleMapper.selectById(req.getRoleId());

        if (ObjectUtil.notEqual(dbRole.getBizType(), curUser.getSysType())
                || ObjectUtil.notEqual(dbRole.getTenantId(), curUser.getTenantId())) {
            ThrowUtils.throwErr(ResponseEnum.UNAUTHORIZED);
        }

        RoleEntity roleEntity = BeanUtil.toBean(req, RoleEntity.class);
        roleEntity.setBizType(curUser.getSysType());

        roleManage.update(roleEntity, req.getMenuIds(), req.getMenuPermissionIds());
        return VOID;
    }

    public Void delete(Long roleId) {
        UserInfoInTokenBo curUser = AuthUserContext.get();

        RoleEntity dbRole = roleMapper.selectById(roleId);

        if (ObjectUtil.notEqual(dbRole.getBizType(), curUser.getSysType())
                || ObjectUtil.notEqual(dbRole.getTenantId(), curUser.getTenantId())) {
            ThrowUtils.throwErr(ResponseEnum.UNAUTHORIZED);
        }

        roleManage.delete(roleId, curUser.getSysType());
        return VOID;
    }
}
