package com.husd.framework.cache;


/**
 * 主要提供了访问缓存的接口，获取缓存，清理缓存，更新缓存里面的值等。 缓存具有失效性，单位为秒。
 * 
 * @author hushengdong
 *
 */
public interface ICacheService {

    /**
     * 根据name的值获取value值，如果缓存已经过期了，会返回空值， 并自动清理过期的缓存值。
     * 
     * @param name
     * @return
     */
    public String getValueByName(String name);

    /**
     * 设置缓存的值。如果name已经存在，会覆盖原来的值，并返回旧的值，如果name不存在，返回当前的value值。
     * 
     * @param name
     * @param value
     * @param expiredSeconds 过期时间，秒数。
     * @return
     */
    public String setValueByName(String name, String value, long expiredSeconds);

    public void setValueByName(String name, String value);

    /**
     * 删除缓存里面的值。
     * 
     * @param name
     * @return
     */
    public void removeName(String name);

    public void updateExpiredTime(String name, long expiredTime);

}
