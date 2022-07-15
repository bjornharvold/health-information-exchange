package com.hxcel.globalhealth.domain.platform.dao;

import com.hxcel.globalhealth.domain.platform.model.ApplicationConfigurationOverride;
import com.hxcel.globalhealth.domain.platform.model.OrganizationLicense;
import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.enums.OrganizationLicenseStatusCd;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

import java.util.List;

/**
 * User: bjorn
 * Date: Sep 25, 2008
 * Time: 1:20:42 PM
 */
public interface OrganizationLicenseDAO extends GenericDAO<OrganizationLicense, String> {
    List<OrganizationLicense> searchForOrganizationLicenses(String organizationId, String name, Boolean platform, OrganizationLicenseStatusCd status, Integer index, Integer maxResults) throws PersistenceException;
    Integer searchForOrganizationLicensesCount(String organizationId, String name, Boolean platform, OrganizationLicenseStatusCd status) throws PersistenceException;
    OrganizationLicense getOrganizationLicense(String organizationId, String licenseId) throws PersistenceException;
    OrganizationLicense getOrganizationLicense(String id) throws PersistenceException;
    Integer getApplicationUsageCount(String applicationId) throws PersistenceException;
    OrganizationLicense getLaunchableApplication(String organizationId, String applicationId, Boolean platform) throws PersistenceException;
    
}