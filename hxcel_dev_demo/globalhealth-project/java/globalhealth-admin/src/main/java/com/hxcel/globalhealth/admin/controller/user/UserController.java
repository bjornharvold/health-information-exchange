package com.hxcel.globalhealth.admin.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.apache.commons.lang.StringUtils;

import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.model.UserRole;
import com.hxcel.globalhealth.domain.common.UserManager;

import java.util.List;

/**
 * <p>Title: UserController</p>
 * <p>Description: Handles user functionality</p>
 *
 * @author Bjorn Harvold
 */
@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final static Integer DEFAULT_INDEX = 0;
    private final static Integer DEFAULT_MAX_RESULTS = 22;
    private final static Integer DEFAULT_CHILD_MAX_RESULTS = 20;

    @RequestMapping(value = "/secure/user/search.admin")
    protected String searchUsers(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "view", required = false) String view,
                                 @RequestParam(value = "index", required = false) Integer index,
                                 @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                 ModelMap map) throws Exception {

        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_MAX_RESULTS;
        }

        List<User> list = userManager.getUsers(name, index, maxResults);
        Integer count = userManager.getUserCount(name);
        map.addAttribute("users", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "users.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching user tiles view: '" + result + "'");
        }

        return result;
    }

    @RequestMapping(value = "/secure/user/view.admin")
    protected String showUser(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "view", required = false) String view,
            ModelMap map) throws Exception {

        User user = userManager.getUser(id);
        map.addAttribute("user", user);

        String result = StringUtils.isBlank(view) ? "user.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching user tiles view: '" + result + "'");
        }

        return result;
    }

    @RequestMapping(value = "/secure/user/role/search.admin")
    protected String searchUserRoles(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "view", required = false) String view,
            @RequestParam(value = "index", required = false) Integer index,
            @RequestParam(value = "maxResults", required = false) Integer maxResults,
            ModelMap map) throws Exception {

        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_CHILD_MAX_RESULTS;
        }

        List<UserRole> list = userManager.searchForUserRoles(id, name, index, maxResults);
        Integer count = userManager.getUserRolesCount(id, name);
        map.addAttribute("userroles", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "user.roles.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching user tiles view: '" + result + "'");
        }

        return showUser(id, result, map);
    }



    @RequestMapping(value = "/secure/user/role/delete.admin")
    protected String deleteUserRole(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "view", required = false) String view,
            ModelMap map) throws Exception {

        userManager.deleteUserRole(id);

        String result = StringUtils.isBlank(view) ? "user.roles.data" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching user tiles view: '" + result + "'");
        }

        return searchUserRoles(userId, null, result, null, null, map);
    }

    // Spring IoC
    @Autowired
    private UserManager userManager;
}