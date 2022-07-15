/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.dto.OrganizationUserDto;
import com.hxcel.globalhealth.platform.dto.OrganizationDto;
import com.hxcel.globalhealth.platform.dto.UserDto;
import com.hxcel.globalhealth.platform.model.OrganizationUser;
import com.hxcel.globalhealth.platform.model.Organization;
import com.hxcel.globalhealth.platform.model.User;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class OrganizationUserCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(OrganizationUserCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof OrganizationUser && dto instanceof OrganizationUserDto) {
            OrganizationUser entity = (OrganizationUser) source;

            ((OrganizationUserDto) dto).setStatus(entity.getStatus());
            ((OrganizationUserDto) dto).setUser((UserDto) mapperIF.map(entity.getUser(), UserDto.class));
            ((OrganizationUserDto) dto).setOrganization((OrganizationDto) mapperIF.map(entity.getOrganization(), OrganizationDto.class));
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
        if (source instanceof OrganizationUserDto && entity instanceof OrganizationUser) {
            OrganizationUserDto dto = (OrganizationUserDto) source;

            ((OrganizationUser) entity).setStatus(dto.getStatus());
            ((OrganizationUser) entity).setUser((User) mapperIF.map(dto.getUser(), User.class));
            ((OrganizationUser) entity).setOrganization((Organization) mapperIF.map(dto.getOrganization(), Organization.class));

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }
}