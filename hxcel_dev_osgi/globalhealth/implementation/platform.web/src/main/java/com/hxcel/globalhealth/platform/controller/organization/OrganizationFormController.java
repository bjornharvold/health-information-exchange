package com.hxcel.globalhealth.platform.controller.organization;

import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;
import com.hxcel.globalhealth.platform.controller.organization.validator.OrganizationValidator;
import com.hxcel.globalhealth.platform.spec.CountryService;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationTypeCd;
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
 * <p>Title: OrganizationFormController</p>
 * <p>Description: Handles working with a single organization</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/edit.admin")
@SessionAttributes("organizationForm")
public class OrganizationFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationFormController.class);
    private String FORM = "organization.edit";

    @ModelAttribute(value = "types")
    protected OrganizationTypeCd[] populateOrganizationalType() {
        return OrganizationTypeCd.values();
    }

    @ModelAttribute(value = "countries")
    protected List<ICountryDto> populateCountries() throws Exception {
        return countryService.searchForCountries(null, null, null);
    }

    @ModelAttribute(value = "statuses")
    protected OrganizationStatusCd[] populateOrganizationalStatus() {
        return OrganizationStatusCd.values();
    }

    @ModelAttribute(value = "organizations")
    protected List<IOrganizationDto> populateOrganizations() throws Exception {
        return organizationService.searchForOrganizations(null, null, null);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false) String id, ModelMap model) throws Exception {
        IOrganizationDto organization = null;

        if (StringUtils.isBlank(id)) {
            organization = organizationService.createOrganization();
        } else {
            organization = organizationService.getOrganization(id);
        }

        model.addAttribute("organizationForm", organization);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute(value = "organizationForm") IOrganizationDto organization,
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

            organizationService.saveOrUpdateOrganization(organization);
            status.setComplete();

        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(IOrganizationDto.class, organizationPropertyEditor);
        binder.registerCustomEditor(ICountryDto.class, countryPropertyEditor);
    }

    // Spring IoC
    private OrganizationService organizationService = null;
    private CountryService countryService = null;
    private PropertyEditor organizationPropertyEditor;
    private PropertyEditor countryPropertyEditor;

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    public void setOrganizationPropertyEditor(PropertyEditor organizationPropertyEditor) {
        this.organizationPropertyEditor = organizationPropertyEditor;
    }

    public void setCountryPropertyEditor(PropertyEditor countryPropertyEditor) {
        this.countryPropertyEditor = countryPropertyEditor;
    }
}