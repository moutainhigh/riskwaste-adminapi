package com.my.battery.to;

import lombok.Data;

@Data
public class AddWasteTypeTo {

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
}
