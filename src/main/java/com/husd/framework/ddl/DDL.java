package com.husd.framework.ddl;

import com.husd.framework.code.DDLColumn;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class DDL {

    private String tableName;
    private String tableComment;
    private List<DDLColumn> columnList;

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {

        if(StringUtils.isNotBlank(this.tableName)) {
            throw new RuntimeException("table name已经设置过了 current:" + this.tableName + " new:" + tableName);
        }
        this.tableName = tableName;
    }

    public List<DDLColumn> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<DDLColumn> columnList) {
        this.columnList = columnList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DDL{");
        sb.append("tableName='").append(tableName).append('\'');
        sb.append(", tableComment='").append(tableComment).append('\'');
        sb.append(", columnList=").append(columnList);
        sb.append('}');
        return sb.toString();
    }
}
