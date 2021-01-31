package com.husd.framework.code;

import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 假设表是 商品明细表 sku_info
 *
 * @author hushengdong
 */
public class JavaFile {

    private String pre = "Dto";

    //class
    private JavaTypeEnum type;
    //SkuInfo
    private String javaTypeName;
    private List<DDLColumn> attributeList;
    //这个是包名，但是包对于每个java类不一样。
    private String packageName;
    //
    private List<String> importList;

    // cn.com.SkuInfo
    private String fullClassName;

    //cn.com.SkuInfoDao
    private String fullJavaClassDaoName;

    //表的名字 sku_info
    private String mysqlTableName;

    // model表的注释 商品明细表(sku_info)
    private String modelComment;

    public String getModelComment() {
        return modelComment;
    }

    public void setModelComment(String modelComment) {
        this.modelComment = modelComment;
    }

    public String getMysqlTableName() {
        return mysqlTableName;
    }

    public void setMysqlTableName(String mysqlTableName) {

        this.mysqlTableName = mysqlTableName;
    }

    public String getFullJavaClassDaoName() {
        return fullJavaClassDaoName;
    }

    public void setFullJavaClassDaoName(String fullJavaClassDaoName) {
        this.fullJavaClassDaoName = fullJavaClassDaoName;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }

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

    //DtoSkuInfo.java
    public String getModelFileName() {

        return pre + javaTypeName + ".java";
    }

    //DtoSkuInfo
    public String getDtoClassName() {

        return pre + javaTypeName;
    }

    //SkuInfoDao.java
    public String getMybatisJavaName() {

        return javaTypeName + "Dao.java";
    }

    //SkuInfoDao
    public String getMybatisJavaClassName() {

        return javaTypeName + "Dao";
    }

    //SkuInfoMapper.xml
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
