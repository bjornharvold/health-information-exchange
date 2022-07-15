package com.hxcel.globalhealth.platform;

import com.hxcel.globalhealth.platform.spec.dto.*;
import com.hxcel.globalhealth.platform.spec.PlatformException;
import com.hxcel.globalhealth.platform.spec.IKeyValuePair;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.model.enums.IconSizeCd;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationLicenseStatusCd;
import com.hxcel.globalhealth.platform.model.*;
import com.hxcel.globalhealth.platform.dto.*;
import com.hxcel.globalhealth.platform.dao.*;
import com.hxcel.globalhealth.platform.utils.KeyValuePair;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.common.spec.image.ImageResizer;
import com.hxcel.globalhealth.platform.model.Image;
import com.hxcel.globalhealth.cms.spec.CmsException;
import com.hxcel.globalhealth.cms.spec.CmsService;

import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import net.sf.dozer.util.mapping.MapperIF;

/**
 * User: Bjorn Harvold
 * Date: Jan 9, 2009
 * Time: 1:14:28 AM
 * Responsibility:
 */
public class OrganizationServiceImpl implements OrganizationService {
    private final static Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);
    private static final String ORGANIZATIONS = "/content/organizations";

    @Override
    public IOrganizationDto createOrganization() {
        return new OrganizationDto();
    }

    @Override
    public IOrganizationUserDto createOrganizationUser() {
        return new OrganizationUserDto();
    }

    /**
     * This method should be called from one place and one place only OrganizationDataCreator!!
     *
     * @param org
     * @return
     * @throws com.hxcel.globalhealth.platform.spec.PlatformException
     */
    @Override
    public IOrganizationDto insertOrganizationFromDataCreator(IOrganizationDto org) throws PlatformException {
        if (org == null) {
            log.error("Organization entity is null");
            throw new PlatformException("error.missing.argument.exception", "org");
        }

        try {
            // convert to entity
            Organization entity = (Organization) mapperIF.map(org, Organization.class);

            entity.setCountry(countryDAO.get(Country.class, entity.getCountry().getId()));

            entity = organizationDAO.save(entity);

            organizationDAO.flush();

            // convert back to dto
            org = (IOrganizationDto) mapperIF.map(org, OrganizationDto.class);

        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return org;
    }

    /**
     * Create a new organization or update an existing one
     *
     * @param org
     * @return
     * @throws com.hxcel.globalhealth.platform.spec.PlatformException
     */
    @Override
    public IOrganizationDto saveOrUpdateOrganization(IOrganizationDto org) throws PlatformException {
        if (org == null) {
            log.error("Organization entity is null");
            throw new PlatformException("error.missing.argument.exception", "org");
        }

        try {
            // convert to entity
            Organization entity = (Organization) mapperIF.map(org, Organization.class);

            // have to do a merge to avoid a NonUniqueObjectException because of the parent org
            entity = organizationDAO.merge(entity);

            organizationDAO.flush();

            // convert back to dto
            org = (IOrganizationDto) mapperIF.map(org, OrganizationDto.class);

        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return org;
    }

    /**
     * Create a new organization or update an existing one
     *
     * @param organizationId
     * @return
     * @throws PlatformException
     */
    @Override
    public IOrganizationDto addOrganizationIcon(String organizationId, String iconName, InputStream icon, IconSizeCd size) throws PlatformException {
        IOrganizationDto result = null;

        if (log.isTraceEnabled()) {
            log.trace("Adding icon to organization");
        }

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }
        if (StringUtils.isBlank(iconName)) {
            log.error("iconName is null");
            throw new PlatformException("error.missing.argument.exception", "iconName");
        }
        if (icon == null) {
            log.error("icon is null");
            throw new PlatformException("error.missing.argument.exception", "icon");
        }
        if (size == null) {
            log.error("size is null");
            throw new PlatformException("error.missing.argument.exception", "size");
        }

        try {

            Organization org = organizationDAO.get(Organization.class, organizationId);

            if (org != null) {
                if (cmsService.isAvailable()) {

                    String iconPath = ORGANIZATIONS + "/" + result.getId();
                    InputStream resize = null;
                    Image image = org.getImage();

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

                    org.setImage(image);
                    org = organizationDAO.update((Organization) result);

                    // convert back to dto here
                    result = (IOrganizationDto) mapperIF.map(org, OrganizationDto.class);
                } else {
                    log.error("CMS service is not available");
                }
            } else {
                log.error("Can't find organization with id: " + organizationId);
            }

        } catch (CmsException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Sets the organization to inactive
     *
     * @param organizationId
     * @throws PlatformException
     */
    @Override
    public IOrganizationDto deactivateOrganization(String organizationId) throws PlatformException {
        IOrganizationDto result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organization id is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }

        try {
            Organization organization = organizationDAO.get(Organization.class, organizationId);

            if (organization != null) {
                organization.setOrganizationStatus(OrganizationStatusCd.INACTIVE);
                organizationDAO.update(organization);

                result = (IOrganizationDto) mapperIF.map(organization, OrganizationDto.class);
            } else {
                log.error("Could not find organization with id: " + organizationId);
            }
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Add user to organization.
     *
     * @param orgId
     * @param userId
     * @throws PlatformException
     */
    @Override
    public IOrganizationUserDto registerUserWithOrganization(String orgId, String userId) throws PlatformException {
        IOrganizationUserDto result = null;

        if (StringUtils.isBlank(orgId)) {
            log.error("Organization id is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }
        if (StringUtils.isBlank(userId)) {
            log.error("User id is null");
            throw new PlatformException("error.missing.argument.exception", "userId");
        }

        try {

            OrganizationUser ou = organizationUserDAO.getOrganizationUser(orgId, userId);

            if (ou == null) {
                Organization org = organizationDAO.get(Organization.class, orgId);
                User u = userDAO.get(User.class, userId);

                ou = organizationUserDAO.save(new OrganizationUser(org, u));

                if (ou != null) {
                    // convert to dto
                    result = (IOrganizationUserDto) mapperIF.map(ou, OrganizationUserDto.class);
                }
            } else {
                log.info("User is already a member of this organization");
            }

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Retrieve organization by id
     *
     * @param organizationId
     * @return
     * @throws PlatformException
     */
    @Override
    public IOrganizationDto getOrganization(String organizationId) throws PlatformException {
        IOrganizationDto result = null;

        if (log.isTraceEnabled()) {
            log.trace("Retrieving organization by id: " + organizationId);
        }

        Organization organization = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("Organization organizationId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }

        try {
            organization = organizationDAO.get(Organization.class, organizationId);

            if (organization != null) {
                // convert to dto here
                result = (IOrganizationDto) mapperIF.map(organization, OrganizationDto.class);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IOrganizationUserDto deleteOrganizationUser(String organizationUserId) throws PlatformException {
        IOrganizationUserDto result = null;

        if (StringUtils.isBlank(organizationUserId)) {
            log.error("organizationUserId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationUserId");
        }

        try {
            OrganizationUser ou = organizationUserDAO.get(OrganizationUser.class, organizationUserId);

            if (result != null) {
                // first we have to delete any outstanding organization user role entities
                List<OrganizationUserRole> list = organizationUserRoleDAO.searchForOrganizationUserRoles(organizationUserId, null, null, null);
                organizationUserRoleDAO.deleteAll(list);

                // convert to dto here
                result = (IOrganizationUserDto) mapperIF.map(ou, OrganizationUserDto.class);

                // now we can delete the org member
                organizationUserDAO.delete(ou);
            }

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Add application to organization
     *
     * @param orgId
     * @param licenseId
     * @throws PlatformException
     */
    @Override
    public IOrganizationLicenseDto saveOrUpdateOrganizationLicense(String orgId, String licenseId) throws PlatformException {
        IOrganizationLicenseDto result = null;

        if (StringUtils.isBlank(orgId)) {
            log.error("organizationId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }
        if (licenseId == null) {
            log.error("licenseId is null");
            throw new PlatformException("error.missing.argument.exception", "licenseId");
        }

        try {
            OrganizationLicense ol = organizationLicenseDAO.getOrganizationLicense(orgId, licenseId);

            if (ol == null) {
                Organization org = organizationDAO.get(Organization.class, orgId);
                License lic = licenseDAO.get(License.class, licenseId);

                ol = organizationLicenseDAO.save(new OrganizationLicense(org, lic, OrganizationLicenseStatusCd.ACTIVE));

                // convert to dto here
                result = (IOrganizationLicenseDto) mapperIF.map(ol, OrganizationLicenseDto.class);
            } else {
                if (log.isInfoEnabled()) {
                    log.info("Organization has already licensed this application");
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IOrganizationLicenseDto unlicenseThirdPartyApplication(String organizationLicenseId) throws PlatformException {
        IOrganizationLicenseDto result = null;

        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("OrganizationLicenseId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationLicenseId");
        }

        try {
            OrganizationLicense ol = organizationLicenseDAO.get(OrganizationLicense.class, organizationLicenseId);

            if (ol != null) {
                // first we have to delete the application configuration overrides that exist for this license
                List<ApplicationConfigurationOverride> overrides = applicationConfigurationOverrideDAO.searchForLicensedThirdPartyApplicationConfigurationOverrides(organizationLicenseId, null, null, null);
                applicationConfigurationOverrideDAO.deleteAll(overrides);

                // convert to dto here
                result = (IOrganizationLicenseDto) mapperIF.map(ol, OrganizationLicenseDto.class);

                // now we can delete the license
                organizationLicenseDAO.delete(ol);
            } else {
                if (log.isInfoEnabled()) {
                    log.info("Cannot unlicense application from organization. Id doesn't exist: " + organizationLicenseId);
                }
            }

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Retrieves organizations by name
     *
     * @param name
     * @param index
     * @param maxResult
     * @return
     * @throws PlatformException
     */
    @Override
    public List<IOrganizationDto> searchForOrganizations(String name, Integer index, Integer maxResult) throws PlatformException {
        List<IOrganizationDto> result = null;

        try {
            List<Organization> list = organizationDAO.searchForOrganizations(name, index, maxResult);

            // convert to dto here
            if (list != null) {
                result = new ArrayList<IOrganizationDto>(list.size());

                for (Organization organization : list) {
                    result.add((IOrganizationDto) mapperIF.map(organization, OrganizationDto.class));
                }
            }
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IKeyValuePair> getOrganizationThinList() throws PlatformException {
        List<IKeyValuePair> result = null;

        try {
            List<KeyValuePair> list = organizationDAO.getOrganizationThinList();

            if (list != null) {
                result = new ArrayList<IKeyValuePair>();

                for (KeyValuePair kv : list) {
                    result.add(kv);
                }
            }
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchForOrganizationsCount(String name) throws PlatformException {
        Integer result;

        try {
            result = organizationDAO.searchForOrganizationsCount(name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IOrganizationLicenseDto> licenseApplicationsForOrganization(String organizationId, List<String> licenseIds) throws PlatformException {
        List<IOrganizationLicenseDto> result = null;

        if (licenseIds != null) {
            result = new ArrayList<IOrganizationLicenseDto>();
            for (String licenseId : licenseIds) {
                result.add(saveOrUpdateOrganizationLicense(organizationId, licenseId));
            }
        }

        return result;
    }

    @Override
    public List<IOrganizationUserDto> registerUsersWithOrganization(String organizationId, List<String> userIds) throws PlatformException {
        List<IOrganizationUserDto> result = null;

        if (userIds != null) {
            result = new ArrayList<IOrganizationUserDto>();

            for (String userId : userIds) {
                result.add(registerUserWithOrganization(organizationId, userId));
            }
        }

        return result;
    }

    @Override
    public List<IOrganizationUserDto> searchForOrganizationUsers(String organizationId, String name, Integer index, Integer maxResults) throws PlatformException {
        List<IOrganizationUserDto> result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }

        try {
            List<OrganizationUser> list = organizationUserDAO.searchForOrganizationUsers(organizationId, name, index, maxResults);

            if (list != null) {
                result = new ArrayList<IOrganizationUserDto>();

                for (OrganizationUser ou : list) {
                    result.add((IOrganizationUserDto) mapperIF.map(ou, OrganizationUserDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchOrganizationUsersCount(String organizationId, String name) throws PlatformException {
        Integer result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }

        try {
            result = organizationUserDAO.searchForOrganizationUsersCount(organizationId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IOrganizationLicenseDto> searchForLicensedThirdPartyApplications(String organizationId, String name, Boolean isPlatform, OrganizationLicenseStatusCd status, Integer index, Integer maxResults) throws PlatformException {
        List<IOrganizationLicenseDto> result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }
        if (status == null) {
            log.error("status is null");
            throw new PlatformException("error.missing.argument.exception", "status");
        }

        try {
            List<OrganizationLicense> list = organizationLicenseDAO.searchForOrganizationLicenses(organizationId, name, isPlatform, status, index, maxResults);

            if (list != null) {
                result = new ArrayList<IOrganizationLicenseDto>();

                for (OrganizationLicense ol : list) {
                    result.add((IOrganizationLicenseDto) mapperIF.map(ol, OrganizationLicenseDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchLicensedThirdPartyApplicationsCount(String organizationId, String name, Boolean isPlatform, OrganizationLicenseStatusCd status) throws PlatformException {
        Integer result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }
        if (status == null) {
            log.error("status is null");
            throw new PlatformException("error.missing.argument.exception", "status");
        }

        try {
            result = organizationLicenseDAO.searchForOrganizationLicensesCount(organizationId, name, isPlatform, status);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IOrganizationUserRoleDto> searchForOrganizationUserRoles(String organizationUserId, String name, Integer index, Integer maxResults) throws PlatformException {
        List<IOrganizationUserRoleDto> result = null;

        if (StringUtils.isBlank(organizationUserId)) {
            log.error("organizationUserId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationUserId");
        }

        try {
            List<OrganizationUserRole> list = organizationUserRoleDAO.searchForOrganizationUserRoles(organizationUserId, name, index, maxResults);

            if (list != null) {
                result = new ArrayList<IOrganizationUserRoleDto>();

                for (OrganizationUserRole our : list) {
                    result.add((IOrganizationUserRoleDto) mapperIF.map(our, OrganizationUserRoleDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchOrganizationUserRolesCount(String organizationUserId, String name) throws PlatformException {
        Integer result = null;

        if (StringUtils.isBlank(organizationUserId)) {
            log.error("organizationUserId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationUserId");
        }

        try {
            result = organizationUserRoleDAO.searchForOrganizationUserRolesCount(organizationUserId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IOrganizationUserDto getOrganizationUser(String organizationUserId) throws PlatformException {
        IOrganizationUserDto result = null;

        if (StringUtils.isBlank(organizationUserId)) {
            log.error("organizationUserId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationUserId");
        }

        try {
            OrganizationUser ou = organizationUserDAO.get(OrganizationUser.class, organizationUserId);

            if (ou != null) {
                result = (IOrganizationUserDto) mapperIF.map(ou, OrganizationUserDto.class);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IOrganizationDto> getLastModifiedOrganizations(Integer maxResults) throws PlatformException {
        List<IOrganizationDto> result = null;

        try {
            List<Organization> list = organizationDAO.getLastModifiedOrganizations(maxResults);

            if (list != null) {
                result = new ArrayList<IOrganizationDto>();

                for (Organization org : list) {
                    result.add((IOrganizationDto) mapperIF.map(org, OrganizationDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IOrganizationUserRoleDto> saveOrUpdateOrganizationUserRoles(String organizationUserId, List<String> roleIds) throws PlatformException {
        List<IOrganizationUserRoleDto> result = null;
        boolean duplicate = false;

        if (StringUtils.isBlank(organizationUserId)) {
            log.error("organizationUserId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationUserId");
        }

        try {
            OrganizationUser ou = organizationUserDAO.get(OrganizationUser.class, organizationUserId);

            if (ou != null) {
                if (roleIds != null) {
                    result = new ArrayList<IOrganizationUserRoleDto>();

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

                                // convert to dto here
                                result.add((IOrganizationUserRoleDto) mapperIF.map(usr, OrganizationUserRoleDto.class));
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
            throw new PlatformException(e.getMessage(), e);
        }


        return result;
    }

    @Override
    public IOrganizationUserRoleDto deleteOrganizationUserRole(String organizationUserRoleId) throws PlatformException {
        IOrganizationUserRoleDto result = null;

        if (StringUtils.isBlank(organizationUserRoleId)) {
            log.error("organizationUserRoleId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationUserRoleId");
        }

        try {
            OrganizationUserRole our = organizationUserRoleDAO.get(OrganizationUserRole.class, organizationUserRoleId);

            if (our != null) {
                // convert to dto here
                result = (IOrganizationUserRoleDto) mapperIF.map(our, OrganizationUserDto.class);

                // delete entity now
                organizationUserRoleDAO.delete(our);
            } else {
                log.error("Cannot find organization user role with id: " + organizationUserRoleId);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IOrganizationUserDto saveOrUpdateOrganizationUser(IOrganizationUserDto organizationUser) throws PlatformException {
        if (organizationUser == null) {
            log.error("organizationUser is null");
            throw new PlatformException("error.missing.argument.exception", "organizationUser");
        }

        try {
            // convert to entity first
            OrganizationUser ou = (OrganizationUser) mapperIF.map(organizationUser, OrganizationUser.class);

            // update
            ou = organizationUserDAO.saveOrUpdate(ou);

            // convert to dto here
            organizationUser = (IOrganizationUserDto) mapperIF.map(ou, OrganizationUserDto.class);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return organizationUser;
    }

    @Override
    public List<IUserDto> searchForNewOrganizationMembers(String organizationId, String name, Integer index, Integer maxResults) throws PlatformException {
        List<IUserDto> result = null;
        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }

        try {
            List<String> excludedUserIds = getUserIdsForOrganizationMembers(organizationId);

            List<User> list = userDAO.searchForUsersWithExcludes(excludedUserIds, name, index, maxResults);

            if (list != null) {
                result = new ArrayList<IUserDto>(list.size());

                for (User user : list) {
                    result.add((IUserDto) mapperIF.map(user, UserDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchForNewOrganizationMembersCount(String organizationId, String name) throws PlatformException {
        Integer result = null;
        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }

        try {
            List<String> excludedUserIds = getUserIdsForOrganizationMembers(organizationId);

            result = userDAO.searchForUsersWithExcludesCount(excludedUserIds, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    private List<String> getUserIdsForOrganizationMembers(String organizationId) throws PlatformException {
        List<String> result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }

        try {
            result = organizationUserDAO.getUserIdsForOrganizationMembers(organizationId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    // Spring IoC
    @Autowired
    private MapperIF mapperIF;

    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private OrganizationUserDAO organizationUserDAO;

    @Autowired
    private OrganizationUserRoleDAO organizationUserRoleDAO;

    @Autowired
    private OrganizationLicenseDAO organizationLicenseDAO;

    @Autowired
    private ApplicationConfigurationOverrideDAO applicationConfigurationOverrideDAO;

    @Autowired
    private LicenseDAO licenseDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private ImageResizer imageResizer;

    @Autowired
    private CmsService cmsService;

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
