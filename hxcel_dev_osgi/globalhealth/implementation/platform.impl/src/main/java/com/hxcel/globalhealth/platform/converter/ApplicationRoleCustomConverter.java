/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.dto.ApplicationDto;
import com.hxcel.globalhealth.platform.dto.ApplicationRoleDto;
import com.hxcel.globalhealth.platform.dto.RoleDto;
import com.hxcel.globalhealth.platform.model.Application;
import com.hxcel.globalhealth.platform.model.ApplicationRole;
import com.hxcel.globalhealth.platform.model.Role;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class ApplicationRoleCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(ApplicationRoleCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof ApplicationRole && dto instanceof ApplicationRoleDto) {
            ApplicationRole entity = (ApplicationRole) source;

            ((ApplicationRoleDto) dto).setApplication((ApplicationDto) mapperIF.map(entity.getApplication(), ApplicationDto.class));
            ((ApplicationRoleDto) dto).setRole((RoleDto) mapperIF.map(entity.getRole(), RoleDto.class));

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * This method should only get called once by the production code (at the generation of the user with the system)
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractEntity toEntity(AbstractEntity entity, IAbstractDto source, Class destClass, Class sourceClass) {
        if (source instanceof ApplicationRoleDto && entity instanceof ApplicationRole) {
            ApplicationRoleDto dto = (ApplicationRoleDto) source;

            ((ApplicationRole) entity).setApplication((Application) mapperIF.map(dto.getApplication(), Application.class));
            ((ApplicationRole) entity).setRole((Role) mapperIF.map(dto.getRole(), Role.class));

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }
}