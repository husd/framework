package com.husd.framework.excel;

/**
 * @description: POI Excel异常
 */
public class POIException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 5844669606522423872L;

    /**
     * @param message
     */
    public POIException(String message) {

        super(message);
    }

    /**
     * @param message
     */
    public POIException(String message, Object... args) {

        super(String.format(message, args));
    }

    /**
     * @param cause
     */
    public POIException(Throwable cause) {

        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public POIException(String message, Throwable cause) {

        super(message, cause);
    }

}
