/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.common.model.*;
import com.hxcel.globalhealth.domain.common.dto.*;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.PractitionerDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.Practitioner;
import net.sf.dozer.util.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * Custom converter for PHR Practitioner entity
 */
public class PractitionerCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(PractitionerCustomConverter.class);

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

        if (source instanceof Practitioner && dto instanceof PractitionerDto) {
            Practitioner entity = (Practitioner) source;

            // flatten entity here
            if (entity.getContact() != null) {
                ((PractitionerDto) dto).setContact((ContactDto) mapperIF.map(entity.getContact(), ContactDto.class));
            }

            ((PractitionerDto) dto).setTitle(entity.getTitle());

        } else {
            throw new MappingException("Converter PractitionerCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
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

        if (source instanceof PractitionerDto && entity instanceof Practitioner) {
            PractitionerDto dto = (PractitionerDto) source;

            if (dto.getContact() != null) {
                ((Practitioner) entity).setContact((Contact) mapperIF.map(dto.getContact(), Contact.class));
            }

            ((Practitioner) entity).setTitle(dto.getTitle());

        } else {
            throw new MappingException("Converter PractitionerCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }
        return entity;
    }

}