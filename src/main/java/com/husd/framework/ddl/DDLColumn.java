package com.husd.framework.ddl;

import com.google.common.base.CaseFormat;

public class DDLColumn {

    /**
     * 列名字
     */
    private String columnName;
    /**
     * 驼峰名字
     */
    private String columnNameLowerCamel;
    private String columnType;
    private boolean id = false;
    /**
     * 注释
     */
    private String comment;

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

        this.comment = comment.replaceAll("'","");
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

    public void setColumnType(String columnType) {
        // 如果包含左括号，就处理下
        if(columnType.contains("(")) {
            columnType = columnType.substring(0,columnType.indexOf("("));
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
}
