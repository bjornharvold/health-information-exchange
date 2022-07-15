package com.hxcel.globalhealth.admin.controller.organization;


import com.hxcel.globalhealth.domain.platform.model.ApplicationConfigurationOverride;
import com.hxcel.globalhealth.domain.platform.model.OrganizationLicense;
import com.hxcel.globalhealth.domain.platform.model.enums.OrganizationLicenseStatusCd;
import com.hxcel.globalhealth.AbstractIntegrationBaseTest;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 11, 2008
 * Time: 5:50:24 PM
 * Description: Tests saving a role
 */
public class ApplicationConfigurationOverrideFormControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(ApplicationConfigurationOverrideFormControllerTest.class);

    @Test
    public void testNewApplicationConfigurationOverrideFormController() {
        try {
            log.info("Testing saving new override form...");
            assertNotNull("applicationConfigurationOverrideFormController is null", licensedApplicationConfigurationOverrideFormController);

            ModelMap map = new ModelMap();
            String view = licensedApplicationConfigurationOverrideFormController.setupForm(null, map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            ApplicationConfigurationOverride override = (ApplicationConfigurationOverride) map.get("override");

            // grab a licensed app
            List<OrganizationLicense> list = platformManager.searchForLicensedThirdPartyApplications(getOrganizations().get(0).getId(), null, false, OrganizationLicenseStatusCd.ACTIVE, null, null);

            BindingResult errors = new BeanPropertyBindingResult(override, "override");
            SessionStatus ss = new SimpleSessionStatus();
            view = licensedApplicationConfigurationOverrideFormController.processSubmit(list.get(0).getId(), list.get(0).getOrganization().getId(), false, override, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving new override form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testExistingOrganizationFormController() {
        try {
            log.info("Testing saving existing Organization form...");
            assertNotNull("applicationConfigurationOverrideFormController is null", licensedApplicationConfigurationOverrideFormController);

            // grab a licensed app and an override
            List<OrganizationLicense> list = platformManager.searchForLicensedThirdPartyApplications(getOrganizations().get(0).getId(), null, false, OrganizationLicenseStatusCd.ACTIVE, null, null);
            List<ApplicationConfigurationOverride> aco = platformManager.searchForLicensedThirdPartyApplicationConfigurationOverrides(list.get(0).getId(), null, null, null);

            ModelMap map = new ModelMap();
            String view = licensedApplicationConfigurationOverrideFormController.setupForm(aco.get(0).getId(), map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            ApplicationConfigurationOverride override = (ApplicationConfigurationOverride) map.get("override");

            BindingResult errors = new BeanPropertyBindingResult(override, "override");
            SessionStatus ss = new SimpleSessionStatus();
            view = licensedApplicationConfigurationOverrideFormController.processSubmit(list.get(0).getId(), list.get(0).getOrganization().getId(), false, override, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving existing Organization form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    // Spring IoC
    @Autowired
    private LicensedApplicationConfigurationOverrideFormController licensedApplicationConfigurationOverrideFormController;

}