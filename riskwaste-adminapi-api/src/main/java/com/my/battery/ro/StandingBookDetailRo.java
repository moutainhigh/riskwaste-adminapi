package com.my.battery.ro;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class StandingBookDetailRo {
    /**
     * 台账明细编号
     */
    private String standingBookDetailNo;

    /**
     * 台账编号
     */
    private String standingBookNo;

    /**
     * 危废种类名称
     */
    private String wasteTypeName;

    /**
     * 记录日期
     */
    private LocalDateTime recordDate;

    /**
     * 记录动作 0-出库，1-入库
     */
    private Integer recordAction;

    /**
     * 破损类别
     */
    private Integer damagedType;

    /**
     * 计数质量(kg)
     */
    private BigDecimal recordWeight;

    /**
     * 计数数量(只)
     */
    private Integer recordQuantity;

    private String companyNo;

    private String companyName;
}
