package com.husd.framework.code;

/**
 * @author hushengdong
 */
public enum JavaTypeEnum {

    _INTERFACE("interface"),
    _CLASS("class"),
    _ANNOTATION("annotation"),
    _ENUM("enum"),
    ;

    private String name;

    JavaTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
