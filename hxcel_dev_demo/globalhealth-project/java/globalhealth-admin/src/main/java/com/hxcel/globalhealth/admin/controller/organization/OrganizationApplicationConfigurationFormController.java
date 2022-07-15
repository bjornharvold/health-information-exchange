package com.hxcel.globalhealth.admin.controller.organization;

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
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.admin.controller.application.validator.ApplicationConfigurationValidator;

/**
 * <p>Title: OrganizationApplicationConfigurationFormController</p>
 * <p>Description: Modifies a configuration property within an organization context</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/application/configuration/edit.admin")
@SessionAttributes(types = ApplicationConfiguration.class)
public class OrganizationApplicationConfigurationFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationApplicationConfigurationFormController.class);
    private final static String FORM = "organization.application.configuration.edit";
    private final PlatformManager platformManager;

    @Autowired
    public OrganizationApplicationConfigurationFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @ModelAttribute(value = "organization")
    protected Organization populateOrganization(@RequestParam(value = "organizationId", required = true)String id) throws Exception {
        return platformManager.getOrganization(id);
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
    protected String processSubmit(@RequestParam(value = "applicationId", required = true) String applicationId,
                                   @RequestParam(value = "organizationId", required = true)String organizationId,
                                   @ModelAttribute(value = "configuration")ApplicationConfiguration ac,
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
                view = "redirect:/secure/organization/application/configuration/search.admin?view=organization.application.configuration.data&id=" + applicationId + "&organizationId=" + organizationId;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }
}