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
 * 出库申请登记
 * </p>
 *
 * @author weibocy
 * @since 2020-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("warehouse_out")
public class WarehouseOutMo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 出库申请编号
     */
    private String warehouseOutNo;

    /**
     * 出库类型 0:正常出库，1:转运中心，2:处置单位
     */
    private Integer warehouseOutType;

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
     * 接收单位
     */
    private String receiveCompanyNo;

    /**
     * 运输车牌号
     */
    private String carNumber;

    /**
     * 是否为危险品车 0:否 ,1:是
     */
    private Integer isDangerousCar;

    /**
     * 危废类型编号
     */
    private String wasteTypeNo;

    /**
     * 出库质量
     */
    private BigDecimal warehouseOutWeight;

    /**
     * 破损类别
     */
    private Integer damagedType;

    /**
     * 出库数量
     */
    private Integer warehouseOutQuantity;

    /**
     * 出库状态 0:等待处理 1：出库成功 2：出库失败
     */
    private Integer state;

    /**
     * 确认人编号
     */
    private String confirmUserNo;

    /**
     * 确认人单位
     */
    private String confirmCompanyNo;

    /**
     * 确认时间
     */
    private LocalDateTime confirmTime;

    /**
     * 创建时间
     */
    private LocalDateTime craetedTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedTime;


}
