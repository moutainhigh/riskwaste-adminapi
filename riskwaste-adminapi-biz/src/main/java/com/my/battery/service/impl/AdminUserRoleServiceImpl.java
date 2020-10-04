package com.my.battery.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.battery.mapper.AdminUserRoleMapper;
import com.my.battery.mo.AdminUserRoleMo;
import com.my.battery.service.AdminUserRoleService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 后台用户角色关联表 服务实现类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
@Service
@Slf4j
public class AdminUserRoleServiceImpl extends ServiceImpl<AdminUserRoleMapper, AdminUserRoleMo>
        implements AdminUserRoleService {

    @Resource
    private AdminUserRoleMapper mapper;

    @Override
    public Integer getCountByRoleNo(final String roleNo) {
        log.info("根据角色编号查询用户角色关联参数，roleNo-{}", roleNo);
        final QueryWrapper<AdminUserRoleMo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_no", roleNo);
        return mapper.selectCount(queryWrapper);
    }

}
