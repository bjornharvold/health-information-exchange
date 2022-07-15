package com.hxcel.globalhealth.platform.controller.application;

import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.dto.*;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.LicenseStatusCd;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>Title: ApplicationController</p>
 * <p>Description: This controller handles alapplication pages</p>
 *
 * @author Bjorn Harvold
 */
@Controller
public class ApplicationController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);
    private final static Integer DEFAULT_INDEX = 0;
    private final static Integer DEFAULT_MAX_RESULTS = 22;
    private final static Integer DEFAULT_CHILD_DATA_MAX_RESULTS = 20;

    /* cannot for OSGi
    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    */

    @RequestMapping(value = "/secure/application/search.admin")
    protected String showApplications(@RequestParam(value = "name", required = false)String name,
                                      @RequestParam(value = "view", required = false)String view,
                                      @RequestParam(value = "platform", required = true)Boolean platform,
                                      @RequestParam(value = "index", required = false)Integer index,
                                      @RequestParam(value = "maxResults", required = false)Integer maxResults,
                                      ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_MAX_RESULTS;
        }

        List<IApplicationDto> list = applicationService.searchForApplications(name, platform, index, maxResults);
        Integer count = applicationService.searchForApplicationsCount(name, platform);
        map.addAttribute("applications", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);
        map.addAttribute("platform", platform);

        String result = StringUtils.isBlank(view) ? "applications.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching application tiles view: '" + result + "'");
        }

        return result;
    }

    @RequestMapping(value = "/secure/application/view.admin")
    protected String showApplication(@RequestParam(value = "id", required = true)String id,
                                     @RequestParam(value = "view", required = false)String view,
                                     ModelMap map) throws Exception {
        IApplicationDto app = applicationService.getApplication(id);
        map.addAttribute("application", app);

        // validate publish status
        map.addAttribute("publishable", applicationService.isApplicationPublishable(id));
        map.addAttribute("unpublishable", applicationService.isApplicationUnpublishable(id));

        String result = StringUtils.isBlank(view) ? "application.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching application tiles view: '" + result + "'");
        }

        return result;
    }

    @RequestMapping(value = "/secure/application/license/search.admin")
    protected String showApplicationLicenses(@RequestParam(value = "id", required = true)String id,
                                             @RequestParam(value = "name", required = false)String name,
                                             @RequestParam(value = "view", required = false)String view,
                                             @RequestParam(value = "status", required = false) LicenseStatusCd status,
                                             @RequestParam(value = "index", required = false)Integer index,
                                             @RequestParam(value = "maxResults", required = false)Integer maxResults,
                                             ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_CHILD_DATA_MAX_RESULTS;
        }
        if (status == null) {
            status = LicenseStatusCd.ACTIVE;
        }

        List<ILicenseDto> list = applicationService.searchForApplicationLicenses(id, name, status, index, maxResults);
        Integer count = applicationService.searchForApplicationLicensesCount(id, name, status);
        map.addAttribute("licenses", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "application.license.content" : view;

        return showApplication(id, result, map);
    }

    @RequestMapping(value = "/secure/application/role/search.admin")
    protected String showApplicationRoles(@RequestParam(value = "id", required = true)String id,
                                          @RequestParam(value = "name", required = false)String name,
                                          @RequestParam(value = "view", required = false)String view,
                                          @RequestParam(value = "index", required = false)Integer index,
                                          @RequestParam(value = "maxResults", required = false)Integer maxResults,
                                          ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_CHILD_DATA_MAX_RESULTS;
        }

        List<IApplicationRoleDto> list = applicationService.searchForApplicationRoles(id, name, index, maxResults);
        Integer count = applicationService.searchForApplicationRolesCount(id, name);
        map.addAttribute("roles", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "application.role.content" : view;

        return showApplication(id, result, map);
    }

    @RequestMapping(value = "/secure/application/configuration/search.admin")
    protected String showApplicationConfigurations(@RequestParam(value = "id", required = true)String id,
                                                   @RequestParam(value = "name", required = false)String name,
                                                   @RequestParam(value = "view", required = false)String view,
                                                   @RequestParam(value = "index", required = false)Integer index,
                                                   @RequestParam(value = "maxResults", required = false)Integer maxResults,
                                                   ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_CHILD_DATA_MAX_RESULTS;
        }

        List<IApplicationConfigurationDto> list = applicationService.searchForApplicationConfigurations(id, name, index, maxResults);
        Integer count = applicationService.searchForApplicationConfigurationsCount(id, name);
        map.addAttribute("configurations", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "application.configuration.content" : view;

        return showApplication(id, result, map);
    }

    @RequestMapping(value = "/secure/application/resourcebundle/search.admin")
    protected String showApplicationResourceBundleEntries(@RequestParam(value = "id", required = true)String id,
                                                   @RequestParam(value = "name", required = false)String name,
                                                   @RequestParam(value = "view", required = false)String view,
                                                   @RequestParam(value = "index", required = false)Integer index,
                                                   @RequestParam(value = "maxResults", required = false)Integer maxResults,
                                                   ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_CHILD_DATA_MAX_RESULTS;
        }

        List<IResourceBundleEntryDto> list = applicationService.searchForResourceBundleEntries(id, name, index, maxResults);
        Integer count = applicationService.searchForResourceBundleEntriesCount(id, name);
        map.addAttribute("entries", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "application.resourcebundle.content" : view;

        return showApplication(id, result, map);
    }

    @RequestMapping(value = "/secure/application/configuration/delete.admin")
    protected String deleteApplicationConfiguration(@RequestParam(value = "applicationId", required = true)String applicationId,
                                                    @RequestParam(value = "id", required = true)String id,
                                                    @RequestParam(value = "name", required = false)String name,
                                                    @RequestParam(value = "view", required = false)String view,
                                                    ModelMap map) throws Exception {
        IApplicationConfigurationDto ac = applicationService.deleteApplicationConfiguration(id);

        if (ac == null) {
            log.error("Unable to delete configuration with id: " + id);
        }

        return showApplicationConfigurations(applicationId, name, view, null, null, map);
    }

    @RequestMapping(value = "/secure/application/role/delete.admin")
    protected String deleteApplicationRole(@RequestParam(value = "applicationId", required = true)String applicationId,
                                           @RequestParam(value = "id", required = true)String id,
                                           @RequestParam(value = "name", required = false)String name,
                                           @RequestParam(value = "view", required = false)String view,
                                           ModelMap map) throws Exception {
        IApplicationRoleDto ar = applicationService.deleteApplicationRole(id);
        String result = null;

        if (ar == null) {
            log.error("Unable to delete role with id: " + id);
        }

        return showApplicationRoles(applicationId, name, view, null, null, map);
    }

    @RequestMapping(value = "/secure/application/resourcebundle/delete.admin")
    protected String deleteApplicationResourcebundleEntry(@RequestParam(value = "applicationId", required = true)String applicationId,
                                           @RequestParam(value = "id", required = true)String id,
                                           @RequestParam(value = "name", required = false)String name,
                                           @RequestParam(value = "view", required = false)String view,
                                           ModelMap map) throws Exception {
        IResourceBundleEntryDto rbe = applicationService.deleteResourceBundleEntry(id);
        String result = null;

        if (rbe == null) {
            log.error("Unable to delete resource bundle entry with id: " + id);
        }

        return showApplicationResourceBundleEntries(applicationId, name, view, null, null, map);
    }

    @RequestMapping(value = "/secure/application/license/delete.admin")
    protected String deleteApplicationLicense(@RequestParam(value = "applicationId", required = true)String applicationId,
                                              @RequestParam(value = "id", required = true)String id,
                                              @RequestParam(value = "name", required = false)String name,
                                              @RequestParam(value = "view", required = false)String view,
                                              @RequestParam(value = "status", required = false)LicenseStatusCd status,
                                              ModelMap map) throws Exception {
        ILicenseDto license = applicationService.deleteApplicationLicense(applicationId, id);

        if (license == null) {
            log.error("Unable to delete license with id: " + id);
        }

        return showApplicationLicenses(applicationId, name, view, status, null, null, map);
    }

    @RequestMapping(value = "/secure/application/publish.admin")
    protected String publishApplication(@RequestParam(value = "id", required = true)String id,
                                              @RequestParam(value = "status", required = true) ApplicationStatusCd status,
                                              @RequestParam(value = "view", required = false)String view,
                                              ModelMap map) throws Exception {


        applicationService.publishApplication(id, status);

        return showApplication(id, view, map);
    }

    // Spring IoC
    private ApplicationService applicationService = null;

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
}