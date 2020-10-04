package com.my.battery.ro;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class StandingBookRo {

    /**
     * 台账编号
     */
    private String standingBookNo;

    /**
     * 危废种类编号
     */
    private String wasteTypeNo;

    /**
     * 危废种类名称
     */
    private String wasteTypeName;

    /**
     * 危废类型
     */
    private String wasteTypeType;

    /**
     * 代码
     */
    private String code;

    /**
     * 单位编号
     */
    private String companyNo;

    /**
     * 产生质量(kg)
     */
    private BigDecimal produceWeight;

    /**
     * 产生数量(只)
     */
    private Integer produceQuantity;

    /**
     * 转出质量(kg)
     */
    private BigDecimal transferOutWeight;

    /**
     * 转出数量(只)
     */
    private Integer transferOutQuantity;

    /**
     * 库存质量(kg)
     */
    private BigDecimal remainderWeight;

    /**
     * 库存数量(只)
     */
    private Integer remainderQuantity;
}
