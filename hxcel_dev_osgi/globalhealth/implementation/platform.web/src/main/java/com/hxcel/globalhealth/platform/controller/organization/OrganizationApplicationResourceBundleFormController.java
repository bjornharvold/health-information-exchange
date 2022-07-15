package com.hxcel.globalhealth.platform.controller.organization;

import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;
import com.hxcel.globalhealth.platform.controller.application.validator.ResourceBundleEntryValidator;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.CountryService;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import com.hxcel.globalhealth.platform.spec.dto.IResourceBundleEntryDto;
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
 * <p>Title: OrganizationApplicationResourceBundleFormController</p>
 * <p>Description: Handles working with a single resource bundle "organizationApplicationResourceBundleForm" within an org context</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/application/resourcebundle/edit.admin")
@SessionAttributes("organizationApplicationResourceBundleForm")
public class OrganizationApplicationResourceBundleFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationApplicationResourceBundleFormController.class);
    private final static String FORM = "organization.application.resourcebundle.edit";

    /* cannotf for OSGi
    @Autowired
    public OrganizationApplicationResourceBundleFormController(OrganizationService organizationService,
                                                               CountryService countryService,
                                                               ApplicationService applicationService) {
        this.organizationService = organizationService;
        this.countryService = countryService;
        this.applicationService = applicationService;
    }
    */

    @ModelAttribute(value = "countries")
    protected List<ICountryDto> populateCountries() throws Exception {
        return countryService.searchForCountries(null, null, null);
    }

    @ModelAttribute(value = "organization")
    protected IOrganizationDto populateOrganization(@RequestParam(value = "organizationId", required = true) String id) throws Exception {
        return organizationService.getOrganization(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false) String id, ModelMap model) throws Exception {
        IResourceBundleEntryDto rbe = null;

        if (StringUtils.isBlank(id)) {
            rbe = applicationService.createResourceBundleEntry();
        } else {
            rbe = applicationService.getResourceBundleEntry(id);
        }

        model.addAttribute("organizationApplicationResourceBundleForm", rbe);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "applicationId", required = true) String applicationId,
                                   @RequestParam(value = "organizationId", required = true) String organizationId,
                                   @ModelAttribute(value = "organizationApplicationResourceBundleForm") IResourceBundleEntryDto rbe,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            new ResourceBundleEntryValidator().validate(rbe, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                rbe = applicationService.saveOrUpdateResourceBundle(rbe);
                status.setComplete();
                view = "redirect:/secure/organization/application/resourcebundle/search.admin?view=organization.application.resourcebundle.data&applicationId=" + applicationId + "&organizationId=" + organizationId;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(ICountryDto.class, countryPropertyEditor);
        binder.registerCustomEditor(IApplicationDto.class, applicationPropertyEditor);
    }

    // Spring IoC
    private OrganizationService organizationService = null;
    private ApplicationService applicationService = null;
    private CountryService countryService = null;
    private PropertyEditor countryPropertyEditor;
    private PropertyEditor applicationPropertyEditor;

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    public void setCountryPropertyEditor(PropertyEditor countryPropertyEditor) {
        this.countryPropertyEditor = countryPropertyEditor;
    }

    public void setApplicationPropertyEditor(PropertyEditor applicationPropertyEditor) {
        this.applicationPropertyEditor = applicationPropertyEditor;
    }
}