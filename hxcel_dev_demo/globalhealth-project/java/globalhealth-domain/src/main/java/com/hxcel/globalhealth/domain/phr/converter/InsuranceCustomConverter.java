/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.phr.dao.PhrContactDAO;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.InsuranceDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.Insurance;
import com.hxcel.globalhealth.domain.phr.model.PhrContact;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import net.sf.dozer.util.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * I LOVE MY LIFE!!!!!!!!!!!!!!!!!!!
 */
public class InsuranceCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(InsuranceCustomConverter.class);

    /**
     * Responsible for converting entity data specific to Insurance to InsuranceDto
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrDto toDto(AbstractPhrDto dto, AbstractPhrEntity source, Class destClass, Class sourceClass) {

        if (source instanceof Insurance && dto instanceof InsuranceDto) {
            Insurance entity = (Insurance) source;

            // flatten entity here
            ((InsuranceDto) dto).setInsuranceType(entity.getInsuranceType());
            ((InsuranceDto) dto).setInsuranceGroupNo(entity.getInsuranceGroupNo());
            ((InsuranceDto) dto).setInsuranceNo(entity.getInsuranceNo());
            ((InsuranceDto) dto).setName(entity.getName());

            if (entity.getPhrContact() != null) {
                ((InsuranceDto) dto).setPhrContact(entity.getPhrContact().getId());
            }


        } else {
            throw new MappingException("Converter InsuranceCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * Responsible for converting dto data specific to InsuranceDto to Insurance
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrEntity toEntity(AbstractPhrEntity entity, AbstractPhrDto source, Class destClass, Class sourceClass) {

        try {

            if (source instanceof InsuranceDto && entity instanceof Insurance) {
                InsuranceDto dto = (InsuranceDto) source;

                // hydrate entity
                ((Insurance) entity).setInsuranceType(dto.getInsuranceType());
                ((Insurance) entity).setInsuranceGroupNo(dto.getInsuranceGroupNo());
                ((Insurance) entity).setInsuranceNo(dto.getInsuranceNo());
                ((Insurance) entity).setName(dto.getName());

                if (dto.getPhrContact() != null) {
                    ((Insurance) entity).setPhrContact(phrContactDAO.get(PhrContact.class, dto.getPhrContact()));
                }
            } else {
                throw new MappingException("Converter InsuranceCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
            }

        } catch (PersistenceException e) {
            log.error("Could not retrieve permission entity from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }

        return entity;
    }


    // Spring IoC
    @Autowired
    private PhrContactDAO phrContactDAO;

}