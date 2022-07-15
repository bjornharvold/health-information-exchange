package com.hxcel.globalhealth.it.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hxcel.globalhealth.it.AbstractIntegrationTest;
import com.hxcel.globalhealth.it.platform.AbstractPlatformIntegrationTest;

/**
 * User: Bjorn Harvold
 * Date: Dec 10, 2008
 * Time: 4:34:13 PM
 * Responsibility: Loads up all modules needed for datasource
 */
public abstract class AbstractCommonIntegrationTest extends AbstractIntegrationTest {
    private final static Logger log = LoggerFactory.getLogger(AbstractCommonIntegrationTest.class);

    // these are paths to maven artifacts here groupId, artifactId, version that the module is dependent on - the order matters
    protected String[] getTestBundlesNames() {
        return new String[]{
                "javax.xml.stream, com.springsource.javax.xml.stream, 1.0.1",
                "javax.transaction, com.springsource.javax.transaction, 1.1.0",
                "org.dom4j, com.springsource.org.dom4j, 1.6.1",
                "org.apache.felix, org.apache.felix.configadmin, 1.0.4",
                "org.apache.xmlcommons, com.springsource.org.apache.xmlcommons, 1.3.3",
                "org.apache.xml, com.springsource.org.apache.xml.resolver, 1.2.0",
                "org.apache.xerces, com.springsource.org.apache.xerces, 2.8.1",
                "edu.oswego.cs.concurrent, com.springsource.edu.oswego.cs.dl.util.concurrent, 1.3.4",
                "org.jgroups, com.springsource.org.jgroups, 2.2.8",
                "net.sourceforge.cglib, com.springsource.net.sf.cglib, 2.1.3",
                "org.jboss.javassist, com.springsource.javassist, 3.3.0.ga",
                "org.xmlpull, com.springsource.org.xmlpull, 1.1.4",
                "org.jaxen, com.springsource.org.jaxen, 1.1.1",
                "org.antlr, com.springsource.antlr, 2.7.6",
                "net.sourceforge.jsr107cache, com.springsource.net.sf.jsr107cache, 1.0.0",
                "org.apache.commons, com.springsource.org.apache.commons.collections, 3.2.0",
                "net.sourceforge.ehcache, com.springsource.net.sf.ehcache, 1.5.0",
                "org.apache.commons, com.springsource.org.apache.commons.pool, 1.4.0",
                "org.apache.commons, com.springsource.org.apache.commons.dbcp, 1.2.2.osgi",
                "javax.persistence, com.springsource.javax.persistence, 1.0.0",
                "org.objectweb.asm, com.springsource.org.objectweb.asm, 1.5.3",
                "org.objectweb.asm, com.springsource.org.objectweb.asm.attrs, 1.5.3",
                "org.hibernate, com.springsource.org.hibernate.annotations.common, 3.3.0.ga",
                "org.hibernate, com.springsource.org.hibernate.annotations, 3.4.0.GA",
                "org.hibernate, com.springsource.org.hibernate, 3.3.1.GA",
                "org.hibernate, com.springsource.org.hibernate.validator, 3.1.0.GA",
                "org.jasypt, com.springsource.org.jasypt.encryption, 1.5.0",
                "org.apache.commons, com.springsource.org.apache.commons.codec, 1.3.0",
                "org.apache.commons, com.springsource.org.apache.commons.collections, 3.2.0",
                "org.apache.commons, com.springsource.org.apache.commons.lang, 2.4.0",
                "org.apache.commons, com.springsource.org.apache.commons.beanutils, 1.7.0",
                "net.sourceforge.dozer, com.springsource.net.sf.dozer.util.mapping, 4.2.1",
                "org.springframework, org.springframework.jdbc, 2.5.6",
                "org.springframework, org.springframework.transaction, 2.5.6",
                "org.springframework, org.springframework.orm, 2.5.6",
                "com.hxcel.globalhealth, common.spec, 1.0.0-SNAPSHOT",
                "com.hxcel.globalhealth, common.impl, 1.0.0-SNAPSHOT"
        };
    }
}