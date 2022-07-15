/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.platform.dto.CountryDto;
import com.hxcel.globalhealth.platform.dto.ImageDto;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.platform.model.Country;
import com.hxcel.globalhealth.platform.model.Image;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.dto.OrganizationDto;
import com.hxcel.globalhealth.platform.model.Organization;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class OrganizationCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(OrganizationCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Organization && dto instanceof OrganizationDto) {
            Organization entity = (Organization) source;

            ((OrganizationDto) dto).setOrganizationStatus(entity.getOrganizationStatus());
            ((OrganizationDto) dto).setOrganizationType(entity.getOrganizationType());
            ((OrganizationDto) dto).setName(entity.getName());

            if (entity.getImage() != null) {
                ((OrganizationDto) dto).setImage((ImageDto) mapperIF.map(entity.getImage(), ImageDto.class));
            }
            if (entity.getCountry() != null) {
                ((OrganizationDto) dto).setCountry((CountryDto) mapperIF.map(entity.getCountry(), CountryDto.class));
            }
            if (entity.getParent() != null) {
                ((OrganizationDto) dto).setParent((OrganizationDto) mapperIF.map(entity.getParent(), OrganizationDto.class));
            }
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
        if (source instanceof OrganizationDto && entity instanceof Organization) {
            OrganizationDto dto = (OrganizationDto) source;

            ((Organization) entity).setOrganizationStatus(dto.getOrganizationStatus());
            ((Organization) entity).setOrganizationType(dto.getOrganizationType());
            ((Organization) entity).setName(dto.getName());

            if (dto.getImage() != null) {
                ((Organization) entity).setImage((Image) mapperIF.map(dto.getImage(), Image.class));
            }

            if (dto.getCountry() != null) {
                ((Organization) entity).setCountry((Country) mapperIF.map(dto.getCountry(), Country.class));
            }

            if (dto.getParent() != null) {
                ((Organization) entity).setParent((Organization) mapperIF.map(dto.getParent(), Organization.class));
            }

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }
}