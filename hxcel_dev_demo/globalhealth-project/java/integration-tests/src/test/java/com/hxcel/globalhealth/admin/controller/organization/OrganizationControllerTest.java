package com.hxcel.globalhealth.admin.controller.organization;


import com.hxcel.globalhealth.domain.platform.model.*;
import com.hxcel.globalhealth.domain.platform.model.enums.OrganizationLicenseStatusCd;
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
 * Description: Test organziation controller functionality
 */
@SuppressWarnings(value = "unchecked")
public class OrganizationControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(OrganizationControllerTest.class);

    @Test
    public void testShowOrganizations() {
        log.info("Testing showOrganizations...");
        assertNotNull("Controller is null", organizationController);

        try {
            ModelMap map = new ModelMap();
            String view = organizationController.showOrganizations(null, null, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 5 properties", 5, map.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load roles" + e.getMessage());
        }
        log.info("Testing showOrganizations COMPLETE!");
    }

    @Test
    public void testShowOrganization() {
        log.info("Testing showOrganization...");
        assertNotNull("Controller is null", organizationController);

        try {
            ModelMap map = new ModelMap();
            String view = organizationController.showOrganization(getOrganizations().get(0).getId(), null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 1 properties", 1, map.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load roles" + e.getMessage());
        }
        log.info("Testing showOrganization COMPLETE!");
    }

    @Test
    public void testShowApplications() {
        log.info("Testing showApplications...");
        assertNotNull("Controller is null", organizationController);

        try {
            ModelMap map = new ModelMap();
            String view = organizationController.showApplications(getOrganizations().get(0).getId(), null, null, false, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map size incorrect", 7, map.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load roles" + e.getMessage());
        }
        log.info("Testing showApplications COMPLETE!");
    }

    @Test
    public void testMembers() {
        log.info("Testing member functions...");
        assertNotNull("Controller is null", organizationController);

        try {
            // list members
            String organizationId = getOrganizations().get(0).getId();
            ModelMap map = new ModelMap();
            String view = organizationController.showMembers(organizationId, null, null, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map size incorrect", 6, map.size());

            List<OrganizationUser> users = (List<OrganizationUser>)map.get("users");
            int currentUserSize = users.size();

            // show one member
            String organizationUserId = users.get(0).getId();
            map = new ModelMap();
            view = organizationController.showMember(organizationUserId, organizationId, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map size incorrect", 2, map.size());
            assertNotNull("Member is null", map.get("ou"));

            // show member roles
            map = new ModelMap();
            view = organizationController.showMemberRoles(organizationUserId, organizationId, null, null, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map size incorrect", 7, map.size());
            assertNotNull("Member is null", map.get("ou"));

            List<OrganizationUserRole> roles = (List<OrganizationUserRole>) map.get("roles");
            assertNotNull("Member roles is null", roles);
            int currentRoleSize = roles.size();

            // delete a member role
            map = new ModelMap();
            view = organizationController.deleteMemberRole(organizationUserId, organizationId, roles.get(0).getId(), null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map size incorrect", 7, map.size());
            assertNotNull("Member is null", map.get("ou"));

            roles = (List<OrganizationUserRole>) map.get("roles");
            assertNotNull("Member roles is null", roles);
            int newRoleSize = roles.size();

            assertTrue("New list size after deletion should be smaller", currentRoleSize > newRoleSize);

            // delete a member
            map = new ModelMap();
            view = organizationController.deleteMember(organizationId, organizationUserId, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map size incorrect", 6, map.size());

            users = (List<OrganizationUser>)map.get("users");
            int newUserSize = users.size();

            assertTrue("New list size after deletion should be smaller", currentUserSize > newUserSize);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load roles" + e.getMessage());
        }
        log.info("Testing member functions COMPLETE!");
    }

    @Test
    public void testShowLicensedThirdPartyConfigurationOverrides() {
        log.info("Testing showLicensedThirdPartyConfigurationOverrides...");
        assertNotNull("Controller is null", organizationController);

        try {
            ModelMap map = new ModelMap();
            String organizationId = getOrganizations().get(0).getId();
            log.info("First we grab all the licenses for selected organization");
            String view = organizationController.showLicensedThirdPartyApplications(organizationId, null, null, false, OrganizationLicenseStatusCd.ACTIVE, null, null, map);

            assertNotNull("View is not defined", view);
            assertEquals("Map size incorrect", 7, map.size());
            assertNotNull("License count is null", map.get("count"));
            assertTrue("Count is incorrect", (Integer)map.get("count") > 0);

            String organizationLicenseId = ((List<OrganizationLicense>) map.get("applications")).get(0).getId();
            map = new ModelMap();
            view = organizationController.showLicensedThirdPartyApplicationConfigurationOverrides(organizationLicenseId, organizationId, null, null, false, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map size incorrect", 8, map.size());
            int currentSize = ((List<ApplicationConfigurationOverride>)map.get("overrides")).size();

            // and now we want to delete an override
            String overrideId = ((List<ApplicationConfigurationOverride>)map.get("overrides")).get(0).getId();
            map = new ModelMap();
            organizationController.deleteApplicationConfigurationOverride(organizationLicenseId, organizationId, overrideId, false, null, null, map);

            map = new ModelMap();
            view = organizationController.showLicensedThirdPartyApplicationConfigurationOverrides(organizationLicenseId, organizationId, null, null, false, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map size incorrect", 8, map.size());
            int newSize = ((List<ApplicationConfigurationOverride>)map.get("overrides")).size();

            assertTrue("List size after deletion should be smaller", currentSize > newSize);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load roles" + e.getMessage());
        }
        log.info("Testing showLicensedThirdPartyConfigurationOverrides COMPLETE!");
    }

    @Test
    public void testUnlicenseLicensing() {
        log.info("Testing unlicensing Application...");
        assertNotNull("Controller is null", organizationController);

        try {
            ModelMap map = new ModelMap();
            String organizationId = getOrganizations().get(0).getId();
            log.info("First we grab all the licenses for selected organization");
            String view = organizationController.showLicensedThirdPartyApplications(organizationId, null, null, false, OrganizationLicenseStatusCd.ACTIVE, null, null, map);

            assertNotNull("View is not defined", view);
            assertEquals("Map size incorrect", 7, map.size());
            assertNotNull("License count is null", map.get("count"));
            assertEquals("Count is incorrect", 2, map.get("count"));

            log.info("Then we remove one license");
            String organizationLicenseId = ((List<OrganizationLicense>) map.get("applications")).get(0).getId();
            view = organizationController.unlicenseApplication(organizationId, organizationLicenseId, null, false, map);

            log.info("Now there should be one less licenses");
            assertNotNull("View is not defined", view);
            assertEquals("Map size incorrect", 7, map.size());
            assertNotNull("License count is null", map.get("count"));
            assertEquals("Count is incorrect", 1, map.get("count"));

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load roles" + e.getMessage());
        }
        log.info("Testing unlicensing Application COMPLETE!");
    }

    @Test
    public void testShowApplicationRoles() {
        log.info("Testing showApplicationRoles...");
        assertNotNull("Controller is null", organizationController);

        try {
            ModelMap map = new ModelMap();
            String view = organizationController.showApplicationRoles(getApplications().get(0).getId(), getApplications().get(0).getOwner().getId(), null, null, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 9 properties", 9, map.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load application roles" + e.getMessage());
        }
        log.info("Testing showApplicationRoles COMPLETE!");
    }
    
    @Test
    public void testApplicationConfiguration() {
        log.info("Testing deleteApplicationConfiguration...");
        assertNotNull("Controller is null", organizationController);

        try {
            ModelMap map = new ModelMap();
            Application app = getApplications().get(0);
            String appId = app.getId();
            String orgId = app.getOwner().getId();
            String view = organizationController.showApplicationConfigurations(appId, orgId, null, null, null, null, map);
            assertNotNull("view is not defined", view);

            assertEquals("Map should hold 9 properties", 9, map.size());

            log.info("Application should have 2 configurations on it...");
            List<ApplicationConfiguration> list = (List<ApplicationConfiguration>) map.get("configurations");
            assertEquals("Incorrect number of configurations in list", 2, list.size());
            log.info("Application has 2 configurations on it");
            String configId = list.get(0).getId();
            assertNotNull("configId is null", configId);

            log.info("Deleting one configuration");
            map = new ModelMap();
            view = organizationController.deleteApplicationConfiguration(appId, orgId, configId, null, null, map);
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
    public void testApplicationLicense() {
        log.info("Testing deleteApplicationLicense...");
        assertNotNull("Controller is null", organizationController);

        try {
            ModelMap map = new ModelMap();
            Application app = getApplications().get(0);
            String appId = app.getId();
            String orgId = app.getOwner().getId();
            String view = organizationController.showApplicationLicenses(appId, orgId, null, null, null, null, null, map);
            assertNotNull("view is not defined", view);

            assertEquals("Map should hold 9 properties", 9, map.size());

            log.info("Application should have 2 licenses on it...");
            List<License> list = (List<License>) map.get("licenses");
            assertEquals("Incorrect number of licenses in list", 2, list.size());
            log.info("Application has 2 licenses on it");
            String licenseId = list.get(0).getId();
            assertNotNull("licenseId is null", licenseId);

            log.info("Deleting one licenses");
            map = new ModelMap();
            view = organizationController.deleteApplicationLicense(appId, orgId, licenseId, null, null, null, map);
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
    public void testApplicationResourceBundleEntry() {
        log.info("Testing deleteApplicationResourceBundleEntry...");
        assertNotNull("Controller is null", organizationController);

        try {
            ModelMap map = new ModelMap();
            Application app = getApplications().get(0);
            String appId = app.getId();
            String orgId = app.getOwner().getId();
            String view = organizationController.showApplicationResourceBundleEntries(appId, orgId, null, null, null, null, map);
            assertNotNull("view is not defined", view);

            assertEquals("Map should hold 9 properties", 9, map.size());

            log.info("Application should have 2 resource bundle entries on it...");
            List<ResourceBundleEntry> list = (List<ResourceBundleEntry>) map.get("entries");
            assertEquals("Incorrect number of entries in list", 2, list.size());
            log.info("Application has 2 resource bundle entries on it");
            String resourceBundleEntryId = list.get(0).getId();
            assertNotNull("resourceBundleEntryId is null", resourceBundleEntryId);

            log.info("Deleting one resource bundle entry");
            map = new ModelMap();
            view = organizationController.deleteApplicationResourcebundleEntry(appId, orgId, resourceBundleEntryId, null, null, map);
            assertNotNull("view is not defined", view);

            log.info("Testing application again. Should now have 1 resource bundle entry on it");

            list = (List<ResourceBundleEntry>) map.get("entries");
            assertEquals("Incorrect number of entries in list", 1, list.size());
            log.info("Application now has 1 resource bundle entries on it");

            log.info("Testing deleteApplicationResourceBundleEntry COMPLETE");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load licenses" + e.getMessage());
        }
    }

    // Spring IoC
    @Autowired
    private OrganizationController organizationController;
}