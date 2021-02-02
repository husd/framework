package com.husd.framework.ddl;

import com.husd.framework.code.JavaAutoCode;
import com.husd.framework.code.JavaAutoCodeUtil;
import com.husd.framework.code.JavaFile;
import com.husd.framework.code.JavaTypeEnum;
import com.husd.framework.code.impl.JavaAutoCodeImpl;
import com.husd.framework.util.FileUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis代码生成器
 */
public class MybatisCodeGenerator {

    private static final boolean WRITE_FILE = false;

    private static final String DTO_PACKAGE_NAME = "cn.com.gome.scot.price.tag.model";
    private static final String DAO_PACKAGE_NAME = "cn.com.gome.scot.price.tag.dao";

    private static final String DTO_PATH = "/Users/hushengdong/hushengdong/git-price-tag/price-tag/price-tag-model/src/main/java/cn/com/gome/scot/price/tag/model/";
    private static final String DAO_PATH = "/Users/hushengdong/hushengdong/git-price-tag/price-tag/price-tag-dao/src/main/java/cn/com/gome/scot/price/tag/dao/";
    private static final String DAO_XML_PATH = "/Users/hushengdong/hushengdong/git-price-tag/price-tag/price-tag-dao/src/main/java/cn/com/gome/scot/price/tag/dao/impl/";
    private static final String DDL_SOURCE_FILE = "/Users/hushengdong/hushengdong/github-source/framework/src/main/java/com/husd/framework/ddl/src_ddl.sql";

    private static final String[] blacklist = {"price_shop_change"};

    public static void main(String[] args) {
        try {
            System.out.println("....................开始解析DDL....................");
            MybatisCodeGenerator mybatisCodeGenerator = new MybatisCodeGenerator();
            String sql = mybatisCodeGenerator.readDDLFromFile(DDL_SOURCE_FILE);
            String[] sqlArray = sql.split(";");
            for (String s : sqlArray) {
                DDL ddl = DDLParser.parseDDL(s);
                if (inBlacklist(ddl)) {
                    continue;
                }
                if (!checkDDL(ddl)) {
                    continue;
                }
                // 生成JAVA代码 和XML代码
                createMybatisJavaFile(ddl);
            }
            System.out.println("....................解析DDL结束....................");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static boolean checkDDL(DDL ddl) {

        return StringUtils.isNoneBlank(ddl.getTableName()) && ddl.getColumnList().size() > 0;
    }

    private static boolean inBlacklist(DDL ddl) {

        for (String s : blacklist) {
            if (StringUtils.equals(ddl.getTableName(), s)) {
                return true;
            }
        }
        return false;
    }

    private static void createMybatisJavaFile(DDL ddl) throws IOException {

        JavaAutoCode javaAutoCode = new JavaAutoCodeImpl();
        JavaFile javaFile = new JavaFile();
        javaFile.setType(JavaTypeEnum._CLASS);
        javaFile.setJavaTypeName(JavaAutoCodeUtil.firstCharUpper(JavaAutoCodeUtil.camel(ddl.getTableName())));
        if (StringUtils.isNotBlank(ddl.getTableComment())) {
            javaFile.setModelComment(ddl.getTableComment() + "(" + ddl.getTableName() + ")");
        } else {
            javaFile.setModelComment(ddl.getTableName());
        }
        javaFile.setAttributeList(ddl.getColumnList());
        javaFile.setMysqlTableName(ddl.getTableName());

        List<String> importList = new ArrayList<>();
        importList.add("java.util.Date");
        javaFile.setImportList(importList);

        javaFile.setFullClassName(DTO_PACKAGE_NAME + "." + javaFile.getDtoClassName());
        javaFile.setFullJavaClassDaoName(DAO_PACKAGE_NAME + "." + javaFile.getMybatisJavaClassName());

        javaFile.setPackageName(DTO_PACKAGE_NAME);
        String content = javaAutoCode.generateDto(javaFile).toString();
        if (WRITE_FILE) {
            FileUtil.write2File(content, DTO_PATH + javaFile.getModelFileName());
        }
        System.out.println("--------------Java dto---------------");
        System.out.println(content);

        javaFile.setPackageName(DAO_PACKAGE_NAME);
        String s2 = javaAutoCode.generateMybatisJava(javaFile);
        if (WRITE_FILE) {
            FileUtil.write2File(s2, DAO_PATH + javaFile.getMybatisJavaName());
        }
        System.out.println("--------------Java interface---------------");
        System.out.println(s2);

        String s3 = javaAutoCode.generateMybatisXml(javaFile);
        if (WRITE_FILE) {
            FileUtil.write2File(s3, DAO_XML_PATH + javaFile.getMybatisXmlName());
        }
        System.out.println("--------------xml---------------");
        System.out.println(s3);
    }

    private String readDDLFromFile(String path) throws IOException {

        if (StringUtils.isBlank(path)) {
            return "";
        }
        List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        for (String s : lines) {
            //要把逗号拆出来
            if (s.endsWith(",") || s.endsWith("，")) {
                sb.append(s.toLowerCase().substring(0, s.length() - 1));
                sb.append(" ");
                sb.append(",");
                sb.append(" ");
            } else {
                sb.append(s.toLowerCase());
                sb.append(" ");
            }
        }
        String str = sb.toString();
        //多个空格替换为1个空格
        str = str.replaceAll(" +", " ");
        // 中文逗号 替换为英文的
        //str = str.replaceAll("，",",");
        // 中文括号 替换为英文的括号
        str = str.replaceAll("（", "(");
        str = str.replaceAll("）", ")");
        if (AutoCodeUtil.DEBUG) {
            System.out.println("解析DDL文件结果 :" + str);
        }
        return str;
    }

}
