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
import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.ApplicationRole;
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.admin.controller.application.form.RoleForm;
import com.hxcel.globalhealth.admin.controller.application.ApplicationRoleFormController;

import java.util.List;

/**
 * <p>Title: OrganizationApplicationRoleFormController</p>
 * <p>Description: Handles adding a new role to the application within an organization context</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/application/role/edit.admin")
@SessionAttributes(types = RoleForm.class)
public class OrganizationApplicationRoleFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationApplicationRoleFormController.class);
    private final static String FORM = "organization.application.role.edit";
    private final PlatformManager platformManager;

    @Autowired
    public OrganizationApplicationRoleFormController(PlatformManager platformManager) {
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
    protected Organization populateOrganization(@RequestParam(value = "organizationId", required = true)String id) throws Exception {
        return platformManager.getOrganization(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "applicationId", required = true)String applicationId, ModelMap map) throws Exception {

        if (StringUtils.isNotBlank(applicationId)) {
            List<ApplicationRole> list = platformManager.searchForApplicationRoles(applicationId, null, null, null);
            RoleForm rf = new RoleForm();

            if (list != null) {
                for (ApplicationRole role : list) {
                    rf.addRole(role.getRole().getId());
                }
            }

            map.addAttribute("role", rf);
        }

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "applicationId", required = true)String applicationId,
                                   @RequestParam(value = "organizationId", required = true)String organizationId,
                                   @ModelAttribute(value = "role")RoleForm roleForm,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            platformManager.addApplicationRoles(applicationId, roleForm.getRoles());

            status.setComplete();
            view = "redirect:/secure/organization/application/role/search.admin?view=organization.application.role.data&id=" + applicationId + "&organizationId=" + organizationId;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }
}