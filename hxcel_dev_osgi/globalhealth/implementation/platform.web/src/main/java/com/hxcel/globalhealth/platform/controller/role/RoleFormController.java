package com.hxcel.globalhealth.platform.controller.role;

import com.hxcel.globalhealth.platform.controller.role.validator.RoleValidator;
import com.hxcel.globalhealth.platform.spec.UserService;
import com.hxcel.globalhealth.platform.spec.dto.IRoleDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Title: RoleFormController</p>
 * <p>Description: Handles working with a single role</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/role/edit.admin")
@SessionAttributes("roleForm")
public class RoleFormController {
    private static final Logger log = LoggerFactory.getLogger(RoleFormController.class);
    private String FORM = "role.edit";

    /* cannot for OSGi
    @Autowired
    public RoleFormController(UserService userService) {
        this.userService = userService;
    }
    */

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false) String id, ModelMap model) throws Exception {
        IRoleDto role = null;

        if (StringUtils.isBlank(id)) {
            role = userService.createRole();
        } else {
            role = userService.getRole(id);
        }

        model.addAttribute("roleForm", role);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute(value = "roleForm") IRoleDto role,
                                   BindingResult result, SessionStatus status) throws Exception {

        new RoleValidator().validate(role, result);
        String view = null;

        if (result.hasErrors()) {
            view = FORM;
        } else {
            view = "redirect:/secure/role/search.admin?view=roles.data";
            role = userService.saveOrUpdateRole(role);
            status.setComplete();
        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    // Spring IoC
    private UserService userService = null;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}