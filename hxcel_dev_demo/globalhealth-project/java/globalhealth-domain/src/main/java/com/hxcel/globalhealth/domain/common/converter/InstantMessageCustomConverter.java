/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.converter;

import net.sf.dozer.util.mapping.converters.CustomConverter;
import net.sf.dozer.util.mapping.MappingException;
import com.hxcel.globalhealth.domain.common.dto.AbstractDto;
import com.hxcel.globalhealth.domain.common.dto.InstantMessageDto;
import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.InstantMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 21, 2007
 * Time: 3:34:00 PM
 */
public class InstantMessageCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(InstantMessageCustomConverter.class);

    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof InstantMessage && dto instanceof InstantMessageDto) {
            InstantMessage entity = (InstantMessage) source;

            // flatten entity here
            ((InstantMessageDto) dto).setNickname(entity.getNickname());
            ((InstantMessageDto) dto).setInstantMessageTypeCd(entity.getInstantMessageTypeCd());

        } else {
            throw new MappingException("Converter InstantMessageCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof InstantMessageDto && entity instanceof InstantMessage) {
            InstantMessageDto dto = (InstantMessageDto) source;

            // hydrate entity
            ((InstantMessage) entity).setNickname(dto.getNickname());
            ((InstantMessage) entity).setInstantMessageTypeCd(dto.getInstantMessageTypeCd());

        } else {
            throw new MappingException("Converter InstantMessageCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }

}