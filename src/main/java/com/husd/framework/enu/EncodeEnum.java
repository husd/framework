package com.husd.framework.enu;

public enum EncodeEnum {

    UTF8("UTF-8");

    private String name;

    private EncodeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
