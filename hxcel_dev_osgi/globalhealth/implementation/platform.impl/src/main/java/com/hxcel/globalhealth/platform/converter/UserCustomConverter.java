/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.platform.dao.CountryDAO;
import com.hxcel.globalhealth.platform.dto.UserInfoDto;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.platform.model.UserInfo;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.common.spec.model.enums.CountryCd;
import com.hxcel.globalhealth.platform.dto.UserDto;
import com.hxcel.globalhealth.platform.model.User;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * Converts a User to a UserDto and vice versa. The conversion to dto will be much more frequent. The conversion
 * to user will be used once at the time the user is created.
 */
public class UserCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(UserCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof User && dto instanceof UserDto) {
            User entity = (User) source;

            ((UserDto) dto).setId(entity.getId());
            ((UserDto) dto).setCountry(CountryCd.valueOf(entity.getCountry().getStatusCode()));
            ((UserDto) dto).setUserStatus(entity.getUserStatus());
            ((UserDto) dto).setUsername(entity.getUsername());
            ((UserDto) dto).setPassword(entity.getPassword());
            ((UserDto) dto).setUserInfo((UserInfoDto) mapperIF.map(entity.getUserInfo(), UserInfoDto.class));
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
        if (source instanceof UserDto && entity instanceof User) {
            UserDto dto = (UserDto) source;

            ((User) entity).setUsername(dto.getUsername());

            // onl set the password and the role when the user is being created for the first time!!
            if (StringUtils.isNotBlank(dto.getPassword()) &&
                    StringUtils.isBlank(((User) entity).getPassword()) &&
                    StringUtils.isBlank(entity.getId())) {
                ((User) entity).setPassword(dto.getPassword());
            }
            try {
                ((User) entity).setCountry(countryDAO.getCountry(dto.getCountry()));
            } catch (PersistenceException e) {
                throw new MappingException("Unable to convert country enum to entity", e);
            }
            ((User) entity).setUserInfo((UserInfo) mapperIF.map(dto.getUserInfo(), UserInfo.class));

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }

    @Autowired
    private CountryDAO countryDAO;
}