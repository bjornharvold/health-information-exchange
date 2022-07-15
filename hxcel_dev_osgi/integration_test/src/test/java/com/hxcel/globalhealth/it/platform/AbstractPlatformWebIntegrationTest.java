/*
 * Copyright 2006-2008 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hxcel.globalhealth.it.platform;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.JdkVersion;
import org.springframework.osgi.test.AbstractConfigurableBundleCreatorTests;
import com.hxcel.globalhealth.it.AbstractIntegrationTest;

/**
 * Web integration test that bootstraps the web containers and its dependencies
 * and tests the Http connection to the local server at
 * <code>http://localhost:8080/</code>.
 *
 * @author Bjorn Harvold
 */
public class AbstractPlatformWebIntegrationTest extends AbstractPlatformIntegrationTest {

    /**
     * Installs the required web bundles (such as Apache Tomcat) before
     * running the integration test.
     */
    protected String[] getTestBundlesNames() {
        String[] parent = super.getTestBundlesNames();

        List bundles = new ArrayList();

        // first we add parent bundles
        for (String s : parent) {
            bundles.add(s);
        }

        // then we add the bundles necessary for web testing

        // Servlet/JSP artifacts
        bundles.add("javax.servlet, com.springsource.javax.servlet, 2.5.0");
        bundles.add("javax.servlet, com.springsource.javax.servlet.jsp, 2.1.0");
        bundles.add("javax.el, com.springsource.javax.el, 1.0.0");

        bundles.add("org.apache.commons, com.springsource.org.apache.commons.el, 1.0.0");

        // JSP compiler
        bundles.add("org.apache.jasper, com.springsource.org.apache.jasper, 6.0.18");

        // standard tag library
        bundles.add("org.apache.xmlcommons, com.springsource.org.apache.xmlcommons, 1.3.3");

        bundles.add("javax.servlet, com.springsource.javax.servlet.jsp.jstl, 1.2.0");

        // deps for catalina
        bundles.add("javax.annotation, com.springsource.javax.annotation, 1.0.0");
        bundles.add("javax.ejb, com.springsource.javax.ejb, 3.0.0");
        bundles.add("javax.xml.rpc, com.springsource.javax.xml.rpc, 1.1.0");
        bundles.add("javax.xml.soap, com.springsource.javax.xml.soap, 1.3.0");
        bundles.add("javax.activation, com.springsource.javax.activation, 1.1.1");
        bundles.add("javax.mail, com.springsource.javax.mail, 1.4.1");
        bundles.add("javax.persistence, com.springsource.javax.persistence, 1.0.0");
        bundles.add("javax.xml.ws, com.springsource.javax.xml.ws, 2.1.1");
        bundles.add("javax.xml.bind, com.springsource.javax.xml.bind, 2.1.7");
        bundles.add("javax.xml.stream, com.springsource.javax.xml.stream, 1.0.1");
        bundles.add("org.apache.coyote, com.springsource.org.apache.coyote, 6.0.18");
        bundles.add("org.apache.juli, com.springsource.org.apache.juli.extras, 6.0.18");
        bundles.add("com.hxcel, catalina.configuration, 1.0.0-SNAPSHOT");
        bundles.add("org.apache.catalina, com.springsource.org.apache.catalina, 6.0.18");
        bundles.add("org.springframework.osgi, catalina.start.osgi, 1.0.0");

        // Spring DM web extender
        bundles.add("org.springframework.osgi, org.springframework.osgi.web," + getSpringDMVersion());
        bundles.add("org.springframework.osgi, org.springframework.osgi.web.extender," + getSpringDMVersion());
        bundles.add("net.sourceforge.cglib, com.springsource.net.sf.cglib, 2.1.3");

        // tiles
        bundles.add("org.apache.commons, com.springsource.org.apache.commons.collections, 3.2.0");
        bundles.add("commons-beanutils, commons-beanutils, 1.8.0");
        bundles.add("commons-digester, commons-digester, 1.8.1");
//        bundles.add("org.apache.tomcat, com.hxcel.org.apache.tomcat.jasper.el, 6.0.18");
        bundles.add("org.apache.el, com.springsource.org.apache.el, 6.0.18");
        bundles.add("org.apache.tiles, tiles-api, 2.1.2");
        bundles.add("org.apache.tiles, tiles-core, 2.1.2");
        bundles.add("org.apache.tiles, tiles-jsp, 2.1.2");
        bundles.add("org.apache.tiles, tiles-servlet, 2.1.2");

        // JSP compiler
        // bundles.add("org.eclipse.jdt, com.springsource.org.eclipse.jdt.core.compiler.batch, 3.3.0");
        bundles.add("org.apache.jasper, com.springsource.org.apache.jasper.org.eclipse.jdt, 6.0.16");

        // file upload
        bundles.add("commons-io, commons-io, 1.4");
        bundles.add("commons-fileupload, commons-fileupload, 1.2.1");

        // tuckey url rewrite
        bundles.add("org.tuckey, com.springsource.org.tuckey.web.filters.urlrewrite, 3.1.0");

        // spring security
        bundles.add("org.springframework.security, org.springframework.security.taglibs, 2.0.4.A");

        // our web module
        bundles.add("com.hxcel.globalhealth, platform.web, 1.0.0-SNAPSHOT,war");

        // the war
        // col.add("org.springframework.osgi.samples.simple-web-app, war, " + getSpringDMVersion() + ",war");
        String[] result = (String[]) bundles.toArray(new String[bundles.size()]);

        return result;
	}
}
