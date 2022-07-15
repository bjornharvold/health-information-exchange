package com.hxcel.globalhealth.it.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hxcel.globalhealth.it.AbstractIntegrationTest;

/**
 * User: Bjorn Harvold
 * Date: Dec 10, 2008
 * Time: 4:34:13 PM
 * Responsibility: Loads up all modules needed for cms
 */
public abstract class AbstractEmailIntegrationTest extends AbstractIntegrationTest {
    private final static Logger log = LoggerFactory.getLogger(AbstractEmailIntegrationTest.class);

    // these are paths to maven artifacts here groupId, artifactId, version that the module is dependent on - the order matters
    protected String[] getTestBundlesNames() {
        return new String[]{
                "org.apache.felix, org.apache.felix.configadmin, 1.0.4",
                "org.apache.velocity, com.springsource.org.apache.velocity, 1.5.0",
                "javax.mail, com.springsource.javax.mail, 1.4.1",
                "javax.activation, com.springsource.javax.activation, 1.1.1",
                "org.jdom, com.springsource.org.jdom, 1.0.0",
                "org.apache.commons, com.springsource.org.apache.commons.collections, 3.2.0",
                "org.apache.commons, com.springsource.org.apache.commons.lang, 2.4.0",
                "org.springframework, org.springframework.context.support, 2.5.6",
                "com.hxcel.globalhealth, email.spec, 1.0.0-SNAPSHOT",
                "com.hxcel.globalhealth, email.impl, 1.0.0-SNAPSHOT"
        };

    }
}