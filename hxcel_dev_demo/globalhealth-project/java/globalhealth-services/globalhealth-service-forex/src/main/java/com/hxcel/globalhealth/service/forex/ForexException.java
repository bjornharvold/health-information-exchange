package com.hxcel.globalhealth.service.forex;

/**
 * User: Bjorn Harvold
 * Date: Jul 6, 2007
 * Time: 7:52:14 AM
 */
public class ForexException extends Exception {
    String[] params;

    public ForexException() {
        super();
    }

    public ForexException(String msg) {
        super(msg);
    }

    public ForexException(String msg, Throwable t) {
        super(msg, t);
    }

    public ForexException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;

    }

    public ForexException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}
