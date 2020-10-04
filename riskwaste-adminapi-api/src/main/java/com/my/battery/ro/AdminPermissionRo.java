package com.my.battery.ro;

import java.util.Set;

import com.my.battery.mo.AdminPermissionMo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "后台用户权限回应模型")
@Data
public class AdminPermissionRo {

    @ApiModelProperty(value = "主键编号")
    private Integer id;

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

    @ApiModelProperty(value = "权限父级名称（为空则是顶级）")
    private String parentName;

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称")
    private String permissionName;

    /**
     * 权限类型(1:目录，2:菜单，3:按钮)
     */
    @ApiModelProperty(value = "权限类型(1:目录，2:菜单，3:按钮)")
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
    private String permissionValue;

    /**
     * 路径
     */
    @ApiModelProperty(value = "路径")
    private String path;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 状态(0:禁用，1:启用)
     */
    @ApiModelProperty(value = "状态(0:禁用，1:启用)")
    private Integer state;

    /**
     * 顺序号
     */
    @ApiModelProperty(value = "顺序号")
    private Integer ordersNo;

    @ApiModelProperty(value = "子权限集合")
    private Set<AdminPermissionMo> children;
}
