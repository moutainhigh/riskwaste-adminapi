package com.my.battery.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.battery.mo.AdminRolePermissionMo;
import com.my.battery.to.AdminRolePermissionTo;

/**
 * <p>
 * 角色权限关联表 服务类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
public interface AdminRolePermissionService extends IService<AdminRolePermissionMo> {

    Boolean addAdminRolePermission(AdminRolePermissionTo to);

    List<AdminRolePermissionMo> getByRoleNo(String roleNo);
}
