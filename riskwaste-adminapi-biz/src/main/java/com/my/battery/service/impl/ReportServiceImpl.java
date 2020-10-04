package com.my.battery.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.battery.mapper.ReportMapper;
import com.my.battery.mo.ReportMo;
import com.my.battery.service.ReportService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 举报登记 服务实现类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
@Service
@Slf4j
public class ReportServiceImpl extends ServiceImpl<ReportMapper, ReportMo> implements ReportService {

    @Resource
    private ReportMapper mapper;

    @Override
    public Page<ReportMo> getReportList(final String userNo, final Integer state, final String dateStart,
            final String dateEnd, final Integer page, final Integer limit) {
        final QueryWrapper<ReportMo> queryWrapper = new QueryWrapper<>();
        if (userNo != null && !userNo.equals("")) {
            queryWrapper.eq("user_no", userNo);
        }
        if (dateStart != null && dateEnd != null) {
            queryWrapper.ge("record_date", dateStart).le("record_date", dateEnd + " 23:59:59");
        }
        if (state != null && state == 0) {
            queryWrapper.isNull("operator_results");
        } else if (state != null && state == 1) {
            queryWrapper.isNotNull("operator_results");
        }
        queryWrapper.orderByDesc("created_time");
        final Page<ReportMo>  iPage  = new Page<>(page, limit);
        final IPage<ReportMo> result = mapper.selectPage(iPage, queryWrapper);
        log.info("查询结果,result-{}", result);
        return (Page<ReportMo>) result;
    }

}
