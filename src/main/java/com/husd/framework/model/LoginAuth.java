package com.husd.framework.model;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

public class LoginAuth {

    private String username;
    private String loginSeq;
    private String loginToken;
    private String ip;

    public LoginAuth() {}

    public LoginAuth(String username, String loginSeq, String loginToken, String ip) {
        this.username = username;
        this.loginSeq = loginSeq;
        this.loginToken = loginToken;
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginSeq() {
        return loginSeq;
    }

    public void setLoginSeq(String loginSeq) {
        this.loginSeq = loginSeq;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public boolean equalsWithAnother(LoginAuth other) {
        if (other == null) {
            return false;
        }
        return StringUtils.isNotBlank(username) && StringUtils.isNotBlank(loginSeq)
                && StringUtils.isNotBlank(loginToken)
                && StringUtils.equals(username, other.getUsername())
                && StringUtils.equals(loginSeq, other.getLoginSeq())
                && StringUtils.equals(ip, other.getIp())
                && StringUtils.equals(loginToken, other.getLoginToken());
    }

    // 登陆时候如果token不一样或者ip不一样，则有可能是被盗了cookie的值。
    public boolean isPasswordMayBeStolen(LoginAuth other) {
        if (other == null) {
            return false;
        }
        return StringUtils.isNotBlank(username) && StringUtils.isNotBlank(loginSeq)
                && StringUtils.isNotBlank(loginToken)
                && StringUtils.equals(username, other.getUsername())
                && StringUtils.equals(loginSeq, other.getLoginSeq())
                && (!StringUtils.equals(loginToken, other.getLoginToken())
                        || !StringUtils.equals(ip, other.getIp()));
    }

}
