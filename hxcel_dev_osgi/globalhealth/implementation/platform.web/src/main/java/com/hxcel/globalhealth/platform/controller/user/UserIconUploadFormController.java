package com.hxcel.globalhealth.platform.controller.user;

import com.hxcel.globalhealth.platform.controller.form.ImageFileUploadForm;
import com.hxcel.globalhealth.platform.controller.form.validator.ImageFileUploadValidator;
import com.hxcel.globalhealth.platform.spec.UserService;
import com.hxcel.globalhealth.platform.spec.dto.IUserDto;
import com.hxcel.globalhealth.platform.spec.model.enums.IconSizeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

/**
 * <p>Title: UserIconUploadFormController</p>
 * <p>Description: Uploads an icon to associated with a user's icons</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/user/icon/upload.admin")
@SessionAttributes("userIconUploadForm")
public class UserIconUploadFormController {
    private static final Logger log = LoggerFactory.getLogger(UserIconUploadFormController.class);
    private final static String FORM = "user.icon.edit";

    /* cannot for OSGi
    @Autowired
    public UserIconUploadFormController(UserService userService) {
        this.userService = userService;
    }
    */

    @ModelAttribute(value = "user")
    protected IUserDto populateOrganization(@RequestParam(value = "id", required = true)String id) throws Exception {
        return userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "size", required = true) IconSizeCd size,
                               ModelMap model) throws Exception {

        model.addAttribute("userIconUploadForm", new ImageFileUploadForm(size));

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "id", required = true)String id,
                                   @ModelAttribute(value = "userIconUploadForm")ImageFileUploadForm form,
                                   BindingResult result,
                                   SessionStatus status) throws Exception {
        String view = null;

        try {
            new ImageFileUploadValidator().validate(form, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                userService.addUserIcon(id, form.getFile().getOriginalFilename(), form.getFile().getInputStream(), form.getSize());
                status.setComplete();
                view = "redirect:/secure/user/view.admin?view=user.data&id=" + id;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    // Spring IoC
    private UserService userService = null;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}