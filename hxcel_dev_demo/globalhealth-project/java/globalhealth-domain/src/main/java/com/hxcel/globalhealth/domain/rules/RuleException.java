/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.rules;


/**
 * User: Bjorn Harvold
 * Date: Nov 25, 2005
 * Time: 10:19:33 AM

 * <p/>
 * Description:
 */
public class RuleException extends Exception {
    String[] params;

    public RuleException() {
        super();
    }

    public RuleException(String msg) {
        super(msg);
    }

    public RuleException(String msg, Throwable t) {
        super(msg, t);
    }

    public RuleException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;

    }

    public RuleException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}
