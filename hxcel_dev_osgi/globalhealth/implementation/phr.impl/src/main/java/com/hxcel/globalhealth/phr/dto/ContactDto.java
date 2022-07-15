package com.hxcel.globalhealth.phr.dto;

import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.common.dto.UserInfoDto;
import com.hxcel.globalhealth.common.dto.LocationDto;
import com.hxcel.globalhealth.phr.model.enums.ContactTypeCd;

import java.util.List;
import java.util.ArrayList;

/**
 * User: bjorn
 * Date: Sep 7, 2008
 * Time: 6:02:42 PM
 */
public class ContactDto extends AbstractDto {
    private UserInfoDto userInfo;
    private List<LocationDto> locations;
    private ContactTypeCd contactType;
    private String contactTypeOther;

    public UserInfoDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDto userInfo) {
        this.userInfo = userInfo;
    }

    public List<LocationDto> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationDto> locations) {
        this.locations = locations;
    }

    public ContactTypeCd getContactType() {
        return contactType;
    }

    public void setContactType(ContactTypeCd contactType) {
        this.contactType = contactType;
    }

    public String getContactTypeOther() {
        return contactTypeOther;
    }

    public void setContactTypeOther(String contactTypeOther) {
        this.contactTypeOther = contactTypeOther;
    }

    public void addLocation(LocationDto dto) {
        if (locations == null) {
            locations = new ArrayList<LocationDto>();
        }

        locations.add(dto);
    }
}
