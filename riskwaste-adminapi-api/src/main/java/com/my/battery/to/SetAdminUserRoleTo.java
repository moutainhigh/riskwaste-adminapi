package com.my.battery.to;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "设置用户角色请求对象", description = "设置用户角色请求对象模型")
public class SetAdminUserRoleTo {

    @ApiModelProperty(value = "后台用户编号")
    @NotNull(message = "用户编号不能为空")
    private String adminUserNo;

    @ApiModelProperty(value = "角色编号集合")
    private List<String> userRoleNos;

//    @ApiModelProperty(value = "用户名")
//    private String username;

//    @ApiModelProperty(value = "用户角色集合")
//    private List<String> userRole = new ArrayList<>();

}