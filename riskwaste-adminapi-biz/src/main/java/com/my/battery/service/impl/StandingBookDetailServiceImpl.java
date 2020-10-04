package com.my.battery.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.battery.mapper.StandingBookDetailMapper;
import com.my.battery.mo.StandingBookDetailMo;
import com.my.battery.service.StandingBookDetailService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 台账明细 服务实现类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
@Service
@Slf4j
public class StandingBookDetailServiceImpl extends ServiceImpl<StandingBookDetailMapper, StandingBookDetailMo>
        implements StandingBookDetailService {

    @Resource
    private StandingBookDetailMapper mapper;

    @Override
    public Page<StandingBookDetailMo> getStandingBookDetailByCompany(final String companyNo, final String wasteTypeName,
            final Integer page, final Integer limit, final String dateStart, final String dateEnd) {
        final QueryWrapper<StandingBookDetailMo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_no", companyNo);
        if (wasteTypeName != null && !wasteTypeName.equals("")) {
            queryWrapper.like("waste_type_name", wasteTypeName);
        }
        if (dateStart != null && dateEnd != null) {
            queryWrapper.ge("record_date", dateStart).le("record_date", dateEnd + " 23:59:59");
        }

        queryWrapper.orderByDesc("created_time");
        final Page<StandingBookDetailMo>  iPage  = new Page<>(page, limit);
        final IPage<StandingBookDetailMo> result = mapper.selectPage(iPage, queryWrapper);
        log.info("查询结果,result-{}", result);
        return (Page<StandingBookDetailMo>) result;
    }

}
