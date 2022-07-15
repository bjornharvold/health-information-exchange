package com.hxcel.globalhealth.it.cms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hxcel.globalhealth.it.AbstractIntegrationTest;

/**
 * User: Bjorn Harvold
 * Date: Dec 10, 2008
 * Time: 4:34:13 PM
 * Responsibility: Loads up all modules needed for cms
 */
public abstract class AbstractCMSIntegrationTest extends AbstractIntegrationTest {
    private final static Logger log = LoggerFactory.getLogger(AbstractCMSIntegrationTest.class);

    // these are paths to maven artifacts here groupId, artifactId, version that the module is dependent on - the order matters
    protected String[] getTestBundlesNames() {
        return new String[]{
                "org.apache.felix, org.apache.felix.configadmin, 1.0.4",
                "org.apache.commons, com.springsource.org.apache.commons.httpclient, 3.1.0",
                "org.apache.commons, com.springsource.org.apache.commons.codec, 1.3.0",
                "org.mozilla.javascript, com.springsource.org.mozilla.javascript, 1.7.0.R1",
                "com.hxcel.globalhealth, cms.spec, 1.0.0-SNAPSHOT",
                "com.hxcel.globalhealth, cms.impl, 1.0.0-SNAPSHOT"
        };

    }
}