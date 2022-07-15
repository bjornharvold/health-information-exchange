/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.ws.WebFault;

/**
 * User: Bjorn Harvold
 * Date: Jan 5, 2007
 * Time: 11:33:39 AM
 */
@WebFault
public class WebServicesException extends Exception {
    private static final Logger log = LoggerFactory.getLogger(WebServicesException.class);

    /**
     * @param message
     * @param throwable
     * @param params
     */
    public WebServicesException(String message, Throwable throwable, String[] params) {
    }

    /*public WebServicesException(Message message, Throwable throwable) {
        super(message, throwable);
    }

    public WebServicesException(Message message) {
        super(message);
    }

    public WebServicesException(Throwable throwable) {
        super(throwable);
    }

    public WebServicesException(Message message, Throwable throwable, QName qname) {
        super(message, throwable, qname);
    }

    public WebServicesException(Message message, QName qname) {
        super(message, qname);
    }

    public WebServicesException(Throwable throwable, QName qname) {
        super(throwable, qname);
    }*/

    /**
     * TODO we still need the right namespace here
     * @return qname
     */
    public static QName getFaultName() {
        return new QName(_namespace, "WebServiceServerFault");
    }


    // Spring IoC
    private static String _namespace = "";
    public void setNamespace(String namespace) {
        _namespace = namespace;
    }
}
