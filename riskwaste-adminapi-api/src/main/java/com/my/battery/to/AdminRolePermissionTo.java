package com.my.battery.to;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AdminRolePermissionTo implements Serializable {

    private static final long serialVersionUID = 6644682349335139254L;

    /**
     * 角色编号
     */
    private String roleNo;

    /**
     * 权限编号
     */
    private List<String> permissionNo;
}
