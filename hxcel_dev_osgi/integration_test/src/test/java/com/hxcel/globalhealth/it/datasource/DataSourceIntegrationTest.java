package com.hxcel.globalhealth.it.datasource;

import org.springframework.osgi.util.OsgiServiceReferenceUtils;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * User: Bjorn Harvold
 * Date: Dec 10, 2008
 * Time: 4:34:13 PM
 * Responsibility: Tests out the datasource module functionality
 */
public class DataSourceIntegrationTest extends AbstractDataSourceIntegrationTest {
    private final static Logger log = LoggerFactory.getLogger(DataSourceIntegrationTest.class);

    public void testDatasourceService() {
        log.info("Testing Datasource OSGi Service");

        ServiceReference sr = OsgiServiceReferenceUtils.getServiceReference(bundleContext, "javax.sql.DataSource", null);

        assertNotNull("ServiceReference for javax.sql.DataSource is null", sr);
        
        DataSource ds = (DataSource) bundleContext.getService(sr);

        log.info("DATASOURCE is available! " + ds.toString());

        log.info("Testing Datasource OSGi Service COMPLETE");
    }
}
