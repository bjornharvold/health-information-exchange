package com.hxcel.globalhealth.admin.controller.regulation;


import com.hxcel.globalhealth.admin.controller.application.form.RoleForm;
import com.hxcel.globalhealth.admin.controller.role.RoleFormController;
import com.hxcel.globalhealth.admin.controller.role.RoleFormControllerTest;
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.platform.model.Regulation;
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
public class RegulationFormControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(RegulationFormControllerTest.class);

    @Test
    public void testNewRegulationFormController() {
        try {
            log.info("Testing saving new regulation form...");
            assertNotNull("regulationFormController is null", regulationFormController);

            ModelMap map = new ModelMap();
            String view = regulationFormController.setupForm(null, map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            Regulation regulation = (Regulation) map.get("regulation");

            // we're on purpose doing nothing here - we are in essence just updating application

            BindingResult errors = new BeanPropertyBindingResult(regulation, "regulation");
            SessionStatus ss = new SimpleSessionStatus();
            view = regulationFormController.processSubmit(regulation, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving new regulation form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testExistingRegulationFormController() {
        try {
            log.info("Testing saving existing regulation form...");
            assertNotNull("regulationFormController is null", regulationFormController);

            ModelMap map = new ModelMap();
            String view = regulationFormController.setupForm(getRegulations().get(0).getId(), map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            Regulation regulation = (Regulation) map.get("regulation");

            // we're on purpose doing nothing here - we are in essence just updating application

            BindingResult errors = new BeanPropertyBindingResult(regulation, "regulation");
            SessionStatus ss = new SimpleSessionStatus();
            view = regulationFormController.processSubmit(regulation, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving existing regulation form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    // Spring IoC
    @Autowired
    private RegulationFormController regulationFormController;

}