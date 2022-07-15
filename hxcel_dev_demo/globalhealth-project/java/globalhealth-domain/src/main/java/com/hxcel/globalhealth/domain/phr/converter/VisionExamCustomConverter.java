/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.phr.dao.PractitionerDAO;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.VisionExamDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.Practitioner;
import com.hxcel.globalhealth.domain.phr.model.VisionExam;
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
 * Custom converter for PHR VisionExam entity
 */
public class VisionExamCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(DentalCustomConverter.class);

    /**
     * Responsible for converting entity data specific to VisionExam to VisionExamDto
     *
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrDto toDto(AbstractPhrDto dto, AbstractPhrEntity source, Class destClass, Class sourceClass) {

        if (source instanceof VisionExam && dto instanceof VisionExamDto) {
            VisionExam entity = (VisionExam) source;

            // flatten entity here
            ((VisionExamDto) dto).setExamDate(entity.getExamDate());
            ((VisionExamDto) dto).setReasonOther(entity.getReasonOther());
            ((VisionExamDto) dto).setReasonType(entity.getReasonType());
            ((VisionExamDto) dto).setVisionLeftEyeXy(entity.getVisionLeftEyeXy());
            ((VisionExamDto) dto).setVisionRightEyeXy(entity.getVisionRightEyeXy());

            if (entity.getPractitioner() != null) {
                ((VisionExamDto) dto).setPractitioner(entity.getPractitioner().getId());
            }

        } else {
            throw new MappingException("Converter VisionExamCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * Responsible for converting dto data specific to VisionExamDto to VisionExam
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrEntity toEntity(AbstractPhrEntity entity, AbstractPhrDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof VisionExamDto && entity instanceof VisionExam) {
                VisionExamDto dto = (VisionExamDto) source;

                // hydrate entity
                ((VisionExam) entity).setExamDate(dto.getExamDate());
                ((VisionExam) entity).setReasonOther(dto.getReasonOther());
                ((VisionExam) entity).setReasonType(dto.getReasonType());
                ((VisionExam) entity).setVisionLeftEyeXy(dto.getVisionLeftEyeXy());
                ((VisionExam) entity).setVisionRightEyeXy(dto.getVisionRightEyeXy());

                if (StringUtils.isNotBlank(dto.getPractitioner())) {
                    ((VisionExam) entity).setPractitioner(practitionerDAO.get(Practitioner.class, dto.getPractitioner()));
                }

            } else {
                throw new MappingException("Converter VisionExamCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
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