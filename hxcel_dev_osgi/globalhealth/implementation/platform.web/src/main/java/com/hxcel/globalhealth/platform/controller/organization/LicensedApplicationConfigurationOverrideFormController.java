package com.hxcel.globalhealth.platform.controller.organization;

import com.hxcel.globalhealth.platform.controller.organization.validator.ApplicationConfigurationOverrideValidator;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationConfigurationDto;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationConfigurationOverrideDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationLicenseDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.beans.PropertyEditor;
import java.util.List;

/**
 * <p>Title: LicensedApplicationConfigurationOverrideFormController</p>
 * <p>Description: Form page for overriding default application configurations</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/licensed/application/override/edit.admin")
@SessionAttributes("licensedApplicationConfigurationOverrideForm")
public class LicensedApplicationConfigurationOverrideFormController {
    private static final Logger log = LoggerFactory.getLogger(LicensedApplicationConfigurationOverrideFormController.class);
    private final static String FORM = "organization.licensed.application.override.edit";

    /* cannot for OSGi
    @Autowired
    public LicensedApplicationConfigurationOverrideFormController(OrganizationService organizationService,
                                                                  ApplicationService applicationService) {
        this.organizationService = organizationService;
        this.applicationService = applicationService;
    }
    */

    @ModelAttribute(value = "configurations")
    protected List<IApplicationConfigurationDto> populateConfigurations(@RequestParam(value = "applicationId", required = true) String applicationId) throws Exception {
        return applicationService.searchForApplicationConfigurations(applicationId, null, null, null);
    }

    @ModelAttribute(value = "organization")
    protected IOrganizationDto populateOrganization(@RequestParam(value = "organizationId", required = true) String organizationId) throws Exception {
        return organizationService.getOrganization(organizationId);
    }

    @ModelAttribute(value = "ol")
    protected IOrganizationLicenseDto populateOrganizationLicense(@RequestParam(value = "organizationLicenseId", required = true) String organizationLicenseId) throws Exception {
        return applicationService.getOrganizationLicense(organizationLicenseId);
    }

    @ModelAttribute(value = "platform")
    protected Boolean populatePlatform(@RequestParam(value = "platform", required = true) Boolean platform) {
        return platform;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false) String id, ModelMap model) throws Exception {
        IApplicationConfigurationOverrideDto configuration = null;

        if (StringUtils.isBlank(id)) {
            configuration = applicationService.createApplicationConfigurationOverride();
        } else {
            configuration = applicationService.getApplicationConfigurationOverride(id);
        }

        model.addAttribute("licensedApplicationConfigurationOverrideForm", configuration);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "organizationLicenseId", required = true) String organizationLicenseId,
                                   @RequestParam(value = "organizationId", required = true) String organizationId,
                                   @RequestParam(value = "platform", required = true) Boolean platform,
                                   @ModelAttribute(value = "licensedApplicationConfigurationOverrideForm") IApplicationConfigurationOverrideDto oc,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            new ApplicationConfigurationOverrideValidator().validate(oc, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                if (StringUtils.isBlank(oc.getId())) {
                    oc = applicationService.addApplicationConfigurationOverride(organizationLicenseId, oc);
                } else {
                    oc = applicationService.updateApplicationConfigurationOverride(oc);
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
        binder.registerCustomEditor(IApplicationConfigurationDto.class, applicationConfigurationPropertyEditor);
        binder.registerCustomEditor(IOrganizationLicenseDto.class, organizationLicensePropertyEditor);
    }

    // Spring IoC
    private OrganizationService organizationService;
    private ApplicationService applicationService;
    private PropertyEditor applicationConfigurationPropertyEditor;
    private PropertyEditor organizationLicensePropertyEditor;

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public void setApplicationConfigurationPropertyEditor(PropertyEditor applicationConfigurationPropertyEditor) {
        this.applicationConfigurationPropertyEditor = applicationConfigurationPropertyEditor;
    }

    public void setOrganizationLicensePropertyEditor(PropertyEditor organizationLicensePropertyEditor) {
        this.organizationLicensePropertyEditor = organizationLicensePropertyEditor;
    }
}