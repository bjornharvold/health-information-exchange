/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import net.sf.dozer.util.mapping.converters.CustomConverter;
import net.sf.dozer.util.mapping.MappingException;
import com.hxcel.globalhealth.platform.dto.LocationDto;
import com.hxcel.globalhealth.platform.model.Location;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.common.spec.model.enums.CountryCd;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.dao.CountryDAO;
import com.hxcel.globalhealth.common.converter.AbstractConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: bjorn
 * Date: Dec 21, 2007
 * Time: 3:34:00 PM
 */
public class LocationCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(LocationCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Location && dto instanceof LocationDto) {
            Location entity = (Location) source;

            // flatten entity here
            ((LocationDto) dto).setAddress1(entity.getAddress1());
            ((LocationDto) dto).setAddress2(entity.getAddress2());
            ((LocationDto) dto).setAddress3(entity.getAddress3());
            ((LocationDto) dto).setCity(entity.getCity());
            ((LocationDto) dto).setState(entity.getState());
            ((LocationDto) dto).setZip(entity.getZip());
            ((LocationDto) dto).setLocationTypeCd(entity.getLocationType());
            ((LocationDto) dto).setPrimary(entity.getPrimary());

            if (entity.getCountry() != null) {
                ((LocationDto) dto).setCountryCd(CountryCd.valueOf(entity.getCountry().getStatusCode()));
            }

        } else {
            throw new MappingException("Converter LocationCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    protected AbstractEntity toEntity(AbstractEntity entity, IAbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof LocationDto && entity instanceof Location) {
            try {
                LocationDto dto = (LocationDto) source;

                // hydrate entity
                ((Location) entity).setAddress1(dto.getAddress1());
                ((Location) entity).setAddress2(dto.getAddress2());
                ((Location) entity).setAddress3(dto.getAddress3());
                ((Location) entity).setCity(dto.getCity());
                ((Location) entity).setState(dto.getState());
                ((Location) entity).setZip(dto.getZip());
                ((Location) entity).setLocationType(dto.getLocationTypeCd());
                ((Location) entity).setPrimary(dto.getPrimary());

                if (dto.getCountryCd() != null) {
                    ((Location) entity).setCountry(countryDAO.getCountry(dto.getCountryCd()));
                }
            } catch (PersistenceException e) {
                log.error("Could not retrieve entity from db: " + e.getMessage(), e);
                throw new MappingException(e.getMessage(), e);
            }
        } else {
            throw new MappingException("Converter LocationCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }

    // Spring IoC
    @Autowired
    private CountryDAO countryDAO;
}