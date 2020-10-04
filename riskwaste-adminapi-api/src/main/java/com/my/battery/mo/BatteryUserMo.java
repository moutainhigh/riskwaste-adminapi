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
 * 用户
 * </p>
 *
 * @author weibocy
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("battery_user")
public class BatteryUserMo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 最近一次登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最近一次登录IP地址
     */
    private String lastLoginIp;

    /**
     * 用户类型 0:个人用户,1:单位用户
     */
    private Integer userType;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户手机号码
     */
    private String cell;

    /**
     * 单位编号
     */
    private String companyNo;

    /**
     * 单位类型
     */
    private Integer companyType;

    /**
     * 单位角色 1:管理员,2:收集员,3:库管员
     */
    private Integer companyRole;

    /**
     * 微信登录openid
     */
    private String weixinOpenid;

    /**
     * 微信登录session_key
     */
    private String weixinSessionKey;

    /**
     * 状态 0:停用,1:正常, 2:注销
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime modifiedTime;


}
