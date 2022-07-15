/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.dto.UserDto;
import com.hxcel.globalhealth.platform.dto.UserRoleDto;
import com.hxcel.globalhealth.platform.dto.RoleDto;
import com.hxcel.globalhealth.platform.model.User;
import com.hxcel.globalhealth.platform.model.UserRole;
import com.hxcel.globalhealth.platform.model.Role;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class UserRoleCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(UserRoleCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof UserRole && dto instanceof UserRoleDto) {
            UserRole entity = (UserRole) source;

            ((UserRoleDto) dto).setUser((UserDto) mapperIF.map(entity.getUser(), UserDto.class));
            ((UserRoleDto) dto).setRole((RoleDto) mapperIF.map(entity.getRole(), RoleDto.class));

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
        if (source instanceof UserRoleDto && entity instanceof UserRole) {
            UserRoleDto dto = (UserRoleDto) source;

            ((UserRole) entity).setUser((User) mapperIF.map(dto.getUser(), User.class));
            ((UserRole) entity).setRole((Role) mapperIF.map(dto.getRole(), Role.class));

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }
}