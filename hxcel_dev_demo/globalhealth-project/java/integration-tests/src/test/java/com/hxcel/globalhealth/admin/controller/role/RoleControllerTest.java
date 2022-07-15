package com.hxcel.globalhealth.admin.controller.role;


import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.AbstractIntegrationBaseTest;
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
 * Description: Test application controller functionality
 */
public class RoleControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(RoleControllerTest.class);

    @Test
    public void testLoadRoles() {
        log.info("Testing showRoles...");
        assertNotNull("Controller is null", roleController);

        try {
            ModelMap map = new ModelMap();
            String view = roleController.showRoles(null, null, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 5 properties", 5, map.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load roles" + e.getMessage());
        }
        log.info("Testing showRoles COMPLETE!");
    }

    @Test
    public void testDeleteRole() {
        log.info("Testing deleteRole...");
        assertNotNull("Controller is null", roleController);

        try {
            ModelMap map = new ModelMap();
            String roleId = getRoles().get(0).getId();

            String view = roleController.showRoles(null, null, null, null, map);

            log.info("Testing system. Should have at least one role");
            assertNotNull("Roles is null", ((List<Role>)map.get("roles")));
            assertTrue("Has 0 roles in system", ((Integer)map.get("count")) > 0);

            int sizeBeforeDelete = (Integer)map.get("count");

            log.info("Deleting one role");
            map = new ModelMap();
            view = roleController.deleteRole(roleId, null, map);
            log.info("Testing system again. Should now have 1 role less");
            assertTrue("Doesn't contain one role less", sizeBeforeDelete > (Integer)map.get("count"));
            log.info("System now has 1 role less");

            assertNotNull("view is not defined", view);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load roles" + e.getMessage());
        }
        log.info("Testing deleteRole... COMPLETE");
    }

    // Spring IoC
    @Autowired
    private RoleController roleController;
}