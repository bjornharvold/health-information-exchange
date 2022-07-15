package com.hxcel.globalhealth.platform.controller.organization;

import com.hxcel.globalhealth.platform.controller.application.form.RoleForm;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.UserService;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationUserDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationUserRoleDto;
import com.hxcel.globalhealth.platform.spec.dto.IRoleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

/**
 * <p>Title: OrganizationUserRoleFormController</p>
 * <p>Description: Handles adding a new role to the user</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/user/role/edit.admin")
@SessionAttributes("organizationUserRoleForm")
public class OrganizationUserRoleFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationUserRoleFormController.class);
    private final static String FORM = "organization.user.role.edit";

    /* cannot for OSGi
    @Autowired
    public OrganizationUserRoleFormController(OrganizationService organizationService,
                                              UserService userService) {
        this.organizationService = organizationService;
        this.userService = userService;
    }
    */

    @ModelAttribute(value = "types")
    protected List<IRoleDto> populateRoles() throws Exception {
        return userService.searchForRoles(null, null, null);
    }

    @ModelAttribute(value = "organization")
    protected IOrganizationDto populateOrganization(@RequestParam(value = "organizationId", required = true) String id) throws Exception {
        return organizationService.getOrganization(id);
    }

    @ModelAttribute(value = "ou")
    protected IOrganizationUserDto populateUser(@RequestParam(value = "id", required = true) String id) throws Exception {
        return organizationService.getOrganizationUser(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = true) String id,
                               ModelMap map) throws Exception {

        List<IOrganizationUserRoleDto> list = organizationService.searchForOrganizationUserRoles(id, null, null, null);
        RoleForm rf = new RoleForm();

        if (list != null) {
            for (IOrganizationUserRoleDto role : list) {
                rf.addRole(role.getRole().getId());
            }
        }

        map.addAttribute("organizationUserRoleForm", rf);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "id", required = true) String id,
                                   @RequestParam(value = "organizationId", required = true) String organizationId,
                                   @ModelAttribute(value = "organizationUserRoleForm") RoleForm roleForm,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            organizationService.saveOrUpdateOrganizationUserRoles(id, roleForm.getRoles());

            status.setComplete();
            view = "redirect:/secure/organization/user/role/search.admin?view=organization.user.roles.data&id=" + id + "&organizationId=" + organizationId;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    // Spring IoC
    private OrganizationService organizationService = null;
    private UserService userService = null;

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}