package com.my.battery.mo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 后台用户信息表
 * </p>
 *
 * @author weibocy
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("admin_user")
public class AdminUserMo implements Serializable {

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
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 手机号码
     */
    private String cell;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别(UNKNOWN-未知，BOY-男，GIRL-女)
     */
    private String sex;

    /**
     * 状态(NORMAL-正常，LOCK-锁定，LOGOFF-注销)
     */
    private String state;

    /**
     * 首次登陆时间
     */
    private LocalDateTime firstLoginTime;

    /**
     * 最后登陆时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedTime;

    /**
     * 版本
     */
    @Version
    private Long version;

    /**
     * 用户对应的角色集合
     */
    @TableField(exist = false)
    private Set<AdminRoleMo> roles;

}
