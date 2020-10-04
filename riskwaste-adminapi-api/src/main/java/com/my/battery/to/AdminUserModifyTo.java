package com.my.battery.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "修改用户服务请求对象", description = "修改用户服务请求对象模型")
public class AdminUserModifyTo {

    @ApiModelProperty(value = "上级ID")
    private Long parentId;

//    @ApiModelProperty(value = "关联的用户类型")
//    private Integer associatedUserType;

    @ApiModelProperty(value = "后台用户编号")
    @NotNull(message = "用户编号不能为空")
    private String adminUserNo;

    @ApiModelProperty(value = "关联的用户编号")
    private String associatedUserNo;

    @ApiModelProperty(value = "关联的用户编号")
    private Integer associatedUserType;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotBlank(message = "用户手机号码不能为空")
    @ApiModelProperty(value = "用户手机号码")
    private String cell;

    @NotNull(message = "性别不能为空")
    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "头像URL")
    private String avatar;

    @ApiModelProperty(value = "状态(0:NORMAL-正常，1:LOCK-锁定，2:LOGOFF-注销)")
    private String state;
}
