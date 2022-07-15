package com.hxcel.globalhealth.platform.controller.organization;

import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.UserService;
import com.hxcel.globalhealth.platform.spec.dto.*;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.LicenseStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationLicenseStatusCd;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>Title: OrganizationsController</p>
 * <p>Description: Displays a list of organizations registered with the system along with their status</p>
 *
 * @author Bjorn Harvold
 */
@Controller
public class OrganizationController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationController.class);
    private final static Integer DEFAULT_INDEX = 0;
    private final static Integer DEFAULT_MAX_RESULTS = 22;
    private final static Integer DEFAULT_CHILD_DATA_MAX_RESULTS = 20;
    private final static Integer DEFAULT_GRAND_CHILD_DATA_MAX_RESULTS = 18;

    /**
     * Display a list of organizations
     *
     * @param name
     * @param view
     * @param index
     * @param maxResults
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/organization/search.admin")
    protected String showOrganizations(@RequestParam(value = "name", required = false) String name,
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

        List<IOrganizationDto> list = organizationService.searchForOrganizations(name, index, maxResults);
        Integer count = organizationService.searchForOrganizationsCount(name);
        map.addAttribute("organizations", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "organizations.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching organization tiles view: '" + result + "'");
        }

        return result;
    }

    /**
     * Display a single organization
     *
     * @param organizationId
     * @param view
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/organization/view.admin")
    protected String showOrganization(@RequestParam(value = "id", required = true) String organizationId,
                                      @RequestParam(value = "view", required = false) String view,
                                      ModelMap map) throws Exception {
        IOrganizationDto org = organizationService.getOrganization(organizationId);

        map.addAttribute("organization", org);

        String result = StringUtils.isBlank(view) ? "organization.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching organization tiles view: '" + result + "'");
        }

        // return default view or specified view
        return result;
    }

    /**
     * Display a single application for the organization
     *
     * @param organizationId
     * @param view
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/organization/application/view.admin")
    protected String showApplication(@RequestParam(value = "organizationId", required = true) String organizationId,
                                     @RequestParam(value = "id", required = true) String applicationId,
                                     @RequestParam(value = "view", required = false) String view,
                                     ModelMap map) throws Exception {
        IApplicationDto application = applicationService.getApplication(applicationId);

        map.addAttribute("application", application);

        // validate publish status
        map.addAttribute("publishable", applicationService.isApplicationPublishable(applicationId));
        map.addAttribute("unpublishable", applicationService.isApplicationUnpublishable(applicationId));

        String result = StringUtils.isBlank(view) ? "organization.application.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching organization tiles view: '" + result + "'");
        }

        return showOrganization(organizationId, result, map);
    }

    /**
     * Display applications organization has created
     *
     * @param organizationId
     * @param name
     * @param platform
     * @param index
     * @param maxResults
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/organization/application/search.admin")
    protected String showApplications(@RequestParam(value = "id", required = true) String organizationId,
                                      @RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "view", required = false) String view,
                                      @RequestParam(value = "platform", required = true) Boolean platform,
                                      @RequestParam(value = "index", required = false) Integer index,
                                      @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                      ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_CHILD_DATA_MAX_RESULTS;
        }

        List<IApplicationDto> list = applicationService.searchApplicationsOwnedByOrganization(organizationId, name, platform, index, maxResults);
        Integer count = applicationService.searchApplicationsOwnedByOrganizationCount(organizationId, name, platform);
        map.addAttribute("applications", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);
        map.addAttribute("platform", platform);

        String result = StringUtils.isBlank(view) ? "organization.applications" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching organization tiles view: '" + result + "'");
        }

        return showOrganization(organizationId, result, map);
    }

    /**
     * Display 3rd party apps for organization
     *
     * @param organizationId
     * @param name
     * @param platform
     * @param index
     * @param maxResults
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/organization/licensed/application/search.admin")
    protected String showLicensedThirdPartyApplications(@RequestParam(value = "id", required = true) String organizationId,
                                                        @RequestParam(value = "name", required = false) String name,
                                                        @RequestParam(value = "view", required = false) String view,
                                                        @RequestParam(value = "platform", required = true) Boolean platform,
                                                        @RequestParam(value = "status", required = true) OrganizationLicenseStatusCd status,
                                                        @RequestParam(value = "index", required = false) Integer index,
                                                        @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                                        ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_CHILD_DATA_MAX_RESULTS;
        }

        List<IOrganizationLicenseDto> list = organizationService.searchForLicensedThirdPartyApplications(organizationId, name, platform, status, index, maxResults);
        Integer count = organizationService.searchLicensedThirdPartyApplicationsCount(organizationId, name, platform, status);
        map.addAttribute("applications", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);
        map.addAttribute("platform", platform);

        String result = StringUtils.isBlank(view) ? "organization.licensed.applications.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching organization tiles view: '" + result + "'");
        }

        return showOrganization(organizationId, result, map);
    }

    /**
     * Display 3rd party app overrides for organization
     *
     * @param organizationId
     * @param name
     * @param platform
     * @param index
     * @param maxResults
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/organization/licensed/application/overrides/search.admin")
    protected String showLicensedThirdPartyApplicationConfigurationOverrides(
            @RequestParam(value = "id", required = true) String organizationLicenseId,
            @RequestParam(value = "organizationId", required = true) String organizationId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "view", required = false) String view,
            @RequestParam(value = "platform", required = true) Boolean platform,
            @RequestParam(value = "index", required = false) Integer index,
            @RequestParam(value = "maxResults", required = false) Integer maxResults,
            ModelMap map) throws Exception {

        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_GRAND_CHILD_DATA_MAX_RESULTS;
        }

        List<IApplicationConfigurationOverrideDto> list = applicationService.searchForLicensedThirdPartyApplicationConfigurationOverrides(organizationLicenseId, name, index, maxResults);
        Integer count = applicationService.searchForLicensedThirdPartyApplicationConfigurationOverridesCount(organizationLicenseId, name);
        map.addAttribute("overrides", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "organization.licensed.application.overrides.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching organization 3rd party overrides tiles view: '" + result + "'");
        }

        return showLicensedApplication(organizationId, organizationLicenseId, result, platform, map);
    }

    /**
     * Display 3rd party app resource bundle entry overrides for organization
     *
     * @param organizationId
     * @param name
     * @param platform
     * @param index
     * @param maxResults
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/organization/licensed/application/resourcebundle/overrides/search.admin")
    protected String showLicensedThirdPartyApplicationResourceBundleEntryOverrides(
            @RequestParam(value = "id", required = true) String organizationLicenseId,
            @RequestParam(value = "organizationId", required = true) String organizationId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "view", required = false) String view,
            @RequestParam(value = "platform", required = true) Boolean platform,
            @RequestParam(value = "index", required = false) Integer index,
            @RequestParam(value = "maxResults", required = false) Integer maxResults,
            ModelMap map) throws Exception {

        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_GRAND_CHILD_DATA_MAX_RESULTS;
        }

        List<IResourceBundleEntryOverrideDto> list = applicationService.searchForLicensedThirdPartyApplicationResourceBundleEntryOverrides(organizationLicenseId, name, index, maxResults);
        Integer count = applicationService.searchForLicensedThirdPartyApplicationResourceBundleEntryOverridesCount(organizationLicenseId, name);
        map.addAttribute("overrides", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "organization.licensed.application.resourcebundle.overrides.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching organization 3rd party overrides tiles view: '" + result + "'");
        }

        return showLicensedApplication(organizationId, organizationLicenseId, result, platform, map);
    }

    @RequestMapping(value = "/secure/organization/licenses/application/resourcebundle/override/delete.admin")
    protected String deleteResourceBundleEntryOverride(@RequestParam(value = "organizationLicenseId", required = true) String organizationLicenseId,
                                                       @RequestParam(value = "organizationId", required = true) String organizationId,
                                                       @RequestParam(value = "id", required = true) String id,
                                                       @RequestParam(value = "platform", required = true) Boolean platform,
                                                       @RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "view", required = false) String view,
                                                       ModelMap map) throws Exception {
        IResourceBundleEntryOverrideDto ac = applicationService.deleteResourceBundleEntryOverride(id);

        String result = StringUtils.isBlank(view) ? "organization.licensed.application.resourcebundle.overrides.data" : view;

        if (ac == null) {
            log.error("Unable to delete configuration override with id: " + id);
        }

        return showLicensedThirdPartyApplicationResourceBundleEntryOverrides(organizationLicenseId, organizationId, name, result, platform, null, null, map);
    }

    @RequestMapping(value = "/secure/organization/licenses/application/override/delete.admin")
    protected String deleteApplicationConfigurationOverride(@RequestParam(value = "organizationLicenseId", required = true) String organizationLicenseId,
                                                       @RequestParam(value = "organizationId", required = true) String organizationId,
                                                       @RequestParam(value = "id", required = true) String id,
                                                       @RequestParam(value = "platform", required = true) Boolean platform,
                                                       @RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "view", required = false) String view,
                                                       ModelMap map) throws Exception {
        IApplicationConfigurationOverrideDto ac = applicationService.deleteApplicationConfigurationOverride(id);

        String result = StringUtils.isBlank(view) ? "organization.licensed.application.overrides.data" : view;

        if (ac == null) {
            log.error("Unable to delete configuration override with id: " + id);
        }

        return showLicensedThirdPartyApplicationConfigurationOverrides(organizationLicenseId, organizationId, name, result, platform, null, null, map);
    }

    @RequestMapping(value = "/secure/organization/licensed/application/view.admin")
    protected String showLicensedApplication(@RequestParam(value = "organizationId", required = true) String organizationId,
                                             @RequestParam(value = "id", required = true) String organizationLicenseId,
                                             @RequestParam(value = "view", required = false) String view,
                                             @RequestParam(value = "platform", required = true) Boolean platform,
                                             ModelMap map) throws Exception {
        IOrganizationLicenseDto ol = applicationService.getOrganizationLicense(organizationLicenseId);

        map.addAttribute("ol", ol);
        map.addAttribute("platform", platform);

        String result = StringUtils.isBlank(view) ? "organization.licensed.application.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching organization licensed application tiles view: '" + result + "'");
        }

        return showOrganization(organizationId, result, map);
    }

    @RequestMapping(value = "/secure/organization/licensed/application/delete.admin")
    protected String unlicenseApplication(@RequestParam(value = "organizationId", required = true) String organizationId,
                                          @RequestParam(value = "id", required = true) String organizationLicenseId,
                                          @RequestParam(value = "view", required = false) String view,
                                          @RequestParam(value = "platform", required = true) Boolean platform,
                                          ModelMap map) throws Exception {
        organizationService.unlicenseThirdPartyApplication(organizationLicenseId);

        return showLicensedThirdPartyApplications(organizationId, null, view, platform, OrganizationLicenseStatusCd.ACTIVE, null, null, map);
    }

    @RequestMapping(value = "/secure/organization/user/search.admin")
    protected String showMembers(@RequestParam(value = "id", required = true) String organizationId,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "view", required = false) String view,
                                 @RequestParam(value = "index", required = false) Integer index,
                                 @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                 ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_CHILD_DATA_MAX_RESULTS;
        }

        List<IOrganizationUserDto> list = organizationService.searchForOrganizationUsers(organizationId, name, index, maxResults);
        Integer count = organizationService.searchOrganizationUsersCount(organizationId, name);
        map.addAttribute("users", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "organization.users.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching organization tiles view: '" + result + "'");
        }

        return showOrganization(organizationId, result, map);
    }


    /**
     * Display a single member
     *
     * @param organizationId
     * @param view
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/organization/user/view.admin")
    protected String showMember(@RequestParam(value = "id", required = true) String organizationUserId,
                                @RequestParam(value = "organizationId", required = true) String organizationId,
                                @RequestParam(value = "view", required = false) String view,
                                ModelMap map) throws Exception {
        IOrganizationUserDto ou = organizationService.getOrganizationUser(organizationUserId);

        map.addAttribute("ou", ou);

        String result = StringUtils.isBlank(view) ? "organization.user.content" : view;

        // return default view or specified view
        return showOrganization(organizationId, result, map);
    }

    @RequestMapping(value = "/secure/organization/user/role/search.admin")
    protected String showMemberRoles(@RequestParam(value = "id", required = true) String organizationUserId,
                                     @RequestParam(value = "organizationId", required = true) String organizationId,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "view", required = false) String view,
                                     @RequestParam(value = "index", required = false) Integer index,
                                     @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                     ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_GRAND_CHILD_DATA_MAX_RESULTS;
        }

        List<IOrganizationUserRoleDto> list = organizationService.searchForOrganizationUserRoles(organizationUserId, name, index, maxResults);
        Integer count = organizationService.searchOrganizationUserRolesCount(organizationUserId, name);
        map.addAttribute("roles", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "organization.user.roles.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching organization user role tiles view: '" + result + "'");
        }

        return showMember(organizationUserId, organizationId, result, map);
    }

    @RequestMapping(value = "/secure/organization/user/role/delete.admin")
    protected String deleteMemberRole(@RequestParam(value = "organizationUserId", required = true) String organizationUserId,
                                      @RequestParam(value = "organizationId", required = true) String organizationId,
                                      @RequestParam(value = "id", required = true) String id,
                                      @RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "view", required = false) String view,
                                      ModelMap map) throws Exception {
        IOrganizationUserRoleDto ar = organizationService.deleteOrganizationUserRole(id);

        if (ar == null) {
            log.error("Unable to delete organization user role with id: " + id);
        }

        String result = StringUtils.isBlank(view) ? "organization.user.roles.data" : view;

        return showMemberRoles(organizationUserId, organizationId, name, result, null, null, map);
    }

    @RequestMapping(value = "/secure/organization/application/license/search.admin")
    protected String showApplicationLicenses(@RequestParam(value = "id", required = true) String id,
                                             @RequestParam(value = "organizationId", required = true) String organizationId,
                                             @RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "view", required = false) String view,
                                             @RequestParam(value = "status", required = false) LicenseStatusCd status,
                                             @RequestParam(value = "index", required = false) Integer index,
                                             @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                             ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_GRAND_CHILD_DATA_MAX_RESULTS;
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

        String result = StringUtils.isBlank(view) ? "organization.application.license.content" : view;

        return showApplication(organizationId, id, result, map);
    }

    @RequestMapping(value = "/secure/organization/application/role/search.admin")
    protected String showApplicationRoles(@RequestParam(value = "id", required = true) String id,
                                          @RequestParam(value = "organizationId", required = true) String organizationId,
                                          @RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "view", required = false) String view,
                                          @RequestParam(value = "index", required = false) Integer index,
                                          @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                          ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_GRAND_CHILD_DATA_MAX_RESULTS;
        }

        List<IApplicationRoleDto> list = applicationService.searchForApplicationRoles(id, name, index, maxResults);
        Integer count = applicationService.searchForApplicationRolesCount(id, name);
        map.addAttribute("roles", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "organization.application.role.content" : view;

        return showApplication(organizationId, id, result, map);
    }

    @RequestMapping(value = "/secure/organization/application/configuration/search.admin")
    protected String showApplicationConfigurations(@RequestParam(value = "id", required = true) String id,
                                                   @RequestParam(value = "organizationId", required = true) String organizationId,
                                                   @RequestParam(value = "name", required = false) String name,
                                                   @RequestParam(value = "view", required = false) String view,
                                                   @RequestParam(value = "index", required = false) Integer index,
                                                   @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                                   ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_GRAND_CHILD_DATA_MAX_RESULTS;
        }

        List<IApplicationConfigurationDto> list = applicationService.searchForApplicationConfigurations(id, name, index, maxResults);
        Integer count = applicationService.searchForApplicationConfigurationsCount(id, name);
        map.addAttribute("configurations", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "organization.application.configuration.content" : view;

        return showApplication(organizationId, id, result, map);
    }

    @RequestMapping(value = "/secure/organization/application/resourcebundle/search.admin")
    protected String showApplicationResourceBundleEntries(@RequestParam(value = "applicationId", required = true) String applicationId,
                                                    @RequestParam(value = "organizationId", required = true) String organizationId,
                                                    @RequestParam(value = "name", required = false) String name,
                                                    @RequestParam(value = "view", required = false) String view,
                                                    @RequestParam(value = "index", required = false) Integer index,
                                                    @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                                    ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_GRAND_CHILD_DATA_MAX_RESULTS;
        }

        List<IResourceBundleEntryDto> list = applicationService.searchForResourceBundleEntries(applicationId, name, index, maxResults);
        Integer count = applicationService.searchForResourceBundleEntriesCount(applicationId, name);
        map.addAttribute("entries", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "organization.application.resourcebundle.content" : view;

        return showApplication(organizationId, applicationId, result, map);
    }

    @RequestMapping(value = "/secure/organization/application/configuration/delete.admin")
    protected String deleteApplicationConfiguration(@RequestParam(value = "applicationId", required = true) String applicationId,
                                                    @RequestParam(value = "organizationId", required = true) String organizationId,
                                                    @RequestParam(value = "id", required = true) String id,
                                                    @RequestParam(value = "name", required = false) String name,
                                                    @RequestParam(value = "view", required = false) String view,
                                                    ModelMap map) throws Exception {
        IApplicationConfigurationDto ac = applicationService.deleteApplicationConfiguration(id);

        if (ac == null) {
            log.error("Unable to delete configuration with id: " + id);
        }

        return showApplicationConfigurations(applicationId, organizationId, name, view, null, null, map);
    }

    @RequestMapping(value = "/secure/organization/application/role/delete.admin")
    protected String deleteApplicationRole(@RequestParam(value = "applicationId", required = true) String applicationId,
                                           @RequestParam(value = "organizationId", required = true) String organizationId,
                                           @RequestParam(value = "id", required = true) String id,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "view", required = false) String view,
                                           ModelMap map) throws Exception {
        IApplicationRoleDto ar = applicationService.deleteApplicationRole(id);
        String result = null;

        if (ar == null) {
            log.error("Unable to delete role with id: " + id);
        }

        return showApplicationRoles(applicationId, organizationId, name, view, null, null, map);
    }

    @RequestMapping(value = "/secure/organization/application/license/delete.admin")
    protected String deleteApplicationLicense(@RequestParam(value = "applicationId", required = true) String applicationId,
                                              @RequestParam(value = "organizationId", required = true) String organizationId,
                                              @RequestParam(value = "id", required = true) String id,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "view", required = false) String view,
                                              @RequestParam(value = "status", required = false) LicenseStatusCd status,
                                              ModelMap map) throws Exception {
        ILicenseDto license = applicationService.deleteApplicationLicense(applicationId, id);

        if (license == null) {
            log.error("Unable to delete license with id: " + id);
        }

        return showApplicationLicenses(applicationId, organizationId, name, view, status, null, null, map);
    }

    @RequestMapping(value = "/secure/organization/application/resourcebundle/delete.admin")
    protected String deleteApplicationResourcebundleEntry(@RequestParam(value = "applicationId", required = true) String applicationId,
                                                          @RequestParam(value = "organizationId", required = true) String organizationId,
                                                          @RequestParam(value = "id", required = true) String id,
                                                          @RequestParam(value = "name", required = false) String name,
                                                          @RequestParam(value = "view", required = false) String view,
                                                          ModelMap map) throws Exception {
        IResourceBundleEntryDto rbe = applicationService.deleteResourceBundleEntry(id);

        if (rbe == null) {
            log.error("Unable to delete resource bundle entry with id: " + id);
        }

        String result = StringUtils.isBlank(view) ? "organization.application.resourcebundle.data" : view;

        return showApplicationResourceBundleEntries(applicationId, organizationId, name, view, null, null, map);
    }

    @RequestMapping(value = "/secure/organization/application/publish.admin")
    protected String publishApplication(@RequestParam(value = "id", required = true) String id,
                                        @RequestParam(value = "organizationId", required = true) String organizationId,
                                        @RequestParam(value = "status", required = true) ApplicationStatusCd status,
                                        @RequestParam(value = "view", required = false) String view,
                                        ModelMap map) throws Exception {

        applicationService.publishApplication(id, status);

        return showApplication(organizationId, id, view, map);
    }

    @RequestMapping(value = "/secure/organization/user/find.admin")
    protected String showExistingUsersExcludingMembers(@RequestParam(value = "organizationId", required = true) String organizationId,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "view", required = false) String view,
                                 @RequestParam(value = "index", required = false) Integer index,
                                 @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                 ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_CHILD_DATA_MAX_RESULTS;
        }

        List<IUserDto> list = organizationService.searchForNewOrganizationMembers(organizationId, name, index, maxResults);
        Integer count = organizationService.searchForNewOrganizationMembersCount(organizationId, name);
        map.addAttribute("users", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "organization.users.find.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching organization tiles view: '" + result + "'");
        }

        return showOrganization(organizationId, result, map);
    }

    @RequestMapping(value = "/secure/organization/user/find/add.admin")
    protected String registerNewMember(@RequestParam(value = "organizationId", required = true) String organizationId,
                                      @RequestParam(value = "id", required = true) String id,
                                      @RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "view", required = false) String view,
                                      ModelMap map) throws Exception {
        IOrganizationUserDto ou = organizationService.registerUserWithOrganization(organizationId, id);

        if (ou == null) {
            log.error("Unable to add organization user id: " + id);
        }

        String result = StringUtils.isBlank(view) ? "organization.users.find.data" : view;

        return showExistingUsersExcludingMembers(organizationId, name, result, null, null, map);
    }

    @RequestMapping(value = "/secure/organization/user/delete.admin")
    protected String deleteMember(@RequestParam(value = "organizationId", required = true) String organizationId,
                                      @RequestParam(value = "id", required = true) String id,
                                      @RequestParam(value = "view", required = false) String view,
                                      ModelMap map) throws Exception {
        IOrganizationUserDto ou = organizationService.deleteOrganizationUser(id);

        if (ou == null) {
            log.error("Unable to delete organization user id: " + id);
        }

        String result = StringUtils.isBlank(view) ? "organization.users.data" : view;

        return showMembers(organizationId, null, result, null, null, map);
    }

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