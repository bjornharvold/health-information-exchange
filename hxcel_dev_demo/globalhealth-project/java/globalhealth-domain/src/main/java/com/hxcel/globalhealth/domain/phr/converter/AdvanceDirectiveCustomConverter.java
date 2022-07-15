/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.phr.dao.PhrContactDAO;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.AdvanceDirectiveDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.AdvanceDirective;
import com.hxcel.globalhealth.domain.phr.model.PhrContact;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import net.sf.dozer.util.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * I LOVE MY LIFE!!!!!!!!!!!!!!!!!!!
 */
public class AdvanceDirectiveCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(AdvanceDirectiveCustomConverter.class);

    /**
     * Responsible for converting entity data specific to AdvanceDirective to AdvanceDirectiveDto
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrDto toDto(AbstractPhrDto dto, AbstractPhrEntity source, Class destClass, Class sourceClass) {

        if (source instanceof AdvanceDirective && dto instanceof AdvanceDirectiveDto) {
            AdvanceDirective entity = (AdvanceDirective) source;

            // flatten entity here
            ((AdvanceDirectiveDto) dto).setAdvanceDirectiveType(entity.getAdvanceDirectiveType());
            if (entity.getPhrContact() != null) {
                ((AdvanceDirectiveDto) dto).setPhrContact(entity.getPhrContact().getId());
            }


        } else {
            throw new MappingException("Converter AdvanceDirectiveCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * Responsible for converting dto data specific to AdvanceDirectiveDto to AdvanceDirective
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrEntity toEntity(AbstractPhrEntity entity, AbstractPhrDto source, Class destClass, Class sourceClass) {

        try {

            if (source instanceof AdvanceDirectiveDto && entity instanceof AdvanceDirective) {
                AdvanceDirectiveDto dto = (AdvanceDirectiveDto) source;

                // hydrate entity
                ((AdvanceDirective) entity).setAdvanceDirectiveType(dto.getAdvanceDirectiveType());

                if (StringUtils.isNotBlank(dto.getPhrContact())) {
                    ((AdvanceDirective) entity).setPhrContact(phrContactDAO.get(PhrContact.class, dto.getPhrContact()));
                }
            } else {
                throw new MappingException("Converter AdvanceDirectiveCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
            }

        } catch (PersistenceException e) {
            log.error("Could not retrieve contact entity from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }

        return entity;
    }


    // Spring IoC
    @Autowired
    private PhrContactDAO phrContactDAO;

}
