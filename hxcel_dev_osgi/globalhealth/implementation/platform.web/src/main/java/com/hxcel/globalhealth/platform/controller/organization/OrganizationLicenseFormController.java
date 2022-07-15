package com.hxcel.globalhealth.platform.controller.organization;

import com.hxcel.globalhealth.platform.controller.organization.form.OrganizationLicenseForm;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.dto.ILicenseDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationLicenseDto;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationLicenseStatusCd;
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
import java.util.List;

/**
 * <p>Title: OrganizationLicenseFormController</p>
 * <p>Description: Handles licensing applications for organizations</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/licensed/application/edit.admin")
@SessionAttributes("organizationLicenseForm")
public class OrganizationLicenseFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationLicenseFormController.class);
    private String FORM = "organization.licensed.application.edit";

    /* cannot for OSGi
    @Autowired
    public OrganizationLicenseFormController(OrganizationService organizationService,
                                             ApplicationService applicationService) {
        this.organizationService = organizationService;
        this.applicationService = applicationService;
    }
    */

    @ModelAttribute(value = "licenses")
    protected List<ILicenseDto> populateApplications(@RequestParam(value = "organizationId", required = true)String id,
                                                 @RequestParam(value = "platform", required = true)Boolean platform) throws Exception {
        return applicationService.getLicensedApplicationsExcludeOwner(id, platform);
    }

    @ModelAttribute(value = "organization")
    protected IOrganizationDto populateOrganization(@RequestParam(value = "organizationId", required = true)String id) throws Exception {
        return organizationService.getOrganization(id);
    }

    @ModelAttribute(value = "platform")
    protected Boolean populatePlatform(@RequestParam(value = "platform", required = true)Boolean platform) {
        return platform;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "organizationId", required = true)String id,
                               @RequestParam(value = "platform", required = true)Boolean platform,
                               ModelMap model) throws Exception {
        OrganizationLicenseForm form = null;

        form = new OrganizationLicenseForm();
        if (StringUtils.isNotBlank(id)) {
            form = new OrganizationLicenseForm();
            List<IOrganizationLicenseDto> licenses = organizationService.searchForLicensedThirdPartyApplications(id, null, platform, OrganizationLicenseStatusCd.ACTIVE, null, null);
            form.setOrganizationId(id);

            if (licenses != null) {
                for (IOrganizationLicenseDto ol : licenses) {
                    form.addLicenseId(ol.getId());
                }
            }
        }

        model.addAttribute("organizationLicenseForm", form);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute(value = "organizationLicenseForm")OrganizationLicenseForm form,
                                   BindingResult result, SessionStatus status) throws Exception {

        organizationService.licenseApplicationsForOrganization(form.getOrganizationId(), form.getLicenseIds());
        status.setComplete();

        return "redirect:/secure/organization/licensed/application/search.admin?view=organization.licensed.applications.data&status=ACTIVE&id=" + form.getOrganizationId();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    // Spring IoC
    private OrganizationService organizationService = null;
    private ApplicationService applicationService = null;

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
}