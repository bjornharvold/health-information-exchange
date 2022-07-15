/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.dto.RegulationDto;
import com.hxcel.globalhealth.platform.model.Regulation;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class RegulationCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(RegulationCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Regulation && dto instanceof RegulationDto) {
            Regulation entity = (Regulation) source;

            ((RegulationDto) dto).setKey(entity.getKey());
            ((RegulationDto) dto).setType(entity.getType());
            ((RegulationDto) dto).setValue(entity.getValue());
            ((RegulationDto) dto).setDescription(entity.getDescription());
            ((RegulationDto) dto).setName(entity.getName());

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
        if (source instanceof RegulationDto && entity instanceof Regulation) {
            RegulationDto dto = (RegulationDto) source;

            ((Regulation) entity).setKey(dto.getKey());
            ((Regulation) entity).setType(dto.getType());
            ((Regulation) entity).setValue(dto.getValue());
            ((Regulation) entity).setDescription(dto.getDescription());
            ((Regulation) entity).setName(dto.getName());

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }
}