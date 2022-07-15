/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.common.dao.CountryDAO;
import com.hxcel.globalhealth.domain.common.dao.LocationDAO;
import com.hxcel.globalhealth.domain.common.model.Location;
import com.hxcel.globalhealth.domain.common.model.UserInfo;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.PhrLocationDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.PhrLocation;
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
 * Custom converter for PHR Employer entity
 */
public class PhrLocationCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(PhrLocationCustomConverter.class);

    /**
     * Responsible for converting entity data specific to PhrLocation to PhrLocationDto
     *
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrDto toDto(AbstractPhrDto dto, AbstractPhrEntity source, Class destClass, Class sourceClass) {

        if (source instanceof PhrLocation && dto instanceof PhrLocationDto) {
            PhrLocation entity = (PhrLocation) source;

            // flatten entity here
            if (entity.getLocation() != null) {
                ((PhrLocationDto) dto).setLocationId(entity.getLocation().getId());
                ((PhrLocationDto) dto).setAddress1(entity.getLocation().getAddress1());
                ((PhrLocationDto) dto).setAddress2(entity.getLocation().getAddress2());
                ((PhrLocationDto) dto).setAddress3(entity.getLocation().getAddress3());
                ((PhrLocationDto) dto).setCity(entity.getLocation().getCity());
                ((PhrLocationDto) dto).setState(entity.getLocation().getState());
                ((PhrLocationDto) dto).setZip(entity.getLocation().getZip());
                if (entity.getLocation().getCountryCd() != null) {
                    ((PhrLocationDto) dto).setCountry(CountryCd.valueOf(entity.getLocation().getCountryCd().getStatusCode()));
                }
            }

        } else {
            throw new MappingException("Converter PhrLocationCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * Responsible for converting dto data specific to PhrLocationDto to PhrLocation
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrEntity toEntity(AbstractPhrEntity entity, AbstractPhrDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof PhrLocationDto && entity instanceof PhrLocation) {
                PhrLocationDto dto = (PhrLocationDto) source;
                Location location = null;
                UserInfo userInfo = null;

                // hydrate entity
                if (StringUtils.isNotBlank(dto.getLocationId())) {
                    location = locationDAO.get(Location.class, dto.getLocationId());
                } else {
                    location = new Location();
                }

                location.setAddress1(dto.getAddress1());
                location.setAddress2(dto.getAddress2());
                location.setAddress3(dto.getAddress3());
                location.setCity(dto.getCity());
                location.setState(dto.getState());
                location.setZip(dto.getZip());
                if (dto.getCountry() != null) {
                    location.setCountryCd(countryDAO.getCountry(dto.getCountry()));
                }

                ((PhrLocation) entity).setLocation(location);

            } else {
                throw new MappingException("Converter PhrLocationCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
            }
        } catch (PersistenceException e) {
            log.error("Could not retrieve permission entity from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }
        return entity;
    }

    // Spring IoC
    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private CountryDAO countryDAO;
}