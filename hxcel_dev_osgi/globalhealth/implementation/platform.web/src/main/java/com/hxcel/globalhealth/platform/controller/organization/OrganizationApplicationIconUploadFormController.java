package com.hxcel.globalhealth.platform.controller.organization;

import com.hxcel.globalhealth.platform.controller.form.ImageFileUploadForm;
import com.hxcel.globalhealth.platform.controller.form.validator.ImageFileUploadValidator;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import com.hxcel.globalhealth.platform.spec.model.enums.IconSizeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

/**
 * <p>Title: OrganizationApplicationIconUploadFormController</p>
 * <p>Description: Uploads an icon to associated with an application's icons</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/application/icon/upload.admin")
@SessionAttributes("organizationApplicationIconUploadForm")
public class OrganizationApplicationIconUploadFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationApplicationIconUploadFormController.class);
    private final static String FORM = "organization.application.icon.edit";

    /* cannot for OSGi
    @Autowired
    public OrganizationApplicationIconUploadFormController(OrganizationService organizationService,
                                                                  ApplicationService applicationService) {
        this.organizationService = organizationService;
        this.applicationService = applicationService;
    }
    */

    @ModelAttribute(value = "application")
    protected IApplicationDto populateApplication(@RequestParam(value = "id", required = true)String id) throws Exception {
        return applicationService.getApplication(id);
    }

    @ModelAttribute(value = "organization")
    protected IOrganizationDto populateOrganization(@RequestParam(value = "organizationId", required = true)String id) throws Exception {
        return organizationService.getOrganization(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "size", required = true) IconSizeCd size, ModelMap model) throws Exception {

        model.addAttribute("organizationApplicationIconUploadForm", new ImageFileUploadForm(size));

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "id", required = true)String id,
                                   @RequestParam(value = "organizationId", required = true)String organizationId,
                                   @ModelAttribute(value = "organizationApplicationIconUploadForm")ImageFileUploadForm form,
                                   BindingResult result,
                                   SessionStatus status) throws Exception {
        String view = null;

        try {
            new ImageFileUploadValidator().validate(form, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                applicationService.addApplicationIcon(id, form.getFile().getOriginalFilename(), form.getFile().getInputStream(), form.getSize());
                status.setComplete();
                view = "redirect:/secure/organization/application/view.admin?view=organization.application.data&id=" + id + "&organizationId=" + organizationId;
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