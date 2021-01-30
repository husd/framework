package com.husd.framework.code;

public enum JavaAttributeEnum {

    _int("int"),
    _short("short"),
    _byte("byte"),
    _char("char"),
    _long("long"),
    _double("double"),
    _float("float"),

    _string("String");

    private String name;


    JavaAttributeEnum(String name) {
        this.name = name;
    }

    public String getBoxName() {

        if (this == _int) {
            return "Integer";
        }
        return JavaAutoCodeUtil.firstCharUpper(this.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
