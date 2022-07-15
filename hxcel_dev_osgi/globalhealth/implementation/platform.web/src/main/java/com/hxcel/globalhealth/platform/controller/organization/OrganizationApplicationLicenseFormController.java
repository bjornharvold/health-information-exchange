package com.hxcel.globalhealth.platform.controller.organization;

import com.hxcel.globalhealth.platform.controller.application.validator.LicenseValidator;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.dto.ILicenseDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import com.hxcel.globalhealth.platform.spec.model.enums.LicenseStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.LicenseTypeCd;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Title: OrganizationApplicationLicenseFormController</p>
 * <p>Description: Modifies an application license within an organization context</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/application/license/edit.admin")
@SessionAttributes("organizationApplicationLicenseForm")
public class OrganizationApplicationLicenseFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationApplicationLicenseFormController.class);
    private final static String FORM = "organization.application.license.edit";

    /* cannot for OSGi
    @Autowired
    public OrganizationApplicationLicenseFormController(OrganizationService organizationService,
                                                                  ApplicationService applicationService) {
        this.organizationService = organizationService;
        this.applicationService = applicationService;
    }
    */

    @ModelAttribute(value = "types")
    protected LicenseTypeCd[] populateLicenseType() {
        return LicenseTypeCd.values();
    }

    @ModelAttribute(value = "statuses")
    protected LicenseStatusCd[] populateLicenseStatus() {
        return LicenseStatusCd.values();
    }

    @ModelAttribute(value = "organization")
    protected IOrganizationDto populateOrganization(@RequestParam(value = "organizationId", required = true)String id) throws Exception {
        return organizationService.getOrganization(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false)String id, ModelMap model) throws Exception {
        ILicenseDto license = null;

        if (StringUtils.isBlank(id)) {
            license = applicationService.createLicense();
        } else {
            license = applicationService.getLicense(id);
        }

        model.addAttribute("organizationApplicationLicenseForm", license);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "applicationId", required = true) String applicationId,
                                   @RequestParam(value = "organizationId", required = true)String organizationId,
                                   @ModelAttribute(value = "organizationApplicationLicenseForm")ILicenseDto license,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            new LicenseValidator().validate(license, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                if (StringUtils.isBlank(license.getId())) {
                    license = applicationService.addApplicationLicense(applicationId, license);
                } else {
                    license = applicationService.updateLicense(license);
                }
                status.setComplete();
                view = "redirect:/secure/organization/application/license/search.admin?view=organization.application.license.data&id=" + applicationId + "&organizationId=" + organizationId;
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

    // Spring IoC
    private OrganizationService organizationService;
    private ApplicationService applicationService;

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
}