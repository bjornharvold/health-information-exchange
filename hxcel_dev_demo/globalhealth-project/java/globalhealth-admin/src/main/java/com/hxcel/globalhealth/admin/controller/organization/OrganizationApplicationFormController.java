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
import org.springmodules.web.propertyeditors.ReflectivePropertyEditor;

import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.platform.model.enums.ApplicationTypeCd;
import com.hxcel.globalhealth.domain.platform.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.admin.controller.application.validator.ApplicationValidator;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>Title: OrganizationApplicationForm</p>
 * <p>Description: Same as its parent but it assumes that there is already an owner for it so an orgid must be present</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/application/edit.admin")
@SessionAttributes(types = Application.class)
public class OrganizationApplicationFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationApplicationFormController.class);
    private final static String FORM = "organization.application.edit";
    private final PlatformManager platformManager;

    @Autowired
    public OrganizationApplicationFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @ModelAttribute(value = "organization")
    protected Organization populateOrganization(@RequestParam(value = "owner", required = true)String organizationId) throws Exception {
        return platformManager.getOrganization(organizationId);
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
        Application app = null;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching organization application tiles form view: '" + FORM + "'");
        }

        if (StringUtils.isBlank(id)) {
            app = new Application();
        } else {
            app = platformManager.getApplication(id);
        }

        model.addAttribute("application", app);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute(value = "application")Application application,
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

                platformManager.saveOrUpdateApplication(application);
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
        binder.registerCustomEditor(Organization.class, organizationPropertyEditor);
    }

    @Resource(name = "organizationPropertyEditor")
    private ReflectivePropertyEditor organizationPropertyEditor;
}