/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.phr.dao.PractitionerDAO;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.VisionGlassesDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.Practitioner;
import com.hxcel.globalhealth.domain.phr.model.VisionGlasses;
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
 * Custom converter for PHR VisionGlasses entity
 */
public class VisionGlassesCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(DentalCustomConverter.class);

    /**
     * Responsible for converting entity data specific to VisionGlasses to VisionGlassesDto
     *
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrDto toDto(AbstractPhrDto dto, AbstractPhrEntity source, Class destClass, Class sourceClass) {

        if (source instanceof VisionGlasses && dto instanceof VisionGlassesDto) {
            VisionGlasses entity = (VisionGlasses) source;

            // flatten entity here
            ((VisionGlassesDto) dto).setAddPowerBifocals(entity.getAddPowerBifocals());
            ((VisionGlassesDto) dto).setEndDate(entity.getEndDate());
            ((VisionGlassesDto) dto).setLeftEyeCylinderPower(entity.getLeftEyeCylinderPower());
            ((VisionGlassesDto) dto).setLeftEyeSpherePower(entity.getLeftEyeSpherePower());
            ((VisionGlassesDto) dto).setNonPrescriptionOther(entity.getNonPrescriptionOther());
            ((VisionGlassesDto) dto).setNonPrescriptionType(entity.getNonPrescriptionType());
            ((VisionGlassesDto) dto).setPrescriptionOther(entity.getPrescriptionOther());
            ((VisionGlassesDto) dto).setPrescriptionType(entity.getPrescriptionType());
            ((VisionGlassesDto) dto).setRightEyeCylinderPower(entity.getRightEyeCylinderPower());
            ((VisionGlassesDto) dto).setRightEyeSpherePower(entity.getRightEyeSpherePower());

            if (entity.getPractitioner() != null) {
                ((VisionGlassesDto) dto).setPractitioner(entity.getPractitioner().getId());
            }

        } else {
            throw new MappingException("Converter VisionGlassesCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * Responsible for converting dto data specific to VisionGlassesDto to VisionGlasses
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrEntity toEntity(AbstractPhrEntity entity, AbstractPhrDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof VisionGlassesDto && entity instanceof VisionGlasses) {
                VisionGlassesDto dto = (VisionGlassesDto) source;

                // hydrate entity
                ((VisionGlasses) entity).setAddPowerBifocals(dto.getAddPowerBifocals());
                ((VisionGlasses) entity).setEndDate(dto.getEndDate());
                ((VisionGlasses) entity).setLeftEyeCylinderPower(dto.getLeftEyeCylinderPower());
                ((VisionGlasses) entity).setLeftEyeSpherePower(dto.getLeftEyeSpherePower());
                ((VisionGlasses) entity).setNonPrescriptionOther(dto.getNonPrescriptionOther());
                ((VisionGlasses) entity).setNonPrescriptionType(dto.getNonPrescriptionType());
                ((VisionGlasses) entity).setPrescriptionOther(dto.getPrescriptionOther());
                ((VisionGlasses) entity).setPrescriptionType(dto.getPrescriptionType());
                ((VisionGlasses) entity).setRightEyeCylinderPower(dto.getRightEyeCylinderPower());
                ((VisionGlasses) entity).setRightEyeSpherePower(dto.getRightEyeSpherePower());

                if (StringUtils.isNotBlank(dto.getPractitioner())) {
                    ((VisionGlasses) entity).setPractitioner(practitionerDAO.get(Practitioner.class, dto.getPractitioner()));
                }

            } else {
                throw new MappingException("Converter VisionGlassesCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
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