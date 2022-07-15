/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.dto.OrganizationLicenseDto;
import com.hxcel.globalhealth.platform.dto.LicenseDto;
import com.hxcel.globalhealth.platform.dto.OrganizationDto;
import com.hxcel.globalhealth.platform.model.OrganizationLicense;
import com.hxcel.globalhealth.platform.model.License;
import com.hxcel.globalhealth.platform.model.Organization;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class OrganizationLicenseCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(OrganizationLicenseCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof OrganizationLicense && dto instanceof OrganizationLicenseDto) {
            OrganizationLicense entity = (OrganizationLicense) source;

            ((OrganizationLicenseDto) dto).setStatus(entity.getStatus());
            ((OrganizationLicenseDto) dto).setLicense((LicenseDto) mapperIF.map(entity.getLicense(), LicenseDto.class));
            ((OrganizationLicenseDto) dto).setOrganization((OrganizationDto) mapperIF.map(entity.getOrganization(), OrganizationDto.class));
        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * This method should only get called once by the production code (at the generation of the user with the system)
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractEntity toEntity(AbstractEntity entity, IAbstractDto source, Class destClass, Class sourceClass) {
        if (source instanceof OrganizationLicenseDto && entity instanceof OrganizationLicense) {
            OrganizationLicenseDto dto = (OrganizationLicenseDto) source;

            ((OrganizationLicense) entity).setStatus(dto.getStatus());
            ((OrganizationLicense) entity).setLicense((License) mapperIF.map(dto.getLicense(), License.class));
            ((OrganizationLicense) entity).setOrganization((Organization) mapperIF.map(dto.getOrganization(), Organization.class));

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }
}