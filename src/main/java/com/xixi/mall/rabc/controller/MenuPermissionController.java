package com.xixi.mall.rabc.controller;

import com.xixi.mall.common.core.aop.PackResponseEnhance;
import com.xixi.mall.common.core.webbase.vo.BasePageReqBodyVo;
import com.xixi.mall.common.core.webbase.vo.BasePageRespBodyVo;
import com.xixi.mall.common.core.webbase.vo.ServerResponse;
import com.xixi.mall.rabc.service.web.MenuPermissionService;
import com.xixi.mall.rabc.vo.MenuPermissionVo;
import com.xixi.mall.rabc.vo.request.MenuPermissionEditReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "权限接口")
@RequestMapping(value = "/menuPermission")
public class MenuPermissionController {

    @Resource
    private MenuPermissionService service;

    @GetMapping("/listByMenuId")
    @ApiOperation(value = "获取菜单资源列表", notes = "分页获取菜单资源列表")
    public ServerResponse<List<MenuPermissionVo>> listByMenu(Long menuId) {
        return PackResponseEnhance.enhance(() -> service.listByMenuId(menuId));
    }

    @GetMapping
    @ApiOperation(value = "获取菜单资源", notes = "根据menuPermissionId获取菜单资源")
    public ServerResponse<MenuPermissionVo> getByMenuPermissionId(@RequestParam Long menuPermissionId) {
        return PackResponseEnhance.enhance(() -> service.getByMenuPermissionId(menuPermissionId));
    }

    @PostMapping
    @ApiOperation(value = "保存菜单资源", notes = "保存菜单资源")
    public ServerResponse<Void> save(@Valid @RequestBody MenuPermissionEditReq req) {
        return PackResponseEnhance.enhance(() -> service.save(req));
    }

    @PutMapping
    @ApiOperation(value = "更新菜单资源", notes = "更新菜单资源")
    public ServerResponse<Void> update(@Valid @RequestBody MenuPermissionEditReq req) {
        return PackResponseEnhance.enhance(() -> service.update(req));
    }

    @DeleteMapping
    @ApiOperation(value = "删除菜单资源", notes = "根据菜单资源id删除菜单资源")
    public ServerResponse<Void> delete(@RequestParam Long menuPermissionId) {
        return PackResponseEnhance.enhance(() -> service.delete(menuPermissionId));
    }

    @GetMapping(value = "/list")
    @ApiOperation(
            value = "获取当前用户拥有的权限",
            notes = "当前用户所拥有的所有权限，精确到按钮，实际上element admin里面的roles就可以理解成权限"
    )
    public ServerResponse<List<String>> list() {
        return PackResponseEnhance.enhance(() -> service.list());
    }


    @GetMapping(value = "/page")
    @ApiOperation(
            value = "获取当前用户拥有的权限",
            notes = "当前用户所拥有的所有权限，精确到按钮，实际上element admin里面的roles就可以理解成权限"
    )
    public ServerResponse<BasePageRespBodyVo<MenuPermissionVo>> page(BasePageReqBodyVo<?> req) {
        return PackResponseEnhance.enhance(() -> service.page(req));
    }

}
