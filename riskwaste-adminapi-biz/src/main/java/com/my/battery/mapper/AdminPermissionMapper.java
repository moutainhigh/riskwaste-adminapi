package com.my.battery.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.battery.mo.AdminPermissionMo;

/**
 * <p>
 * 后台权限表 Mapper 接口
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
public interface AdminPermissionMapper extends BaseMapper<AdminPermissionMo> {

    List<AdminPermissionMo> selectListByChildren();
}
