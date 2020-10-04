package com.my.battery.ro;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class AdminRoleRo {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色编号
     */
    private String roleNo;

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
     * 是否启用(1-启用，0-不启用)
     */
    private Integer isEnabled;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedTime;

    /**
     * 角色权限集合
     */
    private List<String> myPermissions;
}
