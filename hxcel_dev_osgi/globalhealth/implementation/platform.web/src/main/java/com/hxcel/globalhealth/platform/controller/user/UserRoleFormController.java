package com.hxcel.globalhealth.platform.controller.user;

import com.hxcel.globalhealth.platform.controller.application.form.RoleForm;
import com.hxcel.globalhealth.platform.spec.UserService;
import com.hxcel.globalhealth.platform.spec.dto.IRoleDto;
import com.hxcel.globalhealth.platform.spec.dto.IUserDto;
import com.hxcel.globalhealth.platform.spec.dto.IUserRoleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

/**
 * <p>Title: UserRoleFormController</p>
 * <p>Description: Handles adding a new role to the user</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/user/role/edit.admin")
@SessionAttributes("userRoleForm")
public class UserRoleFormController {
    private static final Logger log = LoggerFactory.getLogger(UserRoleFormController.class);
    private final static String FORM = "user.role.edit";

    /* cannot for OSGi
    @Autowired
    public UserRoleFormController(UserService userService) {
        this.userService = userService;
    }
    */

    @ModelAttribute(value = "types")
    protected List<IRoleDto> populateRoles() throws Exception {
        List<IRoleDto> result = null;

        result = userService.searchForRoles(null, null, null);

        return result;
    }

    @ModelAttribute(value = "user")
    protected IUserDto populateUser(@RequestParam(value = "userId", required = true) String id) throws Exception {
        return userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "userId", required = true) String userId,
                               ModelMap map) throws Exception {

        List<IUserRoleDto> list = userService.searchForUserRoles(userId, null, null, null);
        RoleForm rf = new RoleForm();

        if (list != null) {
            for (IUserRoleDto role : list) {
                rf.addRole(role.getRole().getId());
            }
        }

        map.addAttribute("userRoleForm", rf);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "userId", required = true) String userId,
                                   @ModelAttribute(value = "userRoleForm") RoleForm roleForm,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            userService.addUserRoles(userId, roleForm.getRoles());

            status.setComplete();
            view = "redirect:/secure/user/role/search.admin?view=user.roles.data&id=" + userId;

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