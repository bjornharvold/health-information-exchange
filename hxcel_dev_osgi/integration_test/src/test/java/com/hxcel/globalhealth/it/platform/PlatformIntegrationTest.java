package com.hxcel.globalhealth.it.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.osgi.framework.ServiceReference;
import org.springframework.osgi.util.OsgiServiceReferenceUtils;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.CountryService;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.UserService;

/**
 * User: Bjorn Harvold
 * Date: Dec 10, 2008
 * Time: 4:34:13 PM
 * Responsibility: Tests out the session module functionality
 */
public class PlatformIntegrationTest extends AbstractPlatformIntegrationTest {
    private final static Logger log = LoggerFactory.getLogger(PlatformIntegrationTest.class);

    public void testApplicationService() {
        log.info("Testing Platform OSGi bundle's ApplicationService");

        ServiceReference sr = OsgiServiceReferenceUtils.getServiceReference(bundleContext, "com.hxcel.globalhealth.platform.spec.ApplicationService", null);

        assertNotNull("ServiceReference for com.hxcel.globalhealth.platform.spec.ApplicationService is null", sr);

        ApplicationService as = (ApplicationService) bundleContext.getService(sr);

        assertNotNull("ApplicationService is null but the ServiceReference isn't", as);

        log.info("ApplicationService is available! " + as.toString());

        log.info("Testing Platform OSGi bundle's ApplicationService... COMPLETE");
    }

    public void testCountryService() {
        log.info("Testing Platform OSGi bundle's CountryService");

        ServiceReference sr = OsgiServiceReferenceUtils.getServiceReference(bundleContext, "com.hxcel.globalhealth.platform.spec.CountryService", null);

        assertNotNull("ServiceReference for com.hxcel.globalhealth.platform.spec.CountryService is null", sr);

        CountryService as = (CountryService) bundleContext.getService(sr);

        assertNotNull("CountryService is null but the ServiceReference isn't", as);

        log.info("CountryService is available! " + as.toString());

        log.info("Testing Platform OSGi bundle's CountryService... COMPLETE");
    }

    public void testOrganizationService() {
        log.info("Testing Platform OSGi bundle's OrganizationService");

        ServiceReference sr = OsgiServiceReferenceUtils.getServiceReference(bundleContext, "com.hxcel.globalhealth.platform.spec.OrganizationService", null);

        assertNotNull("ServiceReference for com.hxcel.globalhealth.platform.spec.OrganizationService is null", sr);

        OrganizationService as = (OrganizationService) bundleContext.getService(sr);

        assertNotNull("OrganizationService is null but the ServiceReference isn't", as);

        log.info("OrganizationService is available! " + as.toString());

        log.info("Testing Platform OSGi bundle's OrganizationService... COMPLETE");
    }

    public void testUserService() {
        log.info("Testing Platform OSGi bundle's UserService");

        ServiceReference sr = OsgiServiceReferenceUtils.getServiceReference(bundleContext, "com.hxcel.globalhealth.platform.spec.UserService", null);

        assertNotNull("ServiceReference for com.hxcel.globalhealth.platform.spec.UserService is null", sr);

        UserService as = (UserService) bundleContext.getService(sr);

        assertNotNull("UserService is null but the ServiceReference isn't", as);

        log.info("UserService is available! " + as.toString());

        log.info("Testing Platform OSGi bundle's UserService... COMPLETE");
    }
}