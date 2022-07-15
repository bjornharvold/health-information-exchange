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

import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.platform.model.enums.OrganizationTypeCd;
import com.hxcel.globalhealth.domain.platform.model.enums.OrganizationStatusCd;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.admin.controller.organization.validator.OrganizationValidator;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Title: OrganizationFormController</p>
 * <p>Description: Handles working with a single organization</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/edit.admin")
@SessionAttributes(types = Organization.class)
public class OrganizationFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationFormController.class);
    private final PlatformManager platformManager;
    private String FORM = "organization.edit";


    @Autowired
    public OrganizationFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @ModelAttribute(value = "types")
    protected OrganizationTypeCd[] populateOrganizationalType() {
        return OrganizationTypeCd.values();
    }

    @ModelAttribute(value = "countries")
    protected List<Country> populateCountries() throws Exception {
        return platformManager.searchForCountries(null, null, null);
    }

    @ModelAttribute(value = "statuses")
    protected OrganizationStatusCd[] populateOrganizationalStatus() {
        return OrganizationStatusCd.values();
    }

    @ModelAttribute(value = "organizations")
    protected List<Organization> populateOrganizations() {
        List<Organization> result = null;

        try {
            result = platformManager.searchForOrganizations(null, null, null);
        } catch (DomainException e) {
            log.error("Could not load organization reference data");
        }

        return result;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false) String id, ModelMap model) throws Exception {
        Organization organization = null;

        if (StringUtils.isBlank(id)) {
            organization = new Organization();
        } else {
            organization = platformManager.getOrganization(id);
        }

        model.addAttribute("organization", organization);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute(value = "organization") Organization organization,
                                   BindingResult result, SessionStatus status) throws Exception {
        new OrganizationValidator().validate(organization, result);
        String view = null;

        if (result.hasErrors()) {
            view = FORM;
        } else {

            if (StringUtils.isBlank(organization.getId())) {
                view = "redirect:/secure/organization/search.admin?view=organizations.data";
            } else {
                view = "redirect:/secure/organization/view.admin?view=organization.data&id=" + organization.getId();
            }

            platformManager.saveOrUpdateOrganization(organization);
            status.setComplete();

        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Organization.class, organizationPropertyEditor);
        binder.registerCustomEditor(Country.class, countryPropertyEditor);
    }

    @Resource(name = "organizationPropertyEditor")
    private ReflectivePropertyEditor organizationPropertyEditor;

    @Resource(name = "countryPropertyEditor")
    private ReflectivePropertyEditor countryPropertyEditor;
}