package com.husd.framework.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class CookieUtil {

    public static String getCookieByName(HttpServletRequest request, String name) {
        Cookie[] cookieArr = request.getCookies();
        if (cookieArr == null || cookieArr.length == 0) {
            return StringUtils.EMPTY;
        }
        for (Cookie cookie : cookieArr) {
            if (StringUtils.equals(name, cookie.getName())) {
                return cookie.getValue();
            }
        }
        return StringUtils.EMPTY;
    }

    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }
}
