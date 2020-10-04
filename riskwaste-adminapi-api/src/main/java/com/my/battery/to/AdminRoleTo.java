package com.my.battery.to;

import java.util.Set;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "添加用户角色请求对象", description = "添加用户角色对象模型")
public class AdminRoleTo {

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    @NotNull(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    private String roleDescription;

    /**
     * 顺序号
     */
    @ApiModelProperty(value = "顺序号")
    private Integer ordersNo;

    /**
     * 角色权限集合
     */
    @ApiModelProperty(value = "角色权限ID集合")
    private Set<String> permissionNos;

    /**
     * 角色编号
     */
    @ApiModelProperty(value = "角色编号")
    private String roleNo;

    /**
     * 是否启用(1-启用，0-不启用)
     */
    @ApiModelProperty(value = "是否启用(1-启用，0-不启用)")
    private Integer isEnabled;
}
