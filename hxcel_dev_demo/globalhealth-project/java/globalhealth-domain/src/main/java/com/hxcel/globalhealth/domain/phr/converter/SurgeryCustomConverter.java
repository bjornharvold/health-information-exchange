/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.phr.dao.PractitionerDAO;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.SurgeryDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.Practitioner;
import com.hxcel.globalhealth.domain.phr.model.Surgery;
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
 * Custom converter for PHR Surgery entity
 */
public class SurgeryCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(DentalCustomConverter.class);

    /**
     * Responsible for converting entity data specific to Surgery to SurgeryDto
     *
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrDto toDto(AbstractPhrDto dto, AbstractPhrEntity source, Class destClass, Class sourceClass) {

        if (source instanceof Surgery && dto instanceof SurgeryDto) {
            Surgery entity = (Surgery) source;

            // flatten entity here
            ((SurgeryDto) dto).setReason(entity.getReason());
            ((SurgeryDto) dto).setSurgeryDate(entity.getSurgeryDate());
            ((SurgeryDto) dto).setSurgeryOther(entity.getSurgeryOther());
            ((SurgeryDto) dto).setSurgeryType(entity.getSurgeryType());

            if (entity.getPractitioner() != null) {
                ((SurgeryDto) dto).setPractitioner(entity.getPractitioner().getId());
            }

        } else {
            throw new MappingException("Converter SurgeryCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * Responsible for converting dto data specific to SurgeryDto to Surgery
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrEntity toEntity(AbstractPhrEntity entity, AbstractPhrDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof SurgeryDto && entity instanceof Surgery) {
                SurgeryDto dto = (SurgeryDto) source;

                // hydrate entity
                ((Surgery) entity).setReason(dto.getReason());
                ((Surgery) entity).setSurgeryDate(dto.getSurgeryDate());
                ((Surgery) entity).setSurgeryType(dto.getSurgeryType());
                ((Surgery) entity).setSurgeryOther(dto.getSurgeryOther());

                if (StringUtils.isNotBlank(dto.getPractitioner())) {
                    ((Surgery) entity).setPractitioner(practitionerDAO.get(Practitioner.class, dto.getPractitioner()));
                }

            } else {
                throw new MappingException("Converter SurgeryCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
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