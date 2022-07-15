package com.hxcel.globalhealth.web;

/**
 * User: bjorn
 * Date: Jan 1, 2008
 * Time: 6:12:08 PM
 */

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class FlexExceptionHttpServletResponseHandler extends
        HttpServletResponseWrapper {

    public FlexExceptionHttpServletResponseHandler(HttpServletResponse response) {
        super(response);
    }

    @Override
    public void setStatus(int statusCode) {
        if (statusCode == 500) {
            super.setStatus(200);
        }
    }
}
