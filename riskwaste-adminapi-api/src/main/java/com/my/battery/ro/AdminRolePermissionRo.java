package com.my.battery.ro;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AdminRolePermissionRo implements Serializable {

    private static final long serialVersionUID = -3474088739435616756L;

    /**
     * 角色编号
     */
    private String roleNo;

    /**
     * 权限编号
     */
    private List<String> permissionNo;
}
