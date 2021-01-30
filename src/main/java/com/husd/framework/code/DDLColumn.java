package com.husd.framework.code;

import com.google.common.base.CaseFormat;
import com.husd.framework.ddl.AutoCodeUtil;

public class DDLColumn {
    /**
     * 列名字
     */
    private String columnName;
    /**
     * 驼峰名字
     */
    private String columnNameLowerCamel;
    /**
     * 列类型
     */
    private String columnType;
    /**
     * 是否是主键ID
     */
    private boolean id = false;
    /**
     * 注释
     */
    private String comment;
    /**
     * 默认值
     */
    private String defaultVal;
    /**
     * 是否是not null
     */
    private boolean notNull;

    public String getAttributeName() {

        return this.columnName;
    }

    public String getAttributeType() {

        return this.columnType;
    }

    public String getAttributeComment() {

        return this.comment;
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public String getColumnNameLowerCamel() {
        return columnNameLowerCamel;
    }

    public void setColumnNameLowerCamel(String columnNameLowerCamel) {
        this.columnNameLowerCamel = columnNameLowerCamel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {

        this.comment = comment.replaceAll("'", "");
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
        this.columnNameLowerCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName);
    }

    public String getColumnType() {
        return columnType;
    }

    public String getColumnType4Java() {

        return AutoCodeUtil.getJavaType(columnType);
    }

    public void setColumnType(String columnType) {
        // 如果包含左括号，就处理下
        if (columnType.contains("(")) {
            columnType = columnType.substring(0, columnType.indexOf("("));
        }
        this.columnType = columnType;
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DDLColumn{");
        sb.append("columnName='").append(columnName).append('\'');
        sb.append(", columnType=").append(columnType);
        sb.append(", id=").append(id);
        sb.append(", comment='").append(comment).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getAttributeNameFirstUpper() {

        return JavaAutoCodeUtil.firstCharUpper(getAttributeName());
    }
}
