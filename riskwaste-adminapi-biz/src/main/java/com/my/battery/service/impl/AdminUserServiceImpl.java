package com.my.battery.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.battery.mapper.AdminUserMapper;
import com.my.battery.mapper.AdminUserOperationLogMapper;
import com.my.battery.mo.AdminUserMo;
import com.my.battery.mo.AdminUserOperationLogMo;
import com.my.battery.service.AdminUserService;
import com.my.battery.to.AdminUserPageTo;
import com.my.battery.to.OperationLogTO;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 后台用户信息表 服务实现类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
@Service
@Slf4j
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUserMo> implements AdminUserService {

    @Resource
    private AdminUserMapper mapper;

    @Resource
    private AdminUserOperationLogMapper logMapper;

    @Override
    public AdminUserMo getByUsername(final String username) {
        log.info("根据用户名查询后台用户的参数：username-{}", username);

        final AdminUserMo result = mapper.getByUsername(username);
        // mvn打包时读取不到xml，这里该改用了代码子查询方式
//        final Set<AdminRoleMo> resultRoles = mapper.roles(result.getAdminUserNo());
//        final Set<AdminRoleMo> role        = new HashSet<>();
//        for (final AdminRoleMo resultRole : resultRoles) {
//            final Set<AdminPermissionMo> permissions = mapper.permissions(resultRole.getRoleNo());
//            resultRole.setPermissions(permissions);
//            role.add(resultRole);
//        }
//        result.setRoles(role);
        log.info("根据用户名查询后台用户的返回值：result-{}", result);
        return result;
    }

    @Override
    public List<AdminUserMo> getByCellOrUsername(final String cell, final String username) {
        log.info("根据手机号或用户名查询后台用户的参数：cell-{}, username-{}", cell, username);

        final List<AdminUserMo> result = mapper.getByCellOrUsername(cell, username);

        log.info("根据手机号或用户名查询后台用户的返回值：result-{}", result);
        return result;
    }

    @Override
    public Boolean addAdminUser(final AdminUserMo adminUser) {
        final Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        // adminUser.setAssociatedUserType(1); // 管理员
        adminUser.setAdminUserNo(snowflake.nextIdStr()); // 后台用户编号
        adminUser.setState("NORMAL"); // 账户状态
        // adminUser.setSex(SexDic.UNKNOWN.getName()); // 性别
        adminUser.setCreateTime(LocalDateTime.now()); // 创建时间
        final Integer result = mapper.insert(adminUser);
        if (result != 1) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean modifyAdminUser(final AdminUserMo mo) {
        final UpdateWrapper<AdminUserMo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("admin_user_no", mo.getAdminUserNo());
        final Integer result = mapper.update(mo, updateWrapper);
        log.info("修改后台用户的返回值:result-{}", result);
        if (result != 1) {
            return false;
        }
        return true;
    }

    @Override
    public Page<AdminUserMo> getAdminUserList(final AdminUserPageTo to) {
        final QueryWrapper<AdminUserMo> queryWrapper = new QueryWrapper<>();
        if (to.getState() != null && !to.getState().equals("")) {
            queryWrapper.eq("state", to.getState());
        }
        if (to.getAdminUserNo() != null && !to.getAdminUserNo().equals("")) {
            queryWrapper.eq("admin_user_no", to.getAdminUserNo());
        }
        if (to.getUsername() != null && !to.getUsername().equals("")) {
            queryWrapper.eq("username", to.getUsername());
        }
        if (to.getRealName() != null && !to.getRealName().equals("")) {
            queryWrapper.eq("real_name", to.getRealName());
        }
        if (to.getCell() != null && !to.getCell().equals("")) {
            queryWrapper.eq("cell", to.getCell());
        }
        if (to.getAssociatedUserNo() != null && !to.getAssociatedUserNo().equals("")) {
            queryWrapper.eq("associated_user_no", to.getAssociatedUserNo());
        }
        if (to.getAssociatedUserType() != null) {
            queryWrapper.eq("associated_user_type", to.getAssociatedUserType());
        }
        if (to.getEmail() != null && !to.getEmail().equals("")) {
            queryWrapper.eq("email", to.getEmail());
        }
        final Page<AdminUserMo> page = new Page<>(to.getPage(), to.getLimit());

        final IPage<AdminUserMo> result = mapper.selectPage(page, queryWrapper);
        log.info("多条件分页查询后台用户的返回值:result-{}", result);

        return (Page) result;
    }

    @Override
    public Boolean addOperationLog(final OperationLogTO logTo) {
        final AdminUserOperationLogMo adminlog = BeanUtil.copyProperties(logTo, AdminUserOperationLogMo.class);
        adminlog.setCreatedTime(LocalDateTime.now());
        log.info("添加管理员操作日志：adminLog-{}", adminlog);
        final Integer result = logMapper.insert(adminlog);
        if (result != 1) {
            return false;
        }
        return true;
    }

}
