/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.common.converter;

import net.sf.dozer.util.mapping.converters.CustomConverter;
import net.sf.dozer.util.mapping.MappingException;
import com.hxcel.globalhealth.common.dto.PermissionDto;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.common.model.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 21, 2007
 * Time: 3:34:00 PM
 */
public class PermissionCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(PermissionCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
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

    protected AbstractEntity toEntity(AbstractEntity entity, IAbstractDto source, Class destClass, Class sourceClass) {

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

}