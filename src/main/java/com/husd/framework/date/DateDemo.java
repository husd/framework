package com.husd.framework.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class DateDemo {

    public void firstDay() {

        //每个月的周六
        List<String> res = new ArrayList<>(6);
        LocalDate localDate = LocalDate.parse("2020-12-01");
        LocalDate first = localDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY));
        res.add(first.toString());
        LocalDate firstDayOfNextMonth = localDate.with(TemporalAdjusters.firstDayOfNextMonth());
        while (first.isBefore(firstDayOfNextMonth)) {
            first = first.plusWeeks(1);
            res.add(first.toString());
        }
        System.out.println(res);
    }
}
