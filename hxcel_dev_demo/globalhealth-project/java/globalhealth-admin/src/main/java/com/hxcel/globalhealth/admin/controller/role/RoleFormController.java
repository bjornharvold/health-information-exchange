package com.hxcel.globalhealth.admin.controller.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.apache.commons.lang.StringUtils;

import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.admin.controller.role.validator.RoleValidator;

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
@SessionAttributes(types = Role.class)
public class RoleFormController {
    private static final Logger log = LoggerFactory.getLogger(RoleFormController.class);
    private final PlatformManager platformManager;
    private String FORM = "role.edit";


    @Autowired
    public RoleFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false) String id, ModelMap model) throws Exception {
        Role role = null;

        if (StringUtils.isBlank(id)) {
            role = new Role();
        } else {
            role = platformManager.getRole(id);
        }

        model.addAttribute("role", role);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute(value = "role") Role role,
                                   BindingResult result, SessionStatus status) throws Exception {

        new RoleValidator().validate(role, result);
        String view = null;

        if (result.hasErrors()) {
            view = FORM;
        } else {
            view = "redirect:/secure/role/search.admin?view=roles.data";
            role = platformManager.saveOrUpdateRole(role);
            status.setComplete();
        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}