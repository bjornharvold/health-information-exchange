package com.hxcel.globalhealth.platform.controller.application;

import com.hxcel.globalhealth.platform.controller.form.FileUploadForm;
import com.hxcel.globalhealth.platform.controller.form.validator.FileUploadValidator;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

/**
 * <p>Title: ApplicationResourceBundleUploadFormController</p>
 * <p>Description: Uploads a resource bundle property file e.g. global_en_US.properties</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/application/resourcebundle/upload.admin")
@SessionAttributes("applicationResourceBundleUploadForm")
public class ApplicationResourceBundleUploadFormController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationResourceBundleUploadFormController.class);
    private final static String FORM = "application.resourcebundle.upload";
    private static final String PROPERTIES = "properties";

    /* cannot for OSGi
    @Autowired
    public ApplicationResourceBundleUploadFormController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    */

    @ModelAttribute(value = "application")
    protected IApplicationDto populateApplication(@RequestParam(value = "applicationId", required = true)String id) throws Exception {
        return applicationService.getApplication(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(ModelMap model) throws Exception {

        model.addAttribute("applicationResourceBundleUploadForm", new FileUploadForm());

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "applicationId", required = true)String id,
                                   @ModelAttribute(value = "applicationResourceBundleUploadForm")FileUploadForm form,
                                   BindingResult result,
                                   SessionStatus status) throws Exception {
        String view = null;

        try {
            new FileUploadValidator(new String[]{PROPERTIES}).validate(form, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                applicationService.addApplicationResourceBundle(id, form.getFile().getOriginalFilename(), form.getFile().getInputStream());
                status.setComplete();
                view = "redirect:/secure/application/resourcebundle/search.admin?view=application.resourcebundle.data&id=" + id;
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