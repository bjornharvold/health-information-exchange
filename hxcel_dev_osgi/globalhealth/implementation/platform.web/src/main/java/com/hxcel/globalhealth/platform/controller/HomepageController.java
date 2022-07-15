package com.hxcel.globalhealth.platform.controller;

import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.CountryService;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.UserService;
import com.hxcel.globalhealth.platform.utils.BundleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.osgi.context.BundleContextAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.osgi.framework.BundleContext;

/**
 * <p>Title: IndexController</p>
 * <p>Description: This controller will display the admin home page after successful login.</p>
 *
 * @author Bjorn Harvold
 */
@Controller
public class HomepageController implements BundleContextAware {
    private static final Logger log = LoggerFactory.getLogger(HomepageController.class);
    private final BundleUtils bundleUtils = new BundleUtils();

    @RequestMapping(value = "/secure/homepage.admin")
    protected String showHomepage(ModelMap map) throws Exception {
        map.addAttribute("services", bundleUtils.getLastModifiedBundles(bundleContext.getBundles(), 8));
        map.addAttribute("platforms", applicationService.getLastModifiedApplications(true, 8));
        map.addAttribute("applications", applicationService.getLastModifiedApplications(false, 8));
        map.addAttribute("regulations", countryService.getLastModifiedRegulations(8));
        map.addAttribute("countries", countryService.getLastModifiedCountries(8));
        map.addAttribute("organizations", organizationService.getLastModifiedOrganizations(8));
        map.addAttribute("users", userService.getLastModifiedUsers(8));
        map.addAttribute("roles", userService.getLastModifiedRoles(8));

        return "homepage.content";
    }

    // Spring IoC

    private OrganizationService organizationService = null;
    private ApplicationService applicationService = null;
    private CountryService countryService = null;
    private UserService userService = null;
    private BundleContext bundleContext;

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }
}