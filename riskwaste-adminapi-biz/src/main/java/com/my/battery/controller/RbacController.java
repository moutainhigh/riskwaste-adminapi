package com.my.battery.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.battery.mo.AdminPermissionMo;
import com.my.battery.mo.AdminRoleMo;
import com.my.battery.mo.AdminRolePermissionMo;
import com.my.battery.ro.AdminPermissionRo;
import com.my.battery.ro.AdminRolePermissionRo;
import com.my.battery.ro.AdminRoleRo;
import com.my.battery.service.AdminPermissionService;
import com.my.battery.service.AdminRolePermissionService;
import com.my.battery.service.AdminRoleService;
import com.my.battery.service.AdminUserRoleService;
import com.my.battery.service.AdminUserService;
import com.my.battery.to.AddAdminRoleTo;
import com.my.battery.to.AdminPermissionTo;
import com.my.battery.to.AdminRolePermissionTo;
import com.my.battery.to.AdminRoleTo;
import com.my.battery.to.AdminUserRoleTo;
import com.my.battery.to.ModifyAdminPermissionTo;
import com.my.battery.to.ModifyAdminRoleTo;
import com.my.battery.to.SetAdminUserRoleTo;
import com.my.battery.util.R;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(value = "RBAC服务合集", tags = "RBAC接口")
public class RbacController {

    @Resource
    private AdminUserService adminUserSvc;

    @Resource
    private AdminRoleService adminRoleService;

    @Resource
    private AdminPermissionService adminPermissionService;

    @Resource
    private AdminRolePermissionService adminRolePermissionService;

    @Resource
    private AdminUserRoleService adminUserRoleService;

    @PostMapping("/rbac/addAdminRole")
    @RequiresPermissions("/rbac/addAdminRole")
    @ApiOperation(value = "添加后台角色", tags = { "RBAC接口" }, notes = "添加后台角色")
    public R<String> addAdminRole(
            @RequestBody @Validated @ApiParam(name = "adminRoleTo", value = "添加后台用户角色请求", required = true) final AdminRoleTo adminRoleTo) {
        log.info("添加后台用户角色参数:{}", adminRoleTo);

        final AdminRoleMo mo = adminRoleService.getAdminRoleByName(adminRoleTo.getRoleName());
        if (mo != null) {
            return R.failed("已存在该后台用户角色");
        }

        final AddAdminRoleTo to = new AddAdminRoleTo();
        BeanUtil.copyProperties(adminRoleTo, to);

        log.info("AddAdminRoleTo:{}", to);
        final boolean result = adminRoleService.addAdminRole(to);
        if (result) {
            return R.ok("添加后台用户角色成功");
        }
        return R.failed("添加后台用户角色失败");
    }

    @GetMapping("/rbac/getAllAdminRole")
    @RequiresPermissions("/rbac/getAllAdminRole")
    @ApiOperation(value = "获取后台角色列表", tags = { "RBAC接口" }, notes = "获取后台角色列表")
    public R<List<AdminRoleRo>> getAllAdminRole() {
        log.info("获取后台角色列表");
        final List<AdminRoleRo> mos = adminRoleService.getAllAdminRole();
        if (mos.size() == 0) {
            return R.failed();
        }
        return R.ok(mos);
    }

    @PostMapping("/rbac/setAdminUserRole")
    @RequiresPermissions("/rbac/setAdminUserRole")
    @ApiOperation(value = "设置后台用户角色", tags = { "RBAC接口" }, notes = "设置后台用户角色")
    public R<String> setAdminUserRole(
            @RequestBody @Validated @ApiParam(name = "adminUserRoleTo", value = "设置后台用户角色请求", required = true) final SetAdminUserRoleTo adminUserRoleTo,
            final BindingResult bindingResult) {
        log.info("添加用户角色参数:SetAdminUserRoleTo-{}", adminUserRoleTo);
        final AdminUserRoleTo to = new AdminUserRoleTo();
        BeanUtil.copyProperties(adminUserRoleTo, to);
        log.info("SetAdminUserRoleTo:{}", to);
        final Boolean result = adminRoleService.setAdminUserRole(to);
        if (result) {
            return R.ok("设置后台用户角色成功");
        }
        return R.failed("设置后台用户角色失败");
    }

    @GetMapping("/rbac/adminUserRoles")
    @RequiresPermissions("/rbac/adminUserRoles")
    @ApiOperation(value = "获取用户角色集合", tags = { "RBAC接口" }, notes = "获取用户角色集合")
    public R<Set<String>> adminUserRoles(
            @RequestParam("adminUserNo") @ApiParam(name = "adminUserNo", value = "用户编号") final String adminUserNo) {
        log.info("获取用户角色集合:{}", adminUserNo);
        final Set<String> roleSet = adminRoleService.adminUserRoles(adminUserNo);

        /*
         * if (roleSet.isEmpty()) {
         * return R.failed();
         * }
         */
        return R.ok(roleSet);
    }

    @PostMapping("/rbac/addAdminPermission")
    @RequiresPermissions("/rbac/addAdminPermission")
    @ApiOperation(value = "添加后台权限", tags = { "RBAC接口" }, notes = "添加后台权限")
    public R<String> addAdminPermission(
            @RequestBody @Validated @ApiParam(name = "adminPermissionTo", value = "添加后台用户权限请求", required = true) final AdminPermissionTo adminPermissionTo) {
        log.info("添加后台用户权限参数:{}", adminPermissionTo);

        final AdminPermissionMo mm = adminPermissionService
                .getAdminPermissionByName(adminPermissionTo.getPermissionName());
        if (mm != null) {
            return R.failed("已存在该后台权限");
        }

        final AdminPermissionMo mo = new AdminPermissionMo();
        BeanUtil.copyProperties(adminPermissionTo, mo);
        log.info("AdminPermissionMo:{}", mo);
        final boolean result = adminPermissionService.addAdminPermission(mo);
        if (result) {
            return R.ok("添加后台权限成功");
        }
        return R.failed("添加后台权限失败");
    }

    @PostMapping("/rbac/modifyAdminPermission")
    @RequiresPermissions("/rbac/modifyAdminPermission")
    @ApiOperation(value = "修改后台权限", tags = { "RBAC接口" }, notes = "修改后台权限")
    public R<String> modifyAdminPermission(
            @RequestBody @Validated @ApiParam(name = "adminPermissionTo", value = "修改后台用户权限请求", required = true) final ModifyAdminPermissionTo modifyAdminPermissionTo) {
        log.info("修改后台权限参数:{}", modifyAdminPermissionTo);

        final AdminPermissionMo m1 = adminPermissionService
                .getAdminPermissionByPermissionNo(modifyAdminPermissionTo.getPermissionNo());
        if (m1 == null) {
            return R.failed("不存在该后台权限");
        }

        final AdminPermissionMo mm = adminPermissionService
                .getAdminPermissionByName(modifyAdminPermissionTo.getPermissionName());
        if (mm != null) {
            return R.failed("已存在该后台权限名称");
        }

        final AdminPermissionMo mo = new AdminPermissionMo();
        BeanUtil.copyProperties(modifyAdminPermissionTo, mo);
        final boolean result = adminPermissionService.modifyAdminPermission(mo);

        if (result) {
            return R.ok("修改后台权限成功");
        }
        return R.failed("修改后台权限失败");
    }

    @GetMapping("/rbac/deleteAdminPermission")
    @RequiresPermissions("/rbac/deleteAdminPermission")
    @ApiOperation(value = "删除后台权限", tags = { "RBAC接口" }, notes = "添加后台权限")
    public R<String> deleteAdminPermission(
            @RequestParam("permissionNo") @ApiParam(name = "permissionNo", value = "权限编号") final String permissionNo) {
        log.info("删除后台用户权限参数:{}", permissionNo);

        final int deleteNum = adminPermissionService.deleteAdminPermission(permissionNo);
        if (deleteNum == 0) {
            return R.failed("删除后台权限失败");
        }
        return R.ok("删除后台权限成功");
    }

    @GetMapping("/rbac/getAdminPermissionList")
    @RequiresPermissions("/rbac/getAdminPermissionList")
    @ApiOperation(value = "获取所有权限列表", tags = { "RBAC接口" }, notes = "获取所有权限列表")
    public R<?> getAdminPermissionList() {
        log.info("获取所有权限列表");
        final List<AdminPermissionMo> mos = adminPermissionService.getAdminPermissionList();
        List<AdminPermissionRo>       ros = new ArrayList<>();
        log.info("List<AdminPermissionMo>:{}", mos);
        if (mos != null && mos.size() > 0) {
            ros = mos.stream().map(e -> BeanUtil.copyProperties(e, AdminPermissionRo.class))
                    .collect(Collectors.toList());
        }
        log.info("List<AdminPermissionRo>:{}", ros);
        return R.ok(ros);
    }

    @PostMapping("/rbac/addAdminRolePermission")
    @RequiresPermissions("/rbac/AdminRolePermission")
    @ApiOperation(value = "绑定角色权限", tags = { "RBAC接口" }, notes = "绑定角色权限")
    public R<String> addAdminRolePermission(@RequestBody final AdminRolePermissionTo to) {
        log.info("绑定角色权限参数,to-{}", to);

        if (to.getRoleNo() == null || to.getPermissionNo().size() == 0) {
            return R.failed("参数错误");
        }

        if (adminRolePermissionService.addAdminRolePermission(to)) {
            return R.ok("绑定成功");
        }
        return R.failed("绑定失败");
    }

    @GetMapping("/rbac/getRolePermissionByRoleNo")
    @ApiOperation(value = "查询角色权限", tags = { "RBAC接口" }, notes = "查询角色权限")
    public R<AdminRolePermissionRo> getRolePermissionByRoleNo(
            @RequestParam("roleNo") @ApiParam(name = "roleNo", value = "角色编号", required = true) final String roleNo) {
        log.info("查询角色权限参数,roleNo-{}", roleNo);
        if (roleNo == null || roleNo.equals("")) {
            return R.failed("参数错误");
        }

        final List<AdminRolePermissionMo> list = adminRolePermissionService.getByRoleNo(roleNo);
        if (list == null || list.size() == 0) {
            return R.failed("该角色没有绑定权限");
        }

        final List<String>          permission = new ArrayList<>();
        final AdminRolePermissionRo ro         = new AdminRolePermissionRo();
        ro.setRoleNo(roleNo);
        for (final AdminRolePermissionMo mo : list) {
            permission.add(mo.getPermissionNo());
        }
        ro.setPermissionNo(permission);
        return R.ok(ro);
    }

    @GetMapping("/rbac/delRoleByRoleNo")
    @ApiOperation(value = "删除角色", tags = { "RBAC接口" }, notes = "删除角色")
    public R<String> delRoleByRoleNo(
            @RequestParam("roleNo") @ApiParam(name = "roleNo", value = "角色编号", required = true) final String roleNo) {
        final Integer count = adminUserRoleService.getCountByRoleNo(roleNo);
        if (count != null && count > 0) {
            return R.failed("该角色正在使用中无法删除");
        }
        if (adminRoleService.deleteAdminRoleByNo(roleNo)) {
            return R.ok("删除成功");
        }
        return R.failed("删除失败");
    }

    @PostMapping("/rbac/modifyAdminRole")
    @RequiresPermissions("/rbac/modifyAdminRole")
    @ApiOperation(value = "修改后台角色", tags = { "RBAC接口" }, notes = "修改后台角色")
    public R<String> modifyAdminRole(
            @RequestBody @Validated @ApiParam(name = "adminRoleTo", value = "修改后台用户角色请求", required = true) final AdminRoleTo adminRoleTo) {
        log.info("修改后台用户角色参数:{}", adminRoleTo);

        final AdminRoleMo mo = adminRoleService.getAdminRoleByName(adminRoleTo.getRoleName());
        if (mo == null) {
            return R.failed("不存在该后台用户角色");
        }

        final ModifyAdminRoleTo to = new ModifyAdminRoleTo();
        BeanUtil.copyProperties(adminRoleTo, to);

        log.info("AddAdminRoleTo:{}", to);

        if (!adminRoleService.modifyAdminRole(to)) {
            return R.failed("修改后台用户角色失败");
        }
        return R.ok("修改后台用户角色成功");
    }

    @PutMapping("/rbac/enableAdminRole")
    @RequiresPermissions("/rbac/enableAdminRole")
    @ApiOperation(value = "修改后台用户角色启用状态", tags = { "RBAC接口" }, notes = "修改后台用户角色启用状态")
    public R<String> enableAdminRole(@RequestBody final AdminRoleMo mo) {
        log.info("修改后台用户角色启用状态参数,mo-{}", mo);
        if (adminRoleService.enableAdminRole(mo)) {
            return R.ok("修改成功");
        }
        return R.failed("修改失败");
    }
}
