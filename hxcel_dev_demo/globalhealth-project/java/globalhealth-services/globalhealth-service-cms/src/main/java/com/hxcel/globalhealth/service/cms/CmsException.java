package com.hxcel.globalhealth.service.cms;

/**
 * User: bjorn
 * Date: Oct 1, 2008
 * Time: 1:34:50 PM
 */
public class CmsException extends Exception {
    String[] params = null;

    public CmsException() {
        super();
    }

    public CmsException(String s) {
        super(s);
    }

    public CmsException(String s, String ... params) {
        super(s);
    }

    public CmsException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CmsException(String s, Throwable throwable, String ... params) {
        super(s, throwable);
    }

    public CmsException(Throwable throwable) {
        super(throwable);
    }
}
