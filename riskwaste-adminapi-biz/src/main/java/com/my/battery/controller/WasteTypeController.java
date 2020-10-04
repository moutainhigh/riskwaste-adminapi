package com.my.battery.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.my.battery.config.BusinessException;
import com.my.battery.mo.CompanyWasteMo;
import com.my.battery.mo.StandingBookMo;
import com.my.battery.mo.WasteTypeMo;
import com.my.battery.service.CompanyWasteService;
import com.my.battery.service.StandingBookService;
import com.my.battery.service.WasteTypeService;
import com.my.battery.to.AddWasteTypeTo;
import com.my.battery.to.UpdateWasteTypeStateTo;
import com.my.battery.to.WasteTypeTo;
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
@Api(value = "危废服务集合", tags = { "危废接口" })
@Transactional
public class WasteTypeController {

    @Resource
    private WasteTypeService wasteTypeService;

    @Resource
    private CompanyWasteService companyWasteService;

    @Resource
    private StandingBookService standingBookService;

    @GetMapping("/wasteType/getWasteType")
    @ApiOperation(value = "查询危废服务列表", tags = { "危废接口" }, notes = "查询单位台账记录")
    public R<List<WasteTypeMo>> getWasteType(
            @RequestParam(value = "wasteTypeName", required = false) @ApiParam(name = "wasteTypeName", value = "危废种类名称") final String wasteTypeName,
            @RequestParam(value = "isEnable", required = false) @ApiParam(name = "isEnable", value = "是否启用 1-启用，0-不启用") final Integer isEnable) {
        log.info("查询危废服务列表参数,wasteTypeName-{},isEnable-{}", wasteTypeName, isEnable);

        final QueryWrapper<WasteTypeMo> queryWrapper = new QueryWrapper<>();
        if (isEnable != null) {
            queryWrapper.eq("is_enable", isEnable);
        }
        if (wasteTypeName != null) {
            queryWrapper.eq("waste_type_name", wasteTypeName);
        }

        final List<WasteTypeMo> result = wasteTypeService.list(queryWrapper);
        return R.ok(result);
    }

    @PostMapping("/wasteType/addWasteType")
    @ApiOperation(value = "添加危废种类", tags = { "危废接口" }, notes = "添加危废种类")
    public R<String> addWasteType(@RequestBody final AddWasteTypeTo to) {
        log.info("添加危废参数,to-{}", to);

        final WasteTypeMo mo        = BeanUtil.copyProperties(to, WasteTypeMo.class);
        final Snowflake   snowflake = IdUtil.getSnowflake(1, 1);

        mo.setWasteTypeNo(snowflake.nextIdStr());
        mo.setIsEnable(1);
        if (!wasteTypeService.save(mo)) {
            return R.failed();
        }
        return R.ok();
    }

    @PutMapping("/wasteType/updateWasteType")
    @ApiOperation(value = "修改危废种类", tags = { "危废接口" }, notes = "修改危废种类")
    public R<String> updateWasteType(@RequestBody final WasteTypeTo to) {
        log.info("修改危废参数,to-{}", to);
        final WasteTypeMo mo = BeanUtil.copyProperties(to, WasteTypeMo.class);

        final UpdateWrapper<WasteTypeMo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("waste_type_no", to.getWasteTypeNo());
        if (!wasteTypeService.update(mo, updateWrapper)) {
            return R.failed("修改失败");
        }
        return R.ok("修改成功");
    }

    @ExceptionHandler(BusinessException.class)
    @PutMapping("/wasteType/updateState")
    @ApiOperation(value = "修改危废种类状态", tags = { "危废接口" }, notes = "修改危废种类状态")
    public R<String> updateWasteTypeState(@RequestBody final UpdateWasteTypeStateTo to) throws BusinessException {
        log.info("修改危废种类状态参数,to-{}", to);
        if (to.getIsEnable() > 1 || to.getIsEnable() < 0) {
            return R.failed("参数错误");
        }

        final UpdateWrapper<WasteTypeMo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("waste_type_no", to.getWasteTypeNo());
        updateWrapper.set("is_enable", to.getIsEnable());
        if (!wasteTypeService.update(updateWrapper)) {
            throw new BusinessException();
        }

        // 修改单位危废
        final UpdateWrapper<CompanyWasteMo> companyWasteWrapper = new UpdateWrapper<>();
        companyWasteWrapper.eq("waste_no", to.getWasteTypeNo());
        companyWasteWrapper.set("is_enable", to.getIsEnable());
        if (!companyWasteService.update(companyWasteWrapper)) {
            throw new BusinessException("修改单位危废状态失败");
        }

        // 修改台账
        final UpdateWrapper<StandingBookMo> standingBookWrapper = new UpdateWrapper<>();
        standingBookWrapper.eq("waste_type_no", to.getWasteTypeNo());
        standingBookWrapper.set("is_enable", to.getIsEnable());
        if (!standingBookService.update(standingBookWrapper)) {
            throw new BusinessException("修改台账状态失败");
        }
        return R.ok();
    }
}
