/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.phr.dao.PractitionerDAO;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.MedicalConditionDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.MedicalCondition;
import com.hxcel.globalhealth.domain.phr.model.Practitioner;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import net.sf.dozer.util.mapping.MappingException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * I LOVE MY LIFE!!!!!!!!!!!!!!!!!!!
 */
public class MedicalConditionCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(MedicalConditionCustomConverter.class);

    /**
     * Responsible for converting entity data specific to MedicalCondition to MedicalConditionDto
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrDto toDto(AbstractPhrDto dto, AbstractPhrEntity source, Class destClass, Class sourceClass) {

        if (source instanceof MedicalCondition && dto instanceof MedicalConditionDto) {
            MedicalCondition entity = (MedicalCondition) source;

            // flatten entity here
            ((MedicalConditionDto) dto).setCurrentMedicalCondition(entity.getCurrentMedicalCondition());
            ((MedicalConditionDto) dto).setDiagnosisDate(entity.getDiagnosisDate());
            ((MedicalConditionDto) dto).setEndDate(entity.getEndDate());
            ((MedicalConditionDto) dto).setMedicalConditionOther(entity.getMedicalConditionOther());
            ((MedicalConditionDto) dto).setMedicalConditionTypeCd(entity.getMedicalConditionTypeCd());

            if (entity.getPractitioner() != null) {
                ((MedicalConditionDto) dto).setPractitioner(entity.getPractitioner().getId());
            }


        } else {
            throw new MappingException("Converter MedicalConditionCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * Responsible for converting dto data specific to MedicalConditionDto to MedicalCondition
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrEntity toEntity(AbstractPhrEntity entity, AbstractPhrDto source, Class destClass, Class sourceClass) {

        try {

            if (source instanceof MedicalConditionDto && entity instanceof MedicalCondition) {
                MedicalConditionDto dto = (MedicalConditionDto) source;

                // hydrate entity
                ((MedicalCondition) entity).setCurrentMedicalCondition(dto.getCurrentMedicalCondition());
                ((MedicalCondition) entity).setDiagnosisDate(dto.getDiagnosisDate());
                ((MedicalCondition) entity).setEndDate(dto.getEndDate());
                ((MedicalCondition) entity).setMedicalConditionOther(dto.getMedicalConditionOther());
                ((MedicalCondition) entity).setMedicalConditionTypeCd(dto.getMedicalConditionTypeCd());

                if (StringUtils.isNotBlank(dto.getPractitioner())) {
                    ((MedicalCondition) entity).setPractitioner(practitionerDAO.get(Practitioner.class, dto.getPractitioner()));
                }
            } else {
                throw new MappingException("Converter MedicalConditionCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
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