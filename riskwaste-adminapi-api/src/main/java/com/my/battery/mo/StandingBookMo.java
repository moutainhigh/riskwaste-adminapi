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
 * 台账
 * </p>
 *
 * @author weibocy
 * @since 2020-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("standing_book")
public class StandingBookMo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    /**
     * 是否启用(0:不启用 1:启用)
     */
    private Integer isEnable;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedTime;

}
