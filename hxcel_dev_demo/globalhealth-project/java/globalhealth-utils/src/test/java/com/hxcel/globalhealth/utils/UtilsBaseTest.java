/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.utils;

import org.springframework.test.AbstractTransactionalSpringContextTests;

/**
 * User: Bjorn Harvold
 * Date: Jul 5, 2006
 * Time: 3:18:45 PM
 */
public abstract class UtilsBaseTest extends AbstractTransactionalSpringContextTests {
    protected String[] getConfigLocations() {
        String[] resources = {"../../../../../../main/resources/spring-utils-beans.xml"};
        
        return resources;
    }
}
