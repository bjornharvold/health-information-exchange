package com.hxcel.globalhealth.platform.spec;

import com.hxcel.globalhealth.platform.spec.dto.*;
import com.hxcel.globalhealth.platform.spec.PlatformException;
import com.hxcel.globalhealth.platform.spec.IKeyValuePair;
import com.hxcel.globalhealth.platform.spec.model.enums.IconSizeCd;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationLicenseStatusCd;

import java.io.InputStream;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

/**
 * User: Bjorn Harvold
 * Date: Jan 9, 2009
 * Time: 3:36:20 PM
 * Responsibility:
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface OrganizationService {
    IOrganizationDto createOrganization();
    IOrganizationUserDto createOrganizationUser();

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IOrganizationDto insertOrganizationFromDataCreator(IOrganizationDto org) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IOrganizationDto saveOrUpdateOrganization(IOrganizationDto org) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IOrganizationDto addOrganizationIcon(String organizationId, String iconName, InputStream icon, IconSizeCd size) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IOrganizationDto deactivateOrganization(String organizationId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IOrganizationUserDto registerUserWithOrganization(String orgId, String userId) throws PlatformException;

    IOrganizationDto getOrganization(String organizationId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IOrganizationUserDto deleteOrganizationUser(String organizationUserId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IOrganizationLicenseDto saveOrUpdateOrganizationLicense(String orgId, String licenseId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IOrganizationLicenseDto unlicenseThirdPartyApplication(String organizationLicenseId) throws PlatformException;

    List<IOrganizationDto> searchForOrganizations(String name, Integer index, Integer maxResult) throws PlatformException;

    List<IKeyValuePair> getOrganizationThinList() throws PlatformException;

    Integer searchForOrganizationsCount(String name) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    List<IOrganizationLicenseDto> licenseApplicationsForOrganization(String organizationId, List<String> licenseIds) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    List<IOrganizationUserDto> registerUsersWithOrganization(String organizationId, List<String> userIds) throws PlatformException;

    List<IOrganizationUserDto> searchForOrganizationUsers(String organizationId, String name, Integer index, Integer maxResults) throws PlatformException;

    Integer searchOrganizationUsersCount(String organizationId, String name) throws PlatformException;

    List<IOrganizationLicenseDto> searchForLicensedThirdPartyApplications(String organizationId, String name, Boolean isPlatform, OrganizationLicenseStatusCd status, Integer index, Integer maxResults) throws PlatformException;

    Integer searchLicensedThirdPartyApplicationsCount(String organizationId, String name, Boolean isPlatform, OrganizationLicenseStatusCd status) throws PlatformException;

    List<IOrganizationUserRoleDto> searchForOrganizationUserRoles(String organizationUserId, String name, Integer index, Integer maxResults) throws PlatformException;

    Integer searchOrganizationUserRolesCount(String organizationUserId, String name) throws PlatformException;

    IOrganizationUserDto getOrganizationUser(String organizationUserId) throws PlatformException;

    List<IOrganizationDto> getLastModifiedOrganizations(Integer maxResults) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    List<IOrganizationUserRoleDto> saveOrUpdateOrganizationUserRoles(String organizationUserId, List<String> roleIds) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IOrganizationUserRoleDto deleteOrganizationUserRole(String organizationUserRoleId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IOrganizationUserDto saveOrUpdateOrganizationUser(IOrganizationUserDto organizationUser) throws PlatformException;

    List<IUserDto> searchForNewOrganizationMembers(String organizationId, String name, Integer index, Integer maxResults) throws PlatformException;

    Integer searchForNewOrganizationMembersCount(String organizationId, String name) throws PlatformException;

}
