package com.my.battery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.my.battery.config.interceptor.CrossInterceptor;
import com.my.battery.config.interceptor.OptionsInterceptor;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebAppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        registry.addInterceptor(new CrossInterceptor()).addPathPatterns("/**");
        log.info("跨域拦截器注册成功！");

        registry.addInterceptor(new OptionsInterceptor()).addPathPatterns("/**");
        log.info("Options请求拦截器注册成功！");

        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    // 这里实现跨域会导致遇到进行 Token 认证被拦截器或者过滤器处理成401并返回后，跨域也就失败，axios
    // 会读不到error.response，而无法进行401处理
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH").allowCredentials(true).maxAge(3600);

        WebMvcConfigurer.super.addCorsMappings(registry);
    }

}
