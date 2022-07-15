package com.hxcel.globalhealth.platform.controller.organization;

import com.hxcel.globalhealth.platform.controller.application.validator.ApplicationConfigurationValidator;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationConfigurationDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

/**
 * <p>Title: OrganizationApplicationConfigurationFormController</p>
 * <p>Description: Modifies a configuration property within an organization context</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/application/configuration/edit.admin")
@SessionAttributes("organizationApplicationConfigurationForm")
public class OrganizationApplicationConfigurationFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationApplicationConfigurationFormController.class);
    private final static String FORM = "organization.application.configuration.edit";

    /* cannot for OSGi
    @Autowired
    public OrganizationApplicationConfigurationFormController(OrganizationService organizationService,
                                                                  ApplicationService applicationService) {
        this.organizationService = organizationService;
        this.applicationService = applicationService;
    }
    */

    @ModelAttribute(value = "organization")
    protected IOrganizationDto populateOrganization(@RequestParam(value = "organizationId", required = true)String id) throws Exception {
        return organizationService.getOrganization(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false)String id, ModelMap model) throws Exception {
        IApplicationConfigurationDto ac = null;

        if (StringUtils.isBlank(id)) {
            ac = applicationService.createApplicationConfiguration();
        } else {
            ac = applicationService.getApplicationConfiguration(id);
        }

        model.addAttribute("organizationApplicationConfigurationForm", ac);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "applicationId", required = true) String applicationId,
                                   @RequestParam(value = "organizationId", required = true)String organizationId,
                                   @ModelAttribute(value = "organizationApplicationConfigurationForm")IApplicationConfigurationDto ac,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            new ApplicationConfigurationValidator().validate(ac, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                if (StringUtils.isBlank(ac.getId())) {
                    ac = applicationService.addApplicationConfiguration(applicationId, ac);
                } else {
                    ac = applicationService.updateApplicationConfiguration(ac);
                }
                status.setComplete();
                view = "redirect:/secure/organization/application/configuration/search.admin?view=organization.application.configuration.data&id=" + applicationId + "&organizationId=" + organizationId;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
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