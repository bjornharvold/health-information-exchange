/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.socialnetwork.converter;

import com.hxcel.globalhealth.domain.common.model.*;
import com.hxcel.globalhealth.domain.common.dto.*;
import com.hxcel.globalhealth.domain.common.converter.AbstractConverter;
import com.hxcel.globalhealth.domain.socialnetwork.model.PublicProfile;
import com.hxcel.globalhealth.domain.socialnetwork.dto.PublicProfileDto;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import net.sf.dozer.util.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * Custom converter for PublicProfile entity
 */
public class PublicProfileCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(PublicProfileCustomConverter.class);

    /**
     * Responsible for converting entity data specific to PersonalProfile to PersonaProfileDto
     *
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {

        if (source instanceof PublicProfile && dto instanceof PublicProfileDto) {
            PublicProfile entity = (PublicProfile) source;

            // flatten entity here
            /*if (entity.getUserInfo() != null) {
                ((PublicProfileDto) dto).setUserInfo((UserInfoDto) mapperIF.map(entity.getUserInfo(), UserInfoDto.class));
            }*/

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * Responsible for converting dto data specific to PersonalProfileDto to PersonalProfile
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof PublicProfileDto && entity instanceof PublicProfile) {
            PublicProfileDto dto = (PublicProfileDto) source;

            // hydrate entity
            /*if (dto.getUserInfo() != null) {
                ((PublicProfile) entity).setUserInfo((UserInfo) mapperIF.map(dto.getUserInfo(), UserInfo.class));
            }*/

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }

}