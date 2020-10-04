package com.my.battery.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.battery.dic.EnableOrDisableDic;
import com.my.battery.dic.PermissionTypeDic;
import com.my.battery.mapper.AdminPermissionMapper;
import com.my.battery.mo.AdminPermissionMo;
import com.my.battery.service.AdminPermissionService;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 后台权限表 服务实现类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
@Service
@Slf4j
public class AdminPermissionServiceImpl extends ServiceImpl<AdminPermissionMapper, AdminPermissionMo>
        implements AdminPermissionService {

    @Resource
    private AdminPermissionMapper adminPermissionMapper;

    @Override
    public boolean addAdminPermission(final AdminPermissionMo mo) {
        log.info("添加后台用户权限参数:{}", mo);
        final Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        mo.setPermissionNo(snowflake.nextIdStr());
        mo.setState(EnableOrDisableDic.ENABLE.getCode());// 启用
        mo.setCreatedTime(LocalDateTime.now());
        mo.setPermissionType(PermissionTypeDic.MENU.getCode());
        final int result = adminPermissionMapper.insert(mo);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public AdminPermissionMo getAdminPermissionByName(final String permissionName) {
        final QueryWrapper<AdminPermissionMo> queryWrapper = new QueryWrapper<>();
        log.info("根据后台权限名称查询:-{}", permissionName);
        queryWrapper.eq("permission_name", permissionName);
        final AdminPermissionMo mo = adminPermissionMapper.selectOne(queryWrapper);
        log.info("根据后台权限名称查询返回值:-{}", mo);
        return mo;
    }

    @Override
    public boolean modifyAdminPermission(final AdminPermissionMo mo) {
        final UpdateWrapper<AdminPermissionMo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("permission_no", mo.getPermissionNo());

        final int result = adminPermissionMapper.update(mo, updateWrapper);
        if (result != 1) {
            return false;
        }
        return true;
    }

    @Override
    public AdminPermissionMo getAdminPermissionByPermissionNo(final String permissionNo) {
        final QueryWrapper<AdminPermissionMo> queryWrapper = new QueryWrapper<>();
        log.info("根据后台权限编号查询:-{}", permissionNo);
        queryWrapper.eq("permission_no", permissionNo);
        final AdminPermissionMo mo = adminPermissionMapper.selectOne(queryWrapper);
        log.info("根据后台权限编号查询返回值:-{}", mo);
        return mo;
    }

    @Override
    public int deleteAdminPermission(final String no) {
        log.info("删除后台用户权限参数:{}", no);

        final List<AdminPermissionMo> mos = getAdminPermissionList();

        int deleteNum = 0;
        for (final AdminPermissionMo mo : mos) {
            if (mo.getPermissionNo().equals(no)) {
                log.info("nonoonnonono");
                for (final AdminPermissionMo child : mo.getChildren()) {
                    final QueryWrapper<AdminPermissionMo> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("permission_no", child.getPermissionNo());
                    log.info("删除后台用户权限子节点:{}", child.getPermissionNo());
                    final int delete_num = adminPermissionMapper.delete(queryWrapper);
                    deleteNum += delete_num;
                }
                final QueryWrapper<AdminPermissionMo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("permission_no", mo.getPermissionNo());
                log.info("删除后台用户权限:{}", no);
                final int delete_num = adminPermissionMapper.delete(queryWrapper);
                deleteNum += delete_num;
                break;
            }
        }
        return deleteNum;
    }

    @Override
    public List<AdminPermissionMo> getAdminPermissionList() {
        final List<AdminPermissionMo> result = adminPermissionMapper.selectListByChildren();

        log.info("获取后台用户所有权限结果集:{}", result);
        return result;
    }

}
