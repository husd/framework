package com.husd.framework.redis;

import redis.clients.jedis.*;

import java.util.List;
import java.util.Map.Entry;

/**
 * how to delete big key in redis
 * <p>
 * from https://developer.aliyun.com/article/531067
 *
 * @author hushengdong
 */
public class DeleteBigKeyDemo {

    //1. Hash删除: hscan + hdel
    public void delBigHash(String host, int port, String password, String bigHashKey) {

        Jedis jedis = new Jedis(host, port);
        if (password != null && !"".equals(password)) {
            jedis.auth(password);
        }
        ScanParams scanParams = new ScanParams().count(100);
        String cursor = "0";
        do {
            ScanResult<Entry<String, String>> scanResult = jedis.hscan(bigHashKey, cursor, scanParams);
            List<Entry<String, String>> entryList = scanResult.getResult();
            if (entryList != null && !entryList.isEmpty()) {
                for (Entry<String, String> entry : entryList) {
                    jedis.hdel(bigHashKey, entry.getKey());
                }
            }
            cursor = scanResult.getCursor();
        } while (!"0".equals(cursor)); //删除bigkey
        jedis.del(bigHashKey);
        jedis.close();
    }

    public void pool(JedisPool jedisPool) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //具体的命令
            //jedis.executeCommand();
        } catch (Exception e) {
            //logger.error("op key {} error: " + e.getMessage(), key, e);
        } finally {
            //注意这里不是关闭连接，在JedisPool模式下，Jedis会被归还给资源池。
            if (jedis != null)
                jedis.close();
        }
    }

    //2. List删除: ltrim
    public void delBigList(String host, int port, String password, String bigListKey) {
        Jedis jedis = new Jedis(host, port);
        if (password != null && !"".equals(password)) {
            jedis.auth(password);
        }
        long llen = jedis.llen(bigListKey);
        int counter = 0;
        int left = 100;
        while (counter < llen) {
            //每次从左侧截掉100个
            jedis.ltrim(bigListKey, left, llen);
            counter += left;
        }
        //最终删除key
        jedis.del(bigListKey);
        jedis.close();
    }

    //3. Set删除: sscan + srem
    public void delBigSet(String host, int port, String password, String bigSetKey) {
        Jedis jedis = new Jedis(host, port);
        if (password != null && !"".equals(password)) {
            jedis.auth(password);
        }
        ScanParams scanParams = new ScanParams().count(100);
        String cursor = "0";
        do {
            ScanResult<String> scanResult = jedis.sscan(bigSetKey, cursor, scanParams);
            List<String> memberList = scanResult.getResult();
            if (memberList != null && !memberList.isEmpty()) {
                for (String member : memberList) {
                    jedis.srem(bigSetKey, member);
                }
            }
            cursor = scanResult.getCursor();
        } while (!"0".equals(cursor));

        //删除bigkey
        jedis.del(bigSetKey);
        jedis.close();
    }

    //4. SortedSet删除: zscan + zrem
    public void delBigZset(String host, int port, String password, String bigZsetKey) {
        Jedis jedis = new Jedis(host, port);
        if (password != null && !"".equals(password)) {
            jedis.auth(password);
        }
        ScanParams scanParams = new ScanParams().count(100);
        String cursor = "0";
        do {
            ScanResult<Tuple> scanResult = jedis.zscan(bigZsetKey, cursor, scanParams);
            List<Tuple> tupleList = scanResult.getResult();
            if (tupleList != null && !tupleList.isEmpty()) {
                for (Tuple tuple : tupleList) {
                    jedis.zrem(bigZsetKey, tuple.getElement());
                }
            }
            cursor = scanResult.getCursor();
        } while (!"0".equals(cursor));

        //删除bigkey
        jedis.del(bigZsetKey);
    }
}

