package com.husd.framework.code;

import java.util.List;

/**
 * java的各种类
 */
public interface JavaClassService {

    /**
     * 注释
     *
     * @param text
     * @param comment
     */
    public void addComment(StringBuilder text, String... comment);

    /**
     * package
     *
     * @param text
     * @param packageName
     */
    public void addPackage(StringBuilder text, String... packageName);

    /**
     * import
     *
     * @param text
     * @param importList 这个里面，得有所有的 import cn.com.Date;
     */
    public void addImport(StringBuilder text, List<String> importList);

    //从0开始，从1结束，有0必有1
    public void addClass0(StringBuilder text, JavaTypeEnum typeEnum,
                          String className);

    public void addClass1(StringBuilder text);

    /**
     * @param text
     * @param scopeEnum private
     * @param classType String
     * @param attrName  name
     */
    public void addAttribute(StringBuilder text, JavaScopeEnum scopeEnum,
                             String classType, String attrName);

    /**
     * get方法
     *
     * @param text
     * @param attrName 属性名字
     */
    public void addGET_SET(StringBuilder text, String attrType, String attrName);

}
