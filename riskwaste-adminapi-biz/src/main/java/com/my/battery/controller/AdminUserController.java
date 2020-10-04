package com.my.battery.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.battery.dic.AdminUserStateDic;
import com.my.battery.dic.AssociatedUserTypeDic;
import com.my.battery.dic.SexDic;
import com.my.battery.mo.AdminUserMo;
import com.my.battery.service.AdminUserService;
import com.my.battery.to.AdminUserModifyTo;
import com.my.battery.to.AdminUserPageTo;
import com.my.battery.to.AdminUserTo;
import com.my.battery.to.ModifyAdminStateTo;
import com.my.battery.util.R;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "后台用户服务接口集合", tags = { "后台用户服务接口" })
@Slf4j
public class AdminUserController {

    @Resource
    private AdminUserService adminUserSvc;

    @PostMapping("/adminUser/admin/addAdminUser")
    @RequiresPermissions("/adminUser/admin/addAdminUser")
    @ApiOperation(value = "新增后台用户", tags = { "后台用户服务接口" }, notes = "新增后台用户")
    public R<?> addAdminUser(
            @RequestBody @Validated @ApiParam(name = "adminUserTo", value = "新增用户服务请求", required = true) final AdminUserTo adminUserTo,
            final BindingResult result) {
        log.info("新增后台用户参数:{}", adminUserTo);
        if (!adminUserTo.getPassword().equals(adminUserTo.getPasswordVerify())) {
            return R.failed("密码与确认密码不一致");
        }
        /*
         * final AdminUserMo isExist =
         * adminUserSvc.getByUsername(adminUserTo.getUsername());
         * if (isExist != null) {
         * return R.failed("账户名已被使用，请换一个");
         * }
         */
        final List<AdminUserMo> isExist = adminUserSvc.getByCellOrUsername(adminUserTo.getCell(),
                adminUserTo.getUsername());
        if (isExist.size() != 0) {
            return R.failed("手机号或账户名已被使用，请换一个");
        }
        final String      passwordHash = BCrypt.hashpw(adminUserTo.getPassword(), BCrypt.gensalt());
        final AdminUserMo adminUser    = BeanUtil.toBean(adminUserTo, AdminUserMo.class);

        if (AssociatedUserTypeDic.getItem(adminUserTo.getAssociatedUserType()) == null
                || SexDic.getItem(adminUserTo.getSex()) == null) {
            return R.failed("关联用户参数不合法");
        }

        adminUser.setSex(adminUserTo.getSex() + "");
        // final AdminUserMo adminUser = BeanUtil.toBean(adminUserTo,
        // AdminUserMo.class);
        adminUser.setPassword(passwordHash);
        log.info("AdminUserMo:{}", adminUser);
        if (adminUserSvc.addAdminUser(adminUser)) {
            return R.ok("新增后台用户成功");
        }
        return R.failed("新增后台用户失败");
    }

    @PostMapping("/adminUser/modifyAdminUser")
    @ApiOperation(value = "修改后台用户", tags = { "后台用户服务接口" }, notes = "修改后台用户")
    public R<String> modifyAdminUser(
            @RequestBody @Validated @ApiParam(name = "adminUserUpdateTo", value = "修改用户服务请求", required = true) final AdminUserModifyTo adminUserUpdateTo,
            final BindingResult bindingResult) {
        log.info("修改后台用户参数:{}", adminUserUpdateTo);

        /*
         * final AdminUserMo isExist =
         * adminUserSvc.getByUsername(adminUserUpdateTo.getUsername());
         * if (isExist != null) {
         * return R.failed("账户名已被使用，请换一个");
         * }
         */
        final List<AdminUserMo> isExist = adminUserSvc.getByCellOrUsername(adminUserUpdateTo.getCell(),
                adminUserUpdateTo.getUsername());
        if (isExist.size() != 0) {
            return R.failed("手机号或用户名已被使用，请换一个");
        }

        final AdminUserMo adminUser = BeanUtil.toBean(adminUserUpdateTo, AdminUserMo.class);

        if (adminUserUpdateTo.getAssociatedUserType() != null) {
            if (AssociatedUserTypeDic.getItem(adminUserUpdateTo.getAssociatedUserType()) == null) {
                return R.failed("关联用户参数不合法");
            }
        }

        if (adminUserUpdateTo.getSex() != null) {
            if (SexDic.getItem(adminUserUpdateTo.getSex()) == null) {
                return R.failed("关联用户参数不合法");
            }
        }

        if (null != adminUserUpdateTo.getState() && null != AdminUserStateDic.valueOf(adminUserUpdateTo.getState())) {
            adminUser.setState(AdminUserStateDic.valueOf(adminUserUpdateTo.getState()).getName());
        }

        adminUser.setSex(adminUserUpdateTo.getSex() + "");
        if (adminUserSvc.modifyAdminUser(adminUser)) {
            return R.ok();
        }
        return R.failed();
    }

    @PutMapping("/adminUser/modifyAdminUserState")
    @ApiOperation(value = "冻结和解冻用户", tags = { "后台用户服务接口" }, notes = "冻结和解冻用户")
    public R<String> modifyAdminUserState(@RequestBody final ModifyAdminStateTo modifyAdminStateTo) {

        if (AdminUserStateDic.valueOf(modifyAdminStateTo.getState()) == null) {
            return R.failed("关联用户参数不合法");
        }
        final AdminUserMo adminUser = new AdminUserMo();
        adminUser.setAdminUserNo(modifyAdminStateTo.getAdminUserNo());
        adminUser.setState(modifyAdminStateTo.getState());
        log.info("冻结和解冻用户参数:{}", adminUser);
        if (adminUserSvc.modifyAdminUser(adminUser)) {
            return R.ok();
        }
        return R.failed();

    }

    @GetMapping("/getAdminUserList")
    @ApiOperation(value = "查询后台用户", tags = { "后台用户服务接口" }, notes = "查询后台用户")
    public R<Page<AdminUserMo>> getAdminUserList(
            @RequestParam(value = "page", required = false) @ApiParam(name = "page", value = "页码") final Integer page,
            @RequestParam(value = "limit", required = false) @ApiParam(name = "limit", value = "显示数量") final Integer limit,
            @RequestParam(value = "searchType", required = false) @ApiParam(name = "searchType", value = "查询类型") final String searchType,
            @RequestParam(value = "keys", required = false) @ApiParam(name = "keys", value = "查询内容") final String keys,
            @RequestParam(value = "state", required = false) @ApiParam(name = "state", value = "用户状态") final String state,
            @RequestParam(value = "associatedUserType", required = false) @ApiParam(name = "associatedUserType", value = "用户类型") final Integer associatedUserType) {
        log.info("分页查询后台用户参数，page-{},limit-{},searchType-{},keys-{},state-{},associatedUserType-{}", page, limit,
                searchType, keys, state, associatedUserType);
        final AdminUserPageTo to = new AdminUserPageTo();
        // TODO
        to.setPage(page);
        to.setLimit(limit);

        if (null != state && null != AdminUserStateDic.valueOf(state)) {
            to.setState(AdminUserStateDic.valueOf(state).getName());
        }
        if (associatedUserType != null) {
            to.setAssociatedUserType(associatedUserType);
        }

        if (StringUtils.isNotBlank(searchType) && StringUtils.isNotBlank(keys)) {
            switch (searchType) {
            case "associatedUserNo":
                to.setAssociatedUserNo(keys);
                break;
            case "username":
                to.setUsername(keys);
                ;
                break;
            case "email":
                to.setEmail(keys);
                ;
                break;
            case "cell":
                to.setCell(keys);
                ;
                break;
            }
        }

        final Page<AdminUserMo> result = adminUserSvc.getAdminUserList(to);
        log.info("分页查询后台用户返回值:list-{}", result.getRecords());
        if (!CollectionUtils.isEmpty(result.getRecords())) {
            return R.ok(result);
        }
        return R.ok(null);
    }
}
