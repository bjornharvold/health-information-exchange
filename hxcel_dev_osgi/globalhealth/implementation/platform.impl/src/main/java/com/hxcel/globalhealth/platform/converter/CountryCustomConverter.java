/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import net.sf.dozer.util.mapping.converters.CustomConverter;
import net.sf.dozer.util.mapping.MappingException;
import com.hxcel.globalhealth.platform.dto.CountryDto;
import com.hxcel.globalhealth.platform.model.Country;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.common.converter.AbstractConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 21, 2007
 * Time: 3:34:00 PM
 */
public class CountryCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(CountryCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Country && dto instanceof CountryDto) {
            Country entity = (Country) source;

            // flatten entity here
            ((CountryDto) dto).setCountryCode(entity.getCountryCode());
            ((CountryDto) dto).setCurrencyCode(entity.getCurrencyCode());
            ((CountryDto) dto).setCurrencyCodeNumber(entity.getCurrencyCodeNumber());
            ((CountryDto) dto).setCurrencyName(entity.getCurrencyName());
            ((CountryDto) dto).setDateFormatPattern(entity.getDateFormatPattern());
            ((CountryDto) dto).setExchangeDate(entity.getExchangeDate());
            ((CountryDto) dto).setExchangeRate(entity.getExchangeRate());
            ((CountryDto) dto).setLanguage(entity.getLanguage());
            ((CountryDto) dto).setStatusCode(entity.getStatusCode());
            ((CountryDto) dto).setTimeFormatPattern(entity.getTimeFormatPattern());
            ((CountryDto) dto).setTimestampFormatPattern(entity.getTimestampFormatPattern());

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    protected AbstractEntity toEntity(AbstractEntity entity, IAbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof CountryDto && entity instanceof Country) {
            CountryDto dto = (CountryDto) source;

            // hydrate entity
            ((Country) entity).setCountryCode(dto.getCountryCode());
            ((Country) entity).setCurrencyCode(dto.getCurrencyCode());
            ((Country) entity).setCurrencyCodeNumber(dto.getCurrencyCodeNumber());
            ((Country) entity).setCurrencyName(dto.getCurrencyName());
            ((Country) entity).setDateFormatPattern(dto.getDateFormatPattern());
            ((Country) entity).setExchangeDate(dto.getExchangeDate());
            ((Country) entity).setExchangeRate(dto.getExchangeRate());
            ((Country) entity).setLanguage(dto.getLanguage());
            ((Country) entity).setStatusCode(dto.getStatusCode());
            ((Country) entity).setTimeFormatPattern(dto.getTimeFormatPattern());
            ((Country) entity).setTimestampFormatPattern(dto.getTimestampFormatPattern());

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }

}