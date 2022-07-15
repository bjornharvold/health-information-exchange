/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.admin;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author Bjorn Harvold
 */

public class AdminException extends Exception {
    String[] params;

    public AdminException() {
        super();
    }

    public AdminException(String msg) {
        super(msg);
    }

    public AdminException(String msg, Throwable t) {
        super(msg, t);
    }

    public AdminException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;

    }

    public AdminException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}
