package com.hxcel.globalhealth.platform.controller.service;

import com.hxcel.globalhealth.platform.controller.form.ImageFileUploadForm;
import com.hxcel.globalhealth.platform.controller.form.FileUploadForm;
import com.hxcel.globalhealth.platform.controller.form.validator.ImageFileUploadValidator;
import com.hxcel.globalhealth.platform.controller.form.validator.FileUploadValidator;
import com.hxcel.globalhealth.platform.controller.application.ApplicationIconUploadFormController;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationDto;
import com.hxcel.globalhealth.platform.spec.model.enums.IconSizeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.osgi.context.BundleContextAware;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Bundle;

/**
 * <p>Title: BundleUploadFormController</p>
 * <p>Description: Uploads a new bundle and adds it to the bundle context</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/service/upload.admin")
@SessionAttributes("bundleUploadForm")
public class BundleUploadFormController implements BundleContextAware {
    private static final Logger log = LoggerFactory.getLogger(BundleUploadFormController.class);
    private final static String FORM = "service.bundle.edit";

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false) Long id, ModelMap model) throws Exception {

        if (id != null) {
            model.addAttribute("id", id);
        }

        model.addAttribute("bundleUploadForm", new FileUploadForm());

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "id", required = true) Long id,
                                   @ModelAttribute(value = "bundleUploadForm")FileUploadForm form,
                                   BindingResult result,
                                   SessionStatus status) throws Exception {
        String view = null;
        String msg = null;

        try {
            new FileUploadValidator(new String[]{"jar", "par", "war"}).validate(form, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                if (id != null) {
                    // we are updating an existing bundle
                    Bundle bundleToBeUpdated = bundleContext.getBundle(id);

                    if (bundleToBeUpdated != null) {
                        msg = "Updating bundle: " + bundleToBeUpdated.getLocation() + " with new bundle: " + form.getFile().getOriginalFilename();
                        log.info(msg);

                        bundleToBeUpdated.update(form.getFile().getInputStream());
                    } else {
                        log.error("Cannot find bundle with id: " + id);
                    }
                } else {
                    msg = "Installing new bundle: " + form.getFile().getOriginalFilename();

                    log.info(msg);

                    // we are installing a new bundle
                    bundleContext.installBundle(form.getFile().getOriginalFilename(), form.getFile().getInputStream());
                }

                status.setComplete();
                view = "redirect:/secure/service/search.admin?view=services.data&bundleMessage=" + msg;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    // Spring IoC
    private BundleContext bundleContext = null;

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }
}