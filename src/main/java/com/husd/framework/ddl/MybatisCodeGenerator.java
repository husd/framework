package com.husd.framework.ddl;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * mybatis代码生成器
 */
public class MybatisCodeGenerator {

    public static void main(String[] args) {

        try {
            System.out.println("....................开始解析DDL....................");
            String osName = System.getProperty("os.name");
            String src = "D:\\git-source\\framework\\src\\main\\java\\com\\husd\\framework\\ddl\\src_ddl.sql";
            if (osName.contains("Mac")) {
                src = "/Users/hushengdong/hushengdong/github-source/framework/src/main/java/com/husd/framework/ddl/src_ddl.sql";
            }
            MybatisCodeGenerator mybatisCodeGenerator = new MybatisCodeGenerator();
            String sql = mybatisCodeGenerator.readDDLFromFile(src);
            DDL ddl = DDLParser.parseDDL(sql);
            // 生成JAVA代码 和XML代码
            createMybatisJava(ddl);
            createMybatisXML(ddl);
            System.out.println("....................解析DDL结束....................");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void createMybatisXML(DDL ddl) {

        MybatisJavaAutoUtil.createXml(ddl);
    }

    private static void createMybatisJava(DDL ddl) {

        MybatisJavaAutoUtil.createModel(ddl);
    }

    private String readDDLFromFile(String path) throws IOException {

        if(StringUtils.isBlank(path)) {
            return "";
        }
        //String fileName = this.getClass().getClassLoader().getResource(path).getPath();
        List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        for(String s : lines) {
            //要把逗号拆出来
            if(s.endsWith(",") || s.endsWith("，")) {
                sb.append(s.toLowerCase().substring(0,s.length() -1));
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
        str = str.replaceAll(" +"," ");
        // 中文逗号 替换为英文的
        //str = str.replaceAll("，",",");
        // 中文括号 替换为英文的括号
        str = str.replaceAll("（","(");
        str = str.replaceAll("）",")");
        if(AutoCodeUtil.DEBUG) {
            System.out.println("解析DDL文件结果 :" + str);
        }
        return str;
    }


}
