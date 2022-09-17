package com.xixi.mall.rabc.manage;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xixi.mall.common.cache.constant.CacheNames;
import com.xixi.mall.rabc.entity.MenuEntity;
import com.xixi.mall.rabc.mapper.MenuMapper;
import com.xixi.mall.rabc.vo.MenuSimpleVo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuManage {

    @Resource
    private MenuMapper menuMapper;

    @CacheEvict(cacheNames = CacheNames.MENU_LIST_KEY, key = "#menuEntity.bizType")
    public void save(MenuEntity menuEntity) {
        menuMapper.insert(menuEntity);
    }

    @CacheEvict(cacheNames = CacheNames.MENU_LIST_KEY, key = "#menuEntity.bizType")
    public void update(MenuEntity menuEntity) {
        menuMapper.updateById(menuEntity);
    }

    @CacheEvict(cacheNames = CacheNames.MENU_LIST_KEY, key = "#sysType")
    public void deleteById(Long menuId, Integer sysType) {
        menuMapper.delete(
                Wrappers.<MenuEntity>lambdaQuery()
                        .eq(MenuEntity::getMenuId, menuId)
                        .eq(MenuEntity::getBizType, sysType)
        );
    }

    @Cacheable(cacheNames = CacheNames.MENU_LIST_KEY, key = "#sysType")
    public List<MenuEntity> listBySysType(Integer sysType) {
        return menuMapper.selectList(
                Wrappers.<MenuEntity>lambdaQuery()
                        .eq(MenuEntity::getBizType, sysType)
                        .orderByAsc(MenuEntity::getCreateTime)
                        .orderByAsc(MenuEntity::getSeq)
        );
    }

    public List<MenuSimpleVo> listWithPermissions(Integer sysType) {
        return menuMapper.listWithPermissions(sysType);
    }

    @Cacheable(cacheNames = CacheNames.MENU_ID_LIST_KEY, key = "#userId")
    public List<Long> listMenuIds(Long userId) {
        return menuMapper.listMenuIds(userId);
    }
}
