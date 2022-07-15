/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.dto.ApplicationConfigurationDto;
import com.hxcel.globalhealth.platform.dto.ApplicationDto;
import com.hxcel.globalhealth.platform.model.Application;
import com.hxcel.globalhealth.platform.model.ApplicationConfiguration;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationDto;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class ApplicationConfigurationCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(ApplicationConfigurationCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof ApplicationConfiguration && dto instanceof ApplicationConfigurationDto) {
            ApplicationConfiguration entity = (ApplicationConfiguration) source;

            ((ApplicationConfigurationDto) dto).setApplication((IApplicationDto) mapperIF.map(entity.getApplication(), ApplicationDto.class));
            ((ApplicationConfigurationDto) dto).setDescription(entity.getDescription());
            ((ApplicationConfigurationDto) dto).setExportable(entity.getExportable());
            ((ApplicationConfigurationDto) dto).setKey(entity.getKey());
            ((ApplicationConfigurationDto) dto).setValue(entity.getValue());
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
        if (source instanceof ApplicationConfigurationDto && entity instanceof ApplicationConfiguration) {
            ApplicationConfigurationDto dto = (ApplicationConfigurationDto) source;

            ((ApplicationConfiguration) entity).setApplication((Application) mapperIF.map(dto.getApplication(), Application.class));
            ((ApplicationConfiguration) entity).setDescription(dto.getDescription());
            ((ApplicationConfiguration) entity).setExportable(dto.getExportable());
            ((ApplicationConfiguration) entity).setKey(dto.getKey());
            ((ApplicationConfiguration) entity).setValue(dto.getValue());

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }
}