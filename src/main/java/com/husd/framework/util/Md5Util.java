package com.husd.framework.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.husd.framework.model.EncodeEnum;

public class Md5Util {

    private final static Logger LOGGER = LoggerFactory.getLogger(Md5Util.class);

    public static String encode(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes(EncodeEnum.UTF8.getName()));
        } catch (Exception e) {
            // ignore this
            LOGGER.error("[md5] encode error, str is {}", str);
        }
        return new BigInteger(1, md.digest()).toString(16);
    }

    public static void main(String[] args)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String a = "123";
        String b = Md5Util.encode(a);
        System.out.println(b);
    }
}
