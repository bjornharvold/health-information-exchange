package com.hxcel.globalhealth.service.forex;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Jul 6, 2007
 * Time: 7:49:02 AM
 */
public abstract class ForexBaseTest extends AbstractDependencyInjectionSpringContextTests {
    private static final Logger log = LoggerFactory.getLogger(ForexBaseTest.class);

    protected String[] getConfigLocations() {
        String[] resources = {"spring-service-forex-beans.xml"};

        return resources;
    }
}
