/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.common.model.*;
import com.hxcel.globalhealth.domain.common.dto.*;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.EmployerDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.Employer;
import net.sf.dozer.util.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * Custom converter for PHR Employer entity
 */
public class EmployerCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(EmployerCustomConverter.class);

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

        if (source instanceof Employer && dto instanceof EmployerDto) {
            Employer entity = (Employer) source;

            // flatten entity here
            if (entity.getLocations() != null) {
                for (Location l : entity.getLocations()) {
                    ((EmployerDto) dto).addLocation((LocationDto) mapperIF.map(l, LocationDto.class));
                }
            }

            if (entity.getUserInfo() != null) {
                ((EmployerDto) dto).setUserInfo((UserInfoDto) mapperIF.map(entity.getUserInfo(), UserInfoDto.class));
            }

            ((EmployerDto) dto).setCompanyName(entity.getCompanyName());
            ((EmployerDto) dto).setEndDate(entity.getEndDate());
            ((EmployerDto) dto).setProfessionCd(entity.getProfessionCd());
            ((EmployerDto) dto).setProfessionOther(entity.getProfessionOther());
            ((EmployerDto) dto).setStartDate(entity.getStartDate());

        } else {
            throw new MappingException("Converter EmployerCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
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

        if (source instanceof EmployerDto && entity instanceof Employer) {
            EmployerDto dto = (EmployerDto) source;
            Location location = null;
            UserInfo userInfo = null;

            // hydrate entity
            if (dto.getLocations() != null && !dto.getLocations().isEmpty()) {
                dto.getLocations().remove(null);
                for (LocationDto lDto : dto.getLocations()) {
                    ((Employer) entity).addLocation((Location) mapperIF.map(lDto, Location.class));
                }
            }

            if (dto.getUserInfo() != null) {
                ((Employer) entity).setUserInfo((UserInfo) mapperIF.map(dto.getUserInfo(), UserInfo.class));
            }

            ((Employer) entity).setCompanyName(dto.getCompanyName());
            ((Employer) entity).setEndDate(dto.getEndDate());
            ((Employer) entity).setProfessionCd(dto.getProfessionCd());
            ((Employer) entity).setProfessionOther(dto.getProfessionOther());
            ((Employer) entity).setStartDate(dto.getStartDate());

        } else {
            throw new MappingException("Converter EmployerCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }
        return entity;
    }
}