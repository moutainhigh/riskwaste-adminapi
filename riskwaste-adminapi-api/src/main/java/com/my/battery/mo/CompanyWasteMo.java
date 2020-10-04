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
 * 单位危废
 * </p>
 *
 * @author weibocy
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("company_waste")
public class CompanyWasteMo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 单位危废编号
     */
    private String companyWasteNo;

    /**
     * 单位编号
     */
    private String companyNo;

    /**
     * 危废种类编号
     */
    private String wasteNo;

    /**
     * 最大库存(吨)
     */
    private Integer maxValue;

    /**
     * 是否启用 1-启用，0-不启用
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
