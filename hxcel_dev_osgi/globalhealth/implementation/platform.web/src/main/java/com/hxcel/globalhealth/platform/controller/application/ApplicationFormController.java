package com.hxcel.globalhealth.platform.controller.application;

import com.hxcel.globalhealth.platform.controller.application.validator.ApplicationValidator;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.IKeyValuePair;
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
 * <p>Title: ApplicationForm</p>
 * <p>Description: Handles working with a single application</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/application/edit.admin")
@SessionAttributes("applicationForm")
public class ApplicationFormController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationFormController.class);
    private final static String FORM = "application.edit";

    /*
    @Autowired
    public ApplicationFormController(ApplicationService applicationService,
                                     OrganizationService organizationService) {
        this.applicationService = applicationService;
        this.organizationService = organizationService;
    }
    */


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

    @ModelAttribute(value = "organizations")
    protected List<IKeyValuePair> populateOrganizations() throws Exception {
        return organizationService.getOrganizationThinList();
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
            log.trace("Dispatching application Tiles form view: '" + FORM + "'");
        }

        if (StringUtils.isBlank(id)) {
            app = applicationService.createApplication();
        } else {
            app = applicationService.getApplication(id);
        }

        model.addAttribute("applicationForm", app);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute(value = "applicationForm")IApplicationDto application,
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
                    view = "redirect:/secure/application/search.admin";
                } else {
                    view = "redirect:/secure/application/view.admin?platform="+application.getPlatform()+"&view=application.data&id="+application.getId();
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
    private ApplicationService applicationService = null;
    private OrganizationService organizationService = null;
    private PropertyEditor organizationPropertyEditor;

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void setOrganizationPropertyEditor(PropertyEditor organizationPropertyEditor) {
        this.organizationPropertyEditor = organizationPropertyEditor;
    }
}
