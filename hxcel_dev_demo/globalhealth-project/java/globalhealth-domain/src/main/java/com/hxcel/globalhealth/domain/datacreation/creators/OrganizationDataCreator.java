package com.hxcel.globalhealth.domain.datacreation.creators;

import com.hxcel.globalhealth.domain.datacreation.DataCreator;
import com.hxcel.globalhealth.domain.datacreation.DataCreatorException;
import com.hxcel.globalhealth.domain.platform.dao.OrganizationDAO;
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.platform.model.enums.OrganizationStatusCd;
import com.hxcel.globalhealth.domain.platform.model.enums.OrganizationTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.common.dao.CountryDAO;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Aug 21, 2008
 * Time: 3:05:01 PM
 * Creates Health XCEL as a God organization if one doesn't already exist
 */
public class OrganizationDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(OrganizationDataCreator.class);

    public void create() throws DataCreatorException {
        Organization organization = null;

        // check for existence
        try {
            organization = organizationDAO.getOrganizationByName(organizationName);

            if (organization == null) {
                organization = new Organization();
                organization.setName(organizationName);
                organization.setOrganizationStatus(OrganizationStatusCd.ACTIVE);
                organization.setOrganizationType(OrganizationTypeCd.HXCEL);
                organization.setCountry(countryDAO.getCountry(CountryCd.UNITED_STATES));

                organization = organizationDAO.save(organization);

                log.info("Created org successfully with id: " + organization.getId());
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    // Spring IoC
    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private CountryDAO countryDAO;

    private String organizationName;

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
