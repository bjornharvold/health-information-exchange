/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.test;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * User: Bjorn Harvold
 * Date: Aug 17, 2007
 * Time: 9:38:50 PM
 */
@WebService
public interface TestWebService {
    @WebMethod
    @WebResult(name = "TestObject")
    TestObject getTest();
}
