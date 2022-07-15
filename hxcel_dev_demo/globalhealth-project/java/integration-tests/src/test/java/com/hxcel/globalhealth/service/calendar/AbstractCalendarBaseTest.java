/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.AbstractTransactionalSpringContextTests;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * User: Bjorn Harvold
 * Date: Jul 2, 2006
 * Time: 9:40:27 PM
 * This class should be extending PersistenceBaseClass instead of duplicating some methods
 * here. The problem is that test classes do not get jarred up and consequently maven surefire plugin
 * can't find the class and fails.
 */
public abstract class AbstractCalendarBaseTest extends AbstractDependencyInjectionSpringContextTests {
    private static final Logger log = LoggerFactory.getLogger(AbstractCalendarBaseTest.class);

    protected String[] getConfigLocations() {
        String[] resources = {"spring-service-calendar-property-configurer-bean.xml",
                "spring-service-calendar-beans.xml"};

        return resources;
    }

}
