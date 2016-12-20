package com.husd.framework.model;

import com.google.gson.Gson;

public class BooleanMessage {

    private boolean succ;

    private String msg;

    private int code;

    public BooleanMessage(boolean succ, String msg) {
        this.succ = succ;
        this.msg = msg;
    }

    public BooleanMessage(boolean succ, String msg, int code) {
        this.succ = succ;
        this.msg = msg;
        this.code = code;
    }

    public boolean isSucc() {
        return succ;
    }

    public void setSucc(boolean succ) {
        this.succ = succ;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

}
