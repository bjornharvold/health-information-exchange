package com.hxcel.globalhealth.platform;

import com.hxcel.globalhealth.platform.spec.dto.*;
import com.hxcel.globalhealth.platform.spec.PlatformException;
import com.hxcel.globalhealth.platform.spec.ApplicationService;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.LicenseStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.IconSizeCd;
import com.hxcel.globalhealth.platform.model.*;
import com.hxcel.globalhealth.platform.dto.*;
import com.hxcel.globalhealth.platform.dao.*;
import com.hxcel.globalhealth.platform.utils.KeyValuePair;
import com.hxcel.globalhealth.platform.utils.StringParser;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.model.Image;
import com.hxcel.globalhealth.platform.model.Country;
import com.hxcel.globalhealth.common.spec.image.ImageResizer;
import com.hxcel.globalhealth.common.locale.LocaleUtils;
import com.hxcel.globalhealth.platform.dao.CountryDAO;
import com.hxcel.globalhealth.cms.spec.CmsException;
import com.hxcel.globalhealth.cms.spec.CmsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import net.sf.dozer.util.mapping.MapperIF;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.io.InputStream;
import java.io.IOException;

/**
 * User: Bjorn Harvold
 * Date: Jan 9, 2009
 * Time: 1:19:50 AM
 * Responsibility:
 */
public class ApplicationServiceImpl implements ApplicationService {
    private final static Logger log = LoggerFactory.getLogger(ApplicationServiceImpl.class);
    private static final String APPLICATIONS = "/content/applications";

    @Override
    public IApplicationConfigurationDto createApplicationConfiguration() {
        return new ApplicationConfigurationDto();
    }

    @Override
    public ILicenseDto createLicense() {
        return new LicenseDto();
    }

    @Override
    public IResourceBundleEntryDto createResourceBundleEntry() {
        return new ResourceBundleEntryDto();
    }

    @Override
    public IApplicationConfigurationOverrideDto createApplicationConfigurationOverride() {
        return new ApplicationConfigurationOverrideDto();
    }

    @Override
    public IResourceBundleEntryOverrideDto createResourceBundleEntryOverride() {
        return new ResourceBundleEntryOverrideDto();
    }

    @Override
    public IApplicationDto createApplication() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Create a new application or update an existing one
     *
     * @param app
     * @return
     * @throws com.hxcel.globalhealth.platform.spec.PlatformException
     *
     */
    @Override
    public IApplicationDto saveOrUpdateApplication(IApplicationDto app) throws PlatformException {
        if (app == null) {
            log.error("Application dto is null");
            throw new PlatformException("error.missing.argument.exception", "app");
        }

        try {
            // convert to entity here
            Application application = (Application) mapperIF.map(app, Application.class);

            if (StringUtils.isBlank(app.getId())) {
                // creating an application for the first time adds it to the system
                // in an unpublished state.
                application.setApplicationStatus(ApplicationStatusCd.UNPUBLISHED);

                application = applicationDAO.save(application);
            } else {
                application = applicationDAO.update(application);
            }
            applicationDAO.flush();

            // convert back to dto here
            app = (IApplicationDto) mapperIF.map(application, ApplicationDto.class);
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return app;
    }

    @Override
    public IApplicationDto deactivateApplication(String applicationId) throws PlatformException {
        IApplicationDto result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("application id is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }

        try {
            Application app = applicationDAO.get(Application.class, applicationId);

            if (app != null) {
                app.setApplicationStatus(ApplicationStatusCd.UNPUBLISHED);
                applicationDAO.update(app);

                // convert to dto here
                result = (IApplicationDto) mapperIF.map(app, ApplicationDto.class);
            } else {
                log.error("Could not find application with id: " + applicationId);
            }

        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public ILicenseDto addApplicationLicense(String applicationId, ILicenseDto license) throws PlatformException {
        ILicenseDto result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }
        if (license == null) {
            log.error("License is null");
            throw new PlatformException("error.missing.argument.exception", "license");
        }

        try {
            // convert to entity here
            License lic = (License) mapperIF.map(license, License.class);

            if (StringUtils.isBlank(license.getId())) {
                Application app = applicationDAO.get(Application.class, applicationId);

                if (app != null) {
                    if (log.isTraceEnabled()) {
                        log.trace("creating new license for application with id: " + applicationId);
                    }

                    lic.setApplication(app);
                    lic = licenseDAO.save(lic);

                } else {
                    if (log.isTraceEnabled()) {
                        log.trace("updating existing license for application with id: " + applicationId + " and license id: " + license.getId());
                    }
                    lic = licenseDAO.update(lic);
                }
            }

            // convert to dto here
            result = (ILicenseDto) mapperIF.map(lic, LicenseDto.class);
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public ILicenseDto getLicense(String licenseId) throws PlatformException, PersistenceException {
        ILicenseDto result = null;

        if (StringUtils.isBlank(licenseId)) {
            log.error("License id is null");
            throw new PlatformException("error.missing.argument.exception", "licenseId");
        }

        License lic = licenseDAO.get(License.class, licenseId);

        if (lic != null) {
            // convert to dto here
            result = (ILicenseDto) mapperIF.map(lic, LicenseDto.class);
        }

        return result;
    }

    @Override
    public ILicenseDto updateLicense(ILicenseDto license) throws PlatformException {

        if (license == null) {
            log.error("License is null");
            throw new PlatformException("error.missing.argument.exception", "license");
        }
        if (StringUtils.isBlank(license.getId())) {
            throw new PlatformException("error.missing.argument.exception", "licenseId");
        }

        try {
            // convert to entity here
            License lic = (License) mapperIF.map(license, License.class);
            lic = licenseDAO.update(lic);

            if (lic != null) {
                // convert to dto here
                license = (ILicenseDto) mapperIF.map(lic, LicenseDto.class);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return license;
    }

    @Override
    public List<IApplicationDto> searchApplicationsOwnedByOrganization(String organizationId, String name, Boolean platform, Integer index, Integer maxResults) throws PlatformException {
        List<IApplicationDto> result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("Organization organizationId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }

        try {
            List<Application> list = applicationDAO.searchForApplicationsByOwner(organizationId, name, platform, index, maxResults);

            if (list != null) {
                // convert to dto here
                result = new ArrayList<IApplicationDto>(list.size());

                for (Application application : list) {
                    result.add((IApplicationDto) mapperIF.map(application, ApplicationDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
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
     * @throws PlatformException
     */
    @Override
    public List<IApplicationDto> searchForApplications(String name, Boolean isPlatform, Integer index, Integer maxResult) throws PlatformException {
        List<IApplicationDto> result = null;

        if (isPlatform == null) {
            log.error("isPlatform is null");
            throw new PlatformException("error.missing.argument.exception", "isPlatform");
        }

        try {
            List<Application> list = applicationDAO.searchForApplications(name, isPlatform, index, maxResult);

            if (list != null) {
                result = new ArrayList<IApplicationDto>(list.size());

                for (Application application : list) {
                    result.add((IApplicationDto) mapperIF.map(application, ApplicationDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchForApplicationsCount(String name, Boolean isPlatform) throws PlatformException {
        Integer result;

        if (isPlatform == null) {
            log.error("isPlatform is null");
            throw new PlatformException("error.missing.argument.exception", "isPlatform");
        }

        try {
            result = applicationDAO.searchForApplicationsCount(name, isPlatform);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Retrieve application by id
     *
     * @param applicationId
     * @return
     * @throws PlatformException
     */
    @Override
    public IApplicationDto getApplication(String applicationId) throws PlatformException {
        IApplicationDto result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }

        try {
            Application application = applicationDAO.get(Application.class, applicationId);

            if (application != null) {
                // convert to dto here
                result = (IApplicationDto) mapperIF.map(application, ApplicationDto.class);
            }
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IApplicationConfigurationDto getApplicationConfiguration(String applicationConfigurationId) throws PlatformException {
        IApplicationConfigurationDto result = null;

        if (StringUtils.isBlank(applicationConfigurationId)) {
            log.error("applicationConfigurationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationConfigurationId");
        }

        try {
            ApplicationConfiguration ac = applicationConfigurationDAO.get(ApplicationConfiguration.class, applicationConfigurationId);

            if (ac != null) {
                // convert to dto here
                result = (IApplicationConfigurationDto) mapperIF.map(ac, ApplicationConfigurationDto.class);
            }
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IApplicationConfigurationDto addApplicationConfiguration(String applicationId, IApplicationConfigurationDto acDto) throws PlatformException {
        IApplicationConfigurationDto result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }
        if (acDto == null) {
            throw new PlatformException("error.missing.argument.exception", "ac");
        }

        try {
            // convert to entity here
            ApplicationConfiguration ac = (ApplicationConfiguration) mapperIF.map(acDto, OrganizationDto.class);

            if (StringUtils.isBlank(ac.getId())) {
                Application app = applicationDAO.get(Application.class, applicationId);

                // throw error if application does not exist
                if (app != null) {
                    ac.setApplication(app);

                    log.info("Creating new configuration entity for applicationId: " + applicationId);
                    ac = applicationConfigurationDAO.save(ac);
                }
            } else {
                log.info("Updating existing configuration entity for applicationId: " + applicationId);
                ac = applicationConfigurationDAO.update(ac);
            }

            // convert to dto here
            result = (IApplicationConfigurationDto) mapperIF.map(ac, ApplicationConfigurationDto.class);

        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IApplicationConfigurationDto updateApplicationConfiguration(IApplicationConfigurationDto acDto) throws PlatformException {
        IApplicationConfigurationDto result = null;

        if (acDto == null) {
            throw new PlatformException("error.missing.argument.exception", "ac");
        }
        if (StringUtils.isBlank(acDto.getId())) {
            throw new PlatformException("error.missing.argument.exception", "ac Id");
        }

        try {
            log.info("Updating existing configuration entity for applicationId: " + acDto.getApplication().getId());

            // convert to entity
            ApplicationConfiguration ac = (ApplicationConfiguration) mapperIF.map(acDto, ApplicationConfiguration.class);
            ac = applicationConfigurationDAO.update(ac);

            // convert back to dto here
            result = (IApplicationConfigurationDto) mapperIF.map(ac, ApplicationConfigurationDto.class);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IApplicationConfigurationDto deleteApplicationConfiguration(String applicationConfigurationId) throws PlatformException {
        IApplicationConfigurationDto result = null;

        if (StringUtils.isBlank(applicationConfigurationId)) {
            log.error("applicationConfigurationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationConfigurationId");
        }

        try {
            log.info("Deleting configuration entity with it: " + applicationConfigurationId);
            ApplicationConfiguration ac = applicationConfigurationDAO.get(ApplicationConfiguration.class, applicationConfigurationId);

            if (ac != null) {
                // first we need to delete all the overrides that are associated with this configuration
                List<ApplicationConfigurationOverride> list = applicationConfigurationOverrideDAO.getApplicationConfigurationOverrideByApplicationConfigurationId(applicationConfigurationId);

                applicationConfigurationOverrideDAO.deleteAll(list);

                // convert to dto here
                result = (IApplicationConfigurationDto) mapperIF.map(ac, ApplicationConfigurationDto.class);

                // now we are safe to delete the entity
                applicationConfigurationDAO.delete(ac);
            } else {
                log.error("Can't find application configuration with id: " + applicationConfigurationId);
            }

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public ILicenseDto deleteApplicationLicense(String applicationId, String licenseId) throws PlatformException {
        ILicenseDto result = null;

        if (StringUtils.isBlank(licenseId)) {
            log.error("licenseId is null");
            throw new PlatformException("error.missing.argument.exception", "licenseId");
        }
        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }

        try {
            License lic = licenseDAO.get(License.class, licenseId);

            if (lic != null) {
                log.info("Deleting license with id: " + licenseId + " for application with id: " + applicationId);
                result.setStatus(LicenseStatusCd.INVALID);
                lic = licenseDAO.update(lic);

                // convert to dto here
                result = (ILicenseDto) mapperIF.map(lic, LicenseDto.class);
            } else {
                log.error("Can't delete license with id: " + licenseId);
            }

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IApplicationConfigurationOverrideDto getApplicationConfigurationOverride(String organizationConfigurationId) throws PlatformException {
        IApplicationConfigurationOverrideDto result = null;

        if (StringUtils.isBlank(organizationConfigurationId)) {
            log.error("organizationConfigurationId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationConfigurationId");
        }

        try {
            ApplicationConfigurationOverride aco = applicationConfigurationOverrideDAO.get(ApplicationConfigurationOverride.class, organizationConfigurationId);

            // convert to dto here
            result = (IApplicationConfigurationOverrideDto) mapperIF.map(aco, ApplicationConfigurationOverrideDto.class);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IApplicationConfigurationOverrideDto addApplicationConfigurationOverride(String organizationLicenseId, IApplicationConfigurationOverrideDto override) throws PlatformException {

        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("organizationLicenseId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationLicenseId");
        }
        if (override == null) {
            throw new PlatformException("error.missing.argument.exception", "ac");
        }

        try {
            // convert to entity here
            ApplicationConfigurationOverride aco = (ApplicationConfigurationOverride) mapperIF.map(override, ApplicationConfigurationOverride.class);

            if (StringUtils.isNotBlank(aco.getId())) {
                aco = applicationConfigurationOverrideDAO.update(aco);
            } else {
                OrganizationLicense organizationLicense = organizationLicenseDAO.get(OrganizationLicense.class, organizationLicenseId);

                // throw error if application does not exist
                if (organizationLicense != null) {
                    aco.setOrganizationLicense(organizationLicense);
                    aco = applicationConfigurationOverrideDAO.save(aco);
                }
            }

            // convert back to dto here
            override = (IApplicationConfigurationOverrideDto) mapperIF.map(aco, ApplicationConfigurationOverrideDto.class);
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return override;
    }

    @Override
    public IApplicationConfigurationOverrideDto updateApplicationConfigurationOverride(IApplicationConfigurationOverrideDto override) throws PlatformException {
        if (override == null) {
            throw new PlatformException("error.missing.argument.exception", "oc");
        }
        if (StringUtils.isBlank(override.getId())) {
            throw new PlatformException("error.missing.argument.exception", "organizationConfigurationId");
        }

        try {
            // convert to entity here
            ApplicationConfigurationOverride aco = (ApplicationConfigurationOverride) mapperIF.map(override, ApplicationConfigurationOverride.class);

            aco = applicationConfigurationOverrideDAO.update(aco);

            // convert back to dto here
            override = (IApplicationConfigurationOverrideDto) mapperIF.map(aco, ApplicationConfigurationOverrideDto.class);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return override;
    }

    @Override
    public IApplicationDto addApplicationIcon(String applicationId, String iconName, InputStream icon, IconSizeCd size) throws PlatformException {
        IApplicationDto result = null;

        try {

            Application app = applicationDAO.get(Application.class, applicationId);

            if (app != null) {
                if (cmsService.isAvailable()) {

                    String iconPath = APPLICATIONS + "/" + result.getId() + "/";
                    InputStream resize = null;
                    Image image = app.getImage();

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

                    app.setImage(image);
                    app = applicationDAO.update(app);

                    // convert to dto here
                    result = (IApplicationDto) mapperIF.map(app, ApplicationDto.class);
                } else {
                    log.error("CMS service is not available");
                }
            } else {
                log.error("Can't find application with id: " + applicationId);
            }

        } catch (CmsException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IApplicationDto addApplicationSwf(String applicationId, String swfName, InputStream swf) throws PlatformException {
        IApplicationDto result = null;

        try {

            Application app = applicationDAO.get(Application.class, applicationId);

            if (app != null) {
                if (cmsService.isAvailable()) {

                    String swfPath = APPLICATIONS + "/" + result.getId();

                    if (log.isTraceEnabled()) {
                        log.trace("Adding new SWF file to application");
                    }
                    swfPath = cmsService.upload(swfPath, swfName, swf);


                    if (log.isTraceEnabled()) {
                        log.trace("New SWF uploaded with path: " + swfPath);
                    }

                    app.setSwfUrl(swfPath);
                    app = applicationDAO.update(app);
                } else {
                    log.error("CMS service is not available");
                }
            } else {
                log.error("Can't find application with id: " + applicationId);
            }

        } catch (CmsException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IApplicationConfigurationOverrideDto deleteApplicationConfigurationOverride(String applicationConfigurationOverrideId) throws PlatformException {
        IApplicationConfigurationOverrideDto result = null;

        if (StringUtils.isBlank(applicationConfigurationOverrideId)) {
            log.error("applicationConfigurationOverrideId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationConfigurationOverrideId");
        }

        try {
            ApplicationConfigurationOverride aco = applicationConfigurationOverrideDAO.get(ApplicationConfigurationOverride.class, applicationConfigurationOverrideId);

            // convert to dto here
            result = (IApplicationConfigurationOverrideDto) mapperIF.map(aco, ApplicationConfigurationOverrideDto.class);

            if (aco != null) {
                applicationConfigurationOverrideDAO.delete(aco);
            } else {
                log.error("Cannot delete application configuration override with id: " + applicationConfigurationOverrideId);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IApplicationRoleDto> addApplicationRoles(String applicationId, List<String> roles) throws PlatformException {
        List<IApplicationRoleDto> result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }

        try {
            Application app = applicationDAO.get(Application.class, applicationId);

            if (app != null) {
                if (roles != null) {
                    result = new ArrayList<IApplicationRoleDto>();
                    for (String roleId : roles) {
                        result.add(addApplicationRole(applicationId, roleId));
                    }
                }
            } else {
                log.error("Can't find application with id: " + applicationId);
            }

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IApplicationRoleDto addApplicationRole(String applicationId, String roleId) throws PlatformException {
        IApplicationRoleDto result = null;

        try {
            Application app = applicationDAO.get(Application.class, applicationId);
            ApplicationRole ar = applicationRoleDAO.getApplicationRoleByApplicationIdAndRoleId(applicationId, roleId);

            if (ar == null) {
                Role role = roleDAO.get(Role.class, roleId);

                if (role != null) {
                    log.info("Adding new role to application id: " + applicationId + ". Role id: " + roleId);
                    ar = applicationRoleDAO.save(new ApplicationRole(app, role));

                    // convert to dto here
                    result = (IApplicationRoleDto) mapperIF.map(ar, ApplicationRoleDto.class);
                } else {
                    log.error("Can't find role with id: " + roleId);
                }
            } else {
                log.info("Role with id: " + roleId + " already exists for applicationwith id: " + applicationId);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IApplicationRoleDto deleteApplicationRole(String applicationRoleId) throws PlatformException {
        IApplicationRoleDto result = null;

        if (StringUtils.isBlank(applicationRoleId)) {
            log.error("applicationRoleId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationRoleId");
        }

        try {
            ApplicationRole ar = applicationRoleDAO.get(ApplicationRole.class, applicationRoleId);

            if (ar != null) {
                // convert to dto here
                result = (IApplicationRoleDto) mapperIF.map(ar, ApplicationRoleDto.class);

                applicationRoleDAO.delete(ar);
            } else {
                log.error("No application role exists for id: " + applicationRoleId);
            }


        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<ILicenseDto> getLicensedApplicationsExcludeOwner(String organizationId, Boolean platform) throws PlatformException {
        List<ILicenseDto> result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }

        try {
            List<License> list = licenseDAO.getLicensedApplicationsExcludeOwner(organizationId, platform);

            if (list != null) {
                result = new ArrayList<ILicenseDto>(list.size());

                for (License license : list) {
                    result.add((ILicenseDto) mapperIF.map(license, LicenseDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IApplicationConfigurationOverrideDto> searchForLicensedThirdPartyApplicationConfigurationOverrides(String organizationLicenseId, String name, Integer index, Integer maxResults) throws PlatformException {
        List<IApplicationConfigurationOverrideDto> result = null;

        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("organizationLicenseId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationLicenseId");
        }

        try {
            List<ApplicationConfigurationOverride> list = applicationConfigurationOverrideDAO.searchForLicensedThirdPartyApplicationConfigurationOverrides(organizationLicenseId, name, index, maxResults);

            if (list != null) {
                result = new ArrayList<IApplicationConfigurationOverrideDto>();

                for (ApplicationConfigurationOverride aco : list) {
                    result.add((IApplicationConfigurationOverrideDto) mapperIF.map(aco, ApplicationConfigurationOverrideDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchForLicensedThirdPartyApplicationConfigurationOverridesCount(String organizationLicenseId, String name) throws PlatformException {
        Integer result = null;

        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("organizationLicenseId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationLicenseId");
        }

        try {
            result = applicationConfigurationOverrideDAO.searchForLicensedThirdPartyApplicationConfigurationOverridesCount(organizationLicenseId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IApplicationConfigurationOverrideDto saveOrUpdateApplicationConfigurationOverride(IApplicationConfigurationOverrideDto override) throws PlatformException {
        if (override == null) {
            log.error("override is null");
            throw new PlatformException("error.missing.argument.exception", "override");
        }

        try {
            // convert to entity
            ApplicationConfigurationOverride aco = (ApplicationConfigurationOverride) mapperIF.map(override, ApplicationConfigurationOverride.class);

            // update
            aco = applicationConfigurationOverrideDAO.saveOrUpdate(aco);

            // convert back to dto
            override = (IApplicationConfigurationOverrideDto) mapperIF.map(aco, ApplicationConfigurationOverrideDto.class);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return override;
    }

    @Override
    public Integer searchApplicationsOwnedByOrganizationCount(String organizationId, String name, Boolean platform) throws PlatformException {
        Integer result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }

        try {
            result = applicationDAO.searchForApplicationsByOwnerCount(organizationId, name, platform);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<ILicenseDto> searchForApplicationLicenses(String applicationId, String name, LicenseStatusCd status, Integer index, Integer maxResults) throws PlatformException {
        List<ILicenseDto> result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }
        if (status == null) {
            log.error("status is null");
            throw new PlatformException("error.missing.argument.exception", "status");
        }

        try {
            List<License> list = licenseDAO.searchForApplicationLicenses(applicationId, name, status, index, maxResults);

            if (list != null) {
                result = new ArrayList<ILicenseDto>();

                for (License lic : list) {
                    result.add((ILicenseDto) mapperIF.map(lic, LicenseDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchForApplicationLicensesCount(String applicationId, String name, LicenseStatusCd status) throws PlatformException {
        Integer result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }
        if (status == null) {
            log.error("status is null");
            throw new PlatformException("error.missing.argument.exception", "status");
        }

        try {
            result = licenseDAO.searchForApplicationsLicensesCount(applicationId, name, status);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IApplicationRoleDto> searchForApplicationRoles(String applicationId, String name, Integer index, Integer maxResults) throws PlatformException {
        List<IApplicationRoleDto> result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }

        try {
            List<ApplicationRole> list = applicationRoleDAO.searchForApplicationRoles(applicationId, name, index, maxResults);

            if (list != null) {
                result = new ArrayList<IApplicationRoleDto>();

                for (ApplicationRole ar : list) {
                    result.add((IApplicationRoleDto) mapperIF.map(ar, ApplicationRoleDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchForApplicationRolesCount(String applicationId, String name) throws PlatformException {
        Integer result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }

        try {
            result = applicationRoleDAO.searchForApplicationsRolesCount(applicationId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IApplicationConfigurationDto> searchForApplicationConfigurations(String applicationId, String name, Integer index, Integer maxResults) throws PlatformException {
        List<IApplicationConfigurationDto> result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }

        try {
            List<ApplicationConfiguration> list = applicationConfigurationDAO.searchForApplicationConfigurations(applicationId, name, index, maxResults);

            if (list != null) {
                result = new ArrayList<IApplicationConfigurationDto>();

                for (ApplicationConfiguration ac : list) {
                    result.add((IApplicationConfigurationDto) mapperIF.map(ac, ApplicationConfigurationDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchForApplicationConfigurationsCount(String applicationId, String name) throws PlatformException {
        Integer result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }

        try {
            result = applicationConfigurationDAO.searchForApplicationsConfigurationsCount(applicationId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Boolean isApplicationPublishable(String applicationId) throws PlatformException {
        Boolean result = true;

        if (applicationId == null) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }

        try {
            Application application = applicationDAO.get(Application.class, applicationId);

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
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }


        return result;
    }

    @Override
    public Boolean isApplicationUnpublishable(String applicationId) throws PlatformException {
        Boolean result = true;

        if (applicationId == null) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }

        try {
            Integer count = organizationLicenseDAO.getApplicationUsageCount(applicationId);

            if (count > 0) {
                result = false;
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public void publishApplication(String applicationId, ApplicationStatusCd status) throws PlatformException {
        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }
        if (status == null) {
            log.error("status is null");
            throw new PlatformException("error.missing.argument.exception", "status");
        }

        try {
            Application app = applicationDAO.get(Application.class, applicationId);

            if (app != null) {
                app.setApplicationStatus(status);
                applicationDAO.update(app);
            } else {
                log.error("Cannot retrieve application with id: " + applicationId);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }
    }

    @Override
    public ILaunchableApplicationDto getLaunchableApplication(String organizationId, String applicationId, boolean platform) throws PlatformException {
        LaunchableApplicationDto result = null;

        if (StringUtils.isBlank(organizationId)) {
            log.error("organizationId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationId");
        }
        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
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

                IApplicationDto dto = getApplication(app.getId());
                List<IApplicationConfigurationDto> configs = searchForApplicationConfigurations(applicationId, null, null, null);

                result = new LaunchableApplicationDto(dto, configs);

            } else {
                if (log.isInfoEnabled()) {
                    log.info("Looking through 3rd party application licenses instead.");
                }

                OrganizationLicense organizationLicense = organizationLicenseDAO.getLaunchableApplication(organizationId, applicationId, platform);

                if (organizationLicense != null) {
                    IOrganizationLicenseDto dto = getOrganizationLicense(organizationLicense.getId());
                    List<IApplicationConfigurationDto> configs = searchForApplicationConfigurations(applicationId, null, null, null);
                    List<IApplicationConfigurationOverrideDto> overrides = searchForLicensedThirdPartyApplicationConfigurationOverrides(organizationLicense.getId(), null, null, null);

                    result = new LaunchableApplicationDto(dto, configs, overrides);
                } else {
                    log.error("Could not find application. Check whether all criteria have been met to make application launch-ready");
                }

            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IOrganizationLicenseDto getOrganizationLicense(String organizationLicenseId) throws PlatformException {
        IOrganizationLicenseDto result = null;

        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("organizationLicenseId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationLicenseId");
        }

        try {
            OrganizationLicense ol = organizationLicenseDAO.get(OrganizationLicense.class, organizationLicenseId);

            if (ol != null) {
                result = (IOrganizationLicenseDto) mapperIF.map(ol, OrganizationLicenseDto.class);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IApplicationDto> getLastModifiedApplications(Boolean platform, Integer maxResults) throws PlatformException {
        List<IApplicationDto> result = null;

        try {
            List<Application> list = applicationDAO.getLastModifiedApplications(platform, maxResults);

            if (list != null) {
                result = new ArrayList<IApplicationDto>();

                for (Application app : list) {
                    result.add((IApplicationDto) mapperIF.map(app, ApplicationDto.class));
                }
            }

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public void addApplicationResourceBundle(String applicationId, String filename, InputStream resourceBundle) throws PlatformException {

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }
        if (StringUtils.isBlank(filename)) {
            log.error("filename is null");
            throw new PlatformException("error.missing.argument.exception", "filename");
        }
        if (resourceBundle == null) {
            log.error("resourceBundle is null");
            throw new PlatformException("error.missing.argument.exception", "resourceBundle");
        }

        try {
            Application application = applicationDAO.get(Application.class, applicationId);

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
            throw new PlatformException(e.getMessage(), e);
        }
    }

    @Override
    public List<IResourceBundleEntryOverrideDto> searchForLicensedThirdPartyApplicationResourceBundleEntryOverrides(String organizationLicenseId, String name, Integer index, Integer maxResults) throws PlatformException {
        List<IResourceBundleEntryOverrideDto> result = null;

        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("organizationLicenseId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationLicenseId");
        }

        try {
            List<ResourceBundleEntryOverride> list = resourceBundleEntryOverrideDAO.searchForLicensedThirdPartyApplicationResourceBundleEntryOverrides(organizationLicenseId, name, index, maxResults);

            if (list != null) {
                result = new ArrayList<IResourceBundleEntryOverrideDto>(list.size());

                for (ResourceBundleEntryOverride rbo : list) {
                    result.add((IResourceBundleEntryOverrideDto) mapperIF.map(rbo, ResourceBundleEntryDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchForLicensedThirdPartyApplicationResourceBundleEntryOverridesCount(String organizationLicenseId, String name) throws PlatformException {
        Integer result = null;

        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("organizationLicenseId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationLicenseId");
        }

        try {
            result = resourceBundleEntryOverrideDAO.searchForLicensedThirdPartyApplicationResourceBundleEntryOverridesCount(organizationLicenseId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IResourceBundleEntryOverrideDto deleteResourceBundleEntryOverride(String resourceBundleOverrideId) throws PersistenceException, PlatformException {
        IResourceBundleEntryOverrideDto result = null;

        if (StringUtils.isBlank(resourceBundleOverrideId)) {
            log.error("resourceBundleOverrideId is null");
            throw new PlatformException("error.missing.argument.exception", "resourceBundleOverrideId");
        }

        try {
            ResourceBundleEntryOverride rbeo = resourceBundleEntryOverrideDAO.get(ResourceBundleEntryOverride.class, resourceBundleOverrideId);

            if (rbeo != null) {
                // convert to dto here
                result = (IResourceBundleEntryOverrideDto) mapperIF.map(rbeo, ResourceBundleEntryOverrideDto.class);

                // and delete
                resourceBundleEntryOverrideDAO.delete(rbeo);
            } else {
                log.error("Cannot delete application resource bundle entry override with id: " + resourceBundleOverrideId);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IResourceBundleEntryOverrideDto saveOrUpdateResourceBundleEntryOverride(String organizationLicenseId, IResourceBundleEntryOverrideDto override) throws PlatformException {
        if (StringUtils.isBlank(organizationLicenseId)) {
            log.error("organizationLicenseId is null");
            throw new PlatformException("error.missing.argument.exception", "organizationLicenseId");
        }
        if (override == null) {
            throw new PlatformException("error.missing.argument.exception", "override");
        }

        try {

            if (StringUtils.isNotBlank(override.getId())) {
                ResourceBundleEntryOverride rbeo = resourceBundleEntryOverrideDAO.get(ResourceBundleEntryOverride.class, override.getId());
                rbeo.setValue(override.getValue());

                rbeo.setOriginal((ResourceBundleEntry) mapperIF.map(override.getOriginal(), ResourceBundleEntry.class));
                rbeo.setDescription(override.getDescription());

                rbeo = resourceBundleEntryOverrideDAO.update(rbeo);


            } else {
                OrganizationLicense organizationLicense = organizationLicenseDAO.get(OrganizationLicense.class, organizationLicenseId);

                ResourceBundleEntryOverride rbeo = (ResourceBundleEntryOverride) mapperIF.map(override, ResourceBundleEntryOverride.class);

                // throw error if application does not exist
                if (organizationLicense != null) {
                    rbeo.setOrganizationLicense(organizationLicense);
                    rbeo = resourceBundleEntryOverrideDAO.save(rbeo);

                    override = (IResourceBundleEntryOverrideDto) mapperIF.map(rbeo, ResourceBundleEntryOverrideDto.class);
                }
            }
        } catch (PersistenceException e) {
            throw new PlatformException(e.getMessage(), e);
        }

        return override;
    }

    @Override
    public IResourceBundleEntryOverrideDto getResourceBundleEntryOverride(String resourceBundleEntryId) throws PlatformException {
        IResourceBundleEntryOverrideDto result = null;

        if (StringUtils.isBlank(resourceBundleEntryId)) {
            log.error("resourceBundleEntryId is null");
            throw new PlatformException("error.missing.argument.exception", "resourceBundleEntryId");
        }

        try {
            ResourceBundleEntryOverride rbeo = resourceBundleEntryOverrideDAO.get(ResourceBundleEntryOverride.class, resourceBundleEntryId);

            if (rbeo != null) {
                result = (IResourceBundleEntryOverrideDto) mapperIF.map(rbeo, ResourceBundleEntryOverrideDto.class);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IResourceBundleEntryDto> searchForResourceBundleEntries(String applicationId, String name, Integer index, Integer maxResults) throws PlatformException {
        List<IResourceBundleEntryDto> result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }

        try {
            List<ResourceBundleEntry> list = resourceBundleEntryDAO.searchForResourceBundleEntries(applicationId, name, index, maxResults);

            if (list != null) {
                result = new ArrayList<IResourceBundleEntryDto>();

                for (ResourceBundleEntry rbe : list) {
                    result.add((IResourceBundleEntryDto) mapperIF.map(rbe, ResourceBundleEntryDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchForResourceBundleEntriesCount(String applicationId, String name) throws PlatformException {
        Integer result = null;

        if (StringUtils.isBlank(applicationId)) {
            log.error("applicationId is null");
            throw new PlatformException("error.missing.argument.exception", "applicationId");
        }

        try {
            result = resourceBundleEntryDAO.searchForResourceBundleEntriesCount(applicationId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IResourceBundleEntryDto saveOrUpdateResourceBundle(IResourceBundleEntryDto resourceBundleEntry) throws PlatformException {
        try {
            // convert to entity first
            ResourceBundleEntry rbe = (ResourceBundleEntry) mapperIF.map(resourceBundleEntry, ResourceBundleEntry.class);

            // save here
            rbe = resourceBundleEntryDAO.saveOrUpdate(rbe);

            // convert to dto here
            resourceBundleEntry = (IResourceBundleEntryDto) mapperIF.map(rbe, ResourceBundleEntryDto.class);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return resourceBundleEntry;
    }

    @Override
    public IResourceBundleEntryDto getResourceBundleEntry(String resourceBundleEntryId) throws PlatformException {
        IResourceBundleEntryDto result = null;

        if (StringUtils.isBlank(resourceBundleEntryId)) {
            log.error("resourceBundleEntryId is null");
            throw new PlatformException("error.missing.argument.exception", "resourceBundleEntryId");
        }

        try {
            ResourceBundleEntry rbe = resourceBundleEntryDAO.get(ResourceBundleEntry.class, resourceBundleEntryId);

            // convert to dto here
            result = (IResourceBundleEntryDto) mapperIF.map(rbe, ResourceBundleEntryDto.class);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IResourceBundleEntryDto deleteResourceBundleEntry(String resourceBundleEntryId) throws PlatformException {
        IResourceBundleEntryDto result = null;

        if (StringUtils.isBlank(resourceBundleEntryId)) {
            log.error("resourceBundleEntryId is null");
            throw new PlatformException("error.missing.argument.exception", "resourceBundleEntryId");
        }

        try {
            ResourceBundleEntry rbe = resourceBundleEntryDAO.get(ResourceBundleEntry.class, resourceBundleEntryId);

            if (rbe != null) {
                // convert to dto here
                result = (IResourceBundleEntryDto) mapperIF.map(rbe, ResourceBundleEntryDto.class);

                resourceBundleEntryDAO.delete(rbe);
            } else {
                log.error("Cannot delete resource bundle entry with ID: " + resourceBundleEntryId);
            }
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
    private LicenseDAO licenseDAO;

    @Autowired
    private ApplicationDAO applicationDAO;

    @Autowired
    private ApplicationRoleDAO applicationRoleDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private ResourceBundleEntryDAO resourceBundleEntryDAO;

    @Autowired
    private ResourceBundleEntryOverrideDAO resourceBundleEntryOverrideDAO;

    @Autowired
    private ApplicationConfigurationDAO applicationConfigurationDAO;

    @Autowired
    private ApplicationConfigurationOverrideDAO applicationConfigurationOverrideDAO;

    @Autowired
    private OrganizationLicenseDAO organizationLicenseDAO;

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
