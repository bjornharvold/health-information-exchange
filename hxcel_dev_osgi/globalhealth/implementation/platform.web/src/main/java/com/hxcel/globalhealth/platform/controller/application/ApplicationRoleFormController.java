package com.hxcel.globalhealth.platform.controller.application;

import com.hxcel.globalhealth.platform.controller.application.form.RoleForm;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.UserService;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationRoleDto;
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
 * <p>Title: ApplicationRoleFormController</p>
 * <p>Description: Handles addinga new role to the application</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/application/role/edit.admin")
@SessionAttributes("applicationRoleForm")
public class ApplicationRoleFormController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationRoleFormController.class);
    private final static String FORM = "application.role.edit";

    /* cannot for OSGi
    @Autowired
    public ApplicationRoleFormController(ApplicationService applicationService,
                                         UserService userService) {
        this.applicationService = applicationService;
        this.userService = userService;
    }
    */

    @ModelAttribute(value = "types")
    protected List<IRoleDto> populateRoles() throws Exception {
        return userService.searchForRoles(null, null, null);
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

            map.addAttribute("applicationRoleForm", rf);
        }

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "applicationId", required = true)String applicationId,
                                   @ModelAttribute(value = "applicationRoleForm")RoleForm roleForm,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            applicationService.addApplicationRoles(applicationId, roleForm.getRoles());

            status.setComplete();
            view = "redirect:/secure/application/role/search.admin?view=application.role.data&id=" + applicationId;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    // Spring IoC
    private ApplicationService applicationService = null;
    private UserService userService = null;

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}