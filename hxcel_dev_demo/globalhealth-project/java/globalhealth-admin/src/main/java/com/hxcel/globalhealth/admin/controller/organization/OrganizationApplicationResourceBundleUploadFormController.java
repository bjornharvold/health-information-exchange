package com.hxcel.globalhealth.admin.controller.organization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.admin.controller.form.FileUploadForm;
import com.hxcel.globalhealth.admin.controller.form.validator.FileUploadValidator;
import com.hxcel.globalhealth.admin.controller.application.ApplicationResourceBundleUploadFormController;

/**
 * <p>Title: OrganizationApplicationResourceBundleUploadFormController</p>
 * <p>Description: Uploads a resource bundle property file e.g. global_en_US.properties
 * within an organization context</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/application/resourcebundle/upload.admin")
@SessionAttributes(types = FileUploadForm.class)
public class OrganizationApplicationResourceBundleUploadFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationApplicationResourceBundleUploadFormController.class);
    private final static String FORM = "organization.application.resourcebundle.upload";
    private final PlatformManager platformManager;
    private static final String PROPERTIES = "properties";

    @Autowired
    public OrganizationApplicationResourceBundleUploadFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @ModelAttribute(value = "application")
    protected Application populateApplication(@RequestParam(value = "applicationId", required = true)String id) throws Exception {
        return platformManager.getApplication(id);
    }

    @ModelAttribute(value = "organization")
    protected Organization populateOrganization(@RequestParam(value = "organizationId", required = true)String id) throws Exception {
        return platformManager.getOrganization(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(ModelMap model) throws Exception {

        model.addAttribute("upload", new FileUploadForm());

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "applicationId", required = true)String applicationId,
                                   @RequestParam(value = "organizationId", required = true)String organizationId,
                                   @ModelAttribute(value = "upload")FileUploadForm form,
                                   BindingResult result,
                                   SessionStatus status) throws Exception {
        String view = null;

        try {
            new FileUploadValidator(new String[]{PROPERTIES}).validate(form, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                platformManager.addApplicationResourceBundle(applicationId, form.getFile().getOriginalFilename(), form.getFile().getInputStream());
                status.setComplete();
                view = "redirect:/secure/organization/application/resourcebundle/search.admin?view=organization.application.resourcebundle.data&applicationId=" + applicationId + "&organizationId=" + organizationId;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }
}