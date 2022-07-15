/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar;

public class CosmoException extends Exception {
    String[] params;

    public CosmoException() {
        super();
    }

    public CosmoException(String msg) {
        super(msg);
    }

    public CosmoException(String msg, Throwable t) {
        super(msg, t);
    }

    public CosmoException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;

    }

    public CosmoException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}
