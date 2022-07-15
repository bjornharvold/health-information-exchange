/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.PhrFileDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.PhrFile;
import net.sf.dozer.util.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * Custom converter for PHR File entity
 */
public class PhrFileCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(PhrFileCustomConverter.class);

    /**
     * Responsible for converting entity data specific to PhrFile to PhrFileDto
     *
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrDto toDto(AbstractPhrDto dto, AbstractPhrEntity source, Class destClass, Class sourceClass) {

        if (source instanceof PhrFile && dto instanceof PhrFileDto) {
            PhrFile entity = (PhrFile) source;

            // flatten entity here
            ((PhrFileDto) dto).setFileId(entity.getFileId());
            ((PhrFileDto) dto).setFilename(entity.getFilename());
            ((PhrFileDto) dto).setFileSize(entity.getFileSize());

        } else {
            throw new MappingException("Converter PhrFileCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * Responsible for converting dto data specific to PhrFileDto to PhrFile
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrEntity toEntity(AbstractPhrEntity entity, AbstractPhrDto source, Class destClass, Class sourceClass) {

        if (source instanceof PhrFileDto && entity instanceof PhrFile) {
            PhrFileDto dto = (PhrFileDto) source;

            ((PhrFile) entity).setFileId(dto.getFileId());
            ((PhrFile) entity).setFilename(dto.getFilename());
            ((PhrFile) entity).setFileSize(dto.getFileSize());

        } else {
            throw new MappingException("Converter PhrFileCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }

}