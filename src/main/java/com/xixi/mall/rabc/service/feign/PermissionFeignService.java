package com.xixi.mall.rabc.service.feign;

import com.xixi.mall.api.rabc.bo.UriPermissionBo;
import com.xixi.mall.common.core.enums.ResponseEnum;
import com.xixi.mall.common.core.utils.BooleanUtil;
import com.xixi.mall.common.core.utils.ThrowUtils;
import com.xixi.mall.rabc.manage.MenuPermissionManage;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class PermissionFeignService {

    @Resource
    private MenuPermissionManage menuPermissionManage;

    public Boolean checkPermission(Long userId,
                                   Integer sysType,
                                   String uri,
                                   Integer isAdmin,
                                   Integer method) {


        List<String> userPermissions = BooleanUtil.isTrue(isAdmin)
                ? menuPermissionManage.listAll(sysType)
                : menuPermissionManage.listByUserIdAndSysType(userId, sysType);

        List<UriPermissionBo> uriPermissionBos = menuPermissionManage.listUriPermissionInfo(sysType);

        AntPathMatcher pathMatcher = new AntPathMatcher();

        uriPermissionBos.stream()
                .filter(bo -> pathMatcher.match(bo.getUri(), uri) && Objects.equals(bo.getMethod(), method))
                .findAny()
                .ifPresent(bo -> {
                    if (!userPermissions.contains(bo.getPermission())) {
                        ThrowUtils.throwErr(ResponseEnum.UNAUTHORIZED);
                    }
                });

        return Boolean.TRUE;
    }


    public void clearUserPermissionsCache(Long userId, Integer sysType) {
        menuPermissionManage.clearUserPermissionsCache(userId, sysType);
    }
}
