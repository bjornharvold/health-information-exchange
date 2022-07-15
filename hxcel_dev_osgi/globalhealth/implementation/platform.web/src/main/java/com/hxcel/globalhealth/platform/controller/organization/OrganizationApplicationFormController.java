package com.hxcel.globalhealth.platform.controller.organization;

import com.hxcel.globalhealth.platform.controller.application.validator.ApplicationValidator;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationTypeCd;
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

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: OrganizationApplicationForm</p>
 * <p>Description: Same as its parent but it assumes that there is already an owner for it so an orgid must be present</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/application/edit.admin")
@SessionAttributes("organizationApplicationForm")
public class OrganizationApplicationFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationApplicationFormController.class);
    private final static String FORM = "organization.application.edit";

    /* cannot for OSGi
    @Autowired
    public OrganizationApplicationFormController(OrganizationService organizationService,
                                                                  ApplicationService applicationService) {
        this.organizationService = organizationService;
        this.applicationService = applicationService;
    }
    */

    @ModelAttribute(value = "organization")
    protected IOrganizationDto populateOrganization(@RequestParam(value = "owner", required = true)String organizationId) throws Exception {
        return organizationService.getOrganization(organizationId);
    }

    @ModelAttribute(value = "types")
    protected ApplicationTypeCd[] populateApplicationType() {
        ApplicationTypeCd[] types =  ApplicationTypeCd.values();
        List<ApplicationTypeCd> list = new ArrayList<ApplicationTypeCd>();

        for (ApplicationTypeCd type : types) {
            if (!type.equals(ApplicationTypeCd.PLATFORM)) {
                list.add(type);
            }
        }

        return list.toArray(new ApplicationTypeCd[list.size()]);
    }

    @ModelAttribute(value = "statuses")
    protected ApplicationStatusCd[] populateApplicationStatus() {
        return ApplicationStatusCd.values();
    }

    @ModelAttribute(value = "platform")
    protected Boolean populatePlatform(@RequestParam(value = "platform", required = true)Boolean platform) throws Exception {
        return platform;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false)String id,
                               ModelMap model) throws Exception {
        IApplicationDto app = null;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching organization application tiles form view: '" + FORM + "'");
        }

        if (StringUtils.isBlank(id)) {
            app = applicationService.createApplication();
        } else {
            app = applicationService.getApplication(id);
        }

        model.addAttribute("organizationApplicationForm", app);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute(value = "organizationApplicationForm")IApplicationDto application,
                                   BindingResult result, SessionStatus status) throws Exception {
        if (log.isTraceEnabled()) {
            log.trace("Processing POST on " + this.getClass().getCanonicalName() + " form view: '" + FORM + "'");
        }

        String view = null;

        try {
            new ApplicationValidator().validate(application, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {

                if (StringUtils.isBlank(application.getId())) {
                    view = "redirect:/secure/organization/application/search.admin?view=organization.applications.data&id="+application.getOwner().getId() ;
                } else {
                    view = "redirect:/secure/organization/application/view.admin?view=organization.application.data&id="+application.getId() + "&organizationId="+application.getOwner().getId() ;
                }

                applicationService.saveOrUpdateApplication(application);
                status.setComplete();

            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(IOrganizationDto.class, organizationPropertyEditor);
    }

    // Spring IoC
    private OrganizationService organizationService;
    private ApplicationService applicationService;
    private PropertyEditor organizationPropertyEditor;

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public void setOrganizationPropertyEditor(PropertyEditor organizationPropertyEditor) {
        this.organizationPropertyEditor = organizationPropertyEditor;
    }
}