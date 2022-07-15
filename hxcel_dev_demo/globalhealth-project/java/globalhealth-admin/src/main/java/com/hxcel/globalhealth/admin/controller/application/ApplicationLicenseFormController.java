package com.hxcel.globalhealth.admin.controller.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.apache.commons.lang.StringUtils;

import com.hxcel.globalhealth.domain.platform.model.License;
import com.hxcel.globalhealth.domain.platform.model.enums.*;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.admin.controller.application.validator.LicenseValidator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Title: LicenseForm</p>
 * <p>Description: Handles working with a single application</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/application/license/edit.admin")
@SessionAttributes(types = License.class)
public class ApplicationLicenseFormController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationLicenseFormController.class);
    private final static String FORM = "application.license.edit";
    private final PlatformManager platformManager;

    @Autowired
    public ApplicationLicenseFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @ModelAttribute(value = "types")
    protected LicenseTypeCd[] populateLicenseType() {
        return LicenseTypeCd.values();
    }

    @ModelAttribute(value = "statuses")
    protected LicenseStatusCd[] populateLicenseStatus() {
        return LicenseStatusCd.values();
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false)String id, ModelMap model) throws Exception {
        License license = null;

        if (StringUtils.isBlank(id)) {
            license = new License();
        } else {
            license = platformManager.getLicense(id);
        }

        model.addAttribute("license", license);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "applicationId", required = true) String applicationId, @ModelAttribute(value = "license")License license,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            new LicenseValidator().validate(license, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                if (StringUtils.isBlank(license.getId())) {
                    license = platformManager.addApplicationLicense(applicationId, license);
                } else {
                    license = platformManager.updateLicense(license);
                }
                status.setComplete();
                view = "redirect:/secure/application/license/search.admin?id=" + applicationId + "&view=application.license.data";
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}