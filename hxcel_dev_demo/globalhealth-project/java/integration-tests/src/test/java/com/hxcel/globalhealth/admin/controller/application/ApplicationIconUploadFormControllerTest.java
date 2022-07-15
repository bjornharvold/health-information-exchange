package com.hxcel.globalhealth.admin.controller.application;


import com.hxcel.globalhealth.domain.platform.model.ApplicationConfiguration;
import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.enums.IconSizeCd;
import com.hxcel.globalhealth.AbstractIntegrationBaseTest;
import com.hxcel.globalhealth.admin.controller.form.ImageFileUploadForm;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.RandomStringUtils;

import java.io.InputStream;

/**
 * User: Bjorn Harvold
 * Date: Oct 11, 2008
 * Time: 5:50:24 PM
 * Description: Tests uploading an application icon
 */
public class ApplicationIconUploadFormControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(ApplicationIconUploadFormControllerTest.class);

    @Test
    public void testPopulateApplication() {
        log.info("Testing populating application ...");

        try {
            Application app = applicationIconUploadFormController.populateApplication(getApplications().get(0).getId());
            assertNotNull("Application is null", app);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Testing populating application COMPLETE");
    }

    @Test
    public void testApplicationIconFormController() {
        try {
            log.info("Testing saving new application icon form...");
            assertNotNull("controller is null", applicationIconUploadFormController);

            ModelMap map = new ModelMap();
            String view = applicationIconUploadFormController.setupForm(null, map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            ImageFileUploadForm form = (ImageFileUploadForm) map.get("upload");

            // attach a file
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("test.gif");
            MockMultipartFile file = new MockMultipartFile("test", "test.gif", "image/gif", is);
            form.setFile(file);
            form.setSize(IconSizeCd.SMALL);

            BindingResult errors = new BeanPropertyBindingResult(form, "upload");
            SessionStatus ss = new SimpleSessionStatus();
            view = applicationIconUploadFormController.processSubmit(getApplications().get(0).getId(), form, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving new application icon form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }
    
    // Spring IoC
    @Autowired
    private ApplicationIconUploadFormController applicationIconUploadFormController;
}