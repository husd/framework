package com.husd.framework.ddl;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TokenType {

    INIT(1, ""),
    CREATE(1, "create"),
    TEMPORARY(1, "temporary"),
    TABLE(1, "table"),
    TABLE_NAME(1, ""),
    IF_NOT_EXISTS(3, "if not exists"),
    LEFT_P(1, "("), //左括号
    RIGHT_P(1, ")"),//右括号
    //LEFT_1(1,"`"), // 左 `
    //RIGHT_1(1,"`"), // 右 `
    COL_NAME(1, ""),
    AUTO_INCREMENT(1, "auto_increment"),
    COLUMN_TYPE(1, ""),
    NULL(1, "null"),
    NOT_NULL(2, "not null"),
    DEFAULT(1, "default"),
    DEFAULT_VAL(1, ""),
    COMMENT(1, "comment"),
    COMMENT_VAL(1, ""),
    COMMA(1, ","), //逗号
    PRIMARY_KEY(2, "primary key"),
    DISTRIBUTED_BY_HASH(3, "distributed by hash"),
    PARTITION_BY(2, "partition by value"),
    SPACE(1, " "),
    VAR(1, "") // VAR可能是任意的东西，要看这个前后文是什么了
    ; // 空格
    //这个TOKEN ，占几个单词
    private int step;
    //单词
    private String key;
    //是否要跳过下一个()里的东西
    private boolean skipKuoHao;

    //匹配(29)或者(20,12)这样的字符串
    private static Pattern colLenPattern = Pattern.compile("^\\(\\d+(,\\d+)?\\)$");

    TokenType(int step) {
        this.step = step;
    }

    TokenType(int step, String key) {
        this.step = step;
        this.key = key;
    }

    public static TokenType getTokenTypeName(String[] array, int i) {

        String str = array[i];
        str = preDeal(str);
        if (StringUtils.isBlank(str)) {
            return INIT;
        }
        for (TokenType item : TokenType.values()) {

            if (item == INIT || item == SPACE || item == VAR) {
                continue;
            }
            if (StringUtils.equals(item.getKey(), str)) {
                return item;
            }
            //有那种组合的关键词，就需要读取多个
            if (ifNotExists(str, array, i)) {
                return IF_NOT_EXISTS;
            }
            if (notNull(str, array, i)) {
                return NOT_NULL;
            }
            if (primaryKey(str, array, i) > 0) {
                return PRIMARY_KEY;
            }
            if (distributed_by_hash(str, array, i) > 0) {
                return DISTRIBUTED_BY_HASH;
            }
            if (partition_by(str, array, i) > 0) {
                return PARTITION_BY;
            }
            if (col_type(str)) {
                return COLUMN_TYPE;
            }
        }
        if (str.length() > 0) {
            //不确定是什么，可能是表名，也可能是列名。
            return VAR;
        }
        return INIT;
    }

    private static boolean col_type(String str) {

        for (String s : AutoCodeUtil.cols) {
            if (StringUtils.equals(str, s)) {
                return true;
            }
            if (StringUtils.startsWith(str, s)) {
                // 这种就是char(10) varchar(20)这样的
                // 判断是不是(10)这种就行了
                if (str.length() - s.length() < 3) {
                    throw new RuntimeException("列的长度格式不对 ，正确的是(10) 或者 (1,3) str:" + str);
                }
                String e = findColSuffix(str);
                if (e.length() > 0) {
                    Matcher matcher = colLenPattern.matcher(e);
                    if (!matcher.find()) {
                        throw new RuntimeException("列的长度格式不对 ，正确的是(10) 或者 (1,3) str:" + e);
                    }
                }
                return true;
            }
            //TODO husd还有几个类型，也不是完全匹配的

        }
        return false;
    }

    //datetime(3) 这种的，返回datetime
    // varchar 返回varchar
    private static String findColName(String str) {

        if (str.contains("(")) {
            return str.substring(0, str.indexOf("("));
        }
        return str;
    }

    //datetime(3) 这种的，返回(3)
    // varchar 返回 ""
    private static String findColSuffix(String str) {

        if (str.contains("(")) {
            return str.substring(str.indexOf("("), str.length());
        }
        return "";
    }

    private static int partition_by(String str, String[] array, int i) {
        boolean a = StringUtils.equals("partition", str) &&
                StringUtils.startsWith(array[i + 1], "by");
        if (a) {
            if (StringUtils.equals(array[i + 1], "by"))
                return 2;
            else
                return 1;
        } else {
            return 0;
        }
    }

    private static int distributed_by_hash(String str, String[] array, int i) {
        boolean a = StringUtils.equals("distributed", str) &&
                StringUtils.equals("by", array[i + 1]) &&
                StringUtils.startsWith(array[i + 2], "hash");
        if (a) {
            if (StringUtils.equals(array[i + 2], "hash"))
                return 2;
            else
                return 1;
        } else {
            return 0;
        }
    }

    private static int primaryKey(String str, String[] array, int i) {

        boolean a = StringUtils.equals("primary", str) &&
                StringUtils.startsWith(array[i + 1], "key");
        if (a) {
            if (StringUtils.equals(array[i + 1], "key"))
                return 2;
            else
                return 1;
        } else {
            return 0;
        }
    }

    private static boolean notNull(String str, String[] array, int i) {

        return StringUtils.equals("not", str) &&
                StringUtils.equals("null", array[i + 1]);
    }

    private static boolean ifNotExists(String str, String[] array, int i) {

        // 后续2个要是 not exists
        return StringUtils.equals("if", str) &&
                StringUtils.equals("not", array[i + 1]) &&
                StringUtils.equals("exists", array[i + 2]);
    }

    public static String preDeal(String s) {

        if (StringUtils.isBlank(s) || s.length() < 2) {
            return s;
        }
        if (s.startsWith("`") && s.endsWith("`")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    public boolean isSkipKuoHao() {
        return skipKuoHao;
    }

    public void setSkipKuoHao(boolean skipKuoHao) {
        this.skipKuoHao = skipKuoHao;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getStep() {
        return step;
    }

    //因为空格的原因，有些可能要变化
    public void setStep(int step) {
        this.step = step;
    }

    public static void main(String[] args) {

        System.out.println(col_type("int"));
        System.out.println(col_type("int(20)"));
        System.out.println(col_type("int(20,12)"));
        System.out.println(col_type("int(20,1,2)"));
    }
}
