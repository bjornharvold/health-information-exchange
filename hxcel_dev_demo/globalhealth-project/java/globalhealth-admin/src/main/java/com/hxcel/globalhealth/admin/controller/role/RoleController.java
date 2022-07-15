package com.hxcel.globalhealth.admin.controller.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.apache.commons.lang.StringUtils;

import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.common.dto.StatusDto;
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.DomainException;

import java.util.List;

/**
 * <p>Title: RoleController</p>
 * <p>Description: This controller handles all role pages</p>
 *
 * @author Bjorn Harvold
 */
@Controller
public class RoleController {
    private static final Logger log = LoggerFactory.getLogger(RoleController.class);
    private final PlatformManager platformManager;
    private final static Integer DEFAULT_INDEX = 0;
    private final static Integer DEFAULT_MAX_RESULTS = 22;

    @Autowired
    public RoleController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @RequestMapping(value = "/secure/role/search.admin")
    protected String showRoles(@RequestParam(value = "name", required = false)String name,
                               @RequestParam(value = "view", required = false)String view,
                               @RequestParam(value = "index", required = false)Integer index,
                               @RequestParam(value = "maxResults", required = false)Integer maxResults,
                               ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_MAX_RESULTS;
        }

        List<Role> list = platformManager.searchForRoles(name, index, maxResults);
        Integer count = platformManager.searchForRolesCount(name);
        map.addAttribute("roles", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "roles.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching role tiles view: '" + result + "'");
        }

        return result;
    }

    @RequestMapping(value = "/secure/role/view.admin")
    protected String showRole(@RequestParam(value = "id", required = true)String id, ModelMap map) throws Exception {
        Role role = platformManager.getRole(id);

        map.addAttribute("role", role);

        return "role.content";
    }

    @RequestMapping(value = "/secure/role/delete.admin")
    protected String deleteRole(
            @RequestParam(value = "id", required = true)String id,
            @RequestParam(value = "view", required = false)String view,
                                ModelMap map) throws Exception {
        Role role = platformManager.deleteRole(id);
        
        if (role == null) {
            log.error("Unable to delete role with id: " + id);
        }

        return showRoles(null, view, null, null, map);
    }

}