package com.husd.framework.ddl;

public enum DDLColumnType {
    
    CHAR(0,"String"),
    VARCHAR(1,"String"),
    BIGINT(2,"Long"),
    INT(3,"Integer"),
    TINYINT(4,"Short"),
    DATETIME(5,"Date"),
    TIMESTAMP(6,"Date")
    ;

    private int type;
    private String javaType;

    DDLColumnType(int type, String javaType) {
        this.type = type;
        this.javaType = javaType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
}
