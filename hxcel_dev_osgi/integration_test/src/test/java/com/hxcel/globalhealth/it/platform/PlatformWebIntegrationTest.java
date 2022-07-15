package com.hxcel.globalhealth.it.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Bundle;
import org.springframework.osgi.util.OsgiServiceReferenceUtils;

import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Dictionary;

/**
 * User: Bjorn Harvold
 * Date: Dec 10, 2008
 * Time: 4:34:13 PM
 * Responsibility: Tests out the platform web module functionality
 */
public class PlatformWebIntegrationTest extends AbstractPlatformWebIntegrationTest {
    private final static Logger log = LoggerFactory.getLogger(PlatformWebIntegrationTest.class);
    private final static String URL = "http://localhost:8080";

    public void testPlatformWebBundle() {
        log.info("Testing Platform Web OSGi Service");

        ServiceReference sr = OsgiServiceReferenceUtils.getServiceReference(bundleContext, "com.hxcel.globalhealth.platform.spec.ApplicationService", null);

        assertNotNull("ServiceReference for com.hxcel.globalhealth.platform.spec.ApplicationService is null", sr);
        
        /*
        ServiceReference sr = OsgiServiceReferenceUtils.getServiceReference(bundleContext, "com.hxcel.globalhealth.platform.DynamicConfiguration", null);

        assertNotNull("ServiceReference for com.hxcel.globalhealth.platform.DynamicConfiguration is null", sr);

        DynamicConfiguration ds = (DynamicConfiguration) bundleContext.getService(sr);

        log.info("DynamicConfiguration is available! " + ds.toString());
        */

        log.info("Testing Platform Web OSGi Service COMPLETE");
    }

    /*
    public void testJSTLBundle() {
        Bundle[] bundles = bundleContext.getBundles();

        for (Bundle bundle : bundles) {
            if (bundle.getSymbolicName().equals("com.springsource.javax.servlet.jsp.jstl")) {
                log.info("Found jstl bundle. Verifying exported packages....");

                Dictionary dict = bundle.getHeaders();

                log.info("....");
            }

        }
    }
    */

    private void connect(String address) throws Exception {
        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        try {
            con.connect();
            assertEquals(HttpURLConnection.HTTP_OK, con.getResponseCode());
        }
        finally {
            con.disconnect();
        }
    }

    /*
    public void testHtmlPage() throws Exception {
        log.info("Trying to connect to " + URL + "/platform/dispatcher.admin?view=login");
        
        // wait 10 seconds to make sure things are properly deployed
        Thread.sleep(20 * 1000);
        connect(URL + "/platform/dispatcher.admin?view=login");

        log.info("Connect to homepage...SUCCESS");
    }
    */

    // Uncomment this method to stop the test from ending and manually connect to the browser
    public void testWarDeployed() throws Exception {
        System.in.read();
    }

    protected boolean isDisabledInThisEnvironment(String testMethodName) {
        return getPlatformName().indexOf("Felix") > -1;
    }
}