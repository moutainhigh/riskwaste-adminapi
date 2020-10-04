package com.my.battery.to;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "添加用户权限请求对象", description = "添加用户权限对象模型")
public class ModifyAdminPermissionTo {
	
	/**
     * 权限编号
     */
	@ApiModelProperty(value = "权限编号")
    private String permissionNo;

    /**
     * 权限父级编号（为空则是顶级）
     */
    @ApiModelProperty(value = "权限父级编号（为空则是顶级）")
    private String parentNo;

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称")
    @NotNull(message = "权限名称不能为空")
    private String permissionName;

    /**
     * 权限类型(1:目录，2:菜单，3:按钮)
     */
    @ApiModelProperty(value = "权限类型")
    private Integer permissionType;

    /**
     * 权限描述
     */
    @ApiModelProperty(value = "权限描述")
    private String permissionDescribe;

    /**
     * 权限值
     */
    @ApiModelProperty(value = "权限值")
    @NotNull(message = "权限值不能为空")
    private String permissionValue;

    /**
     * 路径
     */
    @ApiModelProperty(value = "权限路径")
    @NotNull(message = "权限路径不能为空")
    private String path;
}
