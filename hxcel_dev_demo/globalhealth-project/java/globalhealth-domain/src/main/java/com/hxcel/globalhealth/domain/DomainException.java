/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain;

/**
 * User: Bjorn Harvold
 * Date: Jul 5, 2007
 * Time: 6:09:15 PM
 */
public class DomainException extends Exception {
    String[] params;

    public DomainException() {
        super();
    }

    public DomainException(String msg) {
        super(msg);
    }

    public DomainException(String msg, Throwable t) {
        super(msg, t);
    }

    public DomainException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;
    }

    public DomainException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}
