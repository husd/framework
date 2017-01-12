package com.husd.framework.cache.impl;

import com.husd.framework.cache.ICacheService;

/**
 * 使用redis来实现的缓存服务。停机后不消失。
 * 
 * @author hushengdong
 *
 */
public class RedisCacheServiceImpl implements ICacheService {

    @Override
    public String getValueByName(String name) {
        return "";
    }

    @Override
    public String setValueByName(String name, String value, long expiredSeconds) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeName(String name) {}

    @Override
    public void updateExpiredTime(String name, long expiredTime) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setValueByName(String name, String value) {
        // TODO Auto-generated method stub

    }

}
