/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain;

/**
 * User: bjorn
 * Date: Jun 5, 2008
 * Time: 12:40:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ModelException extends Throwable {
    String[] params;

    public ModelException() {
        super();
    }

    public ModelException(String msg) {
        super(msg);
    }

    public ModelException(String msg, Throwable t) {
        super(msg, t);
    }

    public ModelException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;
    }

    public ModelException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}
