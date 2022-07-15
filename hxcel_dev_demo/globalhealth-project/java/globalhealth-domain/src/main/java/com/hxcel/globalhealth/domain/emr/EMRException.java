/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.emr;

/**
 * User: bjorn
 * Date: Nov 8, 2007
 * Time: 6:21:55 PM
 */
public class EMRException extends Exception {
    String[] params;

    public EMRException() {
        super();
    }

    public EMRException(String msg) {
        super(msg);
    }

    public EMRException(String msg, Throwable t) {
        super(msg, t);
    }

    public EMRException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;
    }

    public EMRException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}
