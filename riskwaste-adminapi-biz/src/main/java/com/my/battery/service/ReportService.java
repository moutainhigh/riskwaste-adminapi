package com.my.battery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.my.battery.mo.ReportMo;

/**
 * <p>
 * 举报登记 服务类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
public interface ReportService extends IService<ReportMo> {
    Page<ReportMo> getReportList(String userNo, Integer state, String dateStart, String dateEnd, Integer page,
            Integer limit);
}
