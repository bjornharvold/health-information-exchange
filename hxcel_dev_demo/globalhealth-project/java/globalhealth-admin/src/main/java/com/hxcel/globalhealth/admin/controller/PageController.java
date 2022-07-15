package com.hxcel.globalhealth.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.platform.dto.LaunchableApplicationDto;
import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.common.dto.StatusDto;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.UserManager;

import java.util.List;

/**
 * <p>Title: PageController</p>
 * <p>Description: Dispatches pages. This is used for all .html page extensions.</p>
 *
 * @author Bjorn Harvold
 */
@Controller
public class PageController {
    private static final Logger log = LoggerFactory.getLogger(PageController.class);

    @RequestMapping(value = "/dispatcher.admin")
    protected String dispatch(@RequestParam(value = "view", required = true) String view) throws Exception {
        if (log.isTraceEnabled()) {
            log.trace("Dispatching tiles view: '" + view + "'");
        }

        return view;
    }

    /**
     * Displays the login box on the index page.
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login.admin")
    protected String showLoginBox() throws Exception {
        String result = "login.content";

        if (log.isTraceEnabled()) {
            log.trace("Dispatching tiles content view: '" + result + "'");
        }

        return result;
    }

    /**
     * Loads up a platform application for the user.
     * The platform application has to be active and published.
     * @param applicationId
     * @param map
     * @return
     */
    @RequestMapping(value = "/platform.admin")
    protected String loadPlatform(@RequestParam(value = "organizationId", required = true)String organizationId,
                                  @RequestParam(value = "applicationId", required = true)String applicationId,
                                  ModelMap map) throws Exception {

        LaunchableApplicationDto dto = platformManager.getLaunchableApplication(organizationId, applicationId, true);

        map.addAttribute("dto", dto);
        map.addAttribute("organizationId", organizationId);
        map.addAttribute("applicationId", applicationId);

        if (log.isTraceEnabled()) {
            log.trace("Dispatching tiles content view: 'template.flash.master'");
        }

        return "template.flash.master";
    }

    @Autowired
    private PlatformManager platformManager;
}