package com.husd.framework.model;

public enum CookieEnum {

    USER_NAME("_uc_name", "用户名"), USER_LOGIN_SEQ("_uc_login_seq", "用户登陆序列"), USER_LOGIN_TOKEN(
            "_uc_login_token", "用户登陆cookie"), USER_COOKIE("_uc", "判断是否登陆的cookie");

    private CookieEnum(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }

    private String name;

    private String msg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
