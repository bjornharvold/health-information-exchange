package com.hxcel.globalhealth.platform.controller.application;

import com.hxcel.globalhealth.platform.controller.application.validator.ApplicationConfigurationValidator;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationConfigurationDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

/**
 * <p>Title: ApplicationConfigurationFormController</p>
 * <p>Description: Handles working with a single application</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/application/configuration/edit.admin")
@SessionAttributes("applicationConfigurationForm")
public class ApplicationConfigurationFormController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationConfigurationFormController.class);
    private final static String FORM = "application.configuration.edit";

    /* cannot for OSGi
    @Autowired
    public ApplicationConfigurationFormController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    */

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false)String id, ModelMap model) throws Exception {
        IApplicationConfigurationDto ac = null;

        if (StringUtils.isBlank(id)) {
            ac = applicationService.createApplicationConfiguration();
        } else {
            ac = applicationService.getApplicationConfiguration(id);
        }

        model.addAttribute("applicationConfigurationForm", ac);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "applicationId", required = true) String applicationId,
                                   @ModelAttribute(value = "applicationConfigurationForm")IApplicationConfigurationDto ac,
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
                view = "redirect:/secure/application/configuration/search.admin?view=application.configuration.data&id=" + applicationId;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    // Spring IoC
    private ApplicationService applicationService = null;

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
}