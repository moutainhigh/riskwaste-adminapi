package com.my.battery.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.my.battery.dic.OperationActionDic;
import com.my.battery.mo.AdminUserMo;
import com.my.battery.ro.AdminUserRo;
import com.my.battery.service.AdminUserService;
import com.my.battery.to.LoginTo;
import com.my.battery.to.OperationLogTO;
import com.my.battery.util.R;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "登录服务集合", tags = { "登录接口" })
@Slf4j
public class LoginController {

    @Resource
    AdminUserService adminUserSvc;

    @Resource
    HttpServletRequest req;

    // 后台登录
    @ApiOperation(value = "后台登录", tags = { "登录接口" }, notes = "后台登录")
    @PostMapping("login/sign")
    public R<?> login(@RequestBody @Valid final LoginTo loginTo, final HttpServletRequest request) {
        final Subject subject = SecurityUtils.getSubject();
        log.info("后台登录账户:{}， 身份验证码：{}", loginTo.getUsername(), loginTo.getVcode());
        try {
            // 验证账号密码
            final AuthenticationToken token = new UsernamePasswordToken(loginTo.getUsername(), loginTo.getPassword());
            subject.login(token);
            final AdminUserMo adminUser = adminUserSvc.getByUsername(loginTo.getUsername());
            // 当需要安全验证时，才进行短信验证码验证
//            String cell = result.getCell();
//            if (!loginUtil.login(cell, code)) {// 开发环境固定code=888888
//                // 验证码错误
//                return R.failed("验证码或手机号不正确！");
//            }
            // 写登录日志
            final OperationLogTO logTo = new OperationLogTO();
            log.info("AdminUserMo:{}", adminUser);
            logTo.setAdminUserNo(adminUser.getAdminUserNo());
            logTo.setClinetIp(req.getRemoteAddr());
            logTo.setClientAgent(req.getHeader("User-Agent"));
            logTo.setDeviceId(req.getHeader("deviceId"));
            logTo.setSessionId(request.getSession().getId());
            logTo.setOprateAction(OperationActionDic.LOGIN.getName()); // 登录动作
            logTo.setContent("用户登录");
            adminUserSvc.addOperationLog(logTo);

            // todo 获取用户菜单并返回
            final AdminUserRo         result = BeanUtil.toBean(adminUser, AdminUserRo.class);
            final Map<String, Object> data   = new HashMap<>();
            data.put("info", result);
            data.put("token", request.getSession().getId());
            return R.ok(data);
        } catch (final UnknownAccountException e) {
            // 抛出 账户不存在异常
            throw e;
        } catch (final IncorrectCredentialsException e) {
            // 抛出 密码错误异常
            throw e;
        } catch (final LockedAccountException e) {
            // 抛出 项目调用异常
            throw e;
        }
    }
}
