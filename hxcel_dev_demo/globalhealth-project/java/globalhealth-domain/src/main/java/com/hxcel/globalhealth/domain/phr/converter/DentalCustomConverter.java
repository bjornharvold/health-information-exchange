/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.phr.dao.PractitionerDAO;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.DentalDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.Dental;
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
 * Custom converter for PHR Alternative Treatment entity
 */
public class DentalCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(DentalCustomConverter.class);

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

        if (source instanceof Dental && dto instanceof DentalDto) {
            Dental entity = (Dental) source;

            // flatten entity here
            ((DentalDto) dto).setDentalExamReasonOther(entity.getDentalExamReasonOther());
            ((DentalDto) dto).setDentalExamReasonTypeCd(entity.getDentalExamReasonTypeCd());
            ((DentalDto) dto).setExamDate(entity.getExamDate());
            ((DentalDto) dto).setXrayTaken(entity.getXrayTaken());

            if (entity.getPractitioner() != null) {
                ((DentalDto) dto).setPractitioner(entity.getPractitioner().getId());
            }

        } else {
            throw new MappingException("Converter DentalCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
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
            if (source instanceof DentalDto && entity instanceof Dental) {
                DentalDto dto = (DentalDto) source;

                // hydrate entity
                ((Dental) entity).setDentalExamReasonOther(dto.getDentalExamReasonOther());
                ((Dental) entity).setDentalExamReasonTypeCd(dto.getDentalExamReasonTypeCd());
                ((Dental) entity).setExamDate(dto.getExamDate());
                ((Dental) entity).setXrayTaken(dto.getXrayTaken());

                if (StringUtils.isNotBlank(dto.getPractitioner())) {
                    ((Dental) entity).setPractitioner(practitionerDAO.get(Practitioner.class, dto.getPractitioner()));
                }

            } else {
                throw new MappingException("Converter DentalCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
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