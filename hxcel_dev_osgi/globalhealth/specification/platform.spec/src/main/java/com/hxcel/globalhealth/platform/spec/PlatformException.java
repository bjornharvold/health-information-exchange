package com.hxcel.globalhealth.platform.spec;

/**
 * Created by IntelliJ IDEA.
 * User: crash
 * Date: Dec 11, 2008
 * Time: 6:55:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlatformException extends Exception {
    String[] params;

    public PlatformException() {
        super();
    }

    public PlatformException(String msg) {
        super(msg);
    }

    public PlatformException(String msg, Throwable t) {
        super(msg, t);
    }

    public PlatformException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;

    }

    public PlatformException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}
