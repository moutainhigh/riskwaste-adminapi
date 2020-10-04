package com.my.battery.to;

import lombok.Data;

@Data
public class AuditBatteryCompanyTo {

    /**
     * 单位编号
     */
    private String companyNo;

    /**
     * 审核状态 0:未审核,1:通过,2:未通过
     */
    private Integer state;

}
