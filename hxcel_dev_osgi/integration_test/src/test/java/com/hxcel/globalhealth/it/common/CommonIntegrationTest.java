package com.hxcel.globalhealth.it.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Dec 10, 2008
 * Time: 4:34:13 PM
 * Responsibility: Tests out the common bundle
 */
public class CommonIntegrationTest extends AbstractCommonIntegrationTest {
    private final static Logger log = LoggerFactory.getLogger(CommonIntegrationTest.class);

    public void testDynamicConfigurationService() {
        log.info("Testing Common OSGi Bundle");

        /*
        ServiceReference sr = OsgiServiceReferenceUtils.getServiceReference(bundleContext, "com.hxcel.globalhealth.platform.DynamicConfiguration", null);

        assertNotNull("ServiceReference for com.hxcel.globalhealth.platform.DynamicConfiguration is null", sr);

        DynamicConfiguration ds = (DynamicConfiguration) bundleContext.getService(sr);

        log.info("DynamicConfiguration is available! " + ds.toString());
        */

        log.info("Testing Common OSGi bundle COMPLETE");
    }
}