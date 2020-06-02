package com.husd.framework.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Util {

    private final static Logger LOGGER = LoggerFactory.getLogger(Md5Util.class);

    public static String encode(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes("UTF-8"));
        } catch (Exception e) {
            // ignore this
            LOGGER.error("[md5] encode error, str is {}", str);
        }
        return new BigInteger(1, md.digest()).toString(16);
    }

}
