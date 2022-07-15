/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.exception;

public class GlobalhealthException extends Exception {
    String[] params;

    public GlobalhealthException() {
        super();
    }

    public GlobalhealthException(String key) {
        super(key);
    }

    public GlobalhealthException(String key, Throwable t) {
        super(key, t);
    }

    public GlobalhealthException(String key, Throwable t, String... params) {
        this.params = params;
    }

    public GlobalhealthException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}
