package com.my.battery.to;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OperationLogTO {
    /**
     * 后台用户编号
     */
    @NotNull
    private String adminUserNo;

    /**
     * 操作类型(1.普通登陆，2.添加或修改角色，3。添加或修改权限.)
     */
    @NotNull
    private String oprateAction;

    /**
     * 设备指纹id
     */
    @NotNull
    private String deviceId;

    /**
     * 分布式sessionId
     */
    @NotNull
    private String sessionId;

    /**
     * 操作内容描述
     */
    @NotNull
    private String content;

    /**
     * 客户端id
     */
    @NotNull
    private String clinetIp;

    /**
     * 客户端代理信息
     */
    @NotNull
    private String clientAgent;

    /**
     * 备注
     */
    @NotNull
    private String memo;
}
