package com.husd.framework.model;

public class DefaultCacheValue {

    private String value;
    private long expiredSeconds;
    private long expiredTime;

    public DefaultCacheValue() {

    }

    public DefaultCacheValue(String value, long expiredSeconds, long expiredTime) {
        this.value = value;
        this.expiredSeconds = expiredSeconds;
        this.expiredTime = expiredTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getExpiredSeconds() {
        return expiredSeconds;
    }

    public void setExpiredSeconds(long expiredSeconds) {
        this.expiredSeconds = expiredSeconds;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }


}
