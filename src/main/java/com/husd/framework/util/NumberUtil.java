package com.husd.framework.util;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

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
     * @param total 分母
     * @return
     */
    public static String calcPercent(double divisor, double total) {
        if (total <= 0.0) {
            return "0.00%";
        }
        if (divisor < 0.0) {
            divisor = -divisor;
        }
        double tempResult = divisor / total;
        Format df = new DecimalFormat("0.00%");
        String result = df.format(tempResult);
        return result;
    }
}
