package com.hxcel.globalhealth.it.cms;

import org.springframework.osgi.util.OsgiServiceReferenceUtils;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hxcel.globalhealth.cms.spec.CmsService;

/**
 * User: Bjorn Harvold
 * Date: Dec 10, 2008
 * Time: 4:34:13 PM
 * Responsibility: Tests out the cms module functionality
 */
public class CMSIntegrationTest extends AbstractCMSIntegrationTest {
    private final static Logger log = LoggerFactory.getLogger(CMSIntegrationTest.class);

    public void testCMSService() {
        log.info("Testing CMS OSGi Service");

        ServiceReference sr = OsgiServiceReferenceUtils.getServiceReference(bundleContext, "com.hxcel.globalhealth.cms.spec.CmsService", null);

        assertNotNull("ServiceReference for com.hxcel.globalhealth.cms.spec.CmsService is null", sr);

        CmsService service = (CmsService) bundleContext.getService(sr);

        log.info("CmsService bundle is available! " + service.toString());

        Boolean available = service.isAvailable();

        log.info("Is CmsService also available for testing: " + available);

        log.info("Testing CMS OSGi Service COMPLETE");
    }
}