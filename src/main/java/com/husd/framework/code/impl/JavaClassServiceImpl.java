package com.husd.framework.code.impl;

import com.husd.framework.code.JavaAutoCodeUtil;
import com.husd.framework.code.JavaClassService;
import com.husd.framework.code.JavaScopeEnum;
import com.husd.framework.code.JavaTypeEnum;

import java.util.List;

/**
 * @author hushengdong
 */
public class JavaClassServiceImpl implements JavaClassService {

    private static final String LINE_SEP = System.getProperty("line.separator");

    private static final String COMMA = ";";

    @Override
    public void addComment(StringBuilder text, String... comment) {

        if (comment == null || comment.length <= 0) {
            return;
        }
        text.append("    /** ").append(LINE_SEP);
        for (String s : comment) {
            text.append("     * ").append(s).append(LINE_SEP);
        }
        text.append("     */").append(LINE_SEP);
    }

    @Override
    public void addPackage(StringBuilder text, String... packageName) {

        if (packageName == null || packageName.length <= 0) {
            return;
        }
        for (String s : packageName) {
            text.append("package ").append(s).append(COMMA).append(LINE_SEP);
        }
    }

    @Override
    public void addImport(StringBuilder text, List<String> importList) {

        if (importList == null || importList.size() <= 0) {
            return;
        }
        for (String s : importList) {
            text.append("import ").append(s).append(COMMA).append(LINE_SEP);
        }
    }

    @Override
    public void addClass0(StringBuilder text, JavaTypeEnum typeEnum, String className) {

        text.append("public ");
        text.append(typeEnum.getName());
        text.append(" ");
        text.append(className);
        text.append(" {");
        text.append(LINE_SEP);
    }

    @Override
    public void addClass1(StringBuilder text) {

        text.append("}").append(LINE_SEP);
    }

    @Override
    public void addAttribute(StringBuilder text, JavaScopeEnum scopeEnum, String classType, String attrName) {

        //private String name;
        text.append("    ").append(scopeEnum.getName()).append(" ");
        text.append(classType).append(" ");
        text.append(attrName).append(COMMA).append(LINE_SEP);
    }

    @Override
    public void addGET_SET(StringBuilder text, String attrType, String attrName) {

        addSET(text, attrType, attrName);
        addGET(text, attrType, attrName);
    }

    private void addSET(StringBuilder text, String attrType, String attrName) {

        text.append("    public void set").append(JavaAutoCodeUtil.firstCharUpper(attrName));
        text.append("(").append(attrType).append(" ").append(attrName).append(") {").append(LINE_SEP);
        text.append("        this.").append(attrName).append(" = ").append(attrName).append(COMMA).append(LINE_SEP);
        text.append("    }").append(LINE_SEP);
    }

    private void addGET(StringBuilder text, String attrType, String attrName) {

        text.append("    public ").append(attrType).append(" get");
        text.append(JavaAutoCodeUtil.firstCharUpper(attrName)).append("() {").append(LINE_SEP);
        text.append("        return ").append(attrName).append(COMMA).append(LINE_SEP);
        text.append("    }").append(LINE_SEP);
    }
}
