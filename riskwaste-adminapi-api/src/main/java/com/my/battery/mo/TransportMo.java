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
 * 运输申请登记
 * </p>
 *
 * @author weibocy
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("transport")
public class TransportMo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 运单申请登记编号
     */
    private String transportNo;

    /**
     * 申请运输人编号
     */
    private String transportUserNo;

    /**
     * 申请运输单位编号
     */
    private String transportCompanyNo;

    /**
     * 申请运输时间
     */
    private LocalDateTime transportTime;

    /**
     * 出库申请编号
     */
    private String warehouseOutNo;

    /**
     * 入库单位编号
     */
    private String warehouseCompanyNo;

    /**
     * 危废类型
     */
    private String wasteTypeNo;

    /**
     * 运输质量
     */
    private BigDecimal transportWeight;

    /**
     * 破损类别
     */
    private Integer damagedType;

    /**
     * 运输数量
     */
    private Integer transportQuantity;

    /**
     * 运输状态 0:运输中 1：运输成功 2：运输失败 3:申请入库中
     */
    private Integer state;

    /**
     * 收货人编号
     */
    private String receiveUserNo;

    /**
     * 收货人单位
     */
    private String receiveCompanyNo;

    /**
     * 收货时间
     */
    private LocalDateTime receiveTime;

    /**
     * 创建时间
     */
    private LocalDateTime craetedTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedTime;

    /**
     * 实际运输质量
     */
    private BigDecimal actualTransportWeight;

    /**
     * 实际运输数量
     */
    private Integer actualTransportQuantity;
}
