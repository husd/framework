package com.husd.framework.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.regex.Pattern;

public class NumberUtil {

    final static String regx = "[\\+\\-]?[\\d]+([\\.][\\d]*)?([Ee][+-]?[\\d]+)?";// 科学计数法正则表达式
    final static Pattern pattern = Pattern.compile(regx);

    /**
     * 判断这个数字是不是科学计数法表示的数字。
     *
     * @param num
     * @return
     */
    public static boolean isENum(String num) {
        if (StringUtils.isBlank(num)) {
            return false;
        }
        return pattern.matcher(num).matches();
    }

    /**
     * 计算百分比 保留2位小数
     *
     * @param divisor 分子
     * @param total   分母
     * @return
     */
    public static String calcPercent(double divisor, double total) {
        BigDecimal b1 = new BigDecimal(divisor);
        BigDecimal b2 = new BigDecimal(total);
        BigDecimal zero = new BigDecimal("0");
        if (b2.compareTo(zero) == 0) {
            return "0.00%";
        }
        Format df = new DecimalFormat("0.00%");
        String result = df.format(b1.divide(b2));
        return result;
    }

    public static boolean isNumber(String num) {

        if (StringUtils.isBlank(num)) {
            return false;
        }
        final int sz = num.length();
        int pointCount = 0;
        int eCount = 0;
        for (int i = 0; i < sz; i++) {
            char c = num.charAt(i);
            if (i == 0) {
                if (c == '-' || c == '+') {
                    continue;
                }
            }
            if (c == '.') {
                pointCount++;
                if (pointCount > 1) {
                    return false;
                }
                if (i == 0 || i == (sz - 1)) {
                    return false;
                }
                if (!Character.isDigit(num.charAt(i - 1)) || !Character.isDigit(num.charAt(i + 1))) {
                    return false;
                }
            } else if (c == 'E') {
                eCount++;
                if (eCount > 1) {
                    return false;
                }
                if (i == 0 || i == (sz - 1)) {
                    return false;
                }
                //E后面，可选的可以跟一个加号
                if (num.charAt(i + 1) == '+') {
                    i++;
                }
            } else {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
        }
        return true;
    }
}
