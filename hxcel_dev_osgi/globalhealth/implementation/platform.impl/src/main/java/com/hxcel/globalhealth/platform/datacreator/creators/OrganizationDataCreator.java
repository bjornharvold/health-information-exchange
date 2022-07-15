package com.hxcel.globalhealth.platform.datacreator.creators;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hxcel.globalhealth.platform.datacreator.DataCreator;
import com.hxcel.globalhealth.platform.datacreator.DataCreatorException;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationTypeCd;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationStatusCd;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.PlatformException;
import com.hxcel.globalhealth.platform.spec.CountryService;
import com.hxcel.globalhealth.platform.model.Organization;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.common.spec.model.enums.CountryCd;

/**
 * User: bjorn
 * Date: Aug 21, 2008
 * Time: 3:05:01 PM
 * Creates Health XCEL as a God organization if one doesn't already exist
 */
public class OrganizationDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(OrganizationDataCreator.class);

    public void create() throws DataCreatorException {
        IOrganizationDto organization = null;

        // check for existence
        try {
            Integer count = organizationService.searchForOrganizationsCount(organizationName);

            if (count == 0) {
                organization = organizationService.createOrganization();
                organization.setName(organizationName);
                organization.setOrganizationStatus(OrganizationStatusCd.ACTIVE);
                organization.setOrganizationType(OrganizationTypeCd.HXCEL);
                organization.setCountry(countryService.getCountry(CountryCd.UNITED_STATES));

                organization = organizationService.insertOrganizationFromDataCreator(organization);

                log.info("Created org successfully with id: " + organization.getId());
            }
        } catch (PlatformException e) {
            log.error(e.getMessage(), e);
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    // Spring IoC
    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private CountryService countryService;

    private String organizationName;

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
