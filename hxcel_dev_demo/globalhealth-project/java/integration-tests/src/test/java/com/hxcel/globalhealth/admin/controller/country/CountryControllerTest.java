package com.hxcel.globalhealth.admin.controller.country;


import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.platform.model.RegulationOverride;
import com.hxcel.globalhealth.AbstractIntegrationBaseTest;
import com.hxcel.globalhealth.admin.controller.role.RoleController;
import com.hxcel.globalhealth.admin.controller.role.RoleControllerTest;
import com.hxcel.globalhealth.admin.controller.regulation.RegulationController;
import com.hxcel.globalhealth.admin.controller.regulation.RegulationControllerTest;
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
 * Description: Test country controller functionality
 */
public class CountryControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(CountryControllerTest.class);

    @Test
    public void testShowCountries() {
        log.info("Testing showCountries...");
        assertNotNull("Controller is null", countryController);

        try {
            ModelMap map = new ModelMap();
            String view = countryController.showCountries(null, null, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 5 properties", 5, map.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load roles" + e.getMessage());
        }
        log.info("Testing showCountries COMPLETE!");
    }

    @Test
    public void testShowCountry() {
        log.info("Testing showCountry...");
        assertNotNull("Controller is null", countryController);

        try {
            ModelMap map = new ModelMap();
            String view = countryController.showCountry(getCountries().get(0).getId(), null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 1 properties", 1, map.size());

            Country country = (Country) map.get("country");
            assertEquals("Country identifiers are not the same", getCountries().get(0).getId(), country.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load roles" + e.getMessage());
        }
        log.info("Testing showCountry COMPLETE!");
    }

    @Test
    public void testShowRegulationOverridesByCountry() {
        log.info("Testing showRegulationOverridesByCountry...");
        assertNotNull("Controller is null", countryController);

        try {
            ModelMap map = new ModelMap();
            String view = countryController.showRegulationOverridesByCountry(getCountries().get(0).getId(), null, null, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 6 properties", 6, map.size());

            List<RegulationOverride> list = (List<RegulationOverride>) map.get("overrides");
            int currentSize = list.size();

            assertEquals("List size should be 1", 1, currentSize);

            // now let's delete an override
            String overrideId = list.get(0).getId();
            view = countryController.deleteRegulationOverride(getCountries().get(0).getId(), overrideId, null, map);

            map = new ModelMap();
            view = countryController.showRegulationOverridesByCountry(getCountries().get(0).getId(), null, null, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 6 properties", 6, map.size());

            list = (List<RegulationOverride>) map.get("overrides");
            int newSize = list.size();

            assertTrue("Current list size should be greater than the new list size0", currentSize > newSize);
            assertEquals("List size should be 0", 0, newSize);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load roles" + e.getMessage());
        }
        log.info("Testing showCountry COMPLETE!");
    }

    // Spring IoC
    @Autowired
    private CountryController countryController;
}