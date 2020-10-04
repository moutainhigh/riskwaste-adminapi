package com.my.battery.ro;

import java.time.LocalDateTime;
import java.util.Set;

import com.my.battery.mo.AdminRoleMo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "回应模型")
@Data
public class AdminUserRo {

    @ApiModelProperty(name = "用户编号")
    private String adminUserNo;

    @ApiModelProperty(name = "关联的用户编号")
    private String associatedUserNo;

    @ApiModelProperty(name = "关联的用户类型")
    private Integer associatedUserType;

    @ApiModelProperty(name = "用户名")
    private String username;

    @ApiModelProperty(name = "真实姓名")
    private String realName;

    @ApiModelProperty(name = "头像URL")
    private String avatar;

    @ApiModelProperty(name = "手机号码")
    private String cell;

    @ApiModelProperty(name = "邮箱")
    private String email;

    @ApiModelProperty(name = "性别(UNKNOWN-未知，BOY-男，GIRL-女)")
    private String sex;

    @ApiModelProperty(name = "状态(NORMAL-正常，LOCK-锁定，LOGOFF-注销)")
    private String state;

    @ApiModelProperty(name = "最后登陆时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(name = "角色合集")
    private Set<AdminRoleMo> roles;

}
