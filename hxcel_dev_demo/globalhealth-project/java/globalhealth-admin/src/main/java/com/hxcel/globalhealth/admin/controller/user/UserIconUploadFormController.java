package com.hxcel.globalhealth.admin.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.platform.model.enums.IconSizeCd;
import com.hxcel.globalhealth.domain.common.UserManager;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.admin.controller.form.ImageFileUploadForm;
import com.hxcel.globalhealth.admin.controller.form.validator.ImageFileUploadValidator;

/**
 * <p>Title: UserIconUploadFormController</p>
 * <p>Description: Uploads an icon to associated with a user's icons</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/user/icon/upload.admin")
@SessionAttributes(types = ImageFileUploadForm.class)
public class UserIconUploadFormController {
    private static final Logger log = LoggerFactory.getLogger(UserIconUploadFormController.class);
    private final static String FORM = "user.icon.edit";
    private final UserManager userManager;

    @Autowired
    public UserIconUploadFormController(UserManager userManager) {
        this.userManager = userManager;
    }

    @ModelAttribute(value = "user")
    protected User populateOrganization(@RequestParam(value = "id", required = true)String id) throws Exception {
        return userManager.getUser(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "size", required = true)IconSizeCd size,
                               ModelMap model) throws Exception {

        model.addAttribute("upload", new ImageFileUploadForm(size));

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "id", required = true)String id,
                                   @ModelAttribute(value = "upload")ImageFileUploadForm form,
                                   BindingResult result,
                                   SessionStatus status) throws Exception {
        String view = null;

        try {
            new ImageFileUploadValidator().validate(form, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                userManager.addUserIcon(id, form.getFile().getOriginalFilename(), form.getFile().getInputStream(), form.getSize());
                status.setComplete();
                view = "redirect:/secure/user/view.admin?view=user.data&id=" + id;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }
}