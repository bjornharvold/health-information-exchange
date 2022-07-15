package com.hxcel.globalhealth.platform.controller.organization;

import com.hxcel.globalhealth.platform.controller.application.form.RoleForm;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.UserService;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationRoleDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import com.hxcel.globalhealth.platform.spec.dto.IRoleDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

/**
 * <p>Title: OrganizationApplicationRoleFormController</p>
 * <p>Description: Handles adding a new role to the application within an organization context</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/application/role/edit.admin")
@SessionAttributes("organizationApplicationRoleForm")
public class OrganizationApplicationRoleFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationApplicationRoleFormController.class);
    private final static String FORM = "organization.application.role.edit";

    /* cannot for OSGi
    @Autowired
    public OrganizationApplicationRoleFormController(OrganizationService organizationService,
                                                     UserService userService,
                                                     ApplicationService applicationService) {
        this.organizationService = organizationService;
        this.userService = userService;
        this.applicationService = applicationService;
    }
    */

    @ModelAttribute(value = "types")
    protected List<IRoleDto> populateRoles() throws Exception {
        return userService.searchForRoles(null, null, null);
    }

    @ModelAttribute(value = "organization")
    protected IOrganizationDto populateOrganization(@RequestParam(value = "organizationId", required = true)String id) throws Exception {
        return organizationService.getOrganization(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "applicationId", required = true)String applicationId, ModelMap map) throws Exception {

        if (StringUtils.isNotBlank(applicationId)) {
            List<IApplicationRoleDto> list = applicationService.searchForApplicationRoles(applicationId, null, null, null);
            RoleForm rf = new RoleForm();

            if (list != null) {
                for (IApplicationRoleDto role : list) {
                    rf.addRole(role.getRole().getId());
                }
            }

            map.addAttribute("organizationApplicationRoleForm", rf);
        }

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "applicationId", required = true)String applicationId,
                                   @RequestParam(value = "organizationId", required = true)String organizationId,
                                   @ModelAttribute(value = "organizationApplicationRoleForm")RoleForm roleForm,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            applicationService.addApplicationRoles(applicationId, roleForm.getRoles());

            status.setComplete();
            view = "redirect:/secure/organization/application/role/search.admin?view=organization.application.role.data&id=" + applicationId + "&organizationId=" + organizationId;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    // Spring IoC
    private OrganizationService organizationService = null;
    private ApplicationService applicationService = null;
    private UserService userService = null;

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}