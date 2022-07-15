package com.hxcel.globalhealth.admin.controller.application;


import com.hxcel.globalhealth.domain.platform.model.ApplicationConfiguration;
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
public class ApplicationConfigurationFormControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(ApplicationConfigurationFormControllerTest.class);

    @Test
    public void testNewApplicationConfigurationFormController() {
        try {
            log.info("Testing saving new application configuration form...");
            assertNotNull("applicationConfigurationFormController is null", applicationConfigurationFormController);

            ModelMap map = new ModelMap();
            String view = applicationConfigurationFormController.setupForm(null, map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            ApplicationConfiguration configuration = (ApplicationConfiguration) map.get("configuration");

            configuration.setKey(RandomStringUtils.randomAlphabetic(10));
            configuration.setValue(RandomStringUtils.randomAlphabetic(10));
            
            BindingResult errors = new BeanPropertyBindingResult(configuration, "configuration");
            SessionStatus ss = new SimpleSessionStatus();
            view = applicationConfigurationFormController.processSubmit(getApplications().get(0).getId(), configuration, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving new application configuration form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testExistingApplicationConfigurationFormController() {
        try {
            log.info("Testng saving new application configuration form...");
            assertNotNull("applicationConfigurationFormController is null", applicationConfigurationFormController);

            ModelMap map = new ModelMap();
            String view = applicationConfigurationFormController.setupForm(getApplicationConfigurations().get(0).getId(), map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            ApplicationConfiguration configuration = (ApplicationConfiguration) map.get("configuration");

            configuration.setKey(RandomStringUtils.randomAlphabetic(10));
            configuration.setValue(RandomStringUtils.randomAlphabetic(10));

            BindingResult errors = new BeanPropertyBindingResult(configuration, "configuration");
            SessionStatus ss = new SimpleSessionStatus();
            view = applicationConfigurationFormController.processSubmit(getApplications().get(0).getId(), configuration, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving new application configuration form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    // Spring IoC
    @Autowired
    private ApplicationConfigurationFormController applicationConfigurationFormController;
}