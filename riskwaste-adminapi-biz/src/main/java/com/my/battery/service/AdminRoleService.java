package com.my.battery.service;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.battery.mo.AdminRoleMo;
import com.my.battery.ro.AdminRoleRo;
import com.my.battery.to.AddAdminRoleTo;
import com.my.battery.to.AdminUserRoleTo;
import com.my.battery.to.ModifyAdminRoleTo;

/**
 * <p>
 * 后台角色表 服务类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
public interface AdminRoleService extends IService<AdminRoleMo> {

    // 根据后台角色名称查询
    AdminRoleMo getAdminRoleByName(String roleName);

    // 添加后台角色
    boolean addAdminRole(AddAdminRoleTo to);

    // 获取所有后台角色
    List<AdminRoleRo> getAllAdminRole();

    // 根据角色编号获取其权限集合
    List<String> getAdminRolePermissions(String roleNo);

    Boolean setAdminUserRole(AdminUserRoleTo adminUserRoleTo);

    Set<String> adminUserRoles(String adminUserNo);

    Boolean deleteAdminRoleByNo(String roleNo);

    boolean modifyAdminRole(ModifyAdminRoleTo to);

    boolean enableAdminRole(AdminRoleMo mo);
}
