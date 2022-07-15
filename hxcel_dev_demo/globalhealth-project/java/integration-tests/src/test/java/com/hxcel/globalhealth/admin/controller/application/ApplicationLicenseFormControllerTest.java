package com.hxcel.globalhealth.admin.controller.application;


import com.hxcel.globalhealth.domain.platform.model.License;
import com.hxcel.globalhealth.domain.platform.model.enums.LicenseTypeCd;
import com.hxcel.globalhealth.domain.platform.model.enums.LicenseStatusCd;
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
import org.apache.commons.lang.RandomStringUtils;

import java.util.Date;

/**
 * User: Bjorn Harvold
 * Date: Oct 11, 2008
 * Time: 5:50:24 PM
 * Description: Tests saving an application
 */
public class ApplicationLicenseFormControllerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(ApplicationLicenseFormControllerTest.class);

    @Test
    public void testNewApplicationLicenseFormController() {
        try {
            log.info("Testing saving new application license form...");
            assertNotNull("applicationLicenseFormController is null", applicationLicenseFormController);

            ModelMap map = new ModelMap();
            String view = applicationLicenseFormController.setupForm(null, map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            License license = (License) map.get("license");

            license.setDescription(RandomStringUtils.randomAlphabetic(10));
            license.setName(RandomStringUtils.randomAlphabetic(10));
            license.setExpirationDate(new Date());
            license.setLicenseType(LicenseTypeCd.FREE);
            license.setStatus(LicenseStatusCd.ACTIVE);
            license.setPrice((float)10);

            BindingResult errors = new BeanPropertyBindingResult(license, "license");
            SessionStatus ss = new SimpleSessionStatus();
            view = applicationLicenseFormController.processSubmit(getApplications().get(0).getId(), license, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving new application license form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testExistingApplicationLicenseFormController() {
        try {
            log.info("Testing saving new application license form...");
            assertNotNull("applicationLicenseFormController is null", applicationLicenseFormController);

            ModelMap map = new ModelMap();
            String view = applicationLicenseFormController.setupForm(getLicenses().get(0).getId(), map);
            assertNotNull("View is null", view);
            assertEquals("Expecting 1 property in map", 1, map.size());
            License license = (License) map.get("license");

            license.setDescription(RandomStringUtils.randomAlphabetic(10));
            license.setName(RandomStringUtils.randomAlphabetic(10));
            license.setExpirationDate(new Date());
            license.setLicenseType(LicenseTypeCd.FREE);
            license.setStatus(LicenseStatusCd.ACTIVE);
            license.setPrice((float)10);

            BindingResult errors = new BeanPropertyBindingResult(license, "license");
            SessionStatus ss = new SimpleSessionStatus();
            view = applicationLicenseFormController.processSubmit(getApplications().get(0).getId(), license, errors, ss);
            assertNotNull("View is null", view);
            log.info("Testing saving new application license form COMPLETE!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    // Spring IoC
    @Autowired
    private ApplicationLicenseFormController applicationLicenseFormController;
}