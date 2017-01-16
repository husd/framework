package com.husd.framework.util;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyUtil {

    /**
     * 把一个数据显示为RMB格式，例如1.123，返回¥1.123
     * 
     * @param num
     * @return
     */
    public static String transfer2RMB(double num) {
        Format format = NumberFormat.getCurrencyInstance(Locale.SIMPLIFIED_CHINESE);
        return format.format(num);
    }
}
