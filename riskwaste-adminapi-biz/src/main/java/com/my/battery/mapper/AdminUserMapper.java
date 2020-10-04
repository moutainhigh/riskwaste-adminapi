package com.my.battery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.battery.mo.AdminUserMo;

/**
 * <p>
 * 后台用户信息表 Mapper 接口
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
public interface AdminUserMapper extends BaseMapper<AdminUserMo> {

    // 根据用户名查询用户以及所属角色和权限信息
    AdminUserMo getByUsername(@Param("username") String username);

    // 根据手机号或用户名查询用户信息
    List<AdminUserMo> getByCellOrUsername(@Param("cell") String cell, @Param("username") String username);
}
