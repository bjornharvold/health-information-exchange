package com.hxcel.globalhealth.admin.controller.application;


import com.hxcel.globalhealth.domain.platform.model.ResourceBundleEntry;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
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

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 11, 2008
 * Time: 5:50:24 PM
 * Description: Tests saving an application
 */
public class ApplicationResourceBundleFormControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(ApplicationResourceBundleFormControllerTest.class);

    @Test
    public void testPopulateCountries() {
        log.info("Testing populating countries ...");

        try {
            List<Country> list = applicationResourceBundleFormController.populateCountries();
            assertNotNull("List is null", list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Testing populating application COMPLETE");
    }

    @Test
    public void testApplicationResourceBundleFormController() {
        try {
            log.info("Testing saving new application entry form...");
            assertNotNull("controller is null", applicationResourceBundleFormController);

            ModelMap map = new ModelMap();
            String view = applicationResourceBundleFormController.setupForm(null, map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            ResourceBundleEntry entry = (ResourceBundleEntry) map.get("entry");

            entry.setApplication(platformManager.getApplication(getApplications().get(0).getId()));
            entry.setKey(RandomStringUtils.randomAlphabetic(10));
            entry.setValue(RandomStringUtils.randomAlphabetic(10));
            entry.setCountry(platformManager.getCountry(CountryCd.UNITED_STATES));

            BindingResult errors = new BeanPropertyBindingResult(entry, "entry");
            SessionStatus ss = new SimpleSessionStatus();
            view = applicationResourceBundleFormController.processSubmit(getApplications().get(0).getId(), entry, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving new application entry form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    // Spring IoC
    @Autowired
    private ApplicationResourceBundleFormController applicationResourceBundleFormController;

    @Autowired
    private PlatformManager platformManager;
}