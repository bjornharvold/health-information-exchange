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
 * <p>Title: ApplicationIconUploadFormController</p>
 * <p>Description: Uploads an icon to associated with an application's icons</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/application/swf/upload.admin")
@SessionAttributes("applicationSwfUploadForm")
public class ApplicationSwfUploadFormController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationSwfUploadFormController.class);
    private final static String FORM = "application.swf.edit";
    private static final String SWF = "swf";
    private static final String AIR = "air";

    /* cannot for OSGi
    @Autowired
    public ApplicationSwfUploadFormController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    */

    @ModelAttribute(value = "application")
    protected IApplicationDto populateApplication(@RequestParam(value = "id", required = true)String id) throws Exception {
        return applicationService.getApplication(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(ModelMap model) throws Exception {

        model.addAttribute("applicationSwfUploadForm", new FileUploadForm());

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "id", required = true)String id,
                                   @ModelAttribute(value = "applicationSwfUploadForm")FileUploadForm form,
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
                view = "redirect:/secure/application/view.admin?view=application.data&id=" + id;
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