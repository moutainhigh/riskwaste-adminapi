package com.my.battery.to;

import lombok.Data;

@Data
public class ReportTo {
    /**
     * 举报编号
     */
    private String reportNo;

    /**
     * 处理结果
     */
    private String operatorResults;

    private String operatorNo;
}
