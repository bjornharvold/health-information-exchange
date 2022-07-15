package com.hxcel.globalhealth.admin.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.apache.commons.lang.StringUtils;

import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.ApplicationRole;
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.model.UserRole;
import com.hxcel.globalhealth.domain.common.UserManager;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.admin.controller.application.form.RoleForm;
import com.hxcel.globalhealth.admin.controller.application.ApplicationRoleFormController;
import com.hxcel.globalhealth.admin.controller.organization.OrganizationApplicationRoleFormController;

import java.util.List;

/**
 * <p>Title: UserRoleFormController</p>
 * <p>Description: Handles adding a new role to the user</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/user/role/edit.admin")
@SessionAttributes(types = RoleForm.class)
public class UserRoleFormController {
    private static final Logger log = LoggerFactory.getLogger(UserRoleFormController.class);
    private final static String FORM = "user.role.edit";
    private final PlatformManager platformManager;
    private final UserManager userManager;

    @Autowired
    public UserRoleFormController(PlatformManager platformManager, UserManager userManager) {
        this.platformManager = platformManager;
        this.userManager = userManager;
    }

    @ModelAttribute(value = "types")
    protected List<Role> populateRoles() {
        List<Role> result = null;

        try {
            result = platformManager.searchForRoles(null, null, null);
        } catch (DomainException e) {
            log.error("There was an error loading the system roles");
        }

        return result;
    }

    @ModelAttribute(value = "user")
    protected User populateUser(@RequestParam(value = "userId", required = true) String id) throws Exception {
        return userManager.getUser(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "userId", required = true) String userId,
                               ModelMap map) throws Exception {

        List<UserRole> list = userManager.searchForUserRoles(userId, null, null, null);
        RoleForm rf = new RoleForm();

        if (list != null) {
            for (UserRole role : list) {
                rf.addRole(role.getRole().getId());
            }
        }

        map.addAttribute("role", rf);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "userId", required = true) String userId,
                                   @ModelAttribute(value = "role") RoleForm roleForm,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            userManager.addUserRoles(userId, roleForm.getRoles());

            status.setComplete();
            view = "redirect:/secure/user/role/search.admin?view=user.roles.data&id=" + userId;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }
}