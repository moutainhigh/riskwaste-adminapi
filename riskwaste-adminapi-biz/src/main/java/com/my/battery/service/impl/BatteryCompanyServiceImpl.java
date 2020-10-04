package com.my.battery.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.battery.mapper.BatteryCompanyMapper;
import com.my.battery.mo.BatteryCompanyMo;
import com.my.battery.service.BatteryCompanyService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 单位 服务实现类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
@Service
@Slf4j
public class BatteryCompanyServiceImpl extends ServiceImpl<BatteryCompanyMapper, BatteryCompanyMo>
        implements BatteryCompanyService {

    @Resource
    private BatteryCompanyMapper mapper;

    @Override
    public Page<BatteryCompanyMo> getBatteryCompanyListByState(final Integer state, final Integer companyType,
            final String companyName, final String city, final String area, final Integer page, final Integer limit) {
        final QueryWrapper<BatteryCompanyMo> queryWrapper = new QueryWrapper<>();
        if (state != null) {
            queryWrapper.eq("state", state);
        }
        if (companyType != null) {
            queryWrapper.eq("company_type", companyType);
        }
        if (companyName != null && !companyName.equals("")) {
            queryWrapper.like("company_name", companyName);
        }
        if (city != null && !city.equals("")) {
            queryWrapper.eq("company_city_name", city);
        }
        if (area != null && !area.equals("")) {
            queryWrapper.eq("company_area_name", area);
        }

        final Page<BatteryCompanyMo>  iPage  = new Page<>(page, limit);
        final IPage<BatteryCompanyMo> result = mapper.selectPage(iPage, queryWrapper);
        log.info("多条件 分页查询的返回值:result-{}", result);
        return (Page) result;
    }

    @Override
    public Page<BatteryCompanyMo> getBatteryCompanyByRegion(final String city, final String area, final Integer page,
            final Integer limit) {
        final QueryWrapper<BatteryCompanyMo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 1);
        queryWrapper.eq("company_city_name", city);
        if (area != null && !area.equals("")) {
            queryWrapper.eq("company_area_name", area);
        }
        queryWrapper.orderByDesc("id");

        final Page<BatteryCompanyMo>  iPage  = new Page<>(page, limit);
        final IPage<BatteryCompanyMo> result = mapper.selectPage(iPage, queryWrapper);
        return (Page<BatteryCompanyMo>) result;
    }

}
