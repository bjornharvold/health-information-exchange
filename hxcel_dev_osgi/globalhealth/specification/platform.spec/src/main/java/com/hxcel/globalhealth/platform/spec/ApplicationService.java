package com.hxcel.globalhealth.platform.spec;

import com.hxcel.globalhealth.platform.spec.dto.*;
import com.hxcel.globalhealth.platform.spec.model.enums.IconSizeCd;
import com.hxcel.globalhealth.platform.spec.model.enums.LicenseStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.common.spec.PersistenceException;

import java.util.List;
import java.io.InputStream;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

/**
 * User: Bjorn Harvold
 * Date: Jan 9, 2009
 * Time: 3:37:15 PM
 * Responsibility:
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface ApplicationService {
    IApplicationConfigurationDto createApplicationConfiguration();
    ILicenseDto createLicense();
    IResourceBundleEntryDto createResourceBundleEntry();
    IApplicationConfigurationOverrideDto createApplicationConfigurationOverride();
    IResourceBundleEntryOverrideDto createResourceBundleEntryOverride();
    IApplicationDto createApplication();

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IApplicationDto saveOrUpdateApplication(IApplicationDto app) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IApplicationDto deactivateApplication(String applicationId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ILicenseDto addApplicationLicense(String applicationId, ILicenseDto license) throws PlatformException;

    ILicenseDto getLicense(String licenseId) throws PlatformException, PersistenceException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ILicenseDto updateLicense(ILicenseDto license) throws PlatformException;

    List<IApplicationDto> searchApplicationsOwnedByOrganization(String organizationId, String name, Boolean platform, Integer index, Integer maxResults) throws PlatformException;

    List<IApplicationDto> searchForApplications(String name, Boolean isPlatform, Integer index, Integer maxResult) throws PlatformException;

    Integer searchForApplicationsCount(String name, Boolean isPlatform) throws PlatformException;

    IApplicationDto getApplication(String applicationId) throws PlatformException;

    IApplicationConfigurationDto getApplicationConfiguration(String applicationConfigurationId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IApplicationConfigurationDto addApplicationConfiguration(String applicationId, IApplicationConfigurationDto acDto) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IApplicationConfigurationDto updateApplicationConfiguration(IApplicationConfigurationDto acDto) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IApplicationConfigurationDto deleteApplicationConfiguration(String applicationConfigurationId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ILicenseDto deleteApplicationLicense(String applicationId, String licenseId) throws PlatformException;

    IApplicationConfigurationOverrideDto getApplicationConfigurationOverride(String organizationConfigurationId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IApplicationConfigurationOverrideDto addApplicationConfigurationOverride(String organizationLicenseId, IApplicationConfigurationOverrideDto override) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IApplicationConfigurationOverrideDto updateApplicationConfigurationOverride(IApplicationConfigurationOverrideDto override) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IApplicationDto addApplicationIcon(String applicationId, String iconName, InputStream icon, IconSizeCd size) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IApplicationDto addApplicationSwf(String applicationId, String swfName, InputStream swf) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IApplicationConfigurationOverrideDto deleteApplicationConfigurationOverride(String applicationConfigurationOverrideId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    List<IApplicationRoleDto> addApplicationRoles(String applicationId, List<String> roles) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IApplicationRoleDto addApplicationRole(String applicationId, String roleId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IApplicationRoleDto deleteApplicationRole(String applicationRoleId) throws PlatformException;

    List<ILicenseDto> getLicensedApplicationsExcludeOwner(String organizationId, Boolean platform) throws PlatformException;

    List<IApplicationConfigurationOverrideDto> searchForLicensedThirdPartyApplicationConfigurationOverrides(String organizationLicenseId, String name, Integer index, Integer maxResults) throws PlatformException;

    Integer searchForLicensedThirdPartyApplicationConfigurationOverridesCount(String organizationLicenseId, String name) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IApplicationConfigurationOverrideDto saveOrUpdateApplicationConfigurationOverride(IApplicationConfigurationOverrideDto override) throws PlatformException;

    Integer searchApplicationsOwnedByOrganizationCount(String organizationId, String name, Boolean platform) throws PlatformException;

    List<ILicenseDto> searchForApplicationLicenses(String applicationId, String name, LicenseStatusCd status, Integer index, Integer maxResults) throws PlatformException;

    Integer searchForApplicationLicensesCount(String applicationId, String name, LicenseStatusCd status) throws PlatformException;

    List<IApplicationRoleDto> searchForApplicationRoles(String applicationId, String name, Integer index, Integer maxResults) throws PlatformException;

    Integer searchForApplicationRolesCount(String applicationId, String name) throws PlatformException;

    List<IApplicationConfigurationDto> searchForApplicationConfigurations(String applicationId, String name, Integer index, Integer maxResults) throws PlatformException;

    Integer searchForApplicationConfigurationsCount(String applicationId, String name) throws PlatformException;

    Boolean isApplicationPublishable(String applicationId) throws PlatformException;

    Boolean isApplicationUnpublishable(String applicationId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void publishApplication(String applicationId, ApplicationStatusCd status) throws PlatformException;

    ILaunchableApplicationDto getLaunchableApplication(String organizationId, String applicationId, boolean platform) throws PlatformException;

    IOrganizationLicenseDto getOrganizationLicense(String organizationLicenseId) throws PlatformException;

    List<IApplicationDto> getLastModifiedApplications(Boolean platform, Integer maxResults) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void addApplicationResourceBundle(String applicationId, String filename, InputStream resourceBundle) throws PlatformException;

    List<IResourceBundleEntryOverrideDto> searchForLicensedThirdPartyApplicationResourceBundleEntryOverrides(String organizationLicenseId, String name, Integer index, Integer maxResults) throws PlatformException;

    Integer searchForLicensedThirdPartyApplicationResourceBundleEntryOverridesCount(String organizationLicenseId, String name) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IResourceBundleEntryOverrideDto deleteResourceBundleEntryOverride(String resourceBundleOverrideId) throws PersistenceException, PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IResourceBundleEntryOverrideDto saveOrUpdateResourceBundleEntryOverride(String organizationLicenseId, IResourceBundleEntryOverrideDto override) throws PlatformException;

    IResourceBundleEntryOverrideDto getResourceBundleEntryOverride(String resourceBundleEntryId) throws PlatformException;

    List<IResourceBundleEntryDto> searchForResourceBundleEntries(String applicationId, String name, Integer index, Integer maxResults) throws PlatformException;

    Integer searchForResourceBundleEntriesCount(String applicationId, String name) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IResourceBundleEntryDto saveOrUpdateResourceBundle(IResourceBundleEntryDto resourceBundleEntry) throws PlatformException;

    IResourceBundleEntryDto getResourceBundleEntry(String resourceBundleEntryId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IResourceBundleEntryDto deleteResourceBundleEntry(String resourceBundleEntryId) throws PlatformException;

}
