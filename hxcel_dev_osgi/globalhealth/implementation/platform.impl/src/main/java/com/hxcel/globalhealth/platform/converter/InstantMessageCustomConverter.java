/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import net.sf.dozer.util.mapping.converters.CustomConverter;
import net.sf.dozer.util.mapping.MappingException;
import com.hxcel.globalhealth.platform.dto.InstantMessageDto;
import com.hxcel.globalhealth.platform.model.InstantMessage;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 21, 2007
 * Time: 3:34:00 PM
 */
public class InstantMessageCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(InstantMessageCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
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

    protected AbstractEntity toEntity(AbstractEntity entity, IAbstractDto source, Class destClass, Class sourceClass) {

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