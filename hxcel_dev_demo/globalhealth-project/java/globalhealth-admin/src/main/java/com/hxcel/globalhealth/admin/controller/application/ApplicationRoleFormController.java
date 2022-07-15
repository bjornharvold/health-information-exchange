package com.hxcel.globalhealth.admin.controller.application;

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
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.admin.controller.application.form.RoleForm;

import java.util.List;

/**
 * <p>Title: ApplicationRoleFormController</p>
 * <p>Description: Handles addinga new role to the application</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/application/role/edit.admin")
@SessionAttributes(types = RoleForm.class)
public class ApplicationRoleFormController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationRoleFormController.class);
    private final static String FORM = "application.role.edit";
    private final PlatformManager platformManager;

    @Autowired
    public ApplicationRoleFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @ModelAttribute(value = "types")
    protected List<Role> populateRoles() throws DomainException {
        return platformManager.searchForRoles(null, null, null);
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
    protected String processSubmit(@RequestParam(value = "applicationId", required = true)String applicationId, @ModelAttribute(value = "role")RoleForm roleForm,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            platformManager.addApplicationRoles(applicationId, roleForm.getRoles());

            status.setComplete();
            view = "redirect:/secure/application/role/search.admin?view=application.role.data&id=" + applicationId;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }
}