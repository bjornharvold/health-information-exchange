package com.hxcel.globalhealth.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import com.hxcel.globalhealth.domain.platform.PlatformManager;

/**
 * <p>Title: IndexController</p>
 * <p>Description: This controller will display the admin home page after successful login.</p>
 *
 * @author Bjorn Harvold
 */
@Controller
public class HomepageController {
    private static final Logger log = LoggerFactory.getLogger(HomepageController.class);
    private final PlatformManager platformManager;

    @Autowired
    public HomepageController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @RequestMapping(value = "/secure/homepage.admin")
    protected String showHomepage(ModelMap map) throws Exception {
        map.addAttribute("platforms", platformManager.getLastModifiedApplications(true, 8));
        map.addAttribute("applications", platformManager.getLastModifiedApplications(false, 8));
        map.addAttribute("regulations", platformManager.getLastModifiedRegulations(8));
        map.addAttribute("countries", platformManager.getLastModifiedCountries(8));
        map.addAttribute("organizations", platformManager.getLastModifiedOrganizations(8));
        map.addAttribute("users", platformManager.getLastModifiedUsers(8));
        map.addAttribute("roles", platformManager.getLastModifiedRoles(8));

        return "homepage.content";
    }

}