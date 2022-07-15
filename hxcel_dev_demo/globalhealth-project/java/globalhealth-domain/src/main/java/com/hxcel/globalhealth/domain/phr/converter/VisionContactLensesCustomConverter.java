/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.phr.dao.PractitionerDAO;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.VisionContactLensesDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.Practitioner;
import com.hxcel.globalhealth.domain.phr.model.VisionContactLenses;
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
 * Custom converter for PHR VisionContactLenses entity
 */
public class VisionContactLensesCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(DentalCustomConverter.class);

    /**
     * Responsible for converting entity data specific to VisionContactLenses to VisionContactLensesDto
     *
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrDto toDto(AbstractPhrDto dto, AbstractPhrEntity source, Class destClass, Class sourceClass) {

        if (source instanceof VisionContactLenses && dto instanceof VisionContactLensesDto) {
            VisionContactLenses entity = (VisionContactLenses) source;

            // flatten entity here
            ((VisionContactLensesDto) dto).setEndDate(entity.getEndDate());
            ((VisionContactLensesDto) dto).setLeftEyeAxis(entity.getLeftEyeAxis());
            ((VisionContactLensesDto) dto).setLeftEyeBc(entity.getLeftEyeBc());
            ((VisionContactLensesDto) dto).setLeftEyeCylinder(entity.getLeftEyeCylinder());
            ((VisionContactLensesDto) dto).setLeftEyePower(entity.getLeftEyePower());
            ((VisionContactLensesDto) dto).setLeftEyeSphere(entity.getLeftEyeSphere());
            ((VisionContactLensesDto) dto).setLensColor(entity.getLensColor());
            ((VisionContactLensesDto) dto).setLensDuration(entity.getLensDuration());
            ((VisionContactLensesDto) dto).setLensType(entity.getLensType());
            ((VisionContactLensesDto) dto).setPairsOrdered(entity.getPairsOrdered());
            ((VisionContactLensesDto) dto).setRightEyeAxis(entity.getRightEyeAxis());
            ((VisionContactLensesDto) dto).setRightEyeBc(entity.getRightEyeBc());
            ((VisionContactLensesDto) dto).setRightEyeCylinder(entity.getRightEyeCylinder());
            ((VisionContactLensesDto) dto).setRightEyePower(entity.getRightEyePower());
            ((VisionContactLensesDto) dto).setRightEyeSphere(entity.getRightEyeSphere());
            ((VisionContactLensesDto) dto).setSolutionUsed(entity.getSolutionUsed());
            ((VisionContactLensesDto) dto).setStartDate(entity.getStartDate());
            ((VisionContactLensesDto) dto).setTypeOther(entity.getTypeOther());

            if (entity.getPractitioner() != null) {
                ((VisionContactLensesDto) dto).setPractitioner(entity.getPractitioner().getId());
            }

        } else {
            throw new MappingException("Converter VisionContactLensesCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * Responsible for converting dto data specific to VisionContactLensesDto to VisionContactLenses
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrEntity toEntity(AbstractPhrEntity entity, AbstractPhrDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof VisionContactLensesDto && entity instanceof VisionContactLenses) {
                VisionContactLensesDto dto = (VisionContactLensesDto) source;

                // hydrate entity
                ((VisionContactLenses) entity).setEndDate(dto.getEndDate());
                ((VisionContactLenses) entity).setLeftEyeAxis(dto.getLeftEyeAxis());
                ((VisionContactLenses) entity).setLeftEyeBc(dto.getLeftEyeBc());
                ((VisionContactLenses) entity).setLeftEyeCylinder(dto.getLeftEyeCylinder());
                ((VisionContactLenses) entity).setLeftEyePower(dto.getLeftEyePower());
                ((VisionContactLenses) entity).setLeftEyeSphere(dto.getLeftEyeSphere());
                ((VisionContactLenses) entity).setLensColor(dto.getLensColor());
                ((VisionContactLenses) entity).setLensDuration(dto.getLensDuration());
                ((VisionContactLenses) entity).setLensType(dto.getLensType());
                ((VisionContactLenses) entity).setPairsOrdered(dto.getPairsOrdered());
                ((VisionContactLenses) entity).setRightEyeAxis(dto.getRightEyeAxis());
                ((VisionContactLenses) entity).setRightEyeBc(dto.getRightEyeBc());
                ((VisionContactLenses) entity).setRightEyeCylinder(dto.getRightEyeCylinder());
                ((VisionContactLenses) entity).setRightEyePower(dto.getRightEyePower());
                ((VisionContactLenses) entity).setRightEyeSphere(dto.getRightEyeSphere());
                ((VisionContactLenses) entity).setSolutionUsed(dto.getSolutionUsed());
                ((VisionContactLenses) entity).setStartDate(dto.getStartDate());
                ((VisionContactLenses) entity).setTypeOther(dto.getTypeOther());

                if (StringUtils.isNotBlank(dto.getPractitioner())) {
                    ((VisionContactLenses) entity).setPractitioner(practitionerDAO.get(Practitioner.class, dto.getPractitioner()));
                }

            } else {
                throw new MappingException("Converter VisionContactLensesCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
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