package com.hxcel.globalhealth.service.email;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * User: Bjorn Harvold
 * Date: Mar 28, 2007
 * Time: 4:17:43 PM
 */
public abstract class AbstractEmailServiceTest extends AbstractDependencyInjectionSpringContextTests {
    protected String[] getConfigLocations() {
        String[] resources = {"/spring-service-email-property-configurer-bean.xml",
                "/spring-service-email-beans.xml"};

        return resources;
    }
}
