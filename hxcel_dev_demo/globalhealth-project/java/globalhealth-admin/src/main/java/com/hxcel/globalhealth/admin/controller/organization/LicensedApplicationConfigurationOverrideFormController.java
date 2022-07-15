package com.hxcel.globalhealth.admin.controller.organization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.apache.commons.lang.StringUtils;
import org.springmodules.web.propertyeditors.ReflectivePropertyEditor;

import com.hxcel.globalhealth.domain.platform.model.*;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.admin.controller.organization.validator.ApplicationConfigurationOverrideValidator;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Title: LicensedApplicationConfigurationOverrideFormController</p>
 * <p>Description: Form page for overriding default application configurations</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/licensed/application/override/edit.admin")
@SessionAttributes(types = ApplicationConfigurationOverride.class)
public class LicensedApplicationConfigurationOverrideFormController {
    private static final Logger log = LoggerFactory.getLogger(LicensedApplicationConfigurationOverrideFormController.class);
    private final static String FORM = "organization.licensed.application.override.edit";
    private final PlatformManager platformManager;

    @Autowired
    public LicensedApplicationConfigurationOverrideFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @ModelAttribute(value = "configurations")
    protected List<ApplicationConfiguration> populateConfigurations(@RequestParam(value = "applicationId", required = true)String applicationId) {
        List<ApplicationConfiguration> result = null;

        try {
            result = platformManager.searchForApplicationConfigurations(applicationId, null, null, null);
        } catch (DomainException e) {
            log.error("Could not load configurations list", e);
        }

        return result;
    }

    @ModelAttribute(value = "organization")
    protected Organization populateOrganization(@RequestParam(value = "organizationId", required = true)String organizationId) {
        Organization result = null;

        try {
            result = platformManager.getOrganization(organizationId);
        } catch (DomainException e) {
            log.error("Could not load organization with id: " + organizationId, e);
        }

        return result;
    }

    @ModelAttribute(value = "ol")
    protected OrganizationLicense populateOrganizationLicense(@RequestParam(value = "organizationLicenseId", required = true)String organizationLicenseId) {
        OrganizationLicense result = null;

        try {
            result = platformManager.getOrganizationLicense(organizationLicenseId);
        } catch (DomainException e) {
            log.error("Could not load organization license with id: " + organizationLicenseId, e);
        }

        return result;
    }

    @ModelAttribute(value = "platform")
    protected Boolean populatePlatform(@RequestParam(value = "platform", required = true)Boolean platform) {
        return platform;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false)String id, ModelMap model) throws Exception {
        ApplicationConfigurationOverride configuration = null;

        if (StringUtils.isBlank(id)) {
            configuration = new ApplicationConfigurationOverride();
        } else {
            configuration = platformManager.getApplicationConfigurationOverride(id);
        }

        model.addAttribute("override", configuration);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "organizationLicenseId", required = true) String organizationLicenseId,
                                   @RequestParam(value = "organizationId", required = true) String organizationId,
                                   @RequestParam(value = "platform", required = true) Boolean platform,
                                   @ModelAttribute(value = "override")ApplicationConfigurationOverride oc,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            new ApplicationConfigurationOverrideValidator().validate(oc, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                if (StringUtils.isBlank(oc.getId())) {
                    oc = platformManager.addApplicationConfigurationOverride(organizationLicenseId, oc);
                } else {
                    oc = platformManager.updateApplicationConfigurationOverride(oc);
                }
                status.setComplete();
                view = "redirect:/secure/organization/licensed/application/overrides/search.admin?id=" + organizationLicenseId + "&organizationId=" + organizationId + "&view=organization.licensed.application.overrides.data";
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(ApplicationConfiguration.class, applicationConfigurationPropertyEditor);
        binder.registerCustomEditor(OrganizationLicense.class, organizationLicensePropertyEditor);
    }

    @Resource(name = "applicationConfigurationPropertyEditor")
    private ReflectivePropertyEditor applicationConfigurationPropertyEditor;

    @Resource(name = "organizationLicensePropertyEditor")
    private ReflectivePropertyEditor organizationLicensePropertyEditor;
}