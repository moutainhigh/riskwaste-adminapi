package com.my.battery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.my.battery.mo.StandingBookDetailMo;

/**
 * <p>
 * 台账明细 服务类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
public interface StandingBookDetailService extends IService<StandingBookDetailMo> {

    Page<StandingBookDetailMo> getStandingBookDetailByCompany(String companyNo, String wasteTypeName, Integer page,
            Integer limit, final String dateStart, String dateEnd);
}
