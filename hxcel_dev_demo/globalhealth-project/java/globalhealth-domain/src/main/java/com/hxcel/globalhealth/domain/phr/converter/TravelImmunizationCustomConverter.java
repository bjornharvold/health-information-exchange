/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.common.dao.CountryDAO;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.TravelImmunizationDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.TravelImmunization;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import net.sf.dozer.util.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * Custom converter for PHR TravelImmunization entity
 */
public class TravelImmunizationCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(DentalCustomConverter.class);

    /**
     * Responsible for converting entity data specific to TravelImmunization to TravelImmunizationDto
     *
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrDto toDto(AbstractPhrDto dto, AbstractPhrEntity source, Class destClass, Class sourceClass) {

        if (source instanceof TravelImmunization && dto instanceof TravelImmunizationDto) {
            TravelImmunization entity = (TravelImmunization) source;

            // flatten entity here
            ((TravelImmunizationDto) dto).setVaccineDate(entity.getVaccineDate());
            ((TravelImmunizationDto) dto).setVaccineDescription(entity.getVaccineDescription());
            ((TravelImmunizationDto) dto).setVaccineName(entity.getVaccineName());

            if (entity.getCountryCd() != null) {
                ((TravelImmunizationDto) dto).setCountryCd(CountryCd.valueOf(entity.getCountryCd().getStatusCode()));
            }

        } else {
            throw new MappingException("Converter TravelImmunizationCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * Responsible for converting dto data specific to TravelImmunizationDto to TravelImmunization
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrEntity toEntity(AbstractPhrEntity entity, AbstractPhrDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof TravelImmunizationDto && entity instanceof TravelImmunization) {
                TravelImmunizationDto dto = (TravelImmunizationDto) source;

                // hydrate entity
                ((TravelImmunization) entity).setVaccineDate(dto.getVaccineDate());
                ((TravelImmunization) entity).setVaccineDescription(dto.getVaccineDescription());
                ((TravelImmunization) entity).setVaccineName(dto.getVaccineName());

                if (dto.getCountryCd() != null) {
                    ((TravelImmunization) entity).setCountryCd(countryDAO.getCountry(dto.getCountryCd()));
                }

            } else {
                throw new MappingException("Converter TravelImmunizationCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
            }
        } catch (PersistenceException e) {
            log.error("Could not retrieve permission entity from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }
        return entity;
    }

    // Spring IoC
    @Autowired
    private CountryDAO countryDAO;
}