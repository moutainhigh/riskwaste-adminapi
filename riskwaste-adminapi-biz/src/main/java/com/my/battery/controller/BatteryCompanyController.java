package com.my.battery.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.battery.config.BusinessException;
import com.my.battery.mo.BatteryCompanyMo;
import com.my.battery.mo.BatteryUserMo;
import com.my.battery.mo.StandingBookMo;
import com.my.battery.mo.WasteTypeMo;
import com.my.battery.ro.BatteryCompanyRo;
import com.my.battery.service.BatteryCompanyService;
import com.my.battery.service.BatteryUserService;
import com.my.battery.service.CompanyWasteService;
import com.my.battery.service.StandingBookService;
import com.my.battery.service.WasteTypeService;
import com.my.battery.to.AuditBatteryCompanyTo;
import com.my.battery.util.R;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(value = "单位服务集合", tags = { "单位接口" })
public class BatteryCompanyController {

    @Resource
    private BatteryCompanyService companyService;

    @Resource
    private BatteryUserService userService;

    @Resource
    private CompanyWasteService companyWasteService;

    @Resource
    private StandingBookService standingBookService;

    @Resource
    private WasteTypeService wasteTypeService;

    @GetMapping("/company/getBatteryCompanyList")
    @ApiOperation(value = "多条件分页查询单位列表", tags = { "单位接口" }, notes = "多条件分页查询单位列表")
    public R<Page<BatteryCompanyRo>> getBatteryCompanyListByState(
            @RequestParam(value = "state", required = false) @ApiParam(name = "state", value = "审核状态") final Integer state,
            @RequestParam(value = "companyType", required = false) @ApiParam(name = "companyType", value = "单位类型") final Integer companyType,
            @RequestParam(value = "companyName", required = false) @ApiParam(name = "companyName", value = "单位全称") final String companyName,
            @RequestParam(value = "city", required = false) @ApiParam(name = "city", value = "单位所在市") final String city,
            @RequestParam(value = "area", required = false) @ApiParam(name = "area", value = "单位所在区") final String area,
            @RequestParam(value = "page", required = false) @ApiParam(name = "page", value = "页码") Integer page,
            @RequestParam(value = "limit", required = false) @ApiParam(name = "limit", value = "显示数量") Integer limit) {
        if (page == null || page == 0) {
            page = 1;
        }
        if (limit == null || limit == 0) {
            limit = 10;
        }

        log.info("根据单位名称或类型查询单位参数,state-{},companyType-{},companyName-{},page-{},limit-{}", state, companyType,
                companyName);

        final Page<BatteryCompanyMo> list = companyService.getBatteryCompanyListByState(state, companyType, companyName,
                city, area, page, limit);
        log.info("根据单位名称或类型查询单位返回值,list-{}", list);

        final List<BatteryCompanyRo> batteryCompany = new ArrayList<>();
        for (final BatteryCompanyMo mo : list.getRecords()) {
            final BatteryCompanyRo ro = BeanUtil.copyProperties(mo, BatteryCompanyRo.class);
            batteryCompany.add(ro);
        }

        final Page<BatteryCompanyRo> batteryCompanyPage = new Page<>();
        batteryCompanyPage.setRecords(batteryCompany);
        batteryCompanyPage.setCurrent(list.getCurrent());
        batteryCompanyPage.setPages(list.getPages());
        batteryCompanyPage.setSize(list.getSize());
        batteryCompanyPage.setTotal(list.getTotal());
        batteryCompanyPage.setOrders(list.getOrders());

        return R.ok(batteryCompanyPage);
    }

    @GetMapping("/company/getAllBatteryCompany")
    @ApiOperation(value = "查询所有已审核的单位", tags = { "单位接口" }, notes = "查询所有已审核的单位")
    public R<List<BatteryCompanyRo>> getAllBatteryCompany(
            @RequestParam(value = "companyName", required = false) @ApiParam(name = "companyName", value = "单位全称") final String companyName) {
        log.info("查询所有已审核的单位");
        final QueryWrapper<BatteryCompanyMo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 1);
        if (companyName != null) {
            queryWrapper.like("company_name", companyName);
        }
        final List<BatteryCompanyMo> result = companyService.list(queryWrapper);

        final List<BatteryCompanyRo> batteryCompany = new ArrayList<>();
        for (final BatteryCompanyMo mo : result) {
            final QueryWrapper<StandingBookMo> standingBookWrapper = new QueryWrapper<>();
            standingBookWrapper.eq("company_no", mo.getCompanyNo());
            final List<StandingBookMo> standingBookList  = standingBookService.list(standingBookWrapper);
            BigDecimal                 remainderWeight   = BigDecimal.ZERO;
            Integer                    remainderQuantity = 0;

            for (final StandingBookMo standingBook : standingBookList) {
                remainderWeight   = remainderWeight.add(standingBook.getRemainderWeight());
                remainderQuantity = remainderQuantity + standingBook.getRemainderQuantity();
            }

            final BatteryCompanyRo ro = BeanUtil.copyProperties(mo, BatteryCompanyRo.class);
            ro.setRemainderWeight(remainderWeight);
            ro.setRemainderQuantity(remainderQuantity);
            batteryCompany.add(ro);
        }
        return R.ok(batteryCompany);
    }

    @ExceptionHandler(BusinessException.class)
    @PutMapping("/company/auditBatteryCompany")
    @ApiOperation(value = "审核单位", tags = { "单位接口" }, notes = "审核单位")
    public R<String> auditBatteryCompany(@RequestBody final AuditBatteryCompanyTo to) throws BusinessException {
        log.info("审核单位参数,to-{}", to);
        if (to.getCompanyNo() == null || to.getState() == null) {
            return R.failed("参数错误");
        }
        if (to.getState() > 2 || to.getState() < 0) {
            return R.failed("参数错误");
        }

        final QueryWrapper<BatteryCompanyMo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_no", to.getCompanyNo());
        final BatteryCompanyMo mo = companyService.getOne(queryWrapper);

        final QueryWrapper<BatteryUserMo> userWrapper = new QueryWrapper<>();
        userWrapper.eq("user_no", mo.getCreatedUserNo());
        userWrapper.ne("status", 2);
        final BatteryUserMo user = userService.getOne(userWrapper);
        if (user == null) {
            throw new BusinessException("申请人不存在,单位审核失败");
        }

        final UpdateWrapper<BatteryCompanyMo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("company_no", to.getCompanyNo());
        updateWrapper.set("state", to.getState());
        if (!companyService.update(updateWrapper)) {
            throw new BusinessException("审核失败");
        }

        // 根据绑定的危废类型生成台账记录
        if (to.getState() == 1) {
            final QueryWrapper<WasteTypeMo> wasteTypeWrapper = new QueryWrapper<>();
            wasteTypeWrapper.eq("company_no", to.getCompanyNo());
            final List<WasteTypeMo> wasteType = wasteTypeService.list(wasteTypeWrapper);

            for (final WasteTypeMo waste : wasteType) {
                if (wasteType != null) {
                    final Snowflake      snowflake    = IdUtil.getSnowflake(1, 1);
                    final StandingBookMo standingBook = new StandingBookMo();
                    standingBook.setStandingBookNo(snowflake.nextIdStr());
                    standingBook.setCompanyNo(to.getCompanyNo());
                    standingBook.setWasteTypeNo(waste.getWasteTypeNo());
                    standingBook.setWasteTypeName(waste.getWasteTypeName());
                    if (!standingBookService.save(standingBook)) {
                        throw new BusinessException("添加台账记录失败");
                    }
                }
            }
        }
        return R.ok();
    }

//    @GetMapping("/company/getBatteryCompanyByRegion")
//    @ApiOperation(value = "根据地区查询所有已审核的单位", tags = { "单位接口" }, notes = "根据地区查询所有已审核的单位")
//    public R<Page<BatteryCompanyRo>> getBatteryCompanyByRegion(
//            @RequestParam("city") @ApiParam(name = "city", value = "单位所在市") final String city,
//            @RequestParam(value = "area", required = false) @ApiParam(name = "area", value = "单位所在区") final String area,
//            @RequestParam(value = "page", required = false) @ApiParam(name = "page", value = "页码") Integer page,
//            @RequestParam(value = "limit", required = false) @ApiParam(name = "limit", value = "显示数量") Integer limit) {
//        if (page == null || page == 0) {
//            page = 1;
//        }
//        if (limit == null || limit == 0) {
//            limit = 10;
//        }
//        log.info("根据地区查询所有已审核单位的参数,city-{},area-{},page-{},limit-{}", city, area, page, limit);
//
//        final Page<BatteryCompanyMo> list = companyService.getBatteryCompanyByRegion(city, area, page, limit);
//        log.info("根据地区查询所有已审核单位的返回值,list-{}", list);
//
//        final List<BatteryCompanyRo> batteryCompany = new ArrayList<>();
//        for (final BatteryCompanyMo mo : list.getRecords()) {
//            final BatteryCompanyRo ro = BeanUtil.copyProperties(mo, BatteryCompanyRo.class);
//            batteryCompany.add(ro);
//        }
//
//        final Page<BatteryCompanyRo> batteryCompanyPage = new Page<>();
//        batteryCompanyPage.setRecords(batteryCompany);
//        batteryCompanyPage.setCurrent(list.getCurrent());
//        batteryCompanyPage.setPages(list.getPages());
//        batteryCompanyPage.setSize(list.getSize());
//        batteryCompanyPage.setTotal(list.getTotal());
//        batteryCompanyPage.setOrders(list.getOrders());
//
//        return R.ok(batteryCompanyPage);
//    }

}
