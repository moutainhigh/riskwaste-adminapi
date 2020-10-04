package com.my.battery.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    // 将自定义认证与授权实现注入容器 配置BCrypt密码算法
    @Bean
    public CustomRealm customRealm() {
        final CustomRealm customRealm = new CustomRealm();
        // 配置BCrypt
        customRealm.setCredentialsMatcher((token, info) -> {
            final UsernamePasswordToken userToken = (UsernamePasswordToken) token;
            // 要验证的明文密码
            final String plaintext = new String(userToken.getPassword());
            // 数据库中的加密后的密文
            final String hashed = info.getCredentials().toString();
            return BCrypt.checkpw(plaintext, hashed);
        });

        return customRealm;
    }

    // 将自定义认证与授权实现加入默认的web安全管理器
    @Bean
    public SessionsSecurityManager securityManager() {
        final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm());
        return securityManager;
    }

    // Filter工厂，设置对应的过滤条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(final SecurityManager securityManager) {
        final ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 自定义过滤器
        final Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("authc", new MyFormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        final Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 登入、发送验证码、登出、swagger
        filterChainDefinitionMap.put("/login/**", "anon");
        filterChainDefinitionMap.put("/**/error", "anon");
        filterChainDefinitionMap.put("/version/**", "anon");
        filterChainDefinitionMap.put("/csrf", "anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/upload/callback", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html/**", "anon");
        // 对所有用户认证
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setLoginUrl("/login/sign");
        shiroFilterFactoryBean.setSuccessUrl("/home");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        // 设置过滤条件规则
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    // 开启shiro注解
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        final DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            final SecurityManager securityManager) {
        final AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
