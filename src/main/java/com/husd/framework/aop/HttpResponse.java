package com.husd.framework.aop;

import com.husd.framework.model.BooleanMessage;

/**
 * 返回信息
 *
 * @author hushengdong
 */
public class HttpResponse<T> extends BooleanMessage {

    protected T data;

    public HttpResponse(boolean succ, String msg) {
        super(succ, msg);
    }

    public HttpResponse(boolean succ, String msg, int code) {
        super(succ, msg, code);
    }

    public static HttpResponse fail(int code, String msg) {

        return new HttpResponse(false, msg, code);
    }

    public static HttpResponse notAuth() {

        return new HttpResponse(false, "没有登录，请先登录", 101);
    }
}
