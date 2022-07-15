/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.converter;

import net.sf.dozer.util.mapping.converters.CustomConverter;
import net.sf.dozer.util.mapping.MappingException;
import com.hxcel.globalhealth.domain.common.dto.AbstractDto;
import com.hxcel.globalhealth.domain.common.dto.PhoneDto;
import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 21, 2007
 * Time: 3:34:00 PM
 */
public class PhoneCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(PhoneCustomConverter.class);

    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Phone && dto instanceof PhoneDto) {
            Phone entity = (Phone) source;

            // flatten entity here
            ((PhoneDto) dto).setAreaCode(entity.getAreaCode());
            ((PhoneDto) dto).setPhoneTypeCd(entity.getPhoneTypeCd());
            ((PhoneDto) dto).setCountryCode(entity.getCountryCode());
            ((PhoneDto) dto).setNumber(entity.getNumber());

        } else {
            throw new MappingException("Converter PhoneCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof PhoneDto && entity instanceof Phone) {
            PhoneDto dto = (PhoneDto) source;

            // hydrate entity
            ((Phone) entity).setAreaCode(dto.getAreaCode());
            ((Phone) entity).setPhoneTypeCd(dto.getPhoneTypeCd());
            ((Phone) entity).setCountryCode(dto.getCountryCode());
            ((Phone) entity).setNumber(dto.getNumber());

        } else {
            throw new MappingException("Converter PhoneCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }

}