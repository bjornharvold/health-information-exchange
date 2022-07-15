package com.hxcel.globalhealth.admin.controller;


import com.hxcel.globalhealth.AbstractIntegrationBaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * User: Bjorn Harvold
 * Date: Oct 17, 2008
 * Time: 1:38:37 PM
 * Description:
 */
public class HomepageControllerTest extends AbstractIntegrationBaseTest {

    @Test
    public void testShow() {
        try {
            ModelMap map = new ModelMap();
            String view = homepageController.showHomepage(map);

            assertNotNull("View is empty", view);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    // Spring IoC
    @Autowired
    private HomepageController homepageController;
}
