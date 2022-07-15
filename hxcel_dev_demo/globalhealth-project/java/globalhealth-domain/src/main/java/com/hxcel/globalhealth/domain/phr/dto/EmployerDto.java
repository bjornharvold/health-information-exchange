/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.common.dto.*;
import com.hxcel.globalhealth.domain.phr.model.enums.ProfessionCd;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * User: bjorn
 * Date: Dec 9, 2007
 * Time: 2:58:37 AM
 */
public class EmployerDto extends AbstractPhrDto{
    private ProfessionCd professionCd;
    private String companyName;
    private Date startDate;
    private Date endDate;
    private String professionOther;
    private UserInfoDto userInfo;
    private List<LocationDto> locations;

    public ProfessionCd getProfessionCd() {
        return professionCd;
    }

    public void setProfessionCd(ProfessionCd professionCd) {
        this.professionCd = professionCd;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getProfessionOther() {
        return professionOther;
    }

    public void setProfessionOther(String professionOther) {
        this.professionOther = professionOther;
    }

    public void addLocation(LocationDto dto) {
        if (locations == null) {
            locations = new ArrayList<LocationDto>();
        }

        locations.add(dto);
    }

    public List<LocationDto> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationDto> locations) {
        this.locations = locations;
    }

    public UserInfoDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDto userInfo) {
        this.userInfo = userInfo;
    }
}
