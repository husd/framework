package com.husd.framework.ddl;

import java.util.HashMap;
import java.util.Map;

public class AutoCodeUtil {

    public static Map<String,String> col2JavaTypeMap = new HashMap<>();

    public static String [] cols = {"bit","tinyint","smallint","mediumint","int",
            "bigint","float","double","decimal","date","datetime",
            "timestamp","time","year","char","varchar","binary","varbinary",
            "tiny text","text","medium text","long text","tinyblob","blob","mediumblob",
            "longblob","enum","set","geometry","point","linestring","polygon","multipoint",
            "multilinestring","multipolygon","geometrycollection"
    };

    //一些不识别的类型，直接用原来的类型，直接报错就行了。
    public static String [] cols_java = {"Integer","Short","Short","Short","Integer",
            "Long","Float","Double","BigDecimal","Date","Date",
            "Date","Date","String","Char","String","binary","varbinary",
            "String","String","String","String","tinyblob","blob","mediumblob",
            "longblob","enum","set","geometry","point","linestring","polygon","multipoint",
            "multilinestring","multipolygon","geometrycollection"
    };

    static {
        for(int i = 0;i < cols.length;i++) {
            col2JavaTypeMap.put(cols[i],cols_java[i]);
        }
    }

    public static final boolean DEBUG = true;

    public static String getJavaType(String col) {

        return col2JavaTypeMap.getOrDefault(col,col);
    }

}
