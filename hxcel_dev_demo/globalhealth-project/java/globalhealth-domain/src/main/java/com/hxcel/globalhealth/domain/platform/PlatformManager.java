/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.platform;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Service;
import com.hxcel.globalhealth.domain.platform.model.*;
import com.hxcel.globalhealth.domain.platform.model.enums.IconSizeCd;
import com.hxcel.globalhealth.domain.platform.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.domain.platform.model.enums.OrganizationLicenseStatusCd;
import com.hxcel.globalhealth.domain.platform.model.enums.LicenseStatusCd;
import com.hxcel.globalhealth.domain.platform.dto.LaunchableApplicationDto;
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.utils.string.KeyValuePair;

import java.util.List;
import java.io.InputStream;

/**
 * User: bjorn
 * Date: Jun 5, 2008
 * Time: 3:43:19 PM
 * Manager for everything that has to do with platform functionality
 */
@Service
@Secured(value = "ROLE_ADMINISTRATOR")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface PlatformManager {

    /**
     * Creates or updates an organization in the system
     * @param org
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Organization saveOrUpdateOrganization(Organization org) throws DomainException;

    /**
     * Add an icon to organization
     * @param organizationId
     * @param iconName
     * @param icon
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Organization addOrganizationIcon(String organizationId, String iconName, InputStream icon, IconSizeCd size) throws DomainException;

    /**
     * Inactivates an organization in the system
     * @param organizationId
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Organization deactivateOrganization(String organizationId) throws DomainException;

    /**
     * Creates or updates an application in the system
     * @param app
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Application saveOrUpdateApplication(Application app) throws DomainException;

    /**
     * Inactivates an application in the system
     * @param applicationId
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Application deactivateApplication(String applicationId) throws DomainException;

    /**
     * Create or modify an application license 
     * @param applicationId
     * @param license
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    License addApplicationLicense(String applicationId, License license) throws DomainException;

    /**
     * Registers a user with an organization
     * @param orgId
     * @param userId
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    OrganizationUser registerUserWithOrganization(String orgId, String userId) throws DomainException;

    /**
     * Registers an application with an organization
     * @param orgId
     * @param licenseId
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    OrganizationLicense saveOrUpdateOrganizationLicense(String orgId, String licenseId) throws DomainException;

    /**
     * Retrieve license based on id
     * @param licenseId
     * @return
     * @throws DomainException
     * @throws PersistenceException
     */
    License getLicense(String licenseId) throws DomainException, PersistenceException;

    /**
     * Update existing license
     * @param license
     * @return
     * @throws DomainException
     * @throws PersistenceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    License updateLicense(License license) throws DomainException, PersistenceException;
    
    /**
     * Retrieves organization by id
     * @param id
     * @return
     * @throws DomainException
     */
    Organization getOrganization(String id) throws DomainException;

    /**
     * Search for organization by name
     * @param name
     * @param index
     * @param maxResult
     * @return
     * @throws DomainException
     */
    List<Organization> searchForOrganizations(String name, Integer index, Integer maxResult) throws DomainException;

    /**
     * Search for applications by name
     * @param name
     * @param index
     * @param maxResult
     * @return
     * @throws DomainException
     */
    List<Application> searchForApplications(String name, Boolean isPlatform, Integer index, Integer maxResult) throws DomainException;

    /**
     * Does a count of search results
     * @param name
     * @return
     * @throws DomainException
     */
    Integer searchForApplicationsCount(String name, Boolean isPlatform) throws DomainException;

    /**
     * Retrieve application by id
     * @param id
     * @return
     * @throws DomainException
     */
    Application getApplication(String id) throws DomainException;

    /**
     * Retrieves list of applications that organization owns
     * @param organizationId
     * @return
     * @throws DomainException
     */
    List<Application> searchApplicationsOwnedByOrganization(String organizationId, String name, Boolean platform, Integer index, Integer maxResults) throws DomainException;

    /**
     * Inactivate a user from organization
     * @param organizationUserId
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    OrganizationUser deleteOrganizationUser(String organizationUserId) throws DomainException;

    /**
     * Inactivate an application from organization
     * @param organizationLicenseId
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    OrganizationLicense unlicenseThirdPartyApplication(String organizationLicenseId) throws DomainException;

    /**
     * Returns a list of NameValuePair objects that contain the organization id and the organization name
     * @return
     * @throws DomainException
     */
    List<KeyValuePair> getOrganizationThinList() throws DomainException;

    /**
     * Return the ApplicationConfiguration entity by that id
     * @param id
     * @return
     */
    ApplicationConfiguration getApplicationConfiguration(String id) throws DomainException;

    /**
     * Adds a new application configuration entity to the specified application
     * @param applicationId
     * @param ac
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ApplicationConfiguration addApplicationConfiguration(String applicationId, ApplicationConfiguration ac) throws DomainException;

    /**
     * Updates an existing application configuration
     * @param ac
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ApplicationConfiguration updateApplicationConfiguration(ApplicationConfiguration ac) throws DomainException;

    /**
     * Deletes an existing application configuration
     * @param applicationConfigurationId
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ApplicationConfiguration deleteApplicationConfiguration(String applicationConfigurationId) throws DomainException;

    /**
     * Deletes an existing application configuration override
     * @param applicationConfigurationOverrideId
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ApplicationConfigurationOverride deleteApplicationConfigurationOverride(String applicationConfigurationOverrideId) throws DomainException;

    /**
     * Deletes an application license
     * @param applicationId
     * @param applicationLicenseId
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    License deleteApplicationLicense(String applicationId, String applicationLicenseId) throws DomainException;

    /**
     * Retrieves an organization configuration entity
     * @param id
     * @return
     * @throws DomainException
     */
    ApplicationConfigurationOverride getApplicationConfigurationOverride(String id) throws DomainException;

    /**
     * Adds an organization configuration to the organization
     * @param organizationLicenseId
     * @param oc
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ApplicationConfigurationOverride addApplicationConfigurationOverride(String organizationLicenseId, ApplicationConfigurationOverride oc) throws DomainException;

    /**
     * Updates an existing organization configuration
     * @param oc
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ApplicationConfigurationOverride updateApplicationConfigurationOverride(ApplicationConfigurationOverride oc) throws DomainException;

    /**
     * Associated an icon with an application
     * @param applicationId
     * @param iconName
     * @param icon
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Application addApplicationIcon(String applicationId, String iconName, InputStream icon, IconSizeCd size) throws DomainException;

    /**
     * Add a swf to an application
     * @param applicationId
     * @param swfName
     * @param swf
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Application addApplicationSwf(String applicationId, String swfName, InputStream swf) throws DomainException;

    /**
     * Returns a total organization count
     * @param name
     * @return
     */
    Integer searchForOrganizationsCount(String name) throws DomainException;

    /**
     * Returns a role based on status code
     * @param statusCode
     * @return
     * @throws DomainException
     */
    Role getRoleByStatusCode(String statusCode) throws DomainException;

    /**
     * save/updates a role
     * @param role
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Role saveOrUpdateRole(Role role) throws DomainException;

    /**
     * Search for role by name
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<Role> searchForRoles(String name, Integer index, Integer maxResults) throws DomainException;

    /**
     * Get a role count based on name search
     * @param name
     * @return
     * @throws DomainException
     */
    Integer searchForRolesCount(String name) throws DomainException;

    /**
     * Retrieve role by id
     * @param id
     * @return
     * @throws DomainException
     */
    Role getRole(String id) throws DomainException;

    /**
     * Deletes role by id
     * @param id
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Role deleteRole(String id) throws DomainException;

    /**
     * Add system role(s) to the application
     * @param applicationId
     * @param roles
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    List<ApplicationRole> addApplicationRoles(String applicationId, List<String> roles) throws DomainException;

    /**
     * Add a system role to the application
     * @param applicationId
     * @param roleId
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ApplicationRole addApplicationRole(String applicationId, String roleId) throws DomainException;

    /**
     * Remove a role from the application
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ApplicationRole deleteApplicationRole(String applicationRoleId) throws DomainException;

    /**
     * Return a list of applications that are licensable and exclude applications for the organization
     * wanting to license
     * @param organizationId
     * @param platform
     * @return
     */
    List<License> getLicensedApplicationsExcludeOwner(String organizationId, Boolean platform) throws DomainException;

    /**
     * Associates a 3rd party application with an organization via a license
     * @param organizationId
     * @param licenseIds
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    List<OrganizationLicense> licenseApplicationsForOrganization(String organizationId, List<String> licenseIds) throws DomainException;

    /**
     * Associates a list of users with an organization
     * @param organizationId
     * @param userIds
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    List<OrganizationUser> registerUsersWithOrganization(String organizationId, List<String> userIds) throws DomainException;

    /**
     * Retrieves a list of users for an organization
     * @param organizationId
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<OrganizationUser> searchForOrganizationUsers(String organizationId, String name, Integer index, Integer maxResults) throws DomainException;

    /**
     * Retrieves a user count for a list of organizations
     * @param organizationId
     * @param name
     * @return
     * @throws DomainException
     */
    Integer searchOrganizationUsersCount(String organizationId, String name) throws DomainException;

    /**
     * Retrieves a list of applications that were created by organization specified by id
     * @param organizationId
     * @param name (application name filter)
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<OrganizationLicense> searchForLicensedThirdPartyApplications(String organizationId, String name, Boolean isPlatform, OrganizationLicenseStatusCd status, Integer index, Integer maxResults) throws DomainException;

    /**
     * Retrieves a application count for a list of organizations
     * @param organizationId
     * @param name
     * @return
     */
    Integer searchLicensedThirdPartyApplicationsCount(String organizationId, String name, Boolean isPlatform, OrganizationLicenseStatusCd status) throws DomainException;

    /**
     * Retrieves a list of configuration overrides for a licensed application
     * @param organizationLicenseId
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<ApplicationConfigurationOverride> searchForLicensedThirdPartyApplicationConfigurationOverrides(String organizationLicenseId, String name, Integer index, Integer maxResults) throws DomainException;

    /**
     * Returns a count of applicationconfigurationoverride entities
     * @param organizationLicenseId
     * @param name
     * @return
     * @throws DomainException
     */
    Integer searchForLicensedThirdPartyApplicationConfigurationOverridesCount(String organizationLicenseId, String name) throws DomainException;

    /**
     * Saves a new override for the specified organizationlicense
     * @param override
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ApplicationConfigurationOverride saveOrUpdateApplicationConfigurationOverride(ApplicationConfigurationOverride override) throws DomainException;

    /**
     * Returns a count of the number of applications a organization has created
     * @param organizationId
     * @param name
     * @param platform
     * @return
     */
    Integer searchApplicationsOwnedByOrganizationCount(String organizationId, String name, Boolean platform) throws DomainException;

    /**
     * Returns a list of licenses for the application
     * @param applicationId
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<License> searchForApplicationLicenses(String applicationId, String name, LicenseStatusCd status, Integer index, Integer maxResults) throws DomainException;

    /**
     *
     * @param applicationId
     * @param name
     * @return
     * @throws DomainException
     */
    Integer searchForApplicationLicensesCount(String applicationId, String name, LicenseStatusCd status) throws DomainException;

    /**
     * Returns a list of application roles for the specified application
     * @param applicationId
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<ApplicationRole> searchForApplicationRoles(String applicationId, String name, Integer index, Integer maxResults) throws DomainException;

    /**
     * Returns a count for the total number of application roles
     * @param applicationId
     * @param name
     * @return
     * @throws DomainException
     */
    Integer searchForApplicationRolesCount(String applicationId, String name) throws DomainException;

    /**
     * Returns a list of application configurations
     * @param applicationId
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<ApplicationConfiguration> searchForApplicationConfigurations(String applicationId, String name, Integer index, Integer maxResults) throws DomainException;

    /**
     * Returns a total count of application configurations for an application
     * @param applicationId
     * @param name
     * @return
     * @throws DomainException
     */
    Integer searchForApplicationConfigurationsCount(String applicationId, String name) throws DomainException;

    /**
     * Checks to see if this application is fit for consumption.
     * All the assets need to have been uploaded for an application to be ready
     * @param application
     * @return
     */
    Boolean isApplicationPublishable(Application application) throws DomainException;

    /**
     * Checks to see if this application is fit for unconsumption.
     * In order to successfully unpublish an application, no one can be using the application.
     * So if theer are no outstanding licenses, the application can be unpublished
     * @param application
     * @return
     */
    Boolean isApplicationUnpublishable(Application application) throws DomainException;

    /**
     * Convenience method for updating an appication's status
     * @param applicationId
     * @param status
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void publishApplication(String applicationId, ApplicationStatusCd status) throws DomainException;

    /**
     * Returns an application by id if it is published and ready for consumption
     * @param organizationId
     * @param applicationId
     * @param platform
     * @return
     */
    LaunchableApplicationDto getLaunchableApplication(String organizationId, String applicationId, boolean platform) throws DomainException;

    /**
     * Retrieves the OrganizationLicense entity based on its entity id
     * @param organizationLicenseId
     * @return
     * @throws DomainException
     */
    OrganizationLicense getOrganizationLicense(String organizationLicenseId) throws DomainException;

    /**
     * Retrieves a list of countries
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<Country> searchForCountries(String name, Integer index, Integer maxResults) throws DomainException;

    /**
     * Retrieves a total country count
     * @param name
     * @return
     * @throws DomainException
     */
    Integer searchForCountriesCount(String name) throws DomainException;

    /**
     * Retrieves a country entity based on entity id
     * @param countryId
     * @return
     * @throws DomainException
     */
    Country getCountry(String countryId) throws DomainException;

    /**
     * Saves or updates a country
     * @param country
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Country saveOrUpdateCountry(Country country) throws DomainException;

    /**
     * Retrieves a list of regulations
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<Regulation> searchForRegulations(String name, Integer index, Integer maxResults) throws DomainException;

    /**
     * Retrieves a total count of regulations
     * @param name
     * @return
     * @throws DomainException
     */
    Integer searchForRegulationsCount(String name) throws DomainException;

    /**
     * Saves or updates a regulation entity
     * @param regulation
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Regulation saveOrUpdateRegulation(Regulation regulation) throws DomainException;

    /**
     * Returns a regulations based on entity id
     * @param regulationId
     * @return
     * @throws DomainException
     */
    Regulation getRegulation(String regulationId) throws DomainException;

    /**
     * Retrieves a list of Internationalization
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<ResourceBundleEntry> searchForResourceBundleEntries(String applicationId, String name, Integer index, Integer maxResults) throws DomainException;

    /**
     * Retrieves a total count of resource bundles entries
     * @param name
     * @return
     * @throws DomainException
     */
    Integer searchForResourceBundleEntriesCount(String applicationId, String name) throws DomainException;

    /**
     * Saves or updates a resource bundle entry
     * @param resourceBundleEntry
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ResourceBundleEntry saveOrUpdateResourceBundle(ResourceBundleEntry resourceBundleEntry) throws DomainException;

    /**
     * Retrieves a resource bundle entry by entity id
     * @param resourceBundleEntryId
     * @return
     * @throws DomainException
     */
    ResourceBundleEntry getResourceBundleEntry(String resourceBundleEntryId) throws DomainException;

    /**
     * Deletes a regulation entry
     * @param regulationId
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Regulation deleteRegulation(String regulationId) throws DomainException;

    /**
     * Retrieves a list of regulation overrides by country
     * @param countryId
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<RegulationOverride> searchForRegulationOverridesByCountry(String countryId, String name, Integer index, Integer maxResults) throws DomainException;

    /**
     * Retrieves a total count of regulation overrides by country
     * @param countryId
     * @param name
     * @return
     * @throws DomainException
     */
    Integer searchForRegulationOverridesByCountryCount(String countryId, String name) throws DomainException;

    /**
     * Returns a regulation override entity by entity id
     * @param regulationOverrideId
     * @return
     * @throws DomainException
     */
    RegulationOverride getRegulationOverride(String regulationOverrideId) throws DomainException;

    /**
     * Saves or updates a regulation override entity
     * @param regulationOverride
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    RegulationOverride saveOrUpdateRegulationOverride(RegulationOverride regulationOverride) throws DomainException;

    /**
     * Deletes a regulation override by entity id
     * @param regulationOverrideId
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    RegulationOverride deleteRegulationOverride(String regulationOverrideId) throws DomainException;

    /**
     * Returns a list of the last modified application entries
     * @param platform
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<Application> getLastModifiedApplications(Boolean platform, Integer maxResults) throws DomainException;

    /**
     * Returns a list of the last modified regulation entries
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<Regulation> getLastModifiedRegulations(Integer maxResults) throws DomainException;

    /**
     * Returns a list of the last modified country entries
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<Country> getLastModifiedCountries(Integer maxResults) throws DomainException;

    /**
     * Returns a list of the last modified organization entries
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<Organization> getLastModifiedOrganizations(Integer maxResults) throws DomainException;

    /**
     * Returns a list of the last modified user entries
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<User> getLastModifiedUsers(Integer maxResults) throws DomainException;

    /**
     * Returns a list of the last modified role entries
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<Role> getLastModifiedRoles(Integer maxResults) throws DomainException;

    /**
     * Returns a country based on country enum
     * @param countryCd
     * @return
     * @throws DomainException
     */
    Country getCountry(CountryCd countryCd) throws DomainException;

    /**
     * Returns a list of organization user roles
     * @param organizationUserId
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<OrganizationUserRole> searchForOrganizationUserRoles(String organizationUserId, String name, Integer index, Integer maxResults) throws DomainException;

    /**
     * Returns a total count of org user roles
     * @param organizationUserId
     * @param name
     * @return
     * @throws DomainException
     */
    Integer searchOrganizationUserRolesCount(String organizationUserId, String name) throws DomainException;

    /**
     * Returns an organization user entity
     * @param organizationUserId
     * @return
     * @throws DomainException
     */
    OrganizationUser getOrganizationUser(String organizationUserId) throws DomainException;

    /**
     * Modifies an org user role
     * @param id
     * @param roleIds
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    List<OrganizationUserRole> saveOrUpdateOrganizationUserRoles(String id, List<String> roleIds) throws DomainException;

    /**
     * Deletes an org user role
     * @param organizationUserRoleId
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    OrganizationUserRole deleteOrganizationUserRole(String organizationUserRoleId) throws DomainException;

    /**
     * Modifies an org user
     * @param organizationUser
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    OrganizationUser saveOrUpdateOrganizationUser(OrganizationUser organizationUser) throws DomainException;

    /**
     * Deletes an application resource bundle entry
     * @param resourceBundleEntryId
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ResourceBundleEntry deleteResourceBundleEntry(String resourceBundleEntryId) throws DomainException;

    /**
     * Returns a paginated list of users that are not yet members of the specified organization
     * @param organizationId
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    List<User> searchForUsersWithExcludes(String organizationId, String name, Integer index, Integer maxResults) throws DomainException;

    /**
     * Retrieves a list of users but excludes the specified user ids
     * @param organizationId
     * @param name
     * @return
     * @throws DomainException
     */
    Integer searchForUsersWithExcludesCount(String organizationId, String name) throws DomainException;

    /**
     * parses a resource bundle text file and adds the key value pairs to the application's
     * resource bundle entries
     * @param applicationId
     * @param filename
     * @param resourceBundle
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void addApplicationResourceBundle(String applicationId, String filename, InputStream resourceBundle) throws DomainException;

    /**
     * Returns a list of resource bundle entry overrides for the licened application
     * @param organizationLicenseId
     * @param name
     * @param index
     * @param maxResults
     * @return
     */
    List<ResourceBundleEntryOverride> searchForLicensedThirdPartyApplicationResourceBundleEntryOverrides(String organizationLicenseId, String name, Integer index, Integer maxResults) throws DomainException;

    /**
     * Returns a total count of resource bundle entry overrides for the licened application
     * @param organizationLicenseId
     * @param name
     * @return
     */
    Integer searchForLicensedThirdPartyApplicationResourceBundleEntryOverridesCount(String organizationLicenseId, String name) throws DomainException;

    /**
     * Deletes a resource bundle override entry
     * @param resourceBundleOverrideId
     * @return
     * @throws PersistenceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ResourceBundleEntryOverride deleteResourceBundleEntryOverride(String resourceBundleOverrideId) throws PersistenceException, DomainException;

    /**
     * Adds/updates resourcebundleentry override
     * @param organizationId
     * @param rbeo
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ResourceBundleEntryOverride saveOrUpdateResourceBundleEntryOverride(String organizationId, ResourceBundleEntryOverride rbeo) throws DomainException;

    /**
     * Returns a single resource bundle override entity
     * @param resourceBundleEntryId
     * @return
     * @throws DomainException
     */
    ResourceBundleEntryOverride getResourceBundleEntryOverride(String resourceBundleEntryId) throws DomainException;
}
