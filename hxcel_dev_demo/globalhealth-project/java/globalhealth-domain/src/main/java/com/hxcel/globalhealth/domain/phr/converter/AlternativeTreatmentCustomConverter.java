/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.phr.dao.PractitionerDAO;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.AlternativeTreatmentDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.AlternativeTreatment;
import com.hxcel.globalhealth.domain.phr.model.Practitioner;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import net.sf.dozer.util.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * Custom converter for PHR Alternative Treatment entity
 */
public class AlternativeTreatmentCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(AlternativeTreatmentCustomConverter.class);

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

        if (source instanceof AlternativeTreatment && dto instanceof AlternativeTreatmentDto) {
            AlternativeTreatment entity = (AlternativeTreatment) source;

            // flatten entity here
            ((AlternativeTreatmentDto) dto).setAlternativeTreatmentOther(entity.getAlternativeTreatmentOther());
            ((AlternativeTreatmentDto) dto).setAlternativeTreatmentTypeCd(entity.getAlternativeTreatmentTypeCd());
            ((AlternativeTreatmentDto) dto).setMedicalConditionOther(entity.getMedicalConditionOther());
            ((AlternativeTreatmentDto) dto).setMedicalConditionTypeCd(entity.getMedicalConditionTypeCd());
            ((AlternativeTreatmentDto) dto).setEndDate(entity.getEndDate());
            ((AlternativeTreatmentDto) dto).setStartDate(entity.getStartDate());
            if (entity.getPractitioner() != null) {
                ((AlternativeTreatmentDto) dto).setPractitioner(entity.getPractitioner().getId());
            }

        } else {
            throw new MappingException("Converter AlternativeTreatmentCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
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

        try {
        if (source instanceof AlternativeTreatmentDto && entity instanceof AlternativeTreatment) {
            AlternativeTreatmentDto dto = (AlternativeTreatmentDto) source;

            // hydrate entity
            ((AlternativeTreatment) entity).setAlternativeTreatmentOther(dto.getAlternativeTreatmentOther());
            ((AlternativeTreatment) entity).setAlternativeTreatmentTypeCd(dto.getAlternativeTreatmentTypeCd());
            ((AlternativeTreatment) entity).setMedicalConditionOther(dto.getMedicalConditionOther());
            ((AlternativeTreatment) entity).setMedicalConditionTypeCd(dto.getMedicalConditionTypeCd());
            ((AlternativeTreatment) entity).setEndDate(dto.getEndDate());
            ((AlternativeTreatment) entity).setStartDate(dto.getStartDate());
            if (dto.getPractitioner() != null) {
                ((AlternativeTreatment) entity).setPractitioner(practitionerDAO.get(Practitioner.class, dto.getPractitioner()));
            }

        } else {
            throw new MappingException("Converter AlternativeTreatmentCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }
        } catch (PersistenceException e) {
            log.error("Could not retrieve permission entity from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }
        return entity;
    }

    // Spring IoC
    @Autowired
    private PractitionerDAO practitionerDAO;
}