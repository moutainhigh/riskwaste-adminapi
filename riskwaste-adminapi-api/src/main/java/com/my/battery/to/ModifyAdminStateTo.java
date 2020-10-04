package com.my.battery.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "修改客户状态模型", description = "修改客户状态模型")
public class ModifyAdminStateTo {

    @ApiModelProperty(value = "客户编号", required = true)
    private String adminUserNo;

    @ApiModelProperty(value = "客户状态", required = true)
    private String state;

}
