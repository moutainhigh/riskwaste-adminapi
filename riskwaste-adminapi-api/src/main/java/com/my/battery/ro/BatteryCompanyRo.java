package com.my.battery.ro;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BatteryCompanyRo {

    /**
     * 单位编号
     */
    private String companyNo;

    /**
     * 单位类型
     */
    private Integer companyType;

    /**
     * 单位全称
     */
    private String companyName;

    /**
     * 单位简称
     */
    private String companySimpleName;

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
     * 单位创建人名称
     */
    private String createdUserName;

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
     * 库存质量(kg)
     */
    private BigDecimal remainderWeight;

    /**
     * 库存数量(只)
     */
    private Integer remainderQuantity;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
}
