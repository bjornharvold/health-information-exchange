/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.web;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author Bjorn Harvold
 */

public class WebException extends Exception {
    String[] params;

    public WebException() {
        super();
    }

    public WebException(String msg) {
        super(msg);
    }

    public WebException(String msg, Throwable t) {
        super(msg, t);
    }

    public WebException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;

    }

    public WebException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}
