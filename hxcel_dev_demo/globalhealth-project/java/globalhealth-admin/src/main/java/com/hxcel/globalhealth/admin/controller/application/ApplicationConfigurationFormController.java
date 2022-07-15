package com.hxcel.globalhealth.admin.controller.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.apache.commons.lang.StringUtils;

import com.hxcel.globalhealth.domain.platform.model.ApplicationConfiguration;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.admin.controller.application.validator.ApplicationConfigurationValidator;

/**
 * <p>Title: LicenseForm</p>
 * <p>Description: Handles working with a single application</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/application/configuration/edit.admin")
@SessionAttributes(types = ApplicationConfiguration.class)
public class ApplicationConfigurationFormController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationConfigurationFormController.class);
    private final static String FORM = "application.configuration.edit";
    private final PlatformManager platformManager;

    @Autowired
    public ApplicationConfigurationFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false)String id, ModelMap model) throws Exception {
        ApplicationConfiguration ac = null;

        if (StringUtils.isBlank(id)) {
            ac = new ApplicationConfiguration();
        } else {
            ac = platformManager.getApplicationConfiguration(id);
        }

        model.addAttribute("configuration", ac);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "applicationId", required = true) String applicationId, @ModelAttribute(value = "configuration")ApplicationConfiguration ac,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            new ApplicationConfigurationValidator().validate(ac, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                if (StringUtils.isBlank(ac.getId())) {
                    ac = platformManager.addApplicationConfiguration(applicationId, ac);
                } else {
                    ac = platformManager.updateApplicationConfiguration(ac);
                }
                status.setComplete();
                view = "redirect:/secure/application/configuration/search.admin?view=application.configuration.data&id=" + applicationId;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }
}