/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar;

public class CalendarException extends Exception {
    String[] params;

    public CalendarException() {
        super();
    }

    public CalendarException(String msg) {
        super(msg);
    }

    public CalendarException(String msg, Throwable t) {
        super(msg, t);
    }

    public CalendarException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;
    }

    public CalendarException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}
