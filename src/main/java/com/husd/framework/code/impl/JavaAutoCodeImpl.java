package com.husd.framework.code.impl;

import com.husd.framework.code.DDLColumn;
import com.husd.framework.code.JavaAutoCode;
import com.husd.framework.code.JavaAutoCodeUtil;
import com.husd.framework.code.JavaFile;
import com.husd.framework.ddl.AutoCodeUtil;
import com.husd.framework.util.FileUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author hushengdong
 */
public class JavaAutoCodeImpl implements JavaAutoCode {

    private static final String LINE_SEP = System.getProperty("line.separator");

    @Override
    public StringBuilder generateDto(JavaFile javaFile) {

        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNoneBlank(javaFile.getPackageName())) {
            sb.append("package ").append(javaFile.getPackageName()).append(";").append(LINE_SEP);
        }
        for (String s : javaFile.getImportList()) {
            sb.append(s).append(LINE_SEP);
        }
        sb.append("public ").append(javaFile.getType().getName()).append(" ").append(javaFile.getJavaTypeName());
        sb.append("{ ").append(LINE_SEP);
        //写入属性值
        for (DDLColumn item : javaFile.getAttributeList()) {
            sb.append("    /** ").append(LINE_SEP);
            sb.append("     * ").append(item.getAttributeComment()).append(LINE_SEP);
            sb.append("     */").append(LINE_SEP);
            sb.append("     private ").append(item.getColumnType4Java()).append(" ").append(item.getColumnNameLowerCamel()).append(";").append(LINE_SEP);
        }

        //写入set get方法
        String t1 = "    public void set%s(%s %s) {";
        String t2 = "        this.%s = %s ;";

        String t3 = "    public %s get%s() {";
        String t4 = "        return %s;";

        for (DDLColumn item : javaFile.getAttributeList()) {
            sb.append(String.format(t1, AutoCodeUtil.firstUpper(item.getColumnNameLowerCamel()), item.getColumnType4Java(), item.getColumnNameLowerCamel()));
            sb.append(LINE_SEP);
            sb.append(String.format(t2, item.getColumnNameLowerCamel(), AutoCodeUtil.firstUpper(item.getColumnNameLowerCamel()))).append(LINE_SEP);
            sb.append("   }").append(LINE_SEP);

            sb.append(String.format(t3, item.getColumnType4Java(), AutoCodeUtil.firstUpper(item.getColumnNameLowerCamel()))).append(LINE_SEP);
            sb.append(String.format(t4, item.getColumnNameLowerCamel())).append(LINE_SEP);
            sb.append("   }").append(LINE_SEP);
        }
        sb.append("} ").append(LINE_SEP);

        return sb;
    }

    @Override
    public String generateMybatisJava(JavaFile javaFile) {
        FileUtil fileUtil = new FileUtil();
        String t = "/Users/hushengdong/hushengdong/github-source/framework/src/main/resources/template/mybatisJavaTemplate";
        String str = fileUtil.readStrFrom(t);
        str = str.replaceAll("@@package_name@@", javaFile.getPackageName());
        str = str.replaceAll("@@class_name@@", javaFile.getJavaTypeName());
        str = str.replaceAll("@@dto_name@@", JavaAutoCodeUtil.firstCharLower(javaFile.getJavaTypeName()));
        return str;
    }

    @Override
    public String generateMybatisXml(JavaFile javaFile) {

        FileUtil fileUtil = new FileUtil();
        String t = "/Users/hushengdong/hushengdong/github-source/framework/src/main/resources/template/mybatisXmlTemplate";
        String str = fileUtil.readStrFrom(t);
        str = str.replaceAll("@@namespace@@", javaFile.getPackageName() + ".dao." + javaFile.getJavaTypeName());
        str = str.replaceAll("@@class_name@@", javaFile.getJavaTypeName());
        str = str.replaceAll("@@resultMap@@", getResultMap(javaFile));
        str = str.replaceAll("@@insert_sql@@", getInsertSql(javaFile));
        return str;
    }

    private String getResultMap(JavaFile javaFile) {

        StringBuilder sb = new StringBuilder();
        sb.append("<resultMap id=\"" + javaFile.getJavaTypeName() + "ResultMap\" >").append(LINE_SEP);
        String resultMap = "<result column=\"%s\" property=\"%s\" />";
        for (DDLColumn ddlColumn : javaFile.getAttributeList()) {
            sb.append("    ");
            sb.append(String.format(resultMap, ddlColumn.getColumnName(), ddlColumn.getColumnNameLowerCamel()));
            sb.append(LINE_SEP);
        }
        sb.append("</resultMap>");
        sb.append(LINE_SEP);
        return sb.toString();
    }

    private String getInsertSql(JavaFile javaFile) {

        String dtoName = javaFile.getJavaTypeName();
        String tableName = "#{tblName}";
        StringBuilder sb = new StringBuilder();
        sb.append("insert into `" + tableName + "`(");
        sb.append(LINE_SEP);
        List<DDLColumn> columnList = javaFile.getAttributeList();
        for (int i = 0; i < columnList.size(); i++) {
            DDLColumn ddlColumn = columnList.get(i);
            if (ddlColumn.isId()) {
                continue;
            }
            sb.append("    ");
            if (i != columnList.size() - 1) {
                sb.append("`" + ddlColumn.getColumnName() + "`,");
            } else {
                sb.append("`" + ddlColumn.getColumnName() + "`");
            }
            sb.append(LINE_SEP);
        }
        sb.append("values (");
        sb.append(LINE_SEP);
        String columnVal = "#{item.%s}";
        for (int i = 0; i < columnList.size(); i++) {
            DDLColumn ddlColumn = columnList.get(i);
            if (ddlColumn.isId()) {
                continue;
            }
            sb.append("    ");
            if (i != columnList.size() - 1) {
                sb.append(String.format(columnVal, ddlColumn.getColumnNameLowerCamel()) + ",");
            } else {
                sb.append(String.format(columnVal, ddlColumn.getColumnNameLowerCamel()));
            }
            sb.append(LINE_SEP);
        }
        sb.append(")");
        sb.append(LINE_SEP);
        return sb.toString();
    }

}
