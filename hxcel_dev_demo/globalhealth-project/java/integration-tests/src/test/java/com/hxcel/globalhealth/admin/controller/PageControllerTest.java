package com.hxcel.globalhealth.admin.controller;


import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.domain.platform.model.enums.ApplicationTypeCd;
import com.hxcel.globalhealth.AbstractIntegrationBaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Oct 17, 2008
 * Time: 1:38:37 PM
 * Description:
 */
public class PageControllerTest extends AbstractIntegrationBaseTest {
    private static final Logger log = LoggerFactory.getLogger(PageController.class);

    @Test
    public void testDispatch() {
        log.info("Testing pageController.dispatch()");

        String view = "test";

        try {
            String view2 = pageController.dispatch(view);

            assertNotNull("View is empty", view2);
            assertEquals("incoming view is not the same as outgoing view", view, view2);


        } catch (Exception e) {
            fail(e.getMessage());
        }
        log.info("Testing pageController.dispatch() COMPLETE");
    }

    @Test
    public void testShowLoginBox() {
        log.info("Testing pageController.showLoginBox()");

        try {
            String view = pageController.showLoginBox();

            assertNotNull("View is empty", view);

        } catch (Exception e) {
            fail(e.getMessage());
        }

        log.info("Testing pageController.showLoginBox() COMPLETE");
    }

    @Test
    public void testLoadPlatform() {
        log.info("Testing pageController.loadPlatform()");

        try {
            Application app = getApplications().get(0);
            String orgId = app.getOwner().getId();
            String appId = app.getId();

            ModelMap map = new ModelMap();
            String view = pageController.loadPlatform(orgId, appId, map);
            assertNotNull("View is empty", view);
            assertNull("Dto is not empty. The application should not be available yet", map.get("dto"));

            // these are the conditions that will make the application available
            app.setApplicationStatus(ApplicationStatusCd.PUBLISHED);
            app.setPlatform(true);
            app.setApplicationType(ApplicationTypeCd.PLATFORM);

            platformManager.saveOrUpdateApplication(app);

            map = new ModelMap();
            view = pageController.loadPlatform(orgId, appId, map);
            assertNotNull("View is empty", view);
            assertNotNull("Dto is empty. The application should be available now", map.get("dto"));

        } catch (Exception e) {
            fail(e.getMessage());
        }

        log.info("Testing pageController.loadPlatform() COMPLETE");
    }

    // Spring IoC
    @Autowired
    private PageController pageController;
}