package com.my.battery.config;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.my.battery.util.R;

/**
 * 全局异常处理
 * 
 * @author weibocy
 *
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @Autowired
    private HttpServletRequest request;

    // 未知异常 统一回应系统开小差
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> handleException(final Exception e) {
        e.printStackTrace();
        return R.failed("系统开小差了");
    }

    // 认证异常统一回应
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R<?> handleAuthorizationException(final AuthorizationException e) {
        e.printStackTrace();
        return R.failed("未登录或无权限");
    }

    // 当登录无效时，shiro会抛出此异常
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R<?> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        return R.failed("未登录或登录过期");
    }

    // 账号不存在错误
    @ExceptionHandler(UnknownAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R<?> handleUnknownAccountException(final UnknownAccountException e) {
        e.printStackTrace();
        return R.failed("账号不存在或者密码错误");
    }

    // 密码验证错误
    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R<?> handleIncorrectCredentialsException(final IncorrectCredentialsException e) {
        e.printStackTrace();
        return R.failed("账号不存在或者密码错误");
    }

    // 处理参数不合法错误
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleIllegalArgumentException(final IllegalArgumentException e) {
        e.printStackTrace();
        return R.failed("参数不合法");
    }

    // 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        e.printStackTrace();
        // final String message =
        // e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return R.failed("请求参数格式错误");
    }

    // 处理请求参数格式错误
    // @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public R<?> handleConstraintViolationException(final ConstraintViolationException e) {
        e.printStackTrace();
//        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return R.failed("请求参数格式错误");
    }

    // 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
    @ExceptionHandler(BindException.class)
    public R<?> handle(final BindException e) {
//        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return R.failed("请求参数格式错误");
    }
}