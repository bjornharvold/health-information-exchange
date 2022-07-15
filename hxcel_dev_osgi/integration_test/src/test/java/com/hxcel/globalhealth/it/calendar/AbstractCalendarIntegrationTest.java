package com.hxcel.globalhealth.it.calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hxcel.globalhealth.it.AbstractIntegrationTest;
import com.hxcel.globalhealth.it.cms.AbstractCMSIntegrationTest;

/**
 * User: Bjorn Harvold
 * Date: Dec 10, 2008
 * Time: 4:34:13 PM
 * Responsibility: Loads up all modules needed for cms
 */
public abstract class AbstractCalendarIntegrationTest extends AbstractIntegrationTest {
    private final static Logger log = LoggerFactory.getLogger(AbstractCalendarIntegrationTest.class);
    protected final String SERVICE_CALENDAR = "com.hxcel.globalhealth.calendar.spec.CalendarService";

    // these are paths to maven artifacts here groupId, artifactId, version that the module is dependent on - the order matters
    protected String[] getTestBundlesNames() {
        return new String[]{
                "org.apache.felix, org.apache.felix.configadmin, 1.0.4",
                "org.apache.commons, com.springsource.org.apache.commons.lang, 2.4.0",
                "org.apache.commons, com.springsource.org.apache.commons.httpclient, 3.1.0",
                "org.apache.commons, com.springsource.org.apache.commons.codec, 1.3.0",
                "javax.xml.stream, com.springsource.javax.xml.stream, 1.0.1",
                "org.jaxen, com.springsource.org.jaxen, 1.1.1",
                "org.dom4j, com.springsource.org.dom4j, 1.6.1",
                "com.hxcel.globalhealth, calendar.spec, 1.0.0-SNAPSHOT",
                "com.hxcel.globalhealth, calendar.impl, 1.0.0-SNAPSHOT"
        };

    }
}