package com.husd.framework.code;

import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hushengdong
 */
public class JavaFile {

    private String pre = "Dto";

    private JavaTypeEnum type;
    private String javaTypeName;
    private List<DDLColumn> attributeList;
    private String packageName;
    private List<String> importList;

    public String getJavaTypeName() {

        if (StringUtils.isBlank(pre)) {
            return javaTypeName;
        } else {
            return pre + javaTypeName;
        }

    }

    public void setJavaTypeName(String javaTypeName) {

        this.javaTypeName = javaTypeName;
    }

    public String getDtoName() {

        return "Dto" + javaTypeName + ".java";
    }

    public String getMybatisJavaName() {

        return javaTypeName + "Dao.java";
    }

    public String getMybatisXmlName() {

        return javaTypeName + "Mapper.xml";
    }

    public List<String> getImportList() {

        if (this.importList == null) {
            return new ArrayList<>();
        }
        return importList;
    }

    public void setImportList(List<String> importList) {
        this.importList = importList;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public JavaTypeEnum getType() {
        return type;
    }

    public void setType(JavaTypeEnum type) {
        this.type = type;
    }

    public List<DDLColumn> getAttributeList() {

        if (this.attributeList == null) {
            return new ArrayList<>();
        }
        return attributeList;
    }

    public void setAttributeList(List<DDLColumn> attributeList) {
        this.attributeList = attributeList;
    }

}
