package com.hxcel.globalhealth.admin.controller.application;


import com.hxcel.globalhealth.domain.platform.model.*;
import com.hxcel.globalhealth.domain.platform.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.AbstractIntegrationBaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 11, 2008
 * Time: 3:42:28 PM
 * Description: Test application controller functionality
 */
public class ApplicationControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(ApplicationControllerTest.class);

    @Test
    public void testShowApplications() {
        log.info("Testing showApplications...");
        assertNotNull("Controller is null", applicationController);

        try {
            ModelMap map = new ModelMap();
            String view = applicationController.showApplications(null, null, false, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 6 properties", 6, map.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load applications" + e.getMessage());
        }
        log.info("Testing showApplications COMPLETE!");
    }

    @Test
    public void testShowApplication() {
        log.info("Testing showApplication...");
        assertNotNull("Controller is null", applicationController);

        try {
            ModelMap map = new ModelMap();
            String view = applicationController.showApplication(getApplications().get(0).getId(), null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 3 property", 3, map.size());
            assertNotNull("application object is not defined", map.get("application"));
            assertEquals("Ids don't match", getApplications().get(0).getId(), ((Application)map.get("application")).getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load applications" + e.getMessage());
        }
        log.info("Testing showApplication COMPLETE!");
    }

    @Test
    public void testPublishApplication() {
        log.info("Testing publishApplication...");
        assertNotNull("Controller is null", applicationController);

        try {
            ModelMap map = new ModelMap();
            String view = applicationController.publishApplication(getApplications().get(0).getId(), ApplicationStatusCd.PUBLISHED, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 3 property", 3, map.size());
            Application app = (Application) map.get("application");
            assertNotNull("application object is not defined", app);
            assertEquals("Application status is not set to published", app.getApplicationStatus(), ApplicationStatusCd.PUBLISHED);
            assertEquals("Ids don't match", getApplications().get(0).getId(), ((Application)map.get("application")).getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load applications" + e.getMessage());
        }
        log.info("Testing publishApplication COMPLETE!");
    }

    @Test
    public void testShowApplicationLicenses() {
        log.info("Testing showApplicationLicenses...");
        assertNotNull("Controller is null", applicationController);

        try {
            ModelMap map = new ModelMap();
            String view = applicationController.showApplicationLicenses(getApplications().get(0).getId(), null, null, null, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 8 properties", 8, map.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load application licenses" + e.getMessage());
        }
        log.info("Testing showApplicationLicenses COMPLETE!");
    }

    @Test
    public void testShowApplicationRoles() {
        log.info("Testing showApplicationRoles...");
        assertNotNull("Controller is null", applicationController);

        try {
            ModelMap map = new ModelMap();
            String view = applicationController.showApplicationRoles(getApplications().get(0).getId(), null, null, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 8 properties", 8, map.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load application roles" + e.getMessage());
        }
        log.info("Testing showApplicationRoles COMPLETE!");
    }

    @Test
    public void testShowApplicationConfigurations() {
        log.info("Testing showApplicationConfigurations...");
        assertNotNull("Controller is null", applicationController);

        try {
            ModelMap map = new ModelMap();
            String view = applicationController.showApplicationConfigurations(getApplications().get(0).getId(), null, null, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 8 properties", 8, map.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load application configurations" + e.getMessage());
        }
        log.info("Testing showApplicationConfigurations COMPLETE!");
    }

    @Test
    public void testShowApplicationResourceBundleEntries() {
        log.info("Testing showApplicationResourceBundleEntries...");
        assertNotNull("Controller is null", applicationController);

        try {
            ModelMap map = new ModelMap();
            String view = applicationController.showApplicationResourceBundleEntries(getApplications().get(0).getId(), null, null, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 8 properties", 8, map.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load application resource bundle entries" + e.getMessage());
        }

        log.info("Testing showApplicationResourceBundleEntries COMPLETE!");
    }

    @Test
    public void testDeleteApplicationConfiguration() {
        log.info("Testing deleteApplicationConfiguration...");
        assertNotNull("Controller is null", applicationController);

        try {
            ModelMap map = new ModelMap();
            Application app = getApplications().get(0);
            String appId = app.getId();
            String view = applicationController.showApplicationConfigurations(getApplications().get(0).getId(), null, null, null, null, map);
            assertNotNull("view is not defined", view);

            assertEquals("Map should hold 8 properties", 8, map.size());

            log.info("Application should have 2 configurations on it...");
            List<ApplicationConfiguration> list = (List<ApplicationConfiguration>) map.get("configurations");
            assertEquals("Incorrect number of configurations in list", 2, list.size());
            log.info("Application has 2 configurations on it");
            String configId = list.get(0).getId();
            assertNotNull("configId is null", configId);

            log.info("Deleting one configuration");
            map = new ModelMap();
            view = applicationController.deleteApplicationConfiguration(appId, configId, null, null, map);
            assertNotNull("view is not defined", view);

            log.info("Testing application again. Should now have 1 configurations on it");

            list = (List<ApplicationConfiguration>) map.get("configurations");
            assertEquals("Incorrect number of configurations in list", 1, list.size());
            log.info("Application now has 1 configurations on it");

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load configurations" + e.getMessage());
        }
    }

    @Test
    public void testDeleteApplicationLicense() {
        log.info("Testing deleteApplicationLicense...");
        assertNotNull("Controller is null", applicationController);

        try {
            ModelMap map = new ModelMap();
            Application app = getApplications().get(0);
            String appId = app.getId();
            String view = applicationController.showApplicationLicenses(getApplications().get(0).getId(), null, null, null, null, null, map);
            assertNotNull("view is not defined", view);

            assertEquals("Map should hold 8 properties", 8, map.size());

            log.info("Application should have 2 licenses on it...");
            List<License> list = (List<License>) map.get("licenses");
            assertEquals("Incorrect number of licenses in list", 2, list.size());
            log.info("Application has 2 licenses on it");
            String licenseId = list.get(0).getId();
            assertNotNull("licenseId is null", licenseId);

            log.info("Deleting one licenses");
            map = new ModelMap();
            view = applicationController.deleteApplicationLicense(appId, licenseId, null, null, null, map);
            assertNotNull("view is not defined", view);

            log.info("Testing application again. Should now have 1 license on it");

            list = (List<License>) map.get("licenses");
            assertEquals("Incorrect number of licenses in list", 1, list.size());
            log.info("Application now has 1 licenses on it");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load licenses" + e.getMessage());
        }
    }

    @Test
    public void testDeleteApplicationRole() {
        log.info("Testing deleteApplicationRole...");
        assertNotNull("Controller is null", applicationController);

        try {
            ModelMap map = new ModelMap();
            Application app = getApplications().get(0);
            String appId = app.getId();
            String view = applicationController.showApplicationRoles(getApplications().get(0).getId(), null, null, null, null, map);
            assertNotNull("view is not defined", view);

            assertEquals("Map should hold 8 properties", 8, map.size());

            log.info("Application should have 2 roles on it...");
            List<ApplicationRole> list = (List<ApplicationRole>) map.get("roles");
            assertEquals("Incorrect number of roles in list", 2, list.size());
            log.info("Application has 2 roles on it");
            String applicationRoleId = list.get(0).getId();
            assertNotNull("applicationRoleId is null", applicationRoleId);

            log.info("Deleting one role");
            view = applicationController.deleteApplicationRole(appId, applicationRoleId, null, null, map);
            assertNotNull("view is not defined", view);

            log.info("Testing application again. Should now have 1 role on it");

            list = (List<ApplicationRole>) map.get("roles");
            assertEquals("Incorrect number of roles in list", 1, list.size());
            log.info("Application now has 1 roles on it");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load roles" + e.getMessage());
        }
    }

    @Test
    public void testDeleteApplicationResourceBundleEntry() {
        log.info("Testing deleteApplicationResourceBundleEntry...");
        assertNotNull("Controller is null", applicationController);

        try {
            ModelMap map = new ModelMap();
            Application app = getApplications().get(0);
            String appId = app.getId();
            String view = applicationController.showApplicationResourceBundleEntries(getApplications().get(0).getId(), null, null, null, null, map);
            assertNotNull("view is not defined", view);

            assertEquals("Map should hold 8 properties", 8, map.size());

            log.info("Application should have 2 resource bundle entries on it...");
            List<ResourceBundleEntry> list = (List<ResourceBundleEntry>) map.get("entries");
            assertEquals("Incorrect number of resource bundle entries in list", 2, list.size());
            log.info("Application has 2 resource bundle entries on it");
            String resourceBundleEntryId = list.get(0).getId();
            assertNotNull("resourceBundleEntryId is null", resourceBundleEntryId);

            log.info("Deleting one resource bundle entry");
            view = applicationController.deleteApplicationResourcebundleEntry(appId, resourceBundleEntryId, null, null, map);
            assertNotNull("view is not defined", view);

            log.info("Testing application again. Should now have 1 resource bundle entry on it");

            list = (List<ResourceBundleEntry>) map.get("entries");
            assertEquals("Incorrect number of resource bundle entries in list", 1, list.size());
            log.info("Application now has 1 resource bundle entry on it");

            log.info("Testing deleteApplicationResourceBundleEntry... COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load roles" + e.getMessage());
        }
    }

    // Spring IoC
    @Autowired
    private ApplicationController applicationController;
}
