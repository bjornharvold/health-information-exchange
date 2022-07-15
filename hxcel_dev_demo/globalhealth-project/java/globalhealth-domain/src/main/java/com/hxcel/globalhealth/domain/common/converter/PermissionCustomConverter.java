/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.converter;

import net.sf.dozer.util.mapping.converters.CustomConverter;
import net.sf.dozer.util.mapping.MappingException;
import com.hxcel.globalhealth.domain.common.dto.AbstractDto;
import com.hxcel.globalhealth.domain.common.dto.PermissionDto;
import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.Permission;
import com.hxcel.globalhealth.domain.professional.dao.ProfessionalDAO;
import com.hxcel.globalhealth.domain.professional.model.Professional;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: bjorn
 * Date: Dec 21, 2007
 * Time: 3:34:00 PM
 */
public class PermissionCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(PermissionCustomConverter.class);

    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Permission && dto instanceof PermissionDto) {
            Permission entity = (Permission) source;

            // flatten entity here
            ((PermissionDto) dto).setEditable(entity.getEditable());
            ((PermissionDto) dto).setRemoveable(entity.getRemoveable());
            ((PermissionDto) dto).setViewable(entity.getViewable());

            ((PermissionDto) dto).setEntityId(entity.getEntityId());
            ((PermissionDto) dto).setEntityType(entity.getEntityType());


        } else {
            throw new MappingException("Converter PermissionCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof PermissionDto && entity instanceof Permission) {
            PermissionDto dto = (PermissionDto) source;

            // hydrate entity
            ((Permission) entity).setEditable(dto.getEditable());
            ((Permission) entity).setRemoveable(dto.getRemoveable());
            ((Permission) entity).setViewable(dto.getViewable());

            ((Permission) entity).setEntityId(dto.getEntityId());
            ((Permission) entity).setEntityType(dto.getEntityType());


        } else {
            throw new MappingException("Converter PermissionCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }

    // Spring IoC
    @Autowired
    private ProfessionalDAO professionalDAO;
}