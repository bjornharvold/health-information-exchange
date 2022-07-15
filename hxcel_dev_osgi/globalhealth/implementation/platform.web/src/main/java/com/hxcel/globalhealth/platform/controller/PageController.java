package com.hxcel.globalhealth.platform.controller;

import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.dto.ILaunchableApplicationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>Title: PageController</p>
 * <p>Description: Dispatches pages. This is used for all .html page extensions.</p>
 *
 * @author Bjorn Harvold
 */
@Controller
public class PageController {
    private static final Logger log = LoggerFactory.getLogger(PageController.class);

    /* cannot for OSGi
    @Autowired
    public PageController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    */

    /**
     * Same unsecure but we need the spring security filter to kick in once user is logged in
     * @param view
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/dispatcher.admin")
    protected String secureDispatch(@RequestParam(value = "view", required = true) String view) throws Exception {
        return dispatch(view);
    }

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

        ILaunchableApplicationDto dto = applicationService.getLaunchableApplication(organizationId, applicationId, true);

        map.addAttribute("dto", dto);
        map.addAttribute("organizationId", organizationId);
        map.addAttribute("applicationId", applicationId);

        if (log.isTraceEnabled()) {
            log.trace("Dispatching tiles content view: 'template.flash.master'");
        }

        return "template.flash.master";
    }

    // Spring IoC
    private ApplicationService applicationService = null;
}