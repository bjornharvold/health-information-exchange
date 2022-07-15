package com.hxcel.globalhealth.admin.controller.country;


import com.hxcel.globalhealth.domain.common.model.Country;
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
public class CountryFormControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(CountryFormControllerTest.class);

    @Test
    public void testNewCountryFormController() {
        try {
            log.info("Testing saving new country form...");
            assertNotNull("countryFormController is null", countryFormController);

            ModelMap map = new ModelMap();
            String view = countryFormController.setupForm(null, map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            Country country = (Country) map.get("country");

            // we're on purpose doing nothing here - we are in essence just updating application

            BindingResult errors = new BeanPropertyBindingResult(country, "country");
            SessionStatus ss = new SimpleSessionStatus();
            view = countryFormController.processSubmit(country, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving new country form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testExistingCountryFormController() {
        try {
            log.info("Testing saving existing country form...");
            assertNotNull("countryFormController is null", countryFormController);

            ModelMap map = new ModelMap();
            String view = countryFormController.setupForm(getCountries().get(0).getId(), map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            Country country = (Country) map.get("country");

            // we're on purpose doing nothing here - we are in essence just updating application

            BindingResult errors = new BeanPropertyBindingResult(country, "country");
            SessionStatus ss = new SimpleSessionStatus();
            view = countryFormController.processSubmit(country, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving existing country form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    // Spring IoC
    @Autowired
    private CountryFormController countryFormController;

}