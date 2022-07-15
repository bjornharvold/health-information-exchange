package com.hxcel.globalhealth.it.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hxcel.globalhealth.it.AbstractIntegrationTest;

/**
 * User: Bjorn Harvold
 * Date: Dec 10, 2008
 * Time: 4:34:13 PM
 * Responsibility: Loads up all modules needed for datasource
 */
public abstract class AbstractDataSourceIntegrationTest extends AbstractIntegrationTest {
    private final static Logger log = LoggerFactory.getLogger(AbstractDataSourceIntegrationTest.class);

    // these are paths to maven artifacts here groupId, artifactId, version that the module is dependent on - the order matters
    protected String[] getTestBundlesNames() {
        return new String[]{
                "org.apache.felix, org.apache.felix.configadmin, 1.0.4",
                "org.apache.commons, com.springsource.org.apache.commons.lang, 2.4.0",
                "net.sourceforge.ehcache, com.springsource.net.sf.ehcache, 1.5.0",
                "org.apache.xerces, com.springsource.org.apache.xerces, 2.8.1",
                "org.apache.xml, com.springsource.org.apache.xml.resolver, 1.2.0",
                "org.apache.commons, com.springsource.org.apache.commons.collections, 3.2.0",
                "org.apache.commons, com.springsource.org.apache.commons.dbcp, 1.2.2.osgi",
                "org.apache.commons, com.springsource.org.apache.commons.pool, 1.4.0",
                "net.sourceforge.jsr107cache, com.springsource.net.sf.jsr107cache, 1.0.0",
                "javax.persistence, com.springsource.javax.persistence, 1.0.0",
                "org.postgresql, com.springsource.org.postgresql.jdbc3, 8.3.603",
                "org.springframework, org.springframework.jdbc, 2.5.6",
                "org.springframework, org.springframework.transaction, 2.5.6",
                "com.hxcel.globalhealth, datasource, 1.0.0-SNAPSHOT"
        };

    }
}