package com.my.battery.to;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AdminUserRoleTo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String adminUserNo;

    private List<String> userRoleNos;

//    @ApiModelProperty(value = "用户名")
//    private String username;

//    @ApiModelProperty(value = "用户角色集合")
//    private List<String> userRole = new ArrayList<>();

}