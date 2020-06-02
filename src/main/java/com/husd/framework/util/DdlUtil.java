package com.husd.framework.util;

import java.util.List;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 * @date 2020/6/2
 */
public class DdlUtil {

    public void ddl(List<String> lines) {

        String tableName = getTableName(lines.get(0));
        String tableComment = getTableComment(lines.get(lines.size() - 1));
        System.out.println(tableName + "(" + tableComment + ")");
        System.out.println("列 " + "类型 " + " comment " + " 备注");
        for (int i = 0; i < lines.size(); i++) {
            String str = lines.get(i);
            str = str.trim();
            str = str.replaceAll(" ", "");
            if (i == 0 || i == lines.size() - 1) {
                continue;
            }
            if (str.startsWith("PRIMARY") || str.startsWith("KEY") || str.startsWith("UNIQUE")) {
                continue;
            }
            String columnName = getStrBetween(str, "`", "`");
            String columnTypeStr = getStrBetween(str, columnName + "`", "COMMENT'");
            String columnType = getColumnType(columnTypeStr);
            String columnComment = getStrBetween(str, "COMMENT'", "',");
            System.out.println(columnName + " " + columnType + " " + columnComment + " ");
        }
    }

    private String getColumnType(String str) {

        if (str.contains("TEXT") || str.contains("text")) {
            return "TEXT";
        }
        if (str.contains("(") && str.contains(")")) {
            return str.substring(0, str.indexOf(")") + 1);
        }
        int end = str.indexOf("NOT");
        if (end <= 0) {
            end = str.indexOf("DEFAULT");
        }
        if (end <= 0) {
            return "";
        }
        return str.substring(0, end);
    }

    private String getTableName(String str) {

        return getStrBetween(str, "`", "`");
    }

    private String getTableComment(String str) {

        return getStrBetween(str, "COMMENT='", "';");
    }

    private String getStrBetween(String str, String separator1, String separator2) {

        int start = str.indexOf(separator1) + separator1.length() - 1;
        int end = str.indexOf(separator2, start + 1);
        start++;

        int len = str.length();
        if (start <= 0) {
            start = 0;
        }
        if (start >= len) {
            start = len - 1;
        }

        if (end <= 0) {
            end = 0;
        }
        if (end >= len) {
            end = len - 1;
        }

        if (end - start <= 0) {
            return "";
        }

        return str.substring(start, end);
    }
}
