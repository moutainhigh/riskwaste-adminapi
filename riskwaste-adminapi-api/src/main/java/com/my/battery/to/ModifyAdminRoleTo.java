package com.my.battery.to;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class ModifyAdminRoleTo implements Serializable {

    private static final long serialVersionUID = -5544621835438265372L;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDescription;

    /**
     * 顺序号
     */
    private Integer ordersNo;

    /**
     * 角色权限集合
     */
    private Set<String> permissionNos;

    /**
     * 角色编号
     */
    private String roleNo;

    /**
     * 是否启用(1-启用，0-不启用)
     */
    private Integer isEnabled;

}
