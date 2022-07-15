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
 * Time: 9:34:18 PM
 */
@WebService(serviceName = "TestWebService", endpointInterface = "com.hxcel.globalhealth.domain.test.TestWebService")
public class TestWebServiceImpl implements TestWebService {

    @WebMethod
    @WebResult(name = "TestObject")
    public TestObject getTest() {
        return new TestObject();
    }
}
