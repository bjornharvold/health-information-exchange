package com.hxcel.globalhealth.admin.controller.application;


import com.hxcel.globalhealth.admin.controller.application.form.ApplicationForm;
import com.hxcel.globalhealth.domain.platform.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.domain.platform.model.enums.ApplicationTypeCd;
import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
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
import org.apache.commons.lang.RandomStringUtils;

/**
 * User: Bjorn Harvold
 * Date: Oct 11, 2008
 * Time: 5:50:24 PM
 * Description: Tests saving an application
 */
public class ApplicationFormControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(ApplicationFormControllerTest.class);

    @Test
    public void testReferenceData() {
        try {
            assertNotNull("applicationFormController is null", applicationFormController);
            assertNotNull("ApplicationStatuses are null", applicationFormController.populateApplicationStatus());
            assertNotNull("ApplicationTypes are null", applicationFormController.populateApplicationType());
            assertNotNull("Organizations are null", applicationFormController.populateOrganizations());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testApplicationFormController() {
        try {
            log.info("Testng saving new application form...");
            assertNotNull("applicationFormController is null", applicationFormController);

            ModelMap map = new ModelMap();
            String view = applicationFormController.setupForm(getApplications().get(0).getId(), map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            Application app = (Application) map.get("application");

            app.setApplicationStatus(ApplicationStatusCd.TEST);
            app.setApplicationType(ApplicationTypeCd.COUNTRY);
            app.setAppVersion("1.0");
            app.setDescription(RandomStringUtils.randomAlphabetic(10));
            app.setName(RandomStringUtils.randomAlphabetic(10));


            app.setOwner(platformManager.getOrganization(getOrganizations().get(0).getId()));

            BindingResult errors = new BeanPropertyBindingResult(app, "application");
            SessionStatus ss = new SimpleSessionStatus();
            view = applicationFormController.processSubmit(app, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving new application form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    // Spring IoC
    @Autowired
    private ApplicationFormController applicationFormController;

    @Autowired
    private PlatformManager platformManager;
}
