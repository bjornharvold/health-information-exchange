/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.FamilyHistoryDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.FamilyHistory;
import net.sf.dozer.util.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * Custom converter for PHR Allergy entity
 */
public class FamilyHistoryCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(FamilyHistoryCustomConverter.class);

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

        if (source instanceof FamilyHistory && dto instanceof FamilyHistoryDto) {
            FamilyHistory entity = (FamilyHistory) source;

            // flatten entity here
            ((FamilyHistoryDto) dto).setAlive(entity.getAlive());
            ((FamilyHistoryDto) dto).setRelativeConditionOther(entity.getRelativeConditionOther());
            ((FamilyHistoryDto) dto).setRelativeConditionTypeCd(entity.getRelativeConditionTypeCd());
            ((FamilyHistoryDto) dto).setRelativeOther(entity.getRelativeOther());
            ((FamilyHistoryDto) dto).setRelativeTypeCd(entity.getRelativeTypeCd());

        } else {
            throw new MappingException("Converter FamilyHistoryCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
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

        if (source instanceof FamilyHistoryDto && entity instanceof FamilyHistory) {
            FamilyHistoryDto dto = (FamilyHistoryDto) source;

            // hydrate entity
            ((FamilyHistory) entity).setAlive(dto.getAlive());
            ((FamilyHistory) entity).setRelativeConditionOther(dto.getRelativeConditionOther());
            ((FamilyHistory) entity).setRelativeConditionTypeCd(dto.getRelativeConditionTypeCd());
            ((FamilyHistory) entity).setRelativeOther(dto.getRelativeOther());
            ((FamilyHistory) entity).setRelativeTypeCd(dto.getRelativeTypeCd());
        } else {
            throw new MappingException("Converter FamilyHistoryCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }

}