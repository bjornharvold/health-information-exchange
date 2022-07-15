/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.AllergyDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.Allergy;
import net.sf.dozer.util.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * Custom converter for PHR Allergy entity
 */
public class AllergyCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(AllergyCustomConverter.class);

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

        if (source instanceof Allergy && dto instanceof AllergyDto) {
            Allergy entity = (Allergy) source;

            // flatten entity here
            ((AllergyDto) dto).setAllergyReactionOther(entity.getAllergyReactionOther());
            ((AllergyDto) dto).setAllergyReactionTypeCd(entity.getAllergyReactionTypeCd());
            ((AllergyDto) dto).setAllergyTypeCd(entity.getAllergyTypeCd());
            ((AllergyDto) dto).setAllergyTypeOther(entity.getAllergyTypeOther());
            ((AllergyDto) dto).setEndDate(entity.getEndDate());
            ((AllergyDto) dto).setStartDate(entity.getStartDate());
            ((AllergyDto) dto).setFrequencyTypeCd(entity.getFrequencyTypeCd());
            ((AllergyDto) dto).setTreatmentTypeCd(entity.getTreatmentTypeCd());
            ((AllergyDto) dto).setTreatmentTypeOther(entity.getTreatmentTypeOther());


        } else {
            throw new MappingException("Converter AllergyCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
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

        if (source instanceof AllergyDto && entity instanceof Allergy) {
            AllergyDto dto = (AllergyDto) source;

            // hydrate entity
            ((Allergy) entity).setAllergyReactionOther(dto.getAllergyReactionOther());
            ((Allergy) entity).setAllergyReactionTypeCd(dto.getAllergyReactionTypeCd());
            ((Allergy) entity).setAllergyTypeCd(dto.getAllergyTypeCd());
            ((Allergy) entity).setAllergyTypeOther(dto.getAllergyTypeOther());
            ((Allergy) entity).setEndDate(dto.getEndDate());
            ((Allergy) entity).setStartDate(dto.getStartDate());
            ((Allergy) entity).setFrequencyTypeCd(dto.getFrequencyTypeCd());
            ((Allergy) entity).setTreatmentTypeCd(dto.getTreatmentTypeCd());
            ((Allergy) entity).setTreatmentTypeOther(dto.getTreatmentTypeOther());
        } else {
            throw new MappingException("Converter AllergyCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }

}