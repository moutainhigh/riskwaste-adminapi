package com.my.battery.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.my.battery.mo.AdminUserMo;
import com.my.battery.to.AdminUserPageTo;
import com.my.battery.to.OperationLogTO;

/**
 * <p>
 * 后台用户信息表 服务类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
public interface AdminUserService extends IService<AdminUserMo> {
    /**
     * 根据用户名查询
     * 
     * @param username 用户名
     * @return
     */
    AdminUserMo getByUsername(String username);

    List<AdminUserMo> getByCellOrUsername(String cell, String username);

    Boolean addAdminUser(AdminUserMo adminUser);

    Boolean modifyAdminUser(final AdminUserMo mo);

    Boolean addOperationLog(OperationLogTO logTo);

    /**
     * 分页查询后台用户
     * 
     * @param to
     * @return
     */
    Page<AdminUserMo> getAdminUserList(final AdminUserPageTo to);
}
