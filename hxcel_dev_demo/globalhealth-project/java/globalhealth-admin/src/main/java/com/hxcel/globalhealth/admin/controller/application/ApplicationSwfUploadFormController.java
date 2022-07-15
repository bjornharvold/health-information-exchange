package com.hxcel.globalhealth.admin.controller.application;

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
import com.hxcel.globalhealth.domain.platform.model.enums.IconSizeCd;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.admin.controller.form.ImageFileUploadForm;
import com.hxcel.globalhealth.admin.controller.form.FileUploadForm;
import com.hxcel.globalhealth.admin.controller.form.validator.ImageFileUploadValidator;
import com.hxcel.globalhealth.admin.controller.form.validator.FileUploadValidator;

/**
 * <p>Title: ApplicationIconUploadFormController</p>
 * <p>Description: Uploads an icon to associated with an application's icons</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/application/swf/upload.admin")
@SessionAttributes(types = FileUploadForm.class)
public class ApplicationSwfUploadFormController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationSwfUploadFormController.class);
    private final static String FORM = "application.swf.edit";
    private final PlatformManager platformManager;
    private static final String SWF = "swf";
    private static final String AIR = "air";

    @Autowired
    public ApplicationSwfUploadFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @ModelAttribute(value = "application")
    protected Application populateApplication(@RequestParam(value = "id", required = true)String id) throws Exception {
        return platformManager.getApplication(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(ModelMap model) throws Exception {

        model.addAttribute("upload", new FileUploadForm());

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "id", required = true)String id,
                                   @ModelAttribute(value = "upload")FileUploadForm form,
                                   BindingResult result,
                                   SessionStatus status) throws Exception {
        String view = null;

        try {
            new FileUploadValidator(new String[]{SWF, AIR}).validate(form, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                platformManager.addApplicationSwf(id, form.getFile().getOriginalFilename(), form.getFile().getInputStream());
                status.setComplete();
                view = "redirect:/secure/application/view.admin?view=application.data&id=" + id;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }
}