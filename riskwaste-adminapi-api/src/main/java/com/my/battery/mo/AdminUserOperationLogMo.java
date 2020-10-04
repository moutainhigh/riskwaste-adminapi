package com.my.battery.mo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 后台用户操作日志表
 * </p>
 *
 * @author weibocy
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("admin_user_operation_log")
public class AdminUserOperationLogMo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 后台用户编号
     */
    private String adminUserNo;

    /**
     * 操作类型(1.普通登陆，2.添加或修改角色，3。添加或修改权限.)
     */
    private String oprateAction;

    /**
     * 设备指纹id
     */
    private String deviceId;

    /**
     * 分布式sessionId
     */
    private String sessionId;

    /**
     * 操作内容描述
     */
    private String content;

    /**
     * 客户端id
     */
    private String clinetIp;

    /**
     * 客户端代理信息
     */
    private String clientAgent;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 备注
     */
    private String memo;


}
