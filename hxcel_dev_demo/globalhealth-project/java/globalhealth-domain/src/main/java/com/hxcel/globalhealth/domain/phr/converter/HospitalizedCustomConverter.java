/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.HospitalizedDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.Hospitalized;
import net.sf.dozer.util.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * Custom converter for PHR Allergy entity
 */
public class HospitalizedCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(HospitalizedCustomConverter.class);

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

        if (source instanceof Hospitalized && dto instanceof HospitalizedDto) {
            Hospitalized entity = (Hospitalized) source;

            // flatten entity here
            ((HospitalizedDto) dto).setEndDate(entity.getEndDate());
            ((HospitalizedDto) dto).setMedicalConditionOther(entity.getMedicalConditionOther());
            ((HospitalizedDto) dto).setMedicalConditionTypeCd(entity.getMedicalConditionTypeCd());
            ((HospitalizedDto) dto).setStartDate(entity.getStartDate());

        } else {
            throw new MappingException("Converter HospitalizedCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
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

        if (source instanceof HospitalizedDto && entity instanceof Hospitalized) {
            HospitalizedDto dto = (HospitalizedDto) source;

            // hydrate entity
            ((Hospitalized) entity).setEndDate(dto.getEndDate());
            ((Hospitalized) entity).setMedicalConditionOther(dto.getMedicalConditionOther());
            ((Hospitalized) entity).setMedicalConditionTypeCd(dto.getMedicalConditionTypeCd());
            ((Hospitalized) entity).setStartDate(dto.getStartDate());
        } else {
            throw new MappingException("Converter HospitalizedCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }

}