package com.my.battery.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.battery.mo.BatteryCompanyMo;
import com.my.battery.mo.StandingBookDetailMo;
import com.my.battery.mo.StandingBookMo;
import com.my.battery.mo.WasteTypeMo;
import com.my.battery.ro.StandingBookDetailRo;
import com.my.battery.ro.StandingBookRo;
import com.my.battery.service.BatteryCompanyService;
import com.my.battery.service.StandingBookDetailService;
import com.my.battery.service.StandingBookService;
import com.my.battery.service.WasteTypeService;
import com.my.battery.util.R;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(value = "台账服务集合", tags = { "台账接口" })
public class StandingBookController {

    @Resource
    private StandingBookService standingBookService;

    @Resource
    private WasteTypeService wasteTypeService;

    @Resource
    private StandingBookDetailService standingBookDetailService;

    @Resource
    private BatteryCompanyService companyService;

    @GetMapping("/standingBook/getStandingBookByCompany")
    @ApiOperation(value = "查询单位台账记录", tags = { "台账接口" }, notes = "查询单位台账记录")
    public R<List<StandingBookRo>> getStandingBookByCompany(
            @RequestParam("companyNo") @ApiParam(name = "companyNo", value = "单位编号") final String companyNo) {
        log.info("查询单位台账记录参数,companyNo-{}", companyNo);
        final QueryWrapper<StandingBookMo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_no", companyNo);
        queryWrapper.eq("is_enable", 1);
        final List<StandingBookMo> result = standingBookService.list(queryWrapper);
        log.info("查询单位台账记录返回值,result-{}", result);

        final List<StandingBookRo> list = new ArrayList<>();
        for (final StandingBookMo mo : result) {
            final QueryWrapper<WasteTypeMo> wasteTypeWrapper = new QueryWrapper<>();
            wasteTypeWrapper.eq("waste_type_no", mo.getWasteTypeNo());
            final WasteTypeMo wasteType = wasteTypeService.getOne(wasteTypeWrapper);

            final StandingBookRo ro = BeanUtil.copyProperties(mo, StandingBookRo.class);
            ro.setCode(wasteType.getCode());
            ro.setWasteTypeType(wasteType.getWasteTypeType());
            list.add(ro);
        }
        return R.ok(list);
    }

    @GetMapping("/standingBook/getStandingBookDetailByCompany")
    @ApiOperation(value = "查询单位台账明细", tags = { "台账接口" }, notes = "查询单位台账明细")
    public R<Page<StandingBookDetailRo>> getStandingBookDetailByCompany(
            @RequestParam("companyNo") @ApiParam(name = "companyNo", value = "单位编号") final String companyNo,
            @RequestParam(value = "wasteTypeName", required = false) @ApiParam(name = "wasteTypeName", value = "危废种类名称") final String wasteTypeName,
            @RequestParam(value = "page", required = false) @ApiParam(name = "page", value = "页码") Integer page,
            @RequestParam(value = "limit", required = false) @ApiParam(name = "limit", value = "显示数量") Integer limit,
            @RequestParam(value = "dateStart", required = false) @ApiParam(name = "dateStart", value = "记录日期开始(yyyy-MM-dd)") final String dateStart,
            @RequestParam(value = "dateEnd", required = false) @ApiParam(name = "dateEnd", value = "记录日期结束(yyyy-MM-dd)") final String dateEnd) {
        log.info("查询单位台账明细参数,companyNo-{},wasteTypeName-{},page-{},limit-{},dateStart-{},dateEnd-{}", companyNo,
                wasteTypeName, page, limit, dateStart, dateEnd);
        if (page == null || page == 0) {
            page = 1;
        }
        if (limit == null || limit == 0) {
            limit = 10;
        }

        final Page<StandingBookDetailMo> list = standingBookDetailService.getStandingBookDetailByCompany(companyNo,
                wasteTypeName, page, limit, dateStart, dateEnd);
        log.info("查询单位台账明细返回值,list-{}", list);

        final List<StandingBookDetailRo> standingBookDetail = new ArrayList<>();
        for (final StandingBookDetailMo mo : list.getRecords()) {
            final QueryWrapper<BatteryCompanyMo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("company_no", companyNo);
            final BatteryCompanyMo company = companyService.getOne(queryWrapper);

            final StandingBookDetailRo ro = BeanUtil.copyProperties(mo, StandingBookDetailRo.class);
            ro.setCompanyName(company.getCompanyName());
            standingBookDetail.add(ro);
        }

        final Page<StandingBookDetailRo> standingBookDetailPage = new Page<>();
        standingBookDetailPage.setRecords(standingBookDetail);
        standingBookDetailPage.setCurrent(list.getCurrent());
        standingBookDetailPage.setPages(list.getPages());
        standingBookDetailPage.setSize(list.getSize());
        standingBookDetailPage.setTotal(list.getTotal());
        standingBookDetailPage.setOrders(list.getOrders());
        return R.ok(standingBookDetailPage);
    }
}
