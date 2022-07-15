package com.hxcel.globalhealth.admin.controller.role;


import com.hxcel.globalhealth.admin.controller.application.form.RoleForm;
import com.hxcel.globalhealth.domain.common.model.Role;
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
public class RoleFormControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(RoleFormControllerTest.class);

    @Test
    public void testNewRoleFormController() {
        try {
            log.info("Testing saving new role form...");
            assertNotNull("roleFormController is null", roleFormController);

            ModelMap map = new ModelMap();
            String view = roleFormController.setupForm(null, map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            Role role = (Role) map.get("role");

            // we're on purpose doing nothing here - we are in essence just updating application

            BindingResult errors = new BeanPropertyBindingResult(role, "role");
            SessionStatus ss = new SimpleSessionStatus();
            view = roleFormController.processSubmit(role, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving new role form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testExistingRoleFormController() {
        try {
            log.info("Testing saving existing role form...");
            assertNotNull("roleFormController is null", roleFormController);

            ModelMap map = new ModelMap();
            String view = roleFormController.setupForm(getRoles().get(0).getId(), map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            Role role = (Role) map.get("role");

            // we're on purpose doing nothing here - we are in essence just updating application

            BindingResult errors = new BeanPropertyBindingResult(role, "role");
            SessionStatus ss = new SimpleSessionStatus();
            view = roleFormController.processSubmit(role, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving existing role form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    // Spring IoC
    @Autowired
    private RoleFormController roleFormController;

}