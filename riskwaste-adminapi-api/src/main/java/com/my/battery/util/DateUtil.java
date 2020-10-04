package com.my.battery.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
//日期计算和转换
public class DateUtil {

    private static SimpleDateFormat formatter = null;

    public static final String DATETEMPLATE_1 = "yyyyMMdd";

    public static final String DATETEMPLATE_2 = "yyyy/MM/dd";

    public static final String DATETEMPLATE_3 = "yyyy-MM-dd";

    public static final String DATETEMPLATE_4 = "yyyy-MM";

    public static final String DATETEMPLATE_5 = "MM.dd";

    public static final String DATETEMPLATE_6 = "yyyyMMdd";

    /**
     * 日期格式转换(String2Date)
     * 
     * @param strDate      传入的日期
     * @param dateTemplate 日期格式模板
     * @return
     */
    public static Date strToDate(final String strDate, final String dateTemplate) {
        formatter = new SimpleDateFormat(dateTemplate);
        final ParsePosition pos       = new ParsePosition(0);// 用来标明解析开始位
        final Date          strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 日期格式转换(Date2String)
     * 
     * @param date         日期
     * @param dateTemplate 日期格式模板
     * @return
     */
    public static String dateToStr(final Date date, final String dateTemplate) {
        formatter = new SimpleDateFormat(dateTemplate);
        return formatter.format(date);

    }

    /**
     * 日期格式转换(LocalDateTime2String)
     * 
     * @param localDateTime 日期
     * @param dateTemplate  日期格式模板
     * @return
     */
    public static String localDateTimeToStr(final LocalDateTime localDateTime, final String dateTemplate) {
        formatter = new SimpleDateFormat(dateTemplate);
        final Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return formatter.format(date);

    }

    /**
     * 日期格式转换(String2LocalDateTime)
     * 
     * @param str
     * @param dateTemplate
     * @return
     */
    public static LocalDate strToLocalDate(final String str, final String dateTemplate) {
        log.info("参数:str-{},dateTemplate-{}", str, dateTemplate);
        final DateTimeFormatter fmt = DateTimeFormatter.ofPattern(dateTemplate);
        return LocalDate.parse(str, fmt);

    }

    /**
     * 使用LocalDate计算两日期间隔多少天
     * 
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public static long betweenDay(final LocalDate startDate, final LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * Date类型转换成yyyyMMdd的Integer样式
     * 
     * @param date
     * @return
     */
    public static Integer dateToInteger(final Date date) {
        formatter = new SimpleDateFormat(DATETEMPLATE_6);
        final String format = formatter.format(date);
        return Integer.valueOf(format);
    }

    /**
     * 将String类型(yyyyMMdd)的日期转换成模板3的形式(yyyy-MM-dd)
     * 
     * @param strDate
     * @return
     */
    public static String strTOTemp3(final String strDate) {
        final SimpleDateFormat formatter_1 = new SimpleDateFormat(DATETEMPLATE_6);
        final ParsePosition    pos         = new ParsePosition(0);// 用来标明解析开始位
        final Date             date        = formatter_1.parse(strDate, pos);
        formatter = new SimpleDateFormat(DATETEMPLATE_3);
        final String format = formatter.format(date);
        return format;
    }
}
