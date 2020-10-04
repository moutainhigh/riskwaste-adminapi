package com.my.battery.mo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 台账明细
 * </p>
 *
 * @author weibocy
 * @since 2020-07-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("standing_book_detail")
public class StandingBookDetailMo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    private String companyNo;

}
