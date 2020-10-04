package com.my.battery.to;

import lombok.Data;

@Data
public class UpdateWasteTypeStateTo {

    /**
     * 危废种类编号
     */
    private String wasteTypeNo;

    /**
     * 是否启用 1-启用，0-不启用
     */
    private Integer isEnable;
}
