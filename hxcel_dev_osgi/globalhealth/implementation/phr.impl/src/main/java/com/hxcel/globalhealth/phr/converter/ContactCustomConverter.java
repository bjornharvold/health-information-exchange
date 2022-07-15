package com.hxcel.globalhealth.phr.converter;

import com.hxcel.globalhealth.common.dto.LocationDto;
import com.hxcel.globalhealth.common.dto.UserInfoDto;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.common.model.Location;
import com.hxcel.globalhealth.common.model.UserInfo;
import com.hxcel.globalhealth.phr.model.Contact;
import com.hxcel.globalhealth.phr.dto.ContactDto;

/**
 * User: bjorn
 * Date: Sep 7, 2008
 * Time: 4:33:51 PM
 */
public class ContactCustomConverter extends AbstractConverter {
    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Contact && dto instanceof ContactDto) {
            Contact entity = (Contact) source;

            ((ContactDto) dto).setContactType(entity.getContactType());
            ((ContactDto) dto).setContactTypeOther(entity.getContactTypeOther());

            if (entity.getLocations() != null) {
                for (Location location : entity.getLocations()) {
                    ((ContactDto) dto).addLocation((LocationDto) mapperIF.map(location, LocationDto.class));
                }
            }

            if (entity.getUserInfo() != null) {
                ((ContactDto) dto).setUserInfo((UserInfoDto) mapperIF.map(entity.getUserInfo(), UserInfoDto.class));
            }
        }

        return dto;
    }

    protected AbstractEntity toEntity(AbstractEntity entity, IAbstractDto source, Class destClass, Class sourceClass) {
        if (source instanceof ContactDto && entity instanceof Contact) {
            ContactDto dto = (ContactDto) source;

            ((Contact)entity).setContactType(dto.getContactType());
            ((Contact)entity).setContactTypeOther(dto.getContactTypeOther());

            if (dto.getUserInfo() != null) {
                ((Contact)entity).setUserInfo((UserInfo) mapperIF.map(dto.getUserInfo(), UserInfo.class));
            }

            if (dto.getLocations() != null) {
                for (LocationDto location : dto.getLocations()) {
                    ((Contact)entity).addLocation((Location) mapperIF.map(location, Location.class));
                }
            }

        }

        return entity;
    }
}
