/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.converter;

import net.sf.dozer.util.mapping.converters.CustomConverter;
import net.sf.dozer.util.mapping.MappingException;
import com.hxcel.globalhealth.domain.common.dto.AbstractDto;
import com.hxcel.globalhealth.domain.common.dto.EmailDto;
import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 21, 2007
 * Time: 3:34:00 PM
 */
public class EmailCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(EmailCustomConverter.class);

    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Email && dto instanceof EmailDto) {
            Email entity = (Email) source;

            // flatten entity here
            ((EmailDto) dto).setEmail(entity.getEmail());
            ((EmailDto) dto).setEmailTypeCd(entity.getEmailTypeCd());
            ((EmailDto) dto).setPrimary(entity.getPrimary());

        } else {
            throw new MappingException("Converter EmailCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof EmailDto && entity instanceof Email) {
            EmailDto dto = (EmailDto) source;

            // hydrate entity
            ((Email) entity).setEmail(dto.getEmail());
            ((Email) entity).setEmailTypeCd(dto.getEmailTypeCd());
            ((Email) entity).setPrimary(dto.getPrimary());

        } else {
            throw new MappingException("Converter EmailCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }

}
