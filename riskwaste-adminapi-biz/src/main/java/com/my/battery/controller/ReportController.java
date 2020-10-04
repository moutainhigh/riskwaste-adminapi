package com.my.battery.controller;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.battery.config.BusinessException;
import com.my.battery.mo.AdminUserMo;
import com.my.battery.mo.BatteryUserMo;
import com.my.battery.mo.ReportMo;
import com.my.battery.mo.ReportPictureMo;
import com.my.battery.ro.ReportRo;
import com.my.battery.service.AdminUserService;
import com.my.battery.service.BatteryUserService;
import com.my.battery.service.ReportPictureService;
import com.my.battery.service.ReportService;
import com.my.battery.to.ReportTo;
import com.my.battery.util.R;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(value = "违法举报服务集合 ", tags = { "违法举报接口" })
public class ReportController {

    @Resource
    private BatteryUserService userService;

    @Resource
    private ReportService reportService;

    @Resource
    private ReportPictureService reportPictureService;

    @Resource
    private AdminUserService adminUserSvc;

    /**
     * 多条件分页查询用户违法举报
     * 
     * @param userNo
     * @return
     */
    @GetMapping("report/getReportList")
    @ApiOperation(value = "多条件分页查询用户违法举报", tags = { "违法举报接口" }, notes = "多条件分页查询用户违法举报")
    public R<Page<ReportRo>> getReportList(
            @RequestParam(value = "username", required = false) @ApiParam(name = "username", value = "举报用户名称") final String username,
            @RequestParam(value = "state", required = false) @ApiParam(name = "state", value = "处理状态(1-已处理,0-未处理)") final Integer state,
            @RequestParam(value = "dateStart", required = false) @ApiParam(name = "dateStart", value = "举报日期开始(yyyy-MM-dd)") final String dateStart,
            @RequestParam(value = "dateEnd", required = false) @ApiParam(name = "dateEnd", value = "举报日期结束(yyyy-MM-dd)") final String dateEnd,
            @RequestParam(value = "page", required = false) @ApiParam(name = "page", value = "页码") Integer page,
            @RequestParam(value = "limit", required = false) @ApiParam(name = "limit", value = "显示数量") Integer limit) {
        log.info("查询用户违法举报参数,username-{},state-{},dateStart-{},dateEnd-{},page-{},limit-{}", username, state, dateStart,
                dateEnd, page, limit);
        if (page == null || page == 0) {
            page = 1;
        }
        if (limit == null || limit == 0) {
            limit = 10;
        }

        // todo 这代码的查询条件逻辑有待优化，暂时不重构了，不然改动太大
        String userNo = null;
        if (username != null) {
            final QueryWrapper<BatteryUserMo> userWrapper = new QueryWrapper<>();
            userWrapper.eq("username", username);
            final List<BatteryUserMo> user = userService.list(userWrapper);
            if (user != null && !user.isEmpty()) {
                // todo 查询多个只要第一个 这里的逻辑应该要兼容 存在多个用户微信昵称一致的情况
                userNo = user.get(0).getUserNo();
            } else {// 找不到用户也要给个不存在的编号，不然下面的查询逻辑根本走不通.....
                userNo = "xxoo";
            }
        }

        final Page<ReportMo> reportList = reportService.getReportList(userNo, state, dateStart, dateEnd, page, limit);
        log.info("查询用户违法举报返回值,reportList-{}", reportList.getRecords());

        final List<ReportRo> roList = new ArrayList<>();
        for (final ReportMo reportMo : reportList.getRecords()) {
            final QueryWrapper<BatteryUserMo> userWrapper = new QueryWrapper<>();
            userWrapper.eq("user_no", reportMo.getUserNo());

            final QueryWrapper<AdminUserMo> admin = new QueryWrapper<>();
            admin.eq("admin_user_no", reportMo.getOperatorNo());

            final BatteryUserMo reportUser  = userService.getOne(userWrapper);
            final AdminUserMo   adminUserMo = adminUserSvc.getOne(admin);
            final ReportRo      ro          = BeanUtil.copyProperties(reportMo, ReportRo.class);
            // 判断为空
            if (reportUser != null) {
                ro.setUserName(reportUser.getUsername());
            }
            if (adminUserMo != null) {
                ro.setOperatorName(adminUserMo.getUsername());
            }

            final QueryWrapper<ReportPictureMo> reportPictureWrapper = new QueryWrapper<>();
            reportPictureWrapper.eq("report_no", reportMo.getReportNo());
            final List<ReportPictureMo> ReportPictureList = reportPictureService.list(reportPictureWrapper);

            final List<String> url = new ArrayList<>();
            for (final ReportPictureMo reportPictureMo : ReportPictureList) {
                final String pictureUrl = reportPictureMo.getReportPictureUrl();
                url.add(pictureUrl);
            }
            ro.setUrl(url);

            roList.add(ro);
        }

        // mp的分页这样用就错了哦 应该指定控制器返回分页接口即可
        final Page<ReportRo> pageRo = new Page<>();
        pageRo.setRecords(roList);
        pageRo.setCurrent(reportList.getCurrent());
        pageRo.setPages(reportList.getPages());
        pageRo.setSize(reportList.getSize());
        pageRo.setTotal(reportList.getTotal());
        pageRo.setOrders(reportList.getOrders());
        return R.ok(pageRo);
    }

    /**
     * 处理举报违法
     * 
     * @param to
     * @return
     * @throws BusinessException
     */
    @PostMapping("/report/handleReport")
    @ApiOperation(value = "处理举报违法", tags = { "违法举报接口" }, notes = "处理举报违法")
    public R<String> handleReport(@RequestBody final ReportTo to) {
        log.info("处理举报违法参数,to-{}", to);
//        final BatteryUserMo currUser = userService.getCurrUserInfo();

        final UpdateWrapper<ReportMo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("report_no", to.getReportNo());
        final ReportMo mo = BeanUtil.copyProperties(to, ReportMo.class);
//        mo.setOperatorNo(currUser.getUserNo());
        mo.setOperatorTime(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        if (!reportService.update(mo, updateWrapper)) {
            return R.failed();
        }
        return R.ok();
    }
}
