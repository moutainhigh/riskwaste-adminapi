package com.my.battery.to;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "后台用户登录请求模型")
public class LoginTo {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名或密码，不能为空！")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "用户名或密码，不能为空！")
    private String password;

    @Length(min = 4, max = 6)
    @ApiModelProperty(value = "短信验证码")
    private String vcode;

}
