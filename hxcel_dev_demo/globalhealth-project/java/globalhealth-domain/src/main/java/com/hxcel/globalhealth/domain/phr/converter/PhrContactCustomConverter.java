/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.common.model.*;
import com.hxcel.globalhealth.domain.common.dto.*;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.PhrContactDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.PhrContact;
import net.sf.dozer.util.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * Custom converter for PHR PhrContact entity
 */
public class PhrContactCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(PhrContactCustomConverter.class);

    /**
     * Responsible for converting entity data specific to PhrContact to PhrContactDto
     *
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrDto toDto(AbstractPhrDto dto, AbstractPhrEntity source, Class destClass, Class sourceClass) {

        if (source instanceof PhrContact && dto instanceof PhrContactDto) {
            PhrContact entity = (PhrContact) source;

            // flatten entity here
            if (entity.getContact() != null) {

                if (entity.getContact() != null) {
                    ((PhrContactDto) dto).setContact((ContactDto) mapperIF.map(entity.getContact(), ContactDto.class));
                }

            } else {
                throw new MappingException("Converter PhrContactCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
            }
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

        if (source instanceof PhrContactDto && entity instanceof PhrContact) {
            PhrContactDto dto = (PhrContactDto) source;

            // hydrate entity
            if (((PhrContact) entity).getContact() == null) {
                Contact contact = new Contact();
                ((PhrContact) entity).setContact(contact);
            }

            if (dto.getContact() != null) {
                    ((PhrContact) entity).setContact((Contact) mapperIF.map(dto.getContact(), Contact.class));
                }

        } else {
            throw new MappingException("Converter PhrContactCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }
}