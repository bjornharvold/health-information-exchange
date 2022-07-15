/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.platform.impl;

import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.platform.dto.LaunchableApplicationDto;
import com.hxcel.globalhealth.domain.platform.dao.*;
import com.hxcel.globalhealth.domain.platform.model.*;
import com.hxcel.globalhealth.domain.platform.model.enums.*;
import com.hxcel.globalhealth.domain.common.dao.UserDAO;
import com.hxcel.globalhealth.domain.common.dao.RoleDAO;
import com.hxcel.globalhealth.domain.common.dao.CountryDAO;
import com.hxcel.globalhealth.domain.common.dao.UserRoleDAO;
import com.hxcel.globalhealth.domain.common.model.*;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
import com.hxcel.globalhealth.domain.socialnetwork.RelationshipManager;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.utils.string.KeyValuePair;
import com.hxcel.globalhealth.utils.string.StringParser;
import com.hxcel.globalhealth.utils.image.ImageResizer;
import com.hxcel.globalhealth.utils.locale.LocaleUtils;
import com.hxcel.globalhealth.service.cms.CmsService;
import com.hxcel.globalhealth.service.cms.CmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.io.InputStream;
import java.io.IOException;

/**
 * User: bjorn
 * Date: Jun 5, 2008
 * Time: 6:05:18 PM
 */
@SuppressWarnings(value = "unchecked")
public class PlatformManagerImpl implements PlatformManager {
    private final static Logger log = LoggerFactory.getLogger(PlatformManagerImpl.class);
    private static final String APPLICATIONS = "/content/applications";
    private static final String ORGANIZATIONS = "/content/organizations";

    /**
     * Create a new organization or update an existing one
     *
     * @param org
     * @return
     * @throws DomainException
     */
    public Organization saveOrUpdateOrganization(Organization org) throws DomainException {
        if (org == null) {
            log.error("Organization entity is null");
            throw new DomainException("error.missing.argument.exception", "org");
        }

        try {
            // have to do this to avoid a NonUniqueObjectException because of the parent org
            org = organizationDAO.merge(org);

            organizationDAO.flush();
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return org;
    }

    /**
     * Create a new organization or update an existing one
     *
     * @param organizationId
     * @return
     * @throws DomainException
     */
    public Organization addOrganizationIcon(String organizationId, String iconName, InputStream icon, IconSizeCd size) throws DomainException {
        Organization result = null;

        if (log.isTraceEnabled()) {
            log.trace("Adding icon to organization");
        }

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }
        if (StringUtils.isBlank(iconName)) {
            log.error("iconName is null");
            throw new DomainException("error.missing.argument.exception", "iconName");
        }
        if (icon == null) {
            log.error("icon is null");
            throw new DomainException("error.missing.argument.exception", "icon");
        }
        if (size == null) {
            log.error("size is null");
            throw new DomainException("error.missing.argument.exception", "size");
        }

        try {

            result = getOrganization(organizationId);

            if (result != null) {
                if (cmsService.isAvailable()) {

                    String iconPath = ORGANIZATIONS + "/" + result.getId();
                    InputStream resize = null;
                    Image image = result.getImage();

                    if (image == null) {
                        image = new Image();
                    }

                    if (log.isTraceEnabled()) {
                        log.trace("Creating " + size.name() + " icon for organization. Resizing if necessary...");
                    }
                    switch (size) {
                        case LARGE:
                            resize = imageResizer.resize(icon, largeIconWidth, iconName.substring(iconName.lastIndexOf(".") + 1));
                            iconPath += IconSizeCd.LARGE.name();
                            iconPath = cmsService.upload(iconPath, iconName, resize);
                            image.setLargeIconUrl(iconPath);
                            break;
                        case MEDIUM:
                            resize = imageResizer.resize(icon, mediumIconWidth, iconName.substring(iconName.lastIndexOf(".") + 1));
                            iconPath += IconSizeCd.MEDIUM.name();
                            iconPath = cmsService.upload(iconPath, iconName, resize);
                            image.setMediumIconUrl(iconPath);
                            break;
                        case SMALL:
                            resize = imageResizer.resize(icon, smallIconWidth, iconName.substring(iconName.lastIndexOf(".") + 1));
                            iconPath += IconSizeCd.SMALL.name();
                            iconPath = cmsService.upload(iconPath, iconName, resize);
                            image.setSmallIconUrl(iconPath);
                            break;
                    }

                    if (log.isTraceEnabled()) {
                        log.trace("New icon uploaded with path: " + iconPath);
                    }

                    result.setImage(image);
                    organizationDAO.update(result);
                } else {
                    log.error("CMS service is not available");
                }
            } else {
                log.error("Can't find organization with id: " + organizationId);
            }

        } catch (CmsException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Sets the organization to inactive
     *
     * @param organizationId
     * @throws DomainException
     */
    public Organization deactivateOrganization(String organizationId) throws DomainException {
        Organization result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organization id is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }

        try {
            result = organizationDAO.get(Organization.class, organizationId);

            if (result != null) {
                result.setOrganizationStatus(OrganizationStatusCd.INACTIVE);
                organizationDAO.update(result);
            } else {
                log.error("Could not find organization with id: " + organizationId);
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Create a new application or update an existing one
     *
     * @param app
     * @return
     * @throws DomainException
     */
    public Application saveOrUpdateApplication(Application app) throws DomainException {
        if (app == null) {
            log.error("Application entity is null");
            throw new DomainException("error.missing.argument.exception", "app");
        }

        try {
            if (StringUtils.isBlank(app.getId())) {
                // creating an application for the first time adds it to the system
                // in an unpublished state.
                app.setApplicationStatus(ApplicationStatusCd.UNPUBLISHED);
                app = applicationDAO.save(app);
            } else {
                app = applicationDAO.update(app);
            }
            applicationDAO.flush();
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return app;
    }

    public Application deactivateApplication(String applicationId) throws DomainException {
        Application result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("application id is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }

        try {
            result = applicationDAO.get(Application.class, applicationId);

            if (result != null) {
                result.setApplicationStatus(ApplicationStatusCd.UNPUBLISHED);
                applicationDAO.update(result);
            } else {
                log.error("Could not find application with id: " + applicationId);
            }

        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public License addApplicationLicense(String applicationId, License license) throws DomainException {

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }
        if (license == null) {
            log.error("License is null");
            throw new DomainException("error.missing.argument.exception", "license");
        }

        try {
            if (StringUtils.isBlank(license.getId())) {
                Application app = applicationDAO.get(Application.class, applicationId);

                if (app != null) {
                    if (log.isTraceEnabled()) {
                        log.trace("creating new license for application with id: " + applicationId);
                    }
                    license.setApplication(app);
                    licenseDAO.save(license);

                } else {
                    if (log.isTraceEnabled()) {
                        log.trace("updating existing license for application with id: " + applicationId + " and license id: " + license.getId());
                    }
                    licenseDAO.update(license);
                }
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return license;
    }

    public License getLicense(String licenseId) throws DomainException, PersistenceException {
        License result = null;

        if (StringUtils.isBlank(licenseId)) {
            log.error("License id is null");
            throw new DomainException("error.missing.argument.exception", "licenseId");
        }

        result = licenseDAO.get(License.class, licenseId);

        return result;
    }

    public License updateLicense(License license) throws DomainException {
        License result = null;

        if (license == null) {
            log.error("License is null");
            throw new DomainException("error.missing.argument.exception", "license");
        }
        if (StringUtils.isBlank(license.getId())) {
            throw new DomainException("error.missing.argument.exception", "licenseId");
        }

        try {
            result = licenseDAO.update(license);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Add user to organization.
     *
     * @param orgId
     * @param userId
     * @throws DomainException
     */
    public OrganizationUser registerUserWithOrganization(String orgId, String userId) throws DomainException {
        OrganizationUser result = null;

        if (StringUtils.isBlank(orgId)) {
            log.error("Organization id is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }
        if (StringUtils.isBlank(userId)) {
            log.error("User id is null");
            throw new DomainException("error.missing.argument.exception", "userId");
        }

        try {

            result = organizationUserDAO.getOrganizationUser(orgId, userId);

            if (result == null) {
                Organization org = organizationDAO.get(Organization.class, orgId);
                User u = userDAO.get(User.class, userId);

                result = organizationUserDAO.save(new OrganizationUser(org, u));
            } else {
                log.info("User is already a member of this organization");
            }

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Retrieve organization by id
     *
     * @param organizationId
     * @return
     * @throws DomainException
     */
    public Organization getOrganization(String organizationId) throws DomainException {
        if (log.isTraceEnabled()) {
            log.trace("Retrieving organization by id: " + organizationId);
        }

        Organization result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("Organization organizationId is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }

        try {
            result = organizationDAO.get(Organization.class, organizationId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<Application> searchApplicationsOwnedByOrganization(String organizationId, String name, Boolean platform, Integer index, Integer maxResults) throws DomainException {
        List<Application> result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("Organization organizationId is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }

        try {
            result = applicationDAO.searchForApplicationsByOwner(organizationId, name, platform, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public OrganizationUser deleteOrganizationUser(String organizationUserId) throws DomainException {
        OrganizationUser result = null;

        if (StringUtils.isBlank(organizationUserId)) {
            log.error("organizationUserId is null");
            throw new DomainException("error.missing.argument.exception", "organizationUserId");
        }

        try {
            result = organizationUserDAO.get(OrganizationUser.class, organizationUserId);

            if (result != null) {
                // first we have to delete any outstanding organization user role entities
                List<OrganizationUserRole> list = organizationUserRoleDAO.searchForOrganizationUserRoles(organizationUserId, null, null, null);
                organizationUserRoleDAO.deleteAll(list);

                // now we can delete the org member
                organizationUserDAO.delete(result);
            }

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Add application to organization
     *
     * @param orgId
     * @param licenseId
     * @throws DomainException
     */
    public OrganizationLicense saveOrUpdateOrganizationLicense(String orgId, String licenseId) throws DomainException {
        OrganizationLicense result = null;

        if (StringUtils.isBlank(orgId)) {
            log.error("organizationId is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }
        if (licenseId == null) {
            log.error("licenseId is null");
            throw new DomainException("error.missing.argument.exception", "licenseId");
        }

        try {
            result = organizationLicenseDAO.getOrganizationLicense(orgId, licenseId);

            if (result == null) {
                Organization org = organizationDAO.get(Organization.class, orgId);
                License lic = licenseDAO.get(License.class, licenseId);

                result = organizationLicenseDAO.save(new OrganizationLicense(org, lic, OrganizationLicenseStatusCd.ACTIVE));
            } else {
                if (log.isInfoEnabled()) {
                    log.info("Organization has already licensed this application");
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public OrganizationLicense unlicenseThirdPartyApplication(String organizationLicenseId) throws DomainException {
        OrganizationLicense ol = null;

        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("OrganizationLicenseId is null");
            throw new DomainException("error.missing.argument.exception", "organizationLicenseId");
        }

        try {
            ol = organizationLicenseDAO.get(OrganizationLicense.class, organizationLicenseId);

            if (ol != null) {
                // first we have to delete the application configuration overrides that exist for this license
                List<ApplicationConfigurationOverride> overrides = applicationConfigurationOverrideDAO.searchForLicensedThirdPartyApplicationConfigurationOverrides(organizationLicenseId, null, null, null);
                applicationConfigurationOverrideDAO.deleteAll(overrides);
                
                // now we can delete the license
                organizationLicenseDAO.delete(ol);
            } else {
                if (log.isInfoEnabled()) {
                    log.info("Cannot unlicense application from organization. Id doesn't exist: " + organizationLicenseId);
                }
            }

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return ol;
    }

    /**
     * Retrieves organizations by name
     *
     * @param name
     * @param index
     * @param maxResult
     * @return
     * @throws DomainException
     */
    public List<Organization> searchForOrganizations(String name, Integer index, Integer maxResult) throws DomainException {
        List<Organization> result = null;

        try {
            result = organizationDAO.searchForOrganizations(name, index, maxResult);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Lists registered applications in the db
     *
     * @param name
     * @param index
     * @param maxResult
     * @return
     * @throws DomainException
     */
    public List<Application> searchForApplications(String name, Boolean isPlatform, Integer index, Integer maxResult) throws DomainException {
        List<Application> result = null;

        if (isPlatform == null) {
            log.error("isPlatform is null");
            throw new DomainException("error.missing.argument.exception", "isPlatform");
        }

        try {
            result = applicationDAO.searchForApplications(name, isPlatform, index, maxResult);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchForApplicationsCount(String name, Boolean isPlatform) throws DomainException {
        Integer result;

        if (isPlatform == null) {
            log.error("isPlatform is null");
            throw new DomainException("error.missing.argument.exception", "isPlatform");
        }

        try {
            result = applicationDAO.searchForApplicationsCount(name, isPlatform);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Retrieve application by id
     *
     * @param applicationId
     * @return
     * @throws DomainException
     */
    public Application getApplication(String applicationId) throws DomainException {
        Application result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }

        try {
            result = applicationDAO.get(Application.class, applicationId);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<KeyValuePair> getOrganizationThinList() throws DomainException {
        List<KeyValuePair> result = null;

        try {
            result = organizationDAO.getOrganizationThinList();
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public ApplicationConfiguration getApplicationConfiguration(String applicationConfigurationId) throws DomainException {
        ApplicationConfiguration result = null;

        if (StringUtils.isBlank(applicationConfigurationId)) {
            log.error("applicationConfigurationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationConfigurationId");
        }

        try {
            result = applicationConfigurationDAO.get(ApplicationConfiguration.class, applicationConfigurationId);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public ApplicationConfiguration addApplicationConfiguration(String applicationId, ApplicationConfiguration ac) throws DomainException {
        Application app = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }
        if (ac == null) {
            throw new DomainException("error.missing.argument.exception", "ac");
        }

        try {
            if (StringUtils.isBlank(ac.getId())) {
                app = applicationDAO.get(Application.class, applicationId);

                // throw error if application does not exist
                if (app != null) {
                    ac.setApplication(app);

                    log.info("Creating new configuration entity for applicationId: " + applicationId);
                    applicationConfigurationDAO.save(ac);
                }
            } else {
                log.info("Updating existing configuration entity for applicationId: " + applicationId);
                applicationConfigurationDAO.update(ac);
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return ac;
    }

    public ApplicationConfiguration updateApplicationConfiguration(ApplicationConfiguration ac) throws DomainException {

        if (ac == null) {
            throw new DomainException("error.missing.argument.exception", "ac");
        }
        if (StringUtils.isBlank(ac.getId())) {
            throw new DomainException("error.missing.argument.exception", "ac Id");
        }

        try {
            log.info("Updating existing configuration entity for applicationId: " + ac.getApplication().getId());

            ac = applicationConfigurationDAO.update(ac);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return ac;
    }

    public ApplicationConfiguration deleteApplicationConfiguration(String applicationConfigurationId) throws DomainException {
        ApplicationConfiguration result = null;

        if (StringUtils.isBlank(applicationConfigurationId)) {
            log.error("applicationConfigurationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationConfigurationId");
        }

        try {
            log.info("Deleting configuration entity with it: " + applicationConfigurationId);
            result = applicationConfigurationDAO.get(ApplicationConfiguration.class, applicationConfigurationId);

            if (result != null) {
                // first we need to delete all the overrides that are associated with this configuration
                List<ApplicationConfigurationOverride> list = applicationConfigurationOverrideDAO.getApplicationConfigurationOverrideByApplicationConfigurationId(applicationConfigurationId);

                applicationConfigurationOverrideDAO.deleteAll(list);

                // now we are safe to delete the entity
                applicationConfigurationDAO.delete(result);
            } else {
                log.error("Can't find application configuration with id: " + applicationConfigurationId);
            }

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public License deleteApplicationLicense(String applicationId, String applicationLicenseId) throws DomainException {
        License result = null;

        if (StringUtils.isBlank(applicationLicenseId)) {
            log.error("applicationLicenseId is null");
            throw new DomainException("error.missing.argument.exception", "applicationLicenseId");
        }
        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }

        try {
            result = licenseDAO.get(License.class, applicationLicenseId);

            if (result != null) {
                log.info("Deleting license with id: " + applicationLicenseId + " for application with id: " + applicationId);
                result.setStatus(LicenseStatusCd.INVALID);
                result = licenseDAO.update(result);
            } else {
                log.error("Can't delete license with id: " + applicationLicenseId);
            }

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public ApplicationConfigurationOverride getApplicationConfigurationOverride(String organizationConfigurationId) throws DomainException {
        ApplicationConfigurationOverride result = null;

        if (StringUtils.isBlank(organizationConfigurationId)) {
            log.error("organizationConfigurationId is null");
            throw new DomainException("error.missing.argument.exception", "organizationConfigurationId");
        }

        try {
            result = applicationConfigurationOverrideDAO.get(ApplicationConfigurationOverride.class, organizationConfigurationId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public ApplicationConfigurationOverride addApplicationConfigurationOverride(String organizationLicenseId, ApplicationConfigurationOverride override) throws DomainException {

        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("organizationLicenseId is null");
            throw new DomainException("error.missing.argument.exception", "organizationLicenseId");
        }
        if (override == null) {
            throw new DomainException("error.missing.argument.exception", "ac");
        }

        try {
            if (StringUtils.isNotBlank(override.getId())) {
                override = applicationConfigurationOverrideDAO.update(override);
            } else {
                OrganizationLicense organizationLicense = organizationLicenseDAO.get(OrganizationLicense.class, organizationLicenseId);

                // throw error if application does not exist
                if (organizationLicense != null) {
                    override.setOrganizationLicense(organizationLicense);
                    override = applicationConfigurationOverrideDAO.save(override);
                }
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return override;
    }

    public ApplicationConfigurationOverride updateApplicationConfigurationOverride(ApplicationConfigurationOverride oc) throws DomainException {
        if (oc == null) {
            throw new DomainException("error.missing.argument.exception", "oc");
        }
        if (StringUtils.isBlank(oc.getId())) {
            throw new DomainException("error.missing.argument.exception", "organizationConfigurationId");
        }

        try {
            oc = applicationConfigurationOverrideDAO.update(oc);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return oc;
    }

    public Application addApplicationIcon(String applicationId, String iconName, InputStream icon, IconSizeCd size) throws DomainException {
        Application result = null;

        try {

            result = getApplication(applicationId);

            if (result != null) {
                if (cmsService.isAvailable()) {

                    String iconPath = APPLICATIONS + "/" + result.getId() + "/";
                    InputStream resize = null;
                    Image image = result.getImage();

                    if (image == null) {
                        image = new Image();
                    }

                    if (log.isTraceEnabled()) {
                        log.trace("Creating " + size.name() + " icon for application. Resizing if necessary...");
                    }
                    switch (size) {
                        case LARGE:
                            resize = imageResizer.resize(icon, largeIconWidth, iconName.substring(iconName.lastIndexOf(".") + 1));
                            iconPath += IconSizeCd.LARGE.name();
                            iconPath = cmsService.upload(iconPath, iconName, resize);
                            image.setLargeIconUrl(iconPath);
                            break;
                        case MEDIUM:
                            resize = imageResizer.resize(icon, mediumIconWidth, iconName.substring(iconName.lastIndexOf(".") + 1));
                            iconPath += IconSizeCd.MEDIUM.name();
                            iconPath = cmsService.upload(iconPath, iconName, resize);
                            image.setMediumIconUrl(iconPath);
                            break;
                        case SMALL:
                            resize = imageResizer.resize(icon, smallIconWidth, iconName.substring(iconName.lastIndexOf(".") + 1));
                            iconPath += IconSizeCd.SMALL.name();
                            iconPath = cmsService.upload(iconPath, iconName, resize);
                            image.setSmallIconUrl(iconPath);
                            break;
                    }

                    if (log.isTraceEnabled()) {
                        log.trace("New icon uploaded with path: " + iconPath);
                    }

                    result.setImage(image);
                    applicationDAO.update(result);
                } else {
                    log.error("CMS service is not available");
                }
            } else {
                log.error("Can't find application with id: " + applicationId);
            }

        } catch (CmsException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Application addApplicationSwf(String applicationId, String swfName, InputStream swf) throws DomainException {
        Application result = null;

        try {

            result = getApplication(applicationId);

            if (result != null) {
                if (cmsService.isAvailable()) {

                    String swfPath = APPLICATIONS + "/" + result.getId();

                    if (log.isTraceEnabled()) {
                        log.trace("Adding new SWF file to application");
                    }
                    swfPath = cmsService.upload(swfPath, swfName, swf);


                    if (log.isTraceEnabled()) {
                        log.trace("New SWF uploaded with path: " + swfPath);
                    }

                    result.setSwfUrl(swfPath);
                    applicationDAO.update(result);
                } else {
                    log.error("CMS service is not available");
                }
            } else {
                log.error("Can't find application with id: " + applicationId);
            }

        } catch (CmsException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchForOrganizationsCount(String name) throws DomainException {
        Integer result;

        try {
            result = organizationDAO.searchForOrganizationsCount(name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }


    public ApplicationConfigurationOverride deleteApplicationConfigurationOverride(String applicationConfigurationOverrideId) throws DomainException {
        ApplicationConfigurationOverride result = null;

        if (StringUtils.isBlank(applicationConfigurationOverrideId)) {
            log.error("applicationConfigurationOverrideId is null");
            throw new DomainException("error.missing.argument.exception", "applicationConfigurationOverrideId");
        }

        try {
            ApplicationConfigurationOverride aco = applicationConfigurationOverrideDAO.get(ApplicationConfigurationOverride.class, applicationConfigurationOverrideId);

            if (aco != null) {
                applicationConfigurationOverrideDAO.delete(aco);
            } else {
                log.error("Cannot delete application configuration override with id: " + applicationConfigurationOverrideId);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Role getRoleByStatusCode(String statusCode) throws DomainException {
        Role result = null;

        if (StringUtils.isBlank(statusCode)) {
            log.error("statusCode is null");
            throw new DomainException("error.missing.argument.exception", "statusCode");
        }

        try {
            result = roleDAO.getRoleByStatusCode(statusCode);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Role saveOrUpdateRole(Role role) throws DomainException {
        if (role == null) {
            log.error("role is null");
            throw new DomainException("error.missing.argument.exception", "role");
        }

        try {
            // make sure the status code is all upper case
            role.setStatusCode(StringUtils.upperCase(role.getStatusCode()));
            role = roleDAO.saveOrUpdate(role);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return role;
    }

    public List<Role> searchForRoles(String name, Integer index, Integer maxResults) throws DomainException {
        List<Role> result = null;

        try {
            result = roleDAO.searchForRoles(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchForRolesCount(String name) throws DomainException {
        Integer result = null;

        try {
            result = roleDAO.searchForRolesCount(name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Role getRole(String id) throws DomainException {
        Role result = null;

        if (StringUtils.isBlank(id)) {
            log.error("roleId is null");
            throw new DomainException("error.missing.argument.exception", "roleId");
        }

        try {
            result = roleDAO.get(Role.class, id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * This is a pretty big role. Super Super user should be able to execute this
     * as it will change permissions across many entities
     *
     * @param roleId
     * @return
     * @throws DomainException
     */
    public Role deleteRole(String roleId) throws DomainException {
        Role result = null;

        if (StringUtils.isBlank(roleId)) {
            log.error("roleId is null");
            throw new DomainException("error.missing.argument.exception", "roleId");
        }

        try {
            result = roleDAO.get(Role.class, roleId);

            if (result != null) {
                // first we need to delete all constraining entities
                List<ApplicationRole> appRoles = applicationRoleDAO.getApplicationRolesByRoleId(roleId);
                List<UserRole> userRoles = userRoleDAO.getUserRolesByRoleId(roleId);
                List<OrganizationUserRole> orgUserRoles = organizationUserRoleDAO.getOrganizationUserRolesByRoleId(roleId);

                if (appRoles != null && appRoles.size() > 0) {
                    applicationRoleDAO.deleteAll(appRoles);
                }
                if (userRoles != null && userRoles.size() > 0) {
                    userRoleDAO.deleteAll(userRoles);
                }
                if (orgUserRoles != null && orgUserRoles.size() > 0) {
                    organizationUserRoleDAO.deleteAll(orgUserRoles);
                }

                // now delete the role itself
                roleDAO.delete(result);
                roleDAO.flush();
            } else {
                log.error("Role with id: " + roleId + " does not exist");
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<ApplicationRole> addApplicationRoles(String applicationId, List<String> roles) throws DomainException {
        List<ApplicationRole> result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }

        try {
            Application app = applicationDAO.get(Application.class, applicationId);

            if (app != null) {
                if (roles != null) {
                    result = new ArrayList<ApplicationRole>();
                    for (String roleId : roles) {
                        result.add(addApplicationRole(applicationId, roleId));
                    }
                }
            } else {
                log.error("Can't find application with id: " + applicationId);
            }

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public ApplicationRole addApplicationRole(String applicationId, String roleId) throws DomainException {
        ApplicationRole result = null;

        try {
            Application app = applicationDAO.get(Application.class, applicationId);
            result = applicationRoleDAO.getApplicationRoleByApplicationIdAndRoleId(applicationId, roleId);

            if (result == null) {
                Role role = getRole(roleId);

                if (role != null) {
                    log.info("Adding new role to application id: " + applicationId + ". Role id: " + roleId);
                    result = applicationRoleDAO.save(new ApplicationRole(app, role));
                } else {
                    log.error("Can't find role with id: " + roleId);
                }
            } else {
                log.info("Role with id: " + roleId + " already exists for applicationwith id: " + applicationId);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public ApplicationRole deleteApplicationRole(String applicationRoleId) throws DomainException {
        ApplicationRole result = null;

        if (StringUtils.isBlank(applicationRoleId)) {
            log.error("applicationRoleId is null");
            throw new DomainException("error.missing.argument.exception", "applicationRoleId");
        }

        try {
            result = applicationRoleDAO.get(ApplicationRole.class, applicationRoleId);

            if (result != null) {
                applicationRoleDAO.delete(result);
            } else {
                log.error("No application role exists for id: " + applicationRoleId);
            }


        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<License> getLicensedApplicationsExcludeOwner(String organizationId, Boolean platform) throws DomainException {
        List<License> result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }

        try {
            result = licenseDAO.getLicensedApplicationsExcludeOwner(organizationId, platform);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<OrganizationLicense> licenseApplicationsForOrganization(String organizationId, List<String> licenseIds) throws DomainException {
        List<OrganizationLicense> result = null;

        if (licenseIds != null) {
            result = new ArrayList<OrganizationLicense>();
            for (String licenseId : licenseIds) {
                result.add(saveOrUpdateOrganizationLicense(organizationId, licenseId));
            }
        }

        return result;
    }

    public List<OrganizationUser> registerUsersWithOrganization(String organizationId, List<String> userIds) throws DomainException {
        List<OrganizationUser> result = null;

        if (userIds != null) {
            result = new ArrayList<OrganizationUser>();

            for (String userId : userIds) {
                result.add(registerUserWithOrganization(organizationId, userId));
            }
        }

        return result;
    }

    public List<OrganizationUser> searchForOrganizationUsers(String organizationId, String name, Integer index, Integer maxResults) throws DomainException {
        List<OrganizationUser> result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }

        try {
            result = organizationUserDAO.searchForOrganizationUsers(organizationId, name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchOrganizationUsersCount(String organizationId, String name) throws DomainException {
        Integer result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }

        try {
            result = organizationUserDAO.searchForOrganizationUsersCount(organizationId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<OrganizationLicense> searchForLicensedThirdPartyApplications(String organizationId, String name, Boolean isPlatform, OrganizationLicenseStatusCd status, Integer index, Integer maxResults) throws DomainException {
        List<OrganizationLicense> result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }
        if (status == null) {
            log.error("status is null");
            throw new DomainException("error.missing.argument.exception", "status");
        }

        try {
            result = organizationLicenseDAO.searchForOrganizationLicenses(organizationId, name, isPlatform, status, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchLicensedThirdPartyApplicationsCount(String organizationId, String name, Boolean isPlatform, OrganizationLicenseStatusCd status) throws DomainException {
        Integer result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }
        if (status == null) {
            log.error("status is null");
            throw new DomainException("error.missing.argument.exception", "status");
        }

        try {
            result = organizationLicenseDAO.searchForOrganizationLicensesCount(organizationId, name, isPlatform, status);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<ApplicationConfigurationOverride> searchForLicensedThirdPartyApplicationConfigurationOverrides(String organizationLicenseId, String name, Integer index, Integer maxResults) throws DomainException {
        List<ApplicationConfigurationOverride> result = null;

        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("organizationLicenseId is null");
            throw new DomainException("error.missing.argument.exception", "organizationLicenseId");
        }

        try {
            result = applicationConfigurationOverrideDAO.searchForLicensedThirdPartyApplicationConfigurationOverrides(organizationLicenseId, name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchForLicensedThirdPartyApplicationConfigurationOverridesCount(String organizationLicenseId, String name) throws DomainException {
        Integer result = null;

        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("organizationLicenseId is null");
            throw new DomainException("error.missing.argument.exception", "organizationLicenseId");
        }

        try {
            result = applicationConfigurationOverrideDAO.searchForLicensedThirdPartyApplicationConfigurationOverridesCount(organizationLicenseId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public ApplicationConfigurationOverride saveOrUpdateApplicationConfigurationOverride(ApplicationConfigurationOverride override) throws DomainException {
        if (override == null) {
            log.error("override is null");
            throw new DomainException("error.missing.argument.exception", "override");
        }

        try {
            override = applicationConfigurationOverrideDAO.saveOrUpdate(override);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return override;
    }

    public Integer searchApplicationsOwnedByOrganizationCount(String organizationId, String name, Boolean platform) throws DomainException {
        Integer result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }

        try {
            result = applicationDAO.searchForApplicationsByOwnerCount(organizationId, name, platform);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<License> searchForApplicationLicenses(String applicationId, String name, LicenseStatusCd status, Integer index, Integer maxResults) throws DomainException {
        List<License> result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }
        if (status == null) {
            log.error("status is null");
            throw new DomainException("error.missing.argument.exception", "status");
        }

        try {
            result = licenseDAO.searchForApplicationLicenses(applicationId, name, status, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchForApplicationLicensesCount(String applicationId, String name, LicenseStatusCd status) throws DomainException {
        Integer result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }
        if (status == null) {
            log.error("status is null");
            throw new DomainException("error.missing.argument.exception", "status");
        }

        try {
            result = licenseDAO.searchForApplicationsLicensesCount(applicationId, name, status);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<ApplicationRole> searchForApplicationRoles(String applicationId, String name, Integer index, Integer maxResults) throws DomainException {
        List<ApplicationRole> result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }

        try {
            result = applicationRoleDAO.searchForApplicationRoles(applicationId, name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchForApplicationRolesCount(String applicationId, String name) throws DomainException {
        Integer result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }

        try {
            result = applicationRoleDAO.searchForApplicationsRolesCount(applicationId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<ApplicationConfiguration> searchForApplicationConfigurations(String applicationId, String name, Integer index, Integer maxResults) throws DomainException {
        List<ApplicationConfiguration> result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }

        try {
            result = applicationConfigurationDAO.searchForApplicationConfigurations(applicationId, name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchForApplicationConfigurationsCount(String applicationId, String name) throws DomainException {
        Integer result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }

        try {
            result = applicationConfigurationDAO.searchForApplicationsConfigurationsCount(applicationId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Boolean isApplicationPublishable(Application application) throws DomainException {
        Boolean result = true;

        if (application == null) {
            log.error("application is null");
            throw new DomainException("error.missing.argument.exception", "application");
        }

        // we'll check 3 things and if they hold true the application is publishable
        if (StringUtils.isBlank(application.getSwfUrl())) {
            if (log.isTraceEnabled()) {
                log.trace("Cannot publish: SWF url is empty");
            }
            result = false;
        } else if (application.getImage() == null) {
            if (log.isTraceEnabled()) {
                log.trace("Cannot publish: Image entity has not yet been initialized");
            }
            result = false;
        } else if (StringUtils.isBlank(application.getImage().getLargeIconUrl())) {
            if (log.isTraceEnabled()) {
                log.trace("Cannot publish: Large icon url is empty");
            }
            result = false;
        } else if (StringUtils.isBlank(application.getImage().getMediumIconUrl())) {
            if (log.isTraceEnabled()) {
                log.trace("Cannot publish: Medium icon url is empty");
            }
            result = false;
        } else if (StringUtils.isBlank(application.getImage().getSmallIconUrl())) {
            if (log.isTraceEnabled()) {
                log.trace("Cannot publish: Small icon url is empty");
            }
            result = false;
        }


        return result;
    }

    public Boolean isApplicationUnpublishable(Application application) throws DomainException {
        Boolean result = true;

        if (application == null) {
            log.error("application is null");
            throw new DomainException("error.missing.argument.exception", "application");
        }

        try {
            Integer count = organizationLicenseDAO.getApplicationUsageCount(application.getId());

            if (count > 0) {
                result = false;
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public void publishApplication(String applicationId, ApplicationStatusCd status) throws DomainException {
        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }
        if (status == null) {
            log.error("status is null");
            throw new DomainException("error.missing.argument.exception", "status");
        }

        Application app = getApplication(applicationId);

        if (app != null) {
            app.setApplicationStatus(status);
            saveOrUpdateApplication(app);
        } else {
            log.error("Cannot retrieve application with id: " + applicationId);
        }
    }

    public LaunchableApplicationDto getLaunchableApplication(String organizationId, String applicationId, boolean platform) throws DomainException {
        LaunchableApplicationDto result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }
        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }

        try {
            if (log.isInfoEnabled()) {
                log.info("Trying to load launchable application with id: " + applicationId + ". Assuming organization with id: " + organizationId + " is the owner.");
            }

            Application app = applicationDAO.getLaunchableApplication(organizationId, applicationId, platform);

            if (app != null) {
                if (log.isInfoEnabled()) {
                    log.info("Found application. Retrieving configuration entities.");
                }

                List<ApplicationConfiguration> configs = applicationConfigurationDAO.searchForApplicationConfigurations(applicationId, null, null, null);
                result = new LaunchableApplicationDto(app, configs);

            } else {
                if (log.isInfoEnabled()) {
                    log.info("Looking through 3rd party application licenses instead.");
                }

                OrganizationLicense organizationLicense = organizationLicenseDAO.getLaunchableApplication(organizationId, applicationId, platform);

                if (organizationLicense != null) {
                    List<ApplicationConfiguration> configs = applicationConfigurationDAO.searchForApplicationConfigurations(applicationId, null, null, null);
                    List<ApplicationConfigurationOverride> overrides = applicationConfigurationOverrideDAO.searchForLicensedThirdPartyApplicationConfigurationOverrides(organizationLicense.getId(), null, null, null);

                    result = new LaunchableApplicationDto(organizationLicense, configs, overrides);
                } else {
                    log.error("Could not find application. Check whether all criteria have been met to make application launch-ready");
                }

            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public OrganizationLicense getOrganizationLicense(String organizationLicenseId) throws DomainException {
        OrganizationLicense result = null;

        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("organizationLicenseId is null");
            throw new DomainException("error.missing.argument.exception", "organizationLicenseId");
        }

        try {
            result = organizationLicenseDAO.get(OrganizationLicense.class, organizationLicenseId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }


        return result;
    }

    public List<Country> searchForCountries(String name, Integer index, Integer maxResults) throws DomainException {
        List<Country> result = null;

        try {
            result = countryDAO.searchForCountries(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchForCountriesCount(String name) throws DomainException {
        Integer result = null;

        try {
            result = countryDAO.searchForCountriesCount(name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Country saveOrUpdateCountry(Country country) throws DomainException {
        try {
            country = countryDAO.saveOrUpdate(country);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return country;
    }

    public Country getCountry(String countryId) throws DomainException {
        Country result = null;

        if (StringUtils.isBlank(countryId)) {
            log.error("countryId is null");
            throw new DomainException("error.missing.argument.exception", "countryId");
        }

        try {
            result = countryDAO.get(Country.class, countryId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Country getCountry(CountryCd country) throws DomainException {
        Country result = null;

        if (country == null) {
            log.error("country is null");
            throw new DomainException("error.missing.argument.exception", "country");
        }

        try {
            result = countryDAO.getCountry(country);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<OrganizationUserRole> searchForOrganizationUserRoles(String organizationUserId, String name, Integer index, Integer maxResults) throws DomainException {
        List<OrganizationUserRole> result = null;

        if (StringUtils.isBlank(organizationUserId)) {
            log.error("organizationUserId is null");
            throw new DomainException("error.missing.argument.exception", "organizationUserId");
        }

        try {
            result = organizationUserRoleDAO.searchForOrganizationUserRoles(organizationUserId, name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchOrganizationUserRolesCount(String organizationUserId, String name) throws DomainException {
        Integer result = null;

        if (StringUtils.isBlank(organizationUserId)) {
            log.error("organizationUserId is null");
            throw new DomainException("error.missing.argument.exception", "organizationUserId");
        }

        try {
            result = organizationUserRoleDAO.searchForOrganizationUserRolesCount(organizationUserId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public OrganizationUser getOrganizationUser(String organizationUserId) throws DomainException {
        OrganizationUser result = null;

        if (StringUtils.isBlank(organizationUserId)) {
            log.error("organizationUserId is null");
            throw new DomainException("error.missing.argument.exception", "organizationUserId");
        }

        try {
            result = organizationUserDAO.get(OrganizationUser.class, organizationUserId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<Regulation> searchForRegulations(String name, Integer index, Integer maxResults) throws DomainException {
        List<Regulation> result = null;

        try {
            result = regulationDAO.searchForRegulations(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchForRegulationsCount(String name) throws DomainException {
        Integer result = null;

        try {
            result = regulationDAO.searchForRegulationsCount(name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Regulation saveOrUpdateRegulation(Regulation regulation) throws DomainException {
        try {
            regulation = regulationDAO.saveOrUpdate(regulation);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return regulation;
    }

    public Regulation getRegulation(String regulationId) throws DomainException {
        Regulation result = null;

        if (StringUtils.isBlank(regulationId)) {
            log.error("regulationId is null");
            throw new DomainException("error.missing.argument.exception", "regulationId");
        }

        try {
            result = regulationDAO.get(Regulation.class, regulationId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Regulation deleteRegulation(String regulationId) throws DomainException {
        Regulation result = null;

        if (StringUtils.isBlank(regulationId)) {
            log.error("regulationId is null");
            throw new DomainException("error.missing.argument.exception", "regulationId");
        }

        try {
            result = regulationDAO.get(Regulation.class, regulationId);

            if (result != null) {
                // first we have to delete the overrides
                List<RegulationOverride> list = regulationOverrideDAO.getOverridesByRegulationId(regulationId);
                regulationOverrideDAO.deleteAll(list);

                // now we can delete the regulation
                regulationDAO.delete(result);
            } else {
                log.error("Regulation with ID: " + regulationId + " does not exist");
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;

    }

    public List<RegulationOverride> searchForRegulationOverridesByCountry(String countryId, String name, Integer index, Integer maxResults) throws DomainException {
        List<RegulationOverride> result = null;

        if (StringUtils.isBlank(countryId)) {
            log.error("countryId is null");
            throw new DomainException("error.missing.argument.exception", "countryId");
        }

        try {
            result = regulationOverrideDAO.searchForRegulationOverridesByCountry(countryId, name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchForRegulationOverridesByCountryCount(String countryId, String name) throws DomainException {
        Integer result = null;

        if (StringUtils.isBlank(countryId)) {
            log.error("countryId is null");
            throw new DomainException("error.missing.argument.exception", "countryId");
        }

        try {
            result = regulationOverrideDAO.searchForRegulationOverridesByCountryCount(countryId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public RegulationOverride getRegulationOverride(String regulationOverrideId) throws DomainException {
        RegulationOverride result = null;

        if (StringUtils.isBlank(regulationOverrideId)) {
            log.error("regulationOverrideId is null");
            throw new DomainException("error.missing.argument.exception", "regulationOverrideId");
        }

        try {
            result = regulationOverrideDAO.get(RegulationOverride.class, regulationOverrideId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public RegulationOverride saveOrUpdateRegulationOverride(RegulationOverride regulationOverride) throws DomainException {
        RegulationOverride result = null;

        if (regulationOverride == null) {
            log.error("regulationOverride is null");
            throw new DomainException("error.missing.argument.exception", "regulationOverride");
        }

        try {
            result = regulationOverrideDAO.saveOrUpdate(regulationOverride);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public RegulationOverride deleteRegulationOverride(String regulationOverrideId) throws DomainException {
        RegulationOverride result = null;

        if (StringUtils.isBlank(regulationOverrideId)) {
            log.error("regulationOverrideId is null");
            throw new DomainException("error.missing.argument.exception", "regulationOverrideId");
        }

        try {
            result = regulationOverrideDAO.get(RegulationOverride.class, regulationOverrideId);

            if (result != null) {
                regulationOverrideDAO.delete(result);
            } else {
                log.error("Cannot delete regulation override with ID: " + regulationOverrideId);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<Application> getLastModifiedApplications(Boolean platform, Integer maxResults) throws DomainException {
        List<Application> result = null;

        try {
            result = applicationDAO.getLastModifiedApplications(platform, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<Regulation> getLastModifiedRegulations(Integer maxResults) throws DomainException {
        List<Regulation> result = null;

        try {
            result = regulationDAO.getLastModifiedRegulations(maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<Country> getLastModifiedCountries(Integer maxResults) throws DomainException {
        List<Country> result = null;

        try {
            result = countryDAO.getLastModifiedCountries(maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<Organization> getLastModifiedOrganizations(Integer maxResults) throws DomainException {
        List<Organization> result = null;

        try {
            result = organizationDAO.getLastModifiedOrganizations(maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<User> getLastModifiedUsers(Integer maxResults) throws DomainException {
        List<User> result = null;

        try {
            result = userDAO.getLastModifiedUsers(maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<Role> getLastModifiedRoles(Integer maxResults) throws DomainException {
        List<Role> result = null;

        try {
            result = roleDAO.getLastModifiedRoles(maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<ResourceBundleEntry> searchForResourceBundleEntries(String applicationId, String name, Integer index, Integer maxResults) throws DomainException {
        List<ResourceBundleEntry> result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }

        try {
            result = resourceBundleEntryDAO.searchForResourceBundleEntries(applicationId, name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchForResourceBundleEntriesCount(String applicationId, String name) throws DomainException {
        Integer result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }

        try {
            result = resourceBundleEntryDAO.searchForResourceBundleEntriesCount(applicationId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public ResourceBundleEntry saveOrUpdateResourceBundle(ResourceBundleEntry resourceBundleEntry) throws DomainException {
        try {
            resourceBundleEntry = resourceBundleEntryDAO.saveOrUpdate(resourceBundleEntry);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return resourceBundleEntry;
    }

    public ResourceBundleEntry getResourceBundleEntry(String resourceBundleEntryId) throws DomainException {
        ResourceBundleEntry result = null;

        if (StringUtils.isBlank(resourceBundleEntryId)) {
            log.error("resourceBundleEntryId is null");
            throw new DomainException("error.missing.argument.exception", "resourceBundleEntryId");
        }

        try {
            result = resourceBundleEntryDAO.get(ResourceBundleEntry.class, resourceBundleEntryId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<OrganizationUserRole> saveOrUpdateOrganizationUserRoles(String organizationUserId, List<String> roleIds) throws DomainException {
        List<OrganizationUserRole> result = null;
        boolean duplicate = false;

        if (StringUtils.isBlank(organizationUserId)) {
            log.error("organizationUserId is null");
            throw new DomainException("error.missing.argument.exception", "organizationUserId");
        }

        try {
            OrganizationUser ou = organizationUserDAO.get(OrganizationUser.class, organizationUserId);

            if (ou != null) {
                if (roleIds != null) {
                    result = new ArrayList<OrganizationUserRole>();

                    // list grab all existing roles for the user so we can check for dupes
                    List<OrganizationUserRole> existingRoles = organizationUserRoleDAO.searchForOrganizationUserRoles(organizationUserId, null, null, null);

                    for (String roleId : roleIds) {
                        for (OrganizationUserRole existingRole : existingRoles) {
                            if (StringUtils.equals(existingRole.getRole().getId(), roleId)) {
                                duplicate = true;

                                if (log.isTraceEnabled()) {
                                    log.trace("User already has the role with id: " + roleId);
                                }
                            }
                        }

                        if (!duplicate) {
                            Role r = roleDAO.get(Role.class, roleId);

                            if (r != null) {
                                // create new role
                                OrganizationUserRole usr = new OrganizationUserRole(ou, r);
                                usr = organizationUserRoleDAO.save(usr);
                                result.add(usr);
                            } else {
                                log.error("Cannot find role with id: " + roleId);
                            }
                        }

                        // reset flag
                        duplicate = false;
                    }
                }
            } else {
                log.error("Cannot find organization user with id: " + organizationUserId);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }


        return result;
    }

    public OrganizationUserRole deleteOrganizationUserRole(String organizationUserRoleId) throws DomainException {
        OrganizationUserRole result = null;

        if (StringUtils.isBlank(organizationUserRoleId)) {
            log.error("organizationUserRoleId is null");
            throw new DomainException("error.missing.argument.exception", "organizationUserRoleId");
        }

        try {
            result = organizationUserRoleDAO.get(OrganizationUserRole.class, organizationUserRoleId);

            if (result != null) {
                organizationUserRoleDAO.delete(result);
            } else {
                log.error("Cannot find organization user role with id: " + organizationUserRoleId);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public OrganizationUser saveOrUpdateOrganizationUser(OrganizationUser organizationUser) throws DomainException {
        if (organizationUser == null) {
            log.error("organizationUser is null");
            throw new DomainException("error.missing.argument.exception", "organizationUser");
        }

        try {
            organizationUser = organizationUserDAO.saveOrUpdate(organizationUser);

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return organizationUser;
    }

    public ResourceBundleEntry deleteResourceBundleEntry(String resourceBundleEntryId) throws DomainException {
        ResourceBundleEntry result = null;

        if (StringUtils.isBlank(resourceBundleEntryId)) {
            log.error("resourceBundleEntryId is null");
            throw new DomainException("error.missing.argument.exception", "resourceBundleEntryId");
        }

        try {
            result = resourceBundleEntryDAO.get(ResourceBundleEntry.class, resourceBundleEntryId);

            if (result != null) {
                resourceBundleEntryDAO.delete(result);
            } else {
                log.error("Cannot delete resource bundle entry with ID: " + resourceBundleEntryId);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public List<User> searchForUsersWithExcludes(String organizationId, String name, Integer index, Integer maxResults) throws DomainException {
        List<User> result = null;
        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }

        try {
            List<String> excludedUserIds = getUserIdsForOrganizationMembers(organizationId);

            result = userDAO.searchForUsersWithExcludes(excludedUserIds, name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchForUsersWithExcludesCount(String organizationId, String name) throws DomainException {
        Integer result = null;
        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }

        try {
            List<String> excludedUserIds = getUserIdsForOrganizationMembers(organizationId);

            result = userDAO.searchForUsersWithExcludesCount(excludedUserIds, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public void addApplicationResourceBundle(String applicationId, String filename, InputStream resourceBundle) throws DomainException {

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new DomainException("error.missing.argument.exception", "applicationId");
        }
        if (StringUtils.isBlank(filename)) {
            log.error("filename is null");
            throw new DomainException("error.missing.argument.exception", "filename");
        }
        if (resourceBundle == null) {
            log.error("resourceBundle is null");
            throw new DomainException("error.missing.argument.exception", "resourceBundle");
        }

        try {
            Application application = getApplication(applicationId);

            if (application != null) {
                Locale l = LocaleUtils.getLocaleByFilename(filename);
                Country country = countryDAO.findCountryByLocale(l.getCountry(), l.getLanguage());

                if (country != null) {
                    List<KeyValuePair> resourcebundleEntries = new StringParser().parseInputStreamToKeyValuePairs(resourceBundle);

                    // ok, now we have all the ingredients - let's persist resource bundle entries
                    if (resourcebundleEntries != null) {
                        for (KeyValuePair entry : resourcebundleEntries) {
                            ResourceBundleEntry rbe = resourceBundleEntryDAO.getResourceBundleEntryByKey(application.getId(), country.getId(), entry.getKey());

                            if (rbe != null) {
                                // ok this already exist so we will just update the value
                                rbe.setValue(entry.getValue());

                                resourceBundleEntryDAO.update(rbe);
                            } else {
                                // create new resource bundle entry and persist it
                                rbe = new ResourceBundleEntry(application, country);
                                rbe.setKey(entry.getKey());
                                rbe.setValue(entry.getValue());

                                resourceBundleEntryDAO.save(rbe);
                            }
                        }
                    }
                } else {
                    log.error("Can't find country with locale: " + l);
                }
            } else {
                log.error("Can't find application with id: " + applicationId);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    public List<ResourceBundleEntryOverride> searchForLicensedThirdPartyApplicationResourceBundleEntryOverrides(String organizationLicenseId, String name, Integer index, Integer maxResults) throws DomainException {
        List<ResourceBundleEntryOverride> result = null;

        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("organizationLicenseId is null");
            throw new DomainException("error.missing.argument.exception", "organizationLicenseId");
        }

        try {
            result = resourceBundleEntryOverrideDAO.searchForLicensedThirdPartyApplicationResourceBundleEntryOverrides(organizationLicenseId, name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public Integer searchForLicensedThirdPartyApplicationResourceBundleEntryOverridesCount(String organizationLicenseId, String name) throws DomainException {
        Integer result = null;

        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("organizationLicenseId is null");
            throw new DomainException("error.missing.argument.exception", "organizationLicenseId");
        }

        try {
            result = resourceBundleEntryOverrideDAO.searchForLicensedThirdPartyApplicationResourceBundleEntryOverridesCount(organizationLicenseId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public ResourceBundleEntryOverride deleteResourceBundleEntryOverride(String resourceBundleOverrideId) throws PersistenceException, DomainException {
        ResourceBundleEntryOverride result = null;

        if (StringUtils.isBlank(resourceBundleOverrideId)) {
            log.error("resourceBundleOverrideId is null");
            throw new DomainException("error.missing.argument.exception", "resourceBundleOverrideId");
        }

        try {
            ResourceBundleEntryOverride aco = resourceBundleEntryOverrideDAO.get(ResourceBundleEntryOverride.class, resourceBundleOverrideId);

            if (aco != null) {
                resourceBundleEntryOverrideDAO.delete(aco);
            } else {
                log.error("Cannot delete application resource bundle entry override with id: " + resourceBundleOverrideId);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public ResourceBundleEntryOverride saveOrUpdateResourceBundleEntryOverride(String organizationLicenseId, ResourceBundleEntryOverride override) throws DomainException {
        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("organizationLicenseId is null");
            throw new DomainException("error.missing.argument.exception", "organizationLicenseId");
        }
        if (override == null) {
            throw new DomainException("error.missing.argument.exception", "ac");
        }

        try {
            if (StringUtils.isNotBlank(override.getId())) {
                ResourceBundleEntryOverride old = resourceBundleEntryOverrideDAO.get(ResourceBundleEntryOverride.class, override.getId());
                old.setValue(override.getValue());
                old.setOriginal(override.getOriginal());
                old.setDescription(override.getDescription());
                override = resourceBundleEntryOverrideDAO.update(old);
            } else {
                OrganizationLicense organizationLicense = organizationLicenseDAO.get(OrganizationLicense.class, organizationLicenseId);

                // throw error if application does not exist
                if (organizationLicense != null) {
                    override.setOrganizationLicense(organizationLicense);
                    override = resourceBundleEntryOverrideDAO.save(override);
                }
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return override;
    }

    public ResourceBundleEntryOverride getResourceBundleEntryOverride(String resourceBundleEntryId) throws DomainException {
        ResourceBundleEntryOverride result = null;

        if (StringUtils.isBlank(resourceBundleEntryId)) {
            log.error("resourceBundleEntryId is null");
            throw new DomainException("error.missing.argument.exception", "resourceBundleEntryId");
        }

        try {
            result = resourceBundleEntryOverrideDAO.get(ResourceBundleEntryOverride.class, resourceBundleEntryId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    private List<String> getUserIdsForOrganizationMembers(String organizationId) throws DomainException {
        List<String> result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new DomainException("error.missing.argument.exception", "organizationId");
        }

        try {
            result = organizationUserDAO.getUserIdsForOrganizationMembers(organizationId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    // Spring IoC
    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private ApplicationDAO applicationDAO;

    @Autowired
    private ApplicationRoleDAO applicationRoleDAO;

    @Autowired
    private ApplicationConfigurationDAO applicationConfigurationDAO;

    @Autowired
    private ApplicationConfigurationOverrideDAO applicationConfigurationOverrideDAO;

    @Autowired
    private OrganizationLicenseDAO organizationLicenseDAO;

    @Autowired
    private OrganizationUserDAO organizationUserDAO;

    @Autowired
    private OrganizationUserRoleDAO organizationUserRoleDAO;

    @Autowired
    private LicenseDAO licenseDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private RegulationDAO regulationDAO;

    @Autowired
    private RegulationOverrideDAO regulationOverrideDAO;

    @Autowired
    private ResourceBundleEntryDAO resourceBundleEntryDAO;

    @Autowired
    private ResourceBundleEntryOverrideDAO resourceBundleEntryOverrideDAO;

    @Autowired
    private CmsService cmsService;

    @Autowired
    private RelationshipManager relationshipManager;

    @Autowired
    private ImageResizer imageResizer;

    private Integer largeIconWidth;
    private Integer mediumIconWidth;
    private Integer smallIconWidth;

    public void setLargeIconWidth(Integer largeIconWidth) {
        this.largeIconWidth = largeIconWidth;
    }

    public void setMediumIconWidth(Integer mediumIconWidth) {
        this.mediumIconWidth = mediumIconWidth;
    }

    public void setSmallIconWidth(Integer smallIconWidth) {
        this.smallIconWidth = smallIconWidth;
    }
}
