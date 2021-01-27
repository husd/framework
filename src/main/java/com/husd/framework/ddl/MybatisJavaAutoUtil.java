package com.husd.framework.ddl;

/**
 * 生成Java代码的
 */
public class MybatisJavaAutoUtil {

    public static void createModel(DDL ddl) {

        String template = "private %s %s;";
        System.out.println("--------------------- tableName -----------------------");
        System.out.println(ddl.getTableName());
        System.out.println(AutoCodeUtil.getCamelName(ddl.getTableName()));
        System.out.println("--------------------- model -----------------------");
        for (DDLColumn ddlColumn : ddl.getColumnList()) {
            String javaType = convert2JavaType(ddlColumn.getColumnType());
            System.out.println("/**");
            System.out.println(" * " + ddlColumn.getComment());
            System.out.println(" */");
            System.out.println(String.format(template, javaType, ddlColumn.getColumnNameLowerCamel()));
        }
    }

    private static String convert2JavaType(String columnType) {

        return AutoCodeUtil.getJavaType(columnType);
    }

    public static void createXml(DDL ddl) {

        System.out.println("--------------------- xml result map -----------------------");
        System.out.println("<resultMap id=\"" + ddl.getTableName() + "ResultMap\"");
        String resultMap = "<result column=\"%s\" property=\"%s\" />";
        for (DDLColumn ddlColumn : ddl.getColumnList()) {
            System.out.println("    " + String.format(resultMap, ddlColumn.getColumnName(), ddlColumn.getColumnNameLowerCamel()));
        }
        System.out.println("</resultMap>");
        System.out.println("--------------------- xml insert sql -----------------------");
        String s3 = AutoCodeUtil.firstUpper(AutoCodeUtil.getCamelName(ddl.getTableName()));
        String t2 = "public int insert%s (Dto%s dto%s);";
        System.out.println(String.format(t2, s3, s3, s3));
        System.out.println(s3 + "Mapper.xml");
        System.out.println("<insert id=\"insert" + s3 + "\" useGeneratedKeys=\"true\" keyProperty=\"\" parameterType=\"\">\n");
        System.out.println("insert into `" + ddl.getTableName() + "`(");
        for (int i = 0; i < ddl.getColumnList().size(); i++) {
            DDLColumn ddlColumn = ddl.getColumnList().get(i);
            if (i != ddl.getColumnList().size() - 1) {
                System.out.println("`" + ddlColumn.getColumnName() + "`,");
            } else {
                System.out.println("`" + ddlColumn.getColumnName() + "`");
            }
        }
        System.out.println("values (");
        String columnVal = "#{item.%s}";
        for (int i = 0; i < ddl.getColumnList().size(); i++) {
            DDLColumn ddlColumn = ddl.getColumnList().get(i);
            if (i != ddl.getColumnList().size() - 1) {
                System.out.println(String.format(columnVal, ddlColumn.getColumnNameLowerCamel()) + ",");
            } else {
                System.out.println(String.format(columnVal, ddlColumn.getColumnNameLowerCamel()));
            }
        }
        System.out.println(")");
        System.out.println("</insert>");
    }
}
