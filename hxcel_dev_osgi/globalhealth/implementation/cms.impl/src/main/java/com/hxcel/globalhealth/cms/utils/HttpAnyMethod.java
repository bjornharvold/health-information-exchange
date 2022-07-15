package com.hxcel.globalhealth.cms.utils;

/**
 * User: bjorn
 * Date: Oct 1, 2008
 * Time: 3:43:43 PM
 */
import org.apache.commons.httpclient.HttpMethodBase;

/** Allows any HTTP method for HtttpClient */
public class HttpAnyMethod extends HttpMethodBase {
    private final String methodName;

    public HttpAnyMethod(String methodName, String uri) {
        super(uri);
        this.methodName = methodName;
    }

    @Override
    public String getName() {
        return methodName;
    }
}

