/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.socialnetwork.converter;

import com.hxcel.globalhealth.domain.common.model.*;
import com.hxcel.globalhealth.domain.common.dto.*;
import com.hxcel.globalhealth.domain.common.converter.AbstractConverter;
import com.hxcel.globalhealth.domain.socialnetwork.model.PublicProfile;
import com.hxcel.globalhealth.domain.socialnetwork.model.Group;
import com.hxcel.globalhealth.domain.socialnetwork.dto.PublicProfileDto;
import com.hxcel.globalhealth.domain.socialnetwork.dto.GroupDto;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import net.sf.dozer.util.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * Custom converter for Group entity
 */
public class GroupCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(GroupCustomConverter.class);

    /**
     * Responsible for converting entity data specific from Group to GroupDto
     *
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {

        if (source instanceof Group && dto instanceof GroupDto) {
            Group entity = (Group) source;

            // flatten entity here
            ((GroupDto) dto).setModerator((UserDto) mapperIF.map(entity.getModerator(), UserDto.class));
            ((GroupDto) dto).setGroupSize(entity.getGroupSize());
            ((GroupDto) dto).setGroupStatus(entity.getGroupStatus());
            ((GroupDto) dto).setGroupType(entity.getGroupType());
            ((GroupDto) dto).setName(entity.getName());
            ((GroupDto) dto).setOwnerEntityId(entity.getOwnerEntityId());
            ((GroupDto) dto).setOwnerEntityType(entity.getOwnerEntityType());
            ((GroupDto) dto).setVisibility(entity.getVisibility());


        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * Responsible for converting dto data specific from GroupDto to Group
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof GroupDto && entity instanceof Group) {
            GroupDto dto = (GroupDto) source;

            // hydrate entity
            ((Group) entity).setModerator((User) mapperIF.map(dto.getModerator(), User.class));
            ((Group) entity).setGroupSize(dto.getGroupSize());
            ((Group) entity).setGroupStatus(dto.getGroupStatus());
            ((Group) entity).setGroupType(dto.getGroupType());
            ((Group) entity).setName(dto.getName());
            ((Group) entity).setOwnerEntityId(dto.getOwnerEntityId());
            ((Group) entity).setOwnerEntityType(dto.getOwnerEntityType());
            ((Group) entity).setVisibility(dto.getVisibility());

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }

}