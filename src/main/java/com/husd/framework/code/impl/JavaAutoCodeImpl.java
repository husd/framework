package com.husd.framework.code.impl;

import com.husd.framework.code.*;
import com.husd.framework.util.FileUtil;

import java.util.List;

/**
 * @author hushengdong
 */
public class JavaAutoCodeImpl implements JavaAutoCode {

    private static final String LINE_SEP = System.getProperty("line.separator");

    private final String TEMPLATE_MYBATIS_JAVA_INTERFACE = "/Users/hushengdong/hushengdong/github-source/framework/src/main/resources/template/mybatisJavaTemplate";

    private final String TEMPLATE_MYBATIS_XML = "/Users/hushengdong/hushengdong/github-source/framework/src/main/resources/template/mybatisXmlTemplate";


    @Override
    public StringBuilder generateDto(JavaFile javaFile) {

        JavaClassService javaClassService = new JavaClassServiceImpl();
        StringBuilder sb = new StringBuilder();
        //package
        javaClassService.addPackage(sb, javaFile.getPackageName());
        //import
        javaClassService.addImport(sb, javaFile.getImportList());
        //注释
        javaClassService.addComment(sb, javaFile.getModelComment());
        //类开始
        javaClassService.addClass0(sb, javaFile.getType(), javaFile.getJavaTypeName());
        //写入属性值
        for (DDLColumn item : javaFile.getAttributeList()) {
            javaClassService.addComment(sb, item.getAttributeComment());
            javaClassService.addAttribute(sb, JavaScopeEnum._private,
                    item.getColumnType4Java(), item.getColumnNameLowerCamel());
        }
        for (DDLColumn item : javaFile.getAttributeList()) {
            //写入set get方法
            javaClassService.addGET_SET(sb, item.getColumnType4Java(), item.getColumnNameLowerCamel());
        }
        //类结束
        javaClassService.addClass1(sb);
        return sb;
    }

    @Override
    public String generateMybatisJava(JavaFile javaFile) {
        FileUtil fileUtil = new FileUtil();
        String str = fileUtil.readStrFrom(TEMPLATE_MYBATIS_JAVA_INTERFACE);
        str = str.replaceAll("@@package_name@@", javaFile.getPackageName());
        str = str.replaceAll("@@model_comment@@", javaFile.getModelComment());
        str = str.replaceAll("@@class_name@@", javaFile.getDtoClassName());
        str = str.replaceAll("@@mybatis_java_class_name@@", javaFile.getMybatisJavaClassName());
        str = str.replaceAll("@@full_class_name@@", javaFile.getFullClassName());
        str = str.replaceAll("@@dto_name@@", JavaAutoCodeUtil.firstCharLower(javaFile.getJavaTypeName()));
        return str;
    }

    @Override
    public String generateMybatisXml(JavaFile javaFile) {

        FileUtil fileUtil = new FileUtil();
        String str = fileUtil.readStrFrom(TEMPLATE_MYBATIS_XML);
        str = str.replaceAll("@@namespace@@", javaFile.getFullJavaClassDaoName());
        str = str.replaceAll("@@class_name@@", javaFile.getJavaTypeName());
        str = str.replaceAll("@@resultMap@@", getResultMap(javaFile));
        str = str.replaceAll("@@insert_sql@@", getInsertSql(javaFile));
        return str;
    }

    private String getResultMap(JavaFile javaFile) {

        StringBuilder sb = new StringBuilder();
        sb.append("<resultMap id=\"" + javaFile.getJavaTypeName() + "ResultMap\" ");
        sb.append("type = ").append("\"").append(javaFile.getFullClassName()).append("\"");
        sb.append(">").append(LINE_SEP);
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
