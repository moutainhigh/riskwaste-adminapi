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
 * 单位
 * </p>
 *
 * @author weibocy
 * @since 2020-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("battery_company")
public class BatteryCompanyMo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 单位编号
     */
    private String companyNo;

    /**
     * 单位类型
     */
    private Integer companyType;

    /**
     * 单位简称
     */
    private String companySimpleName;

    /**
     * 单位全称
     */
    private String companyName;

    /**
     * 单位联系人姓名
     */
    private String companyContactsName;

    /**
     * 单位联系人电话
     */
    private String companyContactsCell;

    /**
     * 单位地址
     */
    private String companyAddress;

    /**
     * 单位创建人
     */
    private String createdUserNo;

    /**
     * 单位市名称
     */
    private String companyCityName;

    /**
     * 单位区名称
     */
    private String companyAreaName;

    /**
     * 审核状态 0:未审核,1:通过,2:未通过
     */
    private Integer state;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedTime;


}
