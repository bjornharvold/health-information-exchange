package com.hxcel.globalhealth.platform.controller.organization;

import com.hxcel.globalhealth.platform.controller.form.FileUploadForm;
import com.hxcel.globalhealth.platform.controller.form.validator.FileUploadValidator;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
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
@RequestMapping(value = "/secure/organization/application/swf/upload.admin")
@SessionAttributes("organizationApplicationSwfUploadForm")
public class OrganizationApplicationSwfUploadFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationApplicationSwfUploadFormController.class);
    private final static String FORM = "organization.application.swf.edit";
    private static final String SWF = "swf";
    private static final String AIR = "air";

    /* cannot for OSGi
    @Autowired
    public OrganizationApplicationSwfUploadFormController(OrganizationService organizationService,
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
    protected String setupForm(ModelMap model) throws Exception {

        model.addAttribute("organizationApplicationSwfUploadForm", new FileUploadForm());

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "id", required = true)String id,
                                   @RequestParam(value = "organizationId", required = true)String organizationId,
                                   @ModelAttribute(value = "organizationApplicationSwfUploadForm")FileUploadForm form,
                                   BindingResult result,
                                   SessionStatus status) throws Exception {
        String view = null;

        try {
            new FileUploadValidator(new String[]{SWF, AIR}).validate(form, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                applicationService.addApplicationSwf(id, form.getFile().getOriginalFilename(), form.getFile().getInputStream());
                status.setComplete();
                view = "redirect:/secure/organization/application/view.admin?view=organization.application.data&id=" + id + "&organizationId=" + organizationId;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    private OrganizationService organizationService = null;
    private ApplicationService applicationService = null;

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

}