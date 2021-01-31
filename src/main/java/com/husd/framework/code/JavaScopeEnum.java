package com.husd.framework.code;

public enum JavaScopeEnum {

    _private("private"),
    _public("public"),
    _protected("protected"),

    ;

    private String name;

    JavaScopeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
