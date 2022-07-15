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
 * <p>Title: OrganizationApplicationResourceBundleUploadFormController</p>
 * <p>Description: Uploads a resource bundle property file e.g. global_en_US.properties
 * within an organization context</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/application/resourcebundle/upload.admin")
@SessionAttributes("organizationApplicationResourceBundleUploadForm")
public class OrganizationApplicationResourceBundleUploadFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationApplicationResourceBundleUploadFormController.class);
    private final static String FORM = "organization.application.resourcebundle.upload";
    private static final String PROPERTIES = "properties";

    /* cannot for OSGi
    @Autowired
    public OrganizationApplicationResourceBundleUploadFormController(OrganizationService organizationService,
                                                                  ApplicationService applicationService) {
        this.organizationService = organizationService;
        this.applicationService = applicationService;
    }
    */

    @ModelAttribute(value = "application")
    protected IApplicationDto populateApplication(@RequestParam(value = "applicationId", required = true)String id) throws Exception {
        return applicationService.getApplication(id);
    }

    @ModelAttribute(value = "organization")
    protected IOrganizationDto populateOrganization(@RequestParam(value = "organizationId", required = true)String id) throws Exception {
        return organizationService.getOrganization(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(ModelMap model) throws Exception {

        model.addAttribute("organizationApplicationResourceBundleUploadForm", new FileUploadForm());

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "applicationId", required = true)String applicationId,
                                   @RequestParam(value = "organizationId", required = true)String organizationId,
                                   @ModelAttribute(value = "organizationApplicationResourceBundleUploadForm")FileUploadForm form,
                                   BindingResult result,
                                   SessionStatus status) throws Exception {
        String view = null;

        try {
            new FileUploadValidator(new String[]{PROPERTIES}).validate(form, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                applicationService.addApplicationResourceBundle(applicationId, form.getFile().getOriginalFilename(), form.getFile().getInputStream());
                status.setComplete();
                view = "redirect:/secure/organization/application/resourcebundle/search.admin?view=organization.application.resourcebundle.data&applicationId=" + applicationId + "&organizationId=" + organizationId;
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