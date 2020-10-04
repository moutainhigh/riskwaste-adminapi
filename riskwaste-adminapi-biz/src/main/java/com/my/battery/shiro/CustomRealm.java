package com.my.battery.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.my.battery.mo.AdminPermissionMo;
import com.my.battery.mo.AdminRoleMo;
import com.my.battery.mo.AdminUserMo;
import com.my.battery.service.AdminUserService;

/**
 * 自定义Realm用于查询用户的角色、权限信息并保存到权限管理器
 * 
 * @author weibocy
 *
 */
public class CustomRealm extends AuthorizingRealm {

    @Resource
    private AdminUserService adminUserSvc;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
        // 获取登录用户名
        final String username = (String) principals.getPrimaryPrincipal();
        // 根据用户名去数据库查询用户信息
        final AdminUserMo user = adminUserSvc.getByUsername(username);
        // 添加角色权限
        final SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (final AdminRoleMo role : user.getRoles()) {
            // 添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            // 添加权限（用权限表path字段做权限校验）
            for (final AdminPermissionMo permission : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permission.getPath());
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token)
            throws AuthenticationException {
        // 加这一步的目的是在于post请求的时候会先进行认证，然后在到请求
        if (token.getPrincipal() == null) {
            return null;
        }
        // 获取用户信息
        final String      username = token.getPrincipal().toString();
        final AdminUserMo user     = adminUserSvc.getByUsername(username);
        if (user == null) {
            // 这里返回后会报出对应的异常
            return null;
        }
        // 这里验证token和simpleAuthenticationInfo的信息
        final SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,
                user.getPassword().toString(), getName());
        return simpleAuthenticationInfo;
    }

}
