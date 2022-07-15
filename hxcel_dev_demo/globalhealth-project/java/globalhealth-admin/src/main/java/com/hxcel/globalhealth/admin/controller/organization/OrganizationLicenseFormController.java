package com.hxcel.globalhealth.admin.controller.organization;

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

import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.OrganizationLicense;
import com.hxcel.globalhealth.domain.platform.model.License;
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.platform.model.enums.OrganizationLicenseStatusCd;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.admin.controller.organization.form.OrganizationLicenseForm;

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
@SessionAttributes(types = OrganizationLicenseForm.class)
public class OrganizationLicenseFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationLicenseFormController.class);
    private final PlatformManager platformManager;
    private String FORM = "organization.licensed.application.edit";

    @Autowired
    public OrganizationLicenseFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @ModelAttribute(value = "licenses")
    protected List<License> populateApplications(@RequestParam(value = "organizationId", required = true)String id,
                                                 @RequestParam(value = "platform", required = true)Boolean platform) {
        List<License> result = null;

        try {
            result = platformManager.getLicensedApplicationsExcludeOwner(id, platform);
        } catch (DomainException e) {
            log.error("Cannot retrieve applications list for form");
        }

        return result;
    }

    @ModelAttribute(value = "organization")
    protected Organization populateOrganization(@RequestParam(value = "organizationId", required = true)String id) {
        Organization result = null;

        try {
            result = platformManager.getOrganization(id);
        } catch (DomainException e) {
            log.error("Cannot retrieve organization with id: " + id);
        }

        return result;
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
            List<OrganizationLicense> licenses = platformManager.searchForLicensedThirdPartyApplications(id, null, platform, OrganizationLicenseStatusCd.ACTIVE, null, null);
            form.setOrganizationId(id);

            if (licenses != null) {
                for (OrganizationLicense ol : licenses) {
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

        platformManager.licenseApplicationsForOrganization(form.getOrganizationId(), form.getLicenseIds());
        status.setComplete();

        return "redirect:/secure/organization/licensed/application/search.admin?view=organization.licensed.applications.data&status=ACTIVE&id=" + form.getOrganizationId();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}