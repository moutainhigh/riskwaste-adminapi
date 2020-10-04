package com.my.battery.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.battery.mo.AdminPermissionMo;

/**
 * <p>
 * 后台权限表 服务类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
public interface AdminPermissionService extends IService<AdminPermissionMo> {

    // 添加后台用户权限
    boolean addAdminPermission(AdminPermissionMo mo);

    // 根据权限名称查询
    AdminPermissionMo getAdminPermissionByName(String permissionName);

    // 修改后台用户权限
    boolean modifyAdminPermission(AdminPermissionMo mo);

    //
    AdminPermissionMo getAdminPermissionByPermissionNo(String permissionNo);

    // 删除后台用户权限
    int deleteAdminPermission(String no);

    List<AdminPermissionMo> getAdminPermissionList();
}
