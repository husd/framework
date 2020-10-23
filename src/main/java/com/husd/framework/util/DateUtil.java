package com.husd.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DateUtil {

    // 格式：年－月－日 小时：分钟：秒
    public static final String FORMAT_1 = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_10 = "yyyy年MM月dd日HH时mm分";

    public static final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static String date2String(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_1);
        return simpleDateFormat.format(date);
    }

    public static String date2String(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static Date string2Date(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateStr);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 这个方法返回2个日期之间的所有日期 包含start和end
     *
     * @param start 开始日期 2020-10-10
     * @param end   结束日期 2020-10-11
     * @return
     */
    public List<String> getDateBetween(String start, String end) {

        List<String> dateList = new ArrayList<>();

        LocalDate startLocalDate = LocalDate.parse(start);
        LocalDate endLocalDate = LocalDate.parse(end);
        while (startLocalDate.isBefore(endLocalDate) || startLocalDate.isEqual(endLocalDate)) {
            dateList.add(startLocalDate.toString());
            startLocalDate = startLocalDate.plusDays(1L);
        }
        return dateList;
    }

}
