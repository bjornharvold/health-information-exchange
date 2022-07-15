/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.ImmunizationDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.Immunization;
import net.sf.dozer.util.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * Custom converter for PHR Allergy entity
 */
public class ImmunizationCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(ImmunizationCustomConverter.class);

    /**
     * Responsible for converting entity data specific to Allergy to AllergyDto
     *
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrDto toDto(AbstractPhrDto dto, AbstractPhrEntity source, Class destClass, Class sourceClass) {

        if (source instanceof Immunization && dto instanceof ImmunizationDto) {
            Immunization entity = (Immunization) source;

            // flatten entity here
            ((ImmunizationDto) dto).setDate(entity.getDate());
            ((ImmunizationDto) dto).setImmunizationTypeCd(entity.getImmunizationTypeCd());

        } else {
            throw new MappingException("Converter ImmunizationCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * Responsible for converting dto data specific to AdvanceDirectiveDto to AdvanceDirective
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrEntity toEntity(AbstractPhrEntity entity, AbstractPhrDto source, Class destClass, Class sourceClass) {

        if (source instanceof ImmunizationDto && entity instanceof Immunization) {
            ImmunizationDto dto = (ImmunizationDto) source;

            // hydrate entity
            ((Immunization) entity).setDate(dto.getDate());
            ((Immunization) entity).setImmunizationTypeCd(dto.getImmunizationTypeCd());
            
        } else {
            throw new MappingException("Converter ImmunizationCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }

}