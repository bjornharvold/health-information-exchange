package com.hxcel.globalhealth.platform.controller.organization;

import com.hxcel.globalhealth.platform.controller.organization.validator.ResourceBundleEntryOverrideValidator;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.dto.*;
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
@RequestMapping(value = "/secure/organization/licensed/application/resourcebundle/override/edit.admin")
@SessionAttributes("licensedApplicationResourceBundleOverrideForm")
public class LicensedApplicationResourceBundleOverrideFormController {
    private static final Logger log = LoggerFactory.getLogger(LicensedApplicationResourceBundleOverrideFormController.class);
    private final static String FORM = "organization.licensed.application.resourcebundle.override.edit";

    /* cannot for OSGi
    @Autowired
    public LicensedApplicationResourceBundleOverrideFormController(OrganizationService organizationService,
                                                                   ApplicationService applicationService) {
        this.organizationService = organizationService;
        this.applicationService = applicationService;
    }
    */

    @ModelAttribute(value = "resourcebundleentries")
    protected List<IResourceBundleEntryDto> populateResourceBundleEntries(@RequestParam(value = "applicationId", required = true) String applicationId) throws Exception {
        return applicationService.searchForResourceBundleEntries(applicationId, null, null, null);
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
        IResourceBundleEntryOverrideDto override = null;

        if (StringUtils.isBlank(id)) {
            override = applicationService.createResourceBundleEntryOverride();
        } else {
            override = applicationService.getResourceBundleEntryOverride(id);
        }

        model.addAttribute("licensedApplicationResourceBundleOverrideForm", override);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "organizationLicenseId", required = true) String organizationLicenseId,
                                   @RequestParam(value = "organizationId", required = true) String organizationId,
                                   @RequestParam(value = "platform", required = true) Boolean platform,
                                   @ModelAttribute(value = "licensedApplicationResourceBundleOverrideForm") IResourceBundleEntryOverrideDto oc,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            new ResourceBundleEntryOverrideValidator().validate(oc, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                oc = applicationService.saveOrUpdateResourceBundleEntryOverride(organizationLicenseId, oc);

                status.setComplete();
                view = "redirect:/secure/organization/licensed/application/resourcebundle/overrides/search.admin?id=" + organizationLicenseId + "&organizationId=" + organizationId + "&view=organization.licensed.application.resourcebundle.overrides.data";
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(IResourceBundleEntryDto.class, resourceBundleEntryPropertyEditor);
        binder.registerCustomEditor(IOrganizationLicenseDto.class, organizationLicensePropertyEditor);
    }

    // Spring IoC
    private OrganizationService organizationService;
    private ApplicationService applicationService;
    private PropertyEditor resourceBundleEntryPropertyEditor;
    private PropertyEditor organizationLicensePropertyEditor;

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public void setResourceBundleEntryPropertyEditor(PropertyEditor resourceBundleEntryPropertyEditor) {
        this.resourceBundleEntryPropertyEditor = resourceBundleEntryPropertyEditor;
    }

    public void setOrganizationLicensePropertyEditor(PropertyEditor organizationLicensePropertyEditor) {
        this.organizationLicensePropertyEditor = organizationLicensePropertyEditor;
    }
}