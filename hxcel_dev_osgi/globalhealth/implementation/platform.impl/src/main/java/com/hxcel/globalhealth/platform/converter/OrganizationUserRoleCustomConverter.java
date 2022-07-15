/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.dto.*;
import com.hxcel.globalhealth.platform.model.*;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class OrganizationUserRoleCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(OrganizationUserRoleCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof OrganizationUserRole && dto instanceof OrganizationUserRoleDto) {
            OrganizationUserRole entity = (OrganizationUserRole) source;

            ((OrganizationUserRoleDto) dto).setUser((OrganizationUserDto) mapperIF.map(entity.getUser(), OrganizationUserDto.class));
            ((OrganizationUserRoleDto) dto).setRole((RoleDto) mapperIF.map(entity.getRole(), RoleDto.class));
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
        if (source instanceof OrganizationUserRoleDto && entity instanceof OrganizationUserRole) {
            OrganizationUserRoleDto dto = (OrganizationUserRoleDto) source;

            ((OrganizationUserRole) entity).setUser((OrganizationUser) mapperIF.map(dto.getUser(), OrganizationUser.class));
            ((OrganizationUserRole) entity).setRole((Role) mapperIF.map(dto.getRole(), Role.class));

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }
}