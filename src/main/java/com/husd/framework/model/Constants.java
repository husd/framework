package com.husd.framework.model;

import java.util.concurrent.TimeUnit;

public class Constants {

    public final static int ONE_DAY_SECONDS = (int) TimeUnit.DAYS.toSeconds(1);
    // 15分钟的秒数，900
    public final static int MINUTES15 = (int) TimeUnit.MINUTES.toSeconds(15);

    // 登陆过期时间,超过这么长时间不操作将会登陆失效，单位是秒。
    public final static int LOING_EXPIRED_TIME = MINUTES15;
}
