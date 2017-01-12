package com.husd.framework.cache.impl;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.husd.framework.cache.ICacheService;
import com.husd.framework.model.DefaultCacheValue;

/**
 * jvm实现的缓存服务，停机后消失。
 * 
 * @author hushengdong
 *
 */
@Service(value = "cacheService")
public class DefaultCacheServiceImpl implements ICacheService {

    private final static Map<String, DefaultCacheValue> cache = new ConcurrentHashMap<String, DefaultCacheValue>(32);

    @Override
    public String getValueByName(String name) {
        DefaultCacheValue value = cache.get(name);
        if (value == null) {
            return StringUtils.EMPTY;
        }
        long currentTime = System.currentTimeMillis();
        long expectedExpiredTime = value.getExpiredTime();
        if (currentTime > expectedExpiredTime) {
            cache.remove(name);
            return StringUtils.EMPTY;
        } else {
            return value.getValue();
        }
    }

    @Override
    public String setValueByName(String name, String value, long expiredSeconds) {
        long currentTime = System.currentTimeMillis();
        long expectedExpiredTime = currentTime + expiredSeconds * 1000;
        DefaultCacheValue cachedValue = cache.get(name);
        String valueToReturn = value;
        if (cachedValue == null) {
            cachedValue = new DefaultCacheValue();
        } else {
            valueToReturn = cachedValue.getValue();
        }
        cachedValue.setValue(value);
        cachedValue.setExpiredSeconds(expiredSeconds);
        cachedValue.setExpiredTime(expectedExpiredTime);
        cache.put(name, cachedValue);
        return valueToReturn;
    }

    @Override
    public void removeName(String name) {
        if (cache.containsKey(name)) {
            cache.remove(name);
        }
    }

    @Override
    public void updateExpiredTime(String name, long expiredTime) {
        DefaultCacheValue value = cache.get(name);
        if (value != null) {
            value.setExpiredTime(expiredTime);
            cache.put(name, value);
        }
    }

    @Override
    public void setValueByName(String name, String value) {
        DefaultCacheValue valueCache = cache.get(name);
        if (valueCache != null) {
            valueCache.setValue(value);
            cache.put(name, valueCache);
        }
    }

}
