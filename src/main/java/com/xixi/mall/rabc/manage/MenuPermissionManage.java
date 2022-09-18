package com.xixi.mall.rabc.manage;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xixi.mall.api.rabc.bo.UriPermissionBo;
import com.xixi.mall.common.cache.constant.CacheNames;
import com.xixi.mall.rabc.entity.MenuPermissionEntity;
import com.xixi.mall.rabc.mapper.MenuPermissionMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MenuPermissionManage {

    @Resource
    private MenuPermissionMapper menuPermissionMapper;

    @CacheEvict(cacheNames = CacheNames.URI_PERMISSION_KEY, key = "#newMenu.bizType")
    public void save(MenuPermissionEntity newMenu) {
        menuPermissionMapper.insert(newMenu);
    }


    public MenuPermissionEntity queryByPermission(String permission, Integer sysType) {
        return menuPermissionMapper.selectOne(
                Wrappers.<MenuPermissionEntity>lambdaQuery()
                        .eq(MenuPermissionEntity::getPermission, permission)
                        .eq(Objects.nonNull(sysType), MenuPermissionEntity::getBizType, sysType)
        );
    }

    @CacheEvict(cacheNames = CacheNames.URI_PERMISSION_KEY, key = "#newEntity.bizType")
    public void updateById(MenuPermissionEntity newEntity) {
        menuPermissionMapper.updateById(newEntity);
    }

    @CacheEvict(cacheNames = CacheNames.URI_PERMISSION_KEY, key = "#sysType")
    public void delete(Long menuPermissionId, Integer sysType) {
        menuPermissionMapper.delete(
                Wrappers.<MenuPermissionEntity>lambdaQuery()
                        .eq(MenuPermissionEntity::getMenuPermissionId, menuPermissionId)
                        .eq(MenuPermissionEntity::getBizType, sysType)
        );
    }

    @Cacheable(cacheNames = CacheNames.PERMISSIONS_KEY, key = "#sysType")
    public List<String> listAll(Integer sysType) {
        return menuPermissionMapper.selectList(
                Wrappers.<MenuPermissionEntity>lambdaQuery()
                        .select(MenuPermissionEntity::getPermission)
                        .eq(MenuPermissionEntity::getBizType, sysType)
        )
                .stream()
                .map(MenuPermissionEntity::getPermission)
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = CacheNames.USER_PERMISSIONS_KEY, key = "#sysType + ':' + #userId")
    public List<String> listByUserIdAndSysType(Long userId, Integer sysType) {
        return menuPermissionMapper.listByUserIdAndSysType(userId, sysType);
    }

    @Cacheable(cacheNames = CacheNames.URI_PERMISSION_KEY, key = "#sysType")
    public List<UriPermissionBo> listUriPermissionInfo(Integer sysType) {
        return menuPermissionMapper.selectList(
                Wrappers.<MenuPermissionEntity>lambdaQuery()
                        .select(MenuPermissionEntity::getPermission, MenuPermissionEntity::getUri, MenuPermissionEntity::getMethod)
                        .eq(MenuPermissionEntity::getBizType, sysType)
        )
                .stream()
                .map(val -> {
                    UriPermissionBo uriPermissionBo = new UriPermissionBo();
                    uriPermissionBo.setPermission(val.getPermission());
                    uriPermissionBo.setUri(val.getUri());
                    uriPermissionBo.setMethod(val.getMethod());
                    return uriPermissionBo;
                })
                .collect(Collectors.toList());
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = CacheNames.USER_PERMISSIONS_KEY, key = "#sysType + ':' + #userId"),
            @CacheEvict(cacheNames = CacheNames.MENU_ID_LIST_KEY, key = "#userId")
    })
    public void clearUserPermissionsCache(Long userId, Integer sysType) {
    }

}
