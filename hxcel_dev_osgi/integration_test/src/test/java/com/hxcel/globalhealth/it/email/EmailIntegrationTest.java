package com.hxcel.globalhealth.it.email;

import org.springframework.osgi.util.OsgiServiceReferenceUtils;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hxcel.globalhealth.email.spec.MailService;

/**
 * User: Bjorn Harvold
 * Date: Dec 10, 2008
 * Time: 4:34:13 PM
 * Responsibility: Tests out the mail module functionality
 */
public class EmailIntegrationTest extends AbstractEmailIntegrationTest {
    private final static Logger log = LoggerFactory.getLogger(EmailIntegrationTest.class);

    public void testEmailService() {
        log.info("Testing Email OSGi Service");

        ServiceReference sr = OsgiServiceReferenceUtils.getServiceReference(bundleContext, "com.hxcel.globalhealth.email.spec.MailService", null);

        assertNotNull("ServiceReference for com.hxcel.globalhealth.email.spec.MailService is null", sr);

        MailService service = (MailService) bundleContext.getService(sr);

        log.info("MailService bundle is available! " + service.toString());

        // Boolean available = service.isAvailable();

        // log.info("Is MailService also available for testing: " + available);

        log.info("Testing Email OSGi Service COMPLETE");
    }
}