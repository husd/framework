package com.husd.framework.outofmemory;

import java.io.Serializable;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class OOMObj implements Serializable {

    private String name;

    private String name2;

    public OOMObj(String name, String name2) {
        this.name = name;
        this.name2 = name2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
}
