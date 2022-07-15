package com.hxcel.globalhealth.admin.controller.organization;


import com.hxcel.globalhealth.domain.platform.model.Organization;
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

/**
 * User: Bjorn Harvold
 * Date: Oct 11, 2008
 * Time: 5:50:24 PM
 * Description: Tests saving a role
 */
public class OrganizationFormControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(OrganizationFormControllerTest.class);

    @Test
    public void testNewOrganizationFormController() {
        try {
            log.info("Testing saving new organization form...");
            assertNotNull("organizationFormController is null", organizationFormController);

            ModelMap map = new ModelMap();
            String view = organizationFormController.setupForm(null, map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            Organization organization = (Organization) map.get("organization");

            // we're on purpose doing nothing here - we are in essence just updating application

            BindingResult errors = new BeanPropertyBindingResult(organization, "organization");
            SessionStatus ss = new SimpleSessionStatus();
            view = organizationFormController.processSubmit(organization, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving new organization form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testExistingOrganizationFormController() {
        try {
            log.info("Testing saving existing Organization form...");
            assertNotNull("organizationFormController is null", organizationFormController);

            ModelMap map = new ModelMap();
            String view = organizationFormController.setupForm(getOrganizations().get(0).getId(), map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            Organization organization = (Organization) map.get("organization");

            // we're on purpose doing nothing here - we are in essence just updating application

            BindingResult errors = new BeanPropertyBindingResult(organization, "organization");
            SessionStatus ss = new SimpleSessionStatus();
            view = organizationFormController.processSubmit(organization, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving existing Organization form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    // Spring IoC
    @Autowired
    private OrganizationFormController organizationFormController;

}