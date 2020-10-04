package com.my.battery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.battery.mo.AdminUserRoleMo;

/**
 * <p>
 * 后台用户角色关联表 服务类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
public interface AdminUserRoleService extends IService<AdminUserRoleMo> {
    Integer getCountByRoleNo(String roleNo);
}
