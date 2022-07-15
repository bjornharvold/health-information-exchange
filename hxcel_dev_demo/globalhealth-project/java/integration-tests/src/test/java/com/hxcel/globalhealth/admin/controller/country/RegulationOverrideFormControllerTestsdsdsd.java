package com.hxcel.globalhealth.admin.controller.country;


import com.hxcel.globalhealth.AbstractIntegrationBaseTest;
import com.hxcel.globalhealth.domain.platform.model.RegulationOverride;
import com.hxcel.globalhealth.domain.platform.model.Regulation;
import com.hxcel.globalhealth.domain.common.model.Country;
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
public class RegulationOverrideFormControllerTestsdsdsd extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(RegulationOverrideFormControllerTestsdsdsd.class);

    @Test
    public void testPopulateCountry() {
        log.info("Testing populating country ...");

        try {
            Country entity = regulationOverrideFormController.populateCountry(getCountries().get(0).getId());
            assertNotNull("Entity is null", entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Testing populating country COMPLETE");
    }

    @Test
    public void testPopulateRegulations() {
        log.info("Testing populating regulations ...");

        try {
            List<Regulation> list = regulationOverrideFormController.populateRegulations();
            assertNotNull("List is null", list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Testing populating regulations COMPLETE");
    }

    @Test
    public void testRegulationOverrideFormController() {
        try {
            log.info("Testing saving new regulation override form...");
            assertNotNull("controller is null", regulationOverrideFormController);

            ModelMap map = new ModelMap();
            String view = regulationOverrideFormController.setupForm(null, map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            RegulationOverride override = (RegulationOverride) map.get("override");

            override.setCountry(platformManager.getCountry(getCountries().get(0).getId()));
            override.setOriginal(platformManager.getRegulation(getRegulations().get(0).getId()));
            override.setValue(RandomStringUtils.randomAlphabetic(10));
            override.setDescription(RandomStringUtils.randomAlphabetic(10));

            BindingResult errors = new BeanPropertyBindingResult(override, "override");
            SessionStatus ss = new SimpleSessionStatus();
            view = regulationOverrideFormController.processSubmit(getApplications().get(0).getId(), override, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving new regulation override form COMPLETE!");

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testExistingRegulationOverrideFormController() {
        try {
            log.info("Testng updating existing regulation override form...");
            assertNotNull("regulationOverrideFormController is null", regulationOverrideFormController);

            ModelMap map = new ModelMap();
            String view = regulationOverrideFormController.setupForm(getOverrides().get(0).getId(), map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            RegulationOverride override = (RegulationOverride) map.get("override");

            override.setValue(RandomStringUtils.randomAlphabetic(10));

            BindingResult errors = new BeanPropertyBindingResult(override, "override");
            SessionStatus ss = new SimpleSessionStatus();
            view = regulationOverrideFormController.processSubmit(getApplications().get(0).getId(), override, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing updating existing regulation override form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    // Spring IoC
    @Autowired
    private RegulationOverrideFormController regulationOverrideFormController;
}