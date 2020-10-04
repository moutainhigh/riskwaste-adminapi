package com.my.battery.to;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class AddAdminRoleTo implements Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = 1L;

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

}
