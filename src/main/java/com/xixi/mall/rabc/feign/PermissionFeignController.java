package com.xixi.mall.rabc.feign;

import com.xixi.mall.api.rabc.dto.ClearUserPermissionsCacheDto;
import com.xixi.mall.api.rabc.feign.PermissionFeignClient;
import com.xixi.mall.common.core.webbase.vo.ServerResponse;
import com.xixi.mall.common.security.annotations.SkipAuthenticate;
import com.xixi.mall.rabc.service.feign.PermissionFeignService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@SkipAuthenticate
public class PermissionFeignController implements PermissionFeignClient {

    @Resource
    private PermissionFeignService permissionFeignService;

    @Override
    public ServerResponse<Boolean> checkPermission(Long userId,
                                                   Integer sysType,
                                                   String uri,
                                                   Integer isAdmin,
                                                   Integer method) {

        return ServerResponse.success(permissionFeignService.checkPermission(userId, sysType, uri, isAdmin, method));
    }

    @Override
    public ServerResponse<Void> clearUserPermissionsCache(@RequestBody ClearUserPermissionsCacheDto dto) {
        permissionFeignService.clearUserPermissionsCache(dto.getUserId(), dto.getSysType());
        return ServerResponse.success();
    }

}
