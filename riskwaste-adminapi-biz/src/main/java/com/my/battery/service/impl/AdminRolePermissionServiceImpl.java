package com.my.battery.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.battery.mapper.AdminRolePermissionMapper;
import com.my.battery.mo.AdminRolePermissionMo;
import com.my.battery.service.AdminRolePermissionService;
import com.my.battery.to.AdminRolePermissionTo;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 角色权限关联表 服务实现类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
@Service
@Slf4j
public class AdminRolePermissionServiceImpl extends ServiceImpl<AdminRolePermissionMapper, AdminRolePermissionMo>
        implements AdminRolePermissionService {
    @Resource
    private AdminRolePermissionMapper mapper;

    @Override
    public Boolean addAdminRolePermission(final AdminRolePermissionTo to) {
        log.info("添加角色权限关联参数,to-{}", to);

        final QueryWrapper<AdminRolePermissionMo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_no", to.getRoleNo());
        mapper.delete(queryWrapper);

        for (final String permissionNo : to.getPermissionNo()) {

            final AdminRolePermissionMo mo = new AdminRolePermissionMo();
            mo.setRoleNo(to.getRoleNo());
            mo.setPermissionNo(permissionNo);
            final Integer result = mapper.insert(mo);
            if (result != 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<AdminRolePermissionMo> getByRoleNo(final String roleNo) {
        log.info("查询角色权限关联的参数,roleNo-{}", roleNo);
        final QueryWrapper<AdminRolePermissionMo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_no", roleNo);
        final List<AdminRolePermissionMo> list = mapper.selectList(queryWrapper);
        log.info("查询角色权限关联的返回值,list-{}", list);
        return list;
    }

}
