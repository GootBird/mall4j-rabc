package com.xixi.mall.rabc.service.web;

import com.xixi.mall.common.core.webbase.vo.BasePageReqBodyVo;
import com.xixi.mall.common.core.webbase.vo.BasePageRespBodyVo;
import com.xixi.mall.rabc.vo.RoleVo;
import com.xixi.mall.rabc.vo.request.RoleEditReq;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.xixi.mall.common.core.constant.Constant.VOID;

@Service
public class RoleService {

    public BasePageRespBodyVo<RoleVo> page(BasePageReqBodyVo<?> req) {
        return null;
    }

    public List<RoleVo> list() {
        return null;
    }

    public RoleVo getByRoleId(Long roleId) {
        return null;
    }

    public Void save(RoleEditReq req) {
        return VOID;
    }

    public Void update(RoleEditReq req) {
        return VOID;
    }

    public Void delete(Long roleId) {
        return VOID;
    }
}
