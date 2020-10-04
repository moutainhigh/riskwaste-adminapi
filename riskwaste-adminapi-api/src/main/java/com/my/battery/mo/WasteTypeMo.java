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
 * 危废种类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("waste_type")
public class WasteTypeMo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 默认最大库存(吨)
     */
    private Integer defaultMaxValue;

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
