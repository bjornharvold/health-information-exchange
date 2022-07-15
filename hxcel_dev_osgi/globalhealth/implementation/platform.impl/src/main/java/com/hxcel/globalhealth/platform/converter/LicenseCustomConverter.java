/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.dto.ApplicationDto;
import com.hxcel.globalhealth.platform.dto.LicenseDto;
import com.hxcel.globalhealth.platform.model.Application;
import com.hxcel.globalhealth.platform.model.License;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class LicenseCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(LicenseCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof License && dto instanceof LicenseDto) {
            License entity = (License) source;

            ((LicenseDto) dto).setStatus(entity.getStatus());
            ((LicenseDto) dto).setLicenseType(entity.getLicenseType());
            ((LicenseDto) dto).setDescription(entity.getDescription());
            ((LicenseDto) dto).setName(entity.getName());
            ((LicenseDto) dto).setExpirationDate(entity.getExpirationDate());
            ((LicenseDto) dto).setPrice(entity.getPrice());
            ((LicenseDto) dto).setApplication((ApplicationDto) mapperIF.map(entity.getApplication(), ApplicationDto.class));
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
        if (source instanceof LicenseDto && entity instanceof License) {
            LicenseDto dto = (LicenseDto) source;

            ((License) entity).setStatus(dto.getStatus());
            ((License) entity).setLicenseType(dto.getLicenseType());
            ((License) entity).setDescription(dto.getDescription());
            ((License) entity).setName(dto.getName());
            ((License) entity).setExpirationDate(dto.getExpirationDate());
            ((License) entity).setPrice(dto.getPrice());
            ((License) entity).setApplication((Application) mapperIF.map(dto.getApplication(), Application.class));

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }
}