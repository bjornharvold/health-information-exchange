package com.hxcel.globalhealth.admin.controller.organization;

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
import com.hxcel.globalhealth.domain.platform.model.*;
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.model.UserRole;
import com.hxcel.globalhealth.domain.common.UserManager;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.admin.controller.application.form.RoleForm;
import com.hxcel.globalhealth.admin.controller.application.ApplicationRoleFormController;
import com.hxcel.globalhealth.admin.controller.organization.OrganizationApplicationRoleFormController;
import com.hxcel.globalhealth.admin.controller.user.UserRoleFormController;

import java.util.List;

/**
 * <p>Title: OrganizationUserRoleFormController</p>
 * <p>Description: Handles adding a new role to the user</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/user/role/edit.admin")
@SessionAttributes(types = RoleForm.class)
public class OrganizationUserRoleFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationUserRoleFormController.class);
    private final static String FORM = "organization.user.role.edit";
    private final PlatformManager platformManager;

    @Autowired
    public OrganizationUserRoleFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
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

    @ModelAttribute(value = "organization")
    protected Organization populateOrganization(@RequestParam(value = "organizationId", required = true) String id) throws Exception {
        return platformManager.getOrganization(id);
    }

    @ModelAttribute(value = "ou")
    protected OrganizationUser populateUser(@RequestParam(value = "id", required = true) String id) throws Exception {
        return platformManager.getOrganizationUser(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = true) String id,
                               ModelMap map) throws Exception {

        List<OrganizationUserRole> list = platformManager.searchForOrganizationUserRoles(id, null, null, null);
        RoleForm rf = new RoleForm();

        if (list != null) {
            for (OrganizationUserRole role : list) {
                rf.addRole(role.getRole().getId());
            }
        }

        map.addAttribute("role", rf);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "id", required = true) String id,
                                   @RequestParam(value = "organizationId", required = true) String organizationId,
                                   @ModelAttribute(value = "role") RoleForm roleForm,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            platformManager.saveOrUpdateOrganizationUserRoles(id, roleForm.getRoles());

            status.setComplete();
            view = "redirect:/secure/organization/user/role/search.admin?view=organization.user.roles.data&id=" + id + "&organizationId=" + organizationId;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }
}