package com.xixi.mall.rabc.controller;

import com.xixi.mall.common.core.aop.PackResponseEnhance;
import com.xixi.mall.common.core.webbase.vo.BasePageReqBodyVo;
import com.xixi.mall.common.core.webbase.vo.BasePageRespBodyVo;
import com.xixi.mall.common.core.webbase.vo.ServerResponse;
import com.xixi.mall.rabc.service.web.RoleService;
import com.xixi.mall.rabc.vo.RoleVo;
import com.xixi.mall.rabc.vo.request.RoleEditReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/role")
@Api(tags = "角色")
public class RoleController {

    @Resource
    private RoleService service;

    @GetMapping("/page")
    @ApiOperation(value = "分页获取角色列表", notes = "分页获取角色列表")
    public ServerResponse<BasePageRespBodyVo<RoleVo>> page(@Valid BasePageReqBodyVo<?> req) {
        return PackResponseEnhance.enhance(() -> service.page(req));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取角色列表", notes = "分页获取角色列表")
    public ServerResponse<List<RoleVo>> list() {
        return PackResponseEnhance.enhance(() -> service.list());
    }

    @GetMapping
    @ApiOperation(value = "获取角色", notes = "根据roleId获取角色")
    public ServerResponse<RoleVo> getByRoleId(@RequestParam Long roleId) {
        return PackResponseEnhance.enhance(() -> service.getByRoleId(roleId));
    }

    @PostMapping
    @ApiOperation(value = "保存角色", notes = "保存角色")
    public ServerResponse<Void> save(@Valid @RequestBody RoleEditReq req) {
        return PackResponseEnhance.enhance(() -> service.save(req));
    }

    @PutMapping
    @ApiOperation(value = "更新角色", notes = "更新角色")
    public ServerResponse<Void> update(@Valid @RequestBody RoleEditReq req) {
        return PackResponseEnhance.enhance(() -> service.update(req));
    }

    @DeleteMapping
    @ApiOperation(value = "删除角色", notes = "根据角色id删除角色")
    public ServerResponse<Void> delete(@RequestParam Long roleId) {
        return PackResponseEnhance.enhance(() -> service.delete(roleId));
    }
}
