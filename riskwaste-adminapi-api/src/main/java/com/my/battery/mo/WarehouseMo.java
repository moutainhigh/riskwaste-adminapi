package com.my.battery.mo;

import java.math.BigDecimal;
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
 * 入库申请登记
 * </p>
 *
 * @author weibocy
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("warehouse")
public class WarehouseMo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 入库申请编号
     */
    private String warehouseNo;

    /**
     * 申请人编号
     */
    private String applyUserNo;

    /**
     * 申请单位编号
     */
    private String applyCompanyNo;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 危废类型
     */
    private String wasteTypeNo;

    /**
     * 申请运输单编号 用,拼接
     */
    private String transportNo;

    /**
     * 申请质量
     */
    private BigDecimal applyWeight;

    /**
     * 申请数量
     */
    private Integer applyQuantity;

    /**
     * 接收人编号
     */
    private String receiveUserNo;

    /**
     * 接收人单位编号
     */
    private String receiveCompanyNo;

    /**
     * 接收质量
     */
    private BigDecimal receiveWeight;

    /**
     * 接收数量
     */
    private Integer receiveQuantity;

    /**
     * 接收时间
     */
    private LocalDateTime receiveTime;

    /**
     * 入库状态 0:等待处理 1：出库成功 2：出库失败
     */
    private Integer state;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedTime;


}
