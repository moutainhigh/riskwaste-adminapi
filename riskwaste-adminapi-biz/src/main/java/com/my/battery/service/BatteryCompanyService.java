package com.my.battery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.my.battery.mo.BatteryCompanyMo;

/**
 * <p>
 * 单位 服务类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
public interface BatteryCompanyService extends IService<BatteryCompanyMo> {

    Page<BatteryCompanyMo> getBatteryCompanyListByState(Integer state, Integer companyType, String companyName,
            String city, String area, Integer page, Integer limit);

    Page<BatteryCompanyMo> getBatteryCompanyByRegion(String city, String area, Integer page, Integer limit);
}
