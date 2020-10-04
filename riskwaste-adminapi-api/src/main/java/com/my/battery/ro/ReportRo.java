package com.my.battery.ro;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ReportRo {

    /**
     * 举报编号
     */
    private String reportNo;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 情况说明
     */
    private String reportDescribe;

    /**
     * 处理人编号
     */
    private String operatorNo;

    /**
     * 处理人名称
     */
    private String operatorName;

    /**
     * 处理结果
     */
    private String operatorResults;

    /**
     * 处理时间
     */
    private LocalDateTime operatorTime;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedTime;

    /**
     * 举报图片url
     */
    private List<String> url;
}
