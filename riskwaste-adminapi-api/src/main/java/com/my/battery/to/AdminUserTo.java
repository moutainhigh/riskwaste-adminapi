package com.my.battery.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "新增用户服务请求对象", description = "新增用户服务请求对象模型")
public class AdminUserTo {

    @ApiModelProperty(value = "上级ID")
    private Long parentId;

    @ApiModelProperty(value = "关联的用户类型")
    private Integer associatedUserType;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "密码确认")
    @NotBlank(message = "密码确认不能为空")
    private String passwordVerify;

    @NotBlank(message = "真实姓名不能为空")
    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @NotBlank(message = "用户手机号码不能为空")
    @ApiModelProperty(value = "用户手机号码")
    private String cell;

    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像url")
    private String avatar;

    @NotNull(message = "性别不能为空")
    @ApiModelProperty(value = "性别")
    private Integer sex;
}
