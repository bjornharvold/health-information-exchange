package com.hxcel.globalhealth.platform.controller.organization;

import com.hxcel.globalhealth.platform.controller.form.ImageFileUploadForm;
import com.hxcel.globalhealth.platform.controller.form.validator.ImageFileUploadValidator;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
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
 * <p>Title: OrganizationIconUploadFormController</p>
 * <p>Description: Uploads an icon to associated with an organization's icons</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/icon/upload.admin")
@SessionAttributes("organizationIconUploadForm")
public class OrganizationIconUploadFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationIconUploadFormController.class);
    private final static String FORM = "organization.icon.edit";

    /* cannot for OSGi
    @Autowired
    public OrganizationIconUploadFormController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
    */

    @ModelAttribute(value = "organization")
    protected IOrganizationDto populateOrganization(@RequestParam(value = "id", required = true)String id) throws Exception {
        return organizationService.getOrganization(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "size", required = true) IconSizeCd size, ModelMap model) throws Exception {

        model.addAttribute("organizationIconUploadForm", new ImageFileUploadForm(size));

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "id", required = true)String id,
                                   @ModelAttribute(value = "organizationIconUploadForm")ImageFileUploadForm form,
                                   BindingResult result,
                                   SessionStatus status) throws Exception {
        String view = null;

        try {
            new ImageFileUploadValidator().validate(form, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                organizationService.addOrganizationIcon(id, form.getFile().getOriginalFilename(), form.getFile().getInputStream(), form.getSize());
                status.setComplete();
                view = "redirect:/secure/organization/view.admin?view=organization.data&id=" + id;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    // Spring IoC
    private OrganizationService organizationService = null;

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
}