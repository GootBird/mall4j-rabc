package com.xixi.mall.rabc.service.web;

import com.xixi.mall.common.core.webbase.vo.BasePageReqBodyVo;
import com.xixi.mall.common.core.webbase.vo.BasePageRespBodyVo;
import com.xixi.mall.rabc.vo.MenuPermissionVo;
import com.xixi.mall.rabc.vo.request.MenuPermissionEditReq;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.xixi.mall.common.core.constant.Constant.VOID;

@Service
public class MenuPermissionService {


    public List<MenuPermissionVo> listByMenuId(Long menuId) {
        return null;
    }

    public MenuPermissionVo getByMenuPermissionId(Long menuPermissionId) {
        return null;
    }

    public Void save(MenuPermissionEditReq req) {
        return VOID;
    }

    public Void update(MenuPermissionEditReq req) {
        return VOID;
    }

    public Void delete(Long menuPermissionId) {
        return VOID;
    }

    public List<String> list() {
        return null;
    }

    public BasePageRespBodyVo<MenuPermissionVo> page(BasePageReqBodyVo<?> req) {
        return null;
    }
}
