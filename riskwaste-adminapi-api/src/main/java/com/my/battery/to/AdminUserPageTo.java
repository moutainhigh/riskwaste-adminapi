package com.my.battery.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "查询后台用户角色请求对象", description = "查询后台用户角色请求对象模型")
public class AdminUserPageTo {

    @ApiModelProperty(value = "页数")
    private Integer page;

    @ApiModelProperty(value = "显示数量")
    private Integer limit;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "后台用户编号")
    private String adminUserNo;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "手机号码")
    private String cell;

    @ApiModelProperty(value = "关联的用户编号")
    private String associatedUserNo;

    @ApiModelProperty(value = "关联的用户类型")
    private Integer associatedUserType;

    @ApiModelProperty(value = "邮箱")
    private String email;

}
