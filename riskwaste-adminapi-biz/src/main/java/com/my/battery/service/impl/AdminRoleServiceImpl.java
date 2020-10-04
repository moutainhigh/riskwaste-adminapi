package com.my.battery.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.battery.mapper.AdminRoleMapper;
import com.my.battery.mapper.AdminRolePermissionMapper;
import com.my.battery.mapper.AdminUserRoleMapper;
import com.my.battery.mo.AdminRoleMo;
import com.my.battery.mo.AdminRolePermissionMo;
import com.my.battery.mo.AdminUserRoleMo;
import com.my.battery.ro.AdminRoleRo;
import com.my.battery.service.AdminRolePermissionService;
import com.my.battery.service.AdminRoleService;
import com.my.battery.to.AddAdminRoleTo;
import com.my.battery.to.AdminUserRoleTo;
import com.my.battery.to.ModifyAdminRoleTo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 后台角色表 服务实现类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
@Service
@Slf4j
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRoleMo> implements AdminRoleService {

    @Resource
    private AdminRoleMapper adminRoleMapper;

    @Resource
    private AdminRolePermissionMapper adminRolePermissionMapper;

    @Resource
    private AdminUserRoleMapper adminUserRoleMapper;

    @Resource
    private AdminRolePermissionService adminRolePermissionService;

    @Override
    public AdminRoleMo getAdminRoleByName(final String roleName) {
        final QueryWrapper<AdminRoleMo> queryWrapper = new QueryWrapper<>();
        log.info("根据后台角色名称查询:-{}", roleName);
        queryWrapper.eq("role_name", roleName);
        final AdminRoleMo mo = adminRoleMapper.selectOne(queryWrapper);
        log.info("根据后台角色名称查询返回值:-{}", mo);
        return mo;
    }

    @Override
    public boolean addAdminRole(final AddAdminRoleTo to) {
        log.info("添加后台用户角色参数:{}", to);

        final Snowflake   snowflake = IdUtil.getSnowflake(1, 1);
        final String      roleNo    = snowflake.nextIdStr();
        final AdminRoleMo mo        = new AdminRoleMo();
        BeanUtil.copyProperties(to, mo);

        mo.setRoleNo(roleNo);
        mo.setIsEnabled(1);
        log.info("AdminRoleMo:{}", mo);
        final int result = adminRoleMapper.insert(mo);

        // 获取当前新增用户所具有的权限
        final Set<String> permissions = to.getPermissionNos();
        if (permissions == null || permissions.size() <= 0) {
            return true;
        }

        for (final String permissionNo : permissions) {
            final AdminRolePermissionMo rolePermissionMo = new AdminRolePermissionMo();
            rolePermissionMo.setRoleNo(roleNo);
            rolePermissionMo.setPermissionNo(permissionNo);
            final int insert = adminRolePermissionMapper.insert(rolePermissionMo);
            if (insert != 1) {
                return false;
            }
        }

        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<AdminRoleRo> getAllAdminRole() {
        final QueryWrapper<AdminRoleMo> queryWrapper = new QueryWrapper<>();
        log.info("获取所有后台角色");
        queryWrapper.orderByDesc("created_time");
        final List<AdminRoleMo> mos = adminRoleMapper.selectList(queryWrapper);

        final List<AdminRoleRo> ros = mos.stream().map(e -> BeanUtil.copyProperties(e, AdminRoleRo.class))
                .collect(Collectors.toList());

        for (final AdminRoleRo ro : ros) {
            ro.setMyPermissions(getAdminRolePermissions(ro.getRoleNo()));
        }

        return ros;
    }

    @Override
    public List<String> getAdminRolePermissions(final String roleNo) {
        final QueryWrapper<AdminRolePermissionMo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_no", roleNo);
        final List<AdminRolePermissionMo> permissions = adminRolePermissionMapper.selectList(queryWrapper);

        final List<String> list = new ArrayList<>();
        for (final AdminRolePermissionMo mo : permissions) {
            list.add(mo.getPermissionNo());
        }

        return list;
    }

    @Override
    @Transactional
    public Boolean setAdminUserRole(final AdminUserRoleTo adminUserRoleTo) {
        log.info("设置后台用户权限参数:AdminUserRoleTo-{}", adminUserRoleTo);

        // 先根据后天用户编号删除所有用户和角色关联
        // adminUserRoleMapper.deleteById(adminUserRoleTo.getAdminUserNo());
        final QueryWrapper<AdminUserRoleMo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_user_no", adminUserRoleTo.getAdminUserNo());
        adminUserRoleMapper.delete(queryWrapper);

        final List<String> roleNos = adminUserRoleTo.getUserRoleNos();
        if (null == roleNos || roleNos.size() <= 0) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < roleNos.size(); i++) {
            final AdminUserRoleMo mo = new AdminUserRoleMo();
            mo.setAdminUserNo(adminUserRoleTo.getAdminUserNo());
            mo.setRoleNo(roleNos.get(i));
            log.info("设置后台用户权限,第" + i + "条参数:{}", mo);
            // 在添加后台用户和角色的关联
            final int insert = adminUserRoleMapper.insert(mo);
            result = result + insert;
        }

        if (result == roleNos.size()) {
            return true;
        }
        return false;
    }

    @Override
    public Set<String> adminUserRoles(final String adminUserNo) {
        log.info("根据用户编号获取用户所有角色-{}", adminUserNo);
        final Set<String>                   roleSet      = new HashSet<>();
        final QueryWrapper<AdminUserRoleMo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_user_no", adminUserNo);
        final List<AdminUserRoleMo> mos = adminUserRoleMapper.selectList(queryWrapper);

        for (final AdminUserRoleMo mo : mos) {
            roleSet.add(mo.getRoleNo());
        }

        return roleSet;
    }

    @Override
    public Boolean deleteAdminRoleByNo(final String roleNo) {
        log.info("根据后台角色编号删除:{}", roleNo);

        log.info("清空权限关联");
        final QueryWrapper<AdminRolePermissionMo> deleteQueryWrapper = new QueryWrapper<>();
        deleteQueryWrapper.eq("role_no", roleNo);
        adminRolePermissionMapper.delete(deleteQueryWrapper);

        final QueryWrapper<AdminRoleMo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_no", roleNo);
        final Integer result = adminRoleMapper.delete(queryWrapper);
        if (result != 1) {
            return false;
        }
        return true;
    }

    @Override
    public boolean modifyAdminRole(final ModifyAdminRoleTo to) {
        log.info("修改后台用户角色参数:{}", to);
        if (!updateAdminRole(to)) {
            return false;
        }

        final QueryWrapper<AdminRolePermissionMo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_no", to.getRoleNo());
        adminRolePermissionService.remove(queryWrapper);

        final List<AdminRolePermissionMo> list = new ArrayList<>();

        for (final String permissionNo : to.getPermissionNos()) {

            final AdminRolePermissionMo mo = new AdminRolePermissionMo();
            mo.setRoleNo(to.getRoleNo());
            mo.setPermissionNo(permissionNo);
            list.add(mo);
        }
        if (!adminRolePermissionService.saveBatch(list)) {
            return false;
        }
        return true;
    }

    public boolean updateAdminRole(final ModifyAdminRoleTo to) {
        final UpdateWrapper<AdminRoleMo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("role_no", to.getRoleNo());

        final AdminRoleMo mo = new AdminRoleMo();
        BeanUtil.copyProperties(to, mo);
        final int result = adminRoleMapper.update(mo, updateWrapper);
        if (result != 1) {
            return false;
        }
        return true;
    }

    @Override
    public boolean enableAdminRole(final AdminRoleMo mo) {
        log.info("修改后台用户角色启用状态参数,roleNo-{},isEnable-{}", mo.getRoleNo(), mo.getIsEnabled());
        if (mo.getIsEnabled() > 1) {
            return false;
        }
        final UpdateWrapper<AdminRoleMo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("role_no", mo.getRoleNo());

        final Integer result = adminRoleMapper.update(mo, updateWrapper);
        if (result != 1) {
            return false;
        }
        return true;
    }

}
