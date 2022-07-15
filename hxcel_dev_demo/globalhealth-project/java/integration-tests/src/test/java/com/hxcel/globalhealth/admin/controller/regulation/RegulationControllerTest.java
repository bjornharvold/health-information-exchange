package com.hxcel.globalhealth.admin.controller.regulation;


import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.AbstractIntegrationBaseTest;
import com.hxcel.globalhealth.admin.controller.role.RoleController;
import com.hxcel.globalhealth.admin.controller.role.RoleControllerTest;
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
 * Description: Test regulation controller functionality
 */
public class RegulationControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(RegulationControllerTest.class);

    @Test
    public void testShowRegulations() {
        log.info("Testing showRegulations...");
        assertNotNull("Controller is null", regulationController);

        try {
            ModelMap map = new ModelMap();
            String view = regulationController.showRegulations(null, null, null, null, map);

            assertNotNull("view is not defined", view);
            assertEquals("Map should hold 5 properties", 5, map.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load roles" + e.getMessage());
        }
        log.info("Testing showRegulations COMPLETE!");
    }

    @Test
    public void testDeleteRegulation() {
        log.info("Testing deleteRegulation...");
        assertNotNull("Controller is null", regulationController);

        try {
            ModelMap map = new ModelMap();
            String roleId = getRegulations().get(0).getId();

            String view = regulationController.showRegulations(null, null, null, null, map);

            log.info("Testing system. Should have at least one regulation");
            assertNotNull("Regulations is null", ((List<Role>)map.get("regulations")));
            assertTrue("Has 0 regulations in system", ((Integer)map.get("count")) > 0);

            int sizeBeforeDelete = (Integer)map.get("count");

            log.info("Deleting one regulation");
            map = new ModelMap();
            view = regulationController.deleteRegulation(roleId, null, map);
            log.info("Testing system again. Should now have 1 regulation less");
            assertTrue("Doesn't contain one regulation less", sizeBeforeDelete > (Integer)map.get("count"));
            log.info("System now has 1 regulation less");

            assertNotNull("view is not defined", view);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail("Couldn't load roles" + e.getMessage());
        }
        log.info("Testing deleteRegulation... COMPLETE");
    }

    // Spring IoC
    @Autowired
    private RegulationController regulationController;
}