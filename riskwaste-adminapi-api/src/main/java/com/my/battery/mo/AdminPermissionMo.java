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
 * 后台权限表
 * </p>
 *
 * @author weibocy
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("admin_permission")
public class AdminPermissionMo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限父级编号（为空则是顶级）
     */
    private String parentNo;

    /**
     * 权限编号
     */
    private String permissionNo;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限类型(1:目录，2:菜单，3:按钮)
     */
    private Integer permissionType;

    /**
     * 权限描述
     */
    private String permissionDescribe;

    /**
     * 权限值
     */
    private String permissionValue;

    /**
     * 路径
     */
    private String path;

    /**
     * 图标
     */
    private String icon;

    /**
     * 状态(0:禁用，1:启用)
     */
    private Integer state;

    /**
     * 顺序号
     */
    private Integer ordersNo;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

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
     * 子权限集合
     */
    @TableField(exist = false)
    private Set<AdminPermissionMo> children;
}
