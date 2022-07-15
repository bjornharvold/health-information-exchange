package com.hxcel.globalhealth.admin.controller.organization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.apache.commons.lang.StringUtils;

import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.platform.model.enums.IconSizeCd;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.admin.controller.form.ImageFileUploadForm;
import com.hxcel.globalhealth.admin.controller.form.validator.ImageFileUploadValidator;
import com.hxcel.globalhealth.admin.controller.application.ApplicationIconUploadFormController;

/**
 * <p>Title: OrganizationApplicationIconUploadFormController</p>
 * <p>Description: Uploads an icon to associated with an application's icons</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/application/icon/upload.admin")
@SessionAttributes(types = ImageFileUploadForm.class)
public class OrganizationApplicationIconUploadFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationApplicationIconUploadFormController.class);
    private final static String FORM = "organization.application.icon.edit";
    private final PlatformManager platformManager;

    @Autowired
    public OrganizationApplicationIconUploadFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @ModelAttribute(value = "application")
    protected Application populateApplication(@RequestParam(value = "id", required = true)String id) throws Exception {
        return platformManager.getApplication(id);
    }

    @ModelAttribute(value = "organization")
    protected Organization populateOrganization(@RequestParam(value = "organizationId", required = true)String id) throws Exception {
        return platformManager.getOrganization(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "size", required = true)IconSizeCd size, ModelMap model) throws Exception {

        model.addAttribute("upload", new ImageFileUploadForm(size));

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "id", required = true)String id,
                                   @RequestParam(value = "organizationId", required = true)String organizationId,
                                   @ModelAttribute(value = "upload")ImageFileUploadForm form,
                                   BindingResult result,
                                   SessionStatus status) throws Exception {
        String view = null;

        try {
            new ImageFileUploadValidator().validate(form, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                platformManager.addApplicationIcon(id, form.getFile().getOriginalFilename(), form.getFile().getInputStream(), form.getSize());
                status.setComplete();
                view = "redirect:/secure/organization/application/view.admin?view=organization.application.data&id=" + id + "&organizationId=" + organizationId;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }
}