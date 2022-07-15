package com.hxcel.globalhealth.it;

import org.springframework.osgi.test.AbstractConfigurableBundleCreatorTests;
import org.springframework.osgi.test.platform.Platforms;
import org.springframework.core.io.Resource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ClassPathResource;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Dec 10, 2008
 * Time: 4:34:13 PM
 * Responsibility: All integration tests should in someway extend this class
 */
public abstract class AbstractIntegrationTest extends AbstractConfigurableBundleCreatorTests {
    private final static Logger log = LoggerFactory.getLogger(AbstractIntegrationTest.class);
    
    @Override
    protected boolean shouldWaitForSpringBundlesContextCreation() {
        return true;
    }

    @Override
    protected String getPlatformName() {
        return Platforms.EQUINOX;
    }

    /**
     * This is how we can control what bundles get started - like when you want a different version
     * than what is default.
     * @return
     */
    @Override
    protected Resource getTestingFrameworkBundlesConfiguration() {
        String path = "com/hxcel/globalhealth/it/boot-bundles.properties";
        Resource result = new ClassPathResource(path);

        if (!result.exists()) {
            fail("boot-bundles path is unavailable\n" + path);
        }

        return result;
    }

    @Override
    protected List getBootDelegationPackages() {
        List<String> result = super.getBootDelegationPackages();

        log.info("Boot delegate packages");

        for (String s : result) {
            log.info(s);
        }

        return result;
    }

    /*
    @Override
    protected String[] getTestFrameworkBundlesNames() {
        String[] result = super.getTestFrameworkBundlesNames();

        log.info("Test Framework bundle names:");
        for (String s : result) {
            log.info(s);
        }

        return result;
    }

    @Override
    protected Resource[] getTestFrameworkBundles() {
        Resource[] result = super.getTestFrameworkBundles();

        log.info("Test Framework bundle resources:");
        for (Resource s : result) {
            log.info(s.toString());
        }

        return result;
    }

    */
    public void testOsgiPlatformStarts() throws Exception {
        log.info("Testing Framework");
        log.info(bundleContext.getProperty(Constants.FRAMEWORK_VENDOR));
        log.info(bundleContext.getProperty(Constants.FRAMEWORK_VERSION));
        log.info(bundleContext.getProperty(Constants.FRAMEWORK_EXECUTIONENVIRONMENT));
        log.info("Testing Framework COMPLETE");
    }

    /*
    public void testOsgiEnvironment() throws Exception {
        log.info("Testing OSGi environment");
        Bundle[] bundles = bundleContext.getBundles();
        Dictionary d = null;
        Enumeration e = null;

        for (Bundle b : bundles) {
            StringBuilder sb = new StringBuilder(OsgiStringUtils.nullSafeName(b));
            sb.append(" : ");

            switch (b.getState()) {
                case Bundle.ACTIVE:
                    sb.append("ACTIVE");

                    System.out.println(sb.toString());
                    d = b.getHeaders();

                    e = d.keys();

                    System.out.println("Exported Packages: ");
                    while (e.hasMoreElements()) {
                        String key = (String) e.nextElement();
                        if (key.equals("Export-Package")) {
                            String value = (String) d.get(key);
                            System.out.println(key + " - " + value);
                        }
                    }

                    break;
                case Bundle.INSTALLED:
                    sb.append("INSTALLED");

                    System.out.println(sb.toString());

                    d = b.getHeaders();

                    e = d.keys();

                    System.out.println("Imported Packages: ");
                    while (e.hasMoreElements()) {
                        String key = (String) e.nextElement();
                        if (key.equals("Import-Package")) {
                            String value = (String) d.get(key);
                            System.out.println(key + " - " + value);
                        }
                    }
                    break;
                case Bundle.RESOLVED:
                    sb.append("RESOLVED");

                    System.out.println(sb.toString());

                    break;
                case Bundle.STARTING:
                    sb.append("STARTING");

                    System.out.println(sb.toString());

                    break;
                case Bundle.STOPPING:
                    sb.append("STOPPING");

                    System.out.println(sb.toString());

                    break;
                case Bundle.UNINSTALLED:
                    sb.append("UNINSTALLED");

                    System.out.println(sb.toString());

                    break;
            }



        }

        log.info("Testing OSGi environment COMPLETE");
    }
    */

}