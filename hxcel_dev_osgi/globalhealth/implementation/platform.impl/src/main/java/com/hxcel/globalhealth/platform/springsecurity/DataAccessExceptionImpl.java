/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.springsecurity;

import org.springframework.dao.DataAccessException;

/**
 * User: bjorn
 * Date: Jun 9, 2008
 * Time: 12:34:24 PM
 */
public class DataAccessExceptionImpl extends DataAccessException {
    public DataAccessExceptionImpl(String s) {
        super(s);
    }

    public DataAccessExceptionImpl(String s, Throwable throwable) {
        super(s, throwable);
    }
}
