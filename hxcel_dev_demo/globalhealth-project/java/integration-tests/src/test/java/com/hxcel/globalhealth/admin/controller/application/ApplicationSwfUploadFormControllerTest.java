package com.hxcel.globalhealth.admin.controller.application;


import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.AbstractIntegrationBaseTest;
import com.hxcel.globalhealth.admin.controller.form.FileUploadForm;
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

import java.io.InputStream;

/**
 * User: Bjorn Harvold
 * Date: Oct 11, 2008
 * Time: 5:50:24 PM
 * Description: Tests uploading an application icon
 */
public class ApplicationSwfUploadFormControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(ApplicationSwfUploadFormControllerTest.class);

    @Test
    public void testPopulateApplication() {
        log.info("Testing populating application ...");

        try {
            Application app = applicationSwfUploadFormController.populateApplication(getApplications().get(0).getId());
            assertNotNull("Application is null", app);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Testing populating application COMPLETE");
    }

    @Test
    public void testApplicationSwfFormController() {
        try {
            log.info("Testing saving new application swf form...");
            assertNotNull("controller is null", applicationSwfUploadFormController);

            ModelMap map = new ModelMap();
            String view = applicationSwfUploadFormController.setupForm(map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            FileUploadForm form = (FileUploadForm) map.get("upload");

            // attach a file
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("test.gif");
            MockMultipartFile file = new MockMultipartFile("test", "test.swf", "application/x-shockwave-flash", is);
            form.setFile(file);

            BindingResult errors = new BeanPropertyBindingResult(form, "upload");
            SessionStatus ss = new SimpleSessionStatus();
            view = applicationSwfUploadFormController.processSubmit(getApplications().get(0).getId(), form, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving new application swf form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    // Spring IoC
    @Autowired
    private ApplicationSwfUploadFormController applicationSwfUploadFormController;
}