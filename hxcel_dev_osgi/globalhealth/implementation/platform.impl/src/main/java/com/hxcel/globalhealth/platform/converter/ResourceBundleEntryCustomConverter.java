/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.platform.model.Country;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;
import com.hxcel.globalhealth.platform.dto.CountryDto;
import com.hxcel.globalhealth.platform.dto.ResourceBundleEntryDto;
import com.hxcel.globalhealth.platform.dto.ApplicationDto;
import com.hxcel.globalhealth.platform.model.ResourceBundleEntry;
import com.hxcel.globalhealth.platform.model.Application;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationDto;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class ResourceBundleEntryCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(ResourceBundleEntryCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof ResourceBundleEntry && dto instanceof ResourceBundleEntryDto) {
            ResourceBundleEntry entity = (ResourceBundleEntry) source;

            ((ResourceBundleEntryDto) dto).setKey(entity.getKey());
            ((ResourceBundleEntryDto) dto).setApplication((IApplicationDto) mapperIF.map(entity.getApplication(), ApplicationDto.class));
            ((ResourceBundleEntryDto) dto).setCountry((ICountryDto) mapperIF.map(entity.getCountry(), CountryDto.class));
            ((ResourceBundleEntryDto) dto).setValue(entity.getValue());
            ((ResourceBundleEntryDto) dto).setDescription(entity.getDescription());
            ((ResourceBundleEntryDto) dto).setKey(entity.getKey());

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
        if (source instanceof ResourceBundleEntryDto && entity instanceof ResourceBundleEntry) {
            ResourceBundleEntryDto dto = (ResourceBundleEntryDto) source;

            ((ResourceBundleEntry) entity).setKey(dto.getKey());
            ((ResourceBundleEntry) entity).setApplication((Application) mapperIF.map(dto.getApplication(), Application.class));
            ((ResourceBundleEntry) entity).setCountry((Country) mapperIF.map(dto.getCountry(), Country.class));
            ((ResourceBundleEntry) entity).setValue(dto.getValue());
            ((ResourceBundleEntry) entity).setDescription(dto.getDescription());
            ((ResourceBundleEntry) entity).setKey(dto.getKey());

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }
}