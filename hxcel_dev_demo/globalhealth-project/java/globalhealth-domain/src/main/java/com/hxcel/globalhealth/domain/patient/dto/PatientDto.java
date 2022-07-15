package com.hxcel.globalhealth.domain.patient.dto;

import com.hxcel.globalhealth.domain.common.dto.AbstractDto;
import com.hxcel.globalhealth.domain.common.dto.UserInfoDto;
import com.hxcel.globalhealth.domain.patient.model.enums.PatientStatusCd;
import com.hxcel.globalhealth.domain.patient.model.enums.PatientTypeCd;

import java.io.Serializable;

/**
 * User: bjorn
 * Date: Sep 7, 2008
 * Time: 3:45:21 PM
 */
public class PatientDto extends AbstractDto implements Serializable {
    private static final long serialVersionUID = -9200319702424254184L;
    private PatientStatusCd patientStatus;
    private PatientTypeCd patientType;
    private String phr;
    private String emr;
    private UserInfoDto userInfo;

    public PatientStatusCd getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(PatientStatusCd patientStatus) {
        this.patientStatus = patientStatus;
    }

    public PatientTypeCd getPatientType() {
        return patientType;
    }

    public void setPatientType(PatientTypeCd patientType) {
        this.patientType = patientType;
    }

    public String getPhr() {
        return phr;
    }

    public void setPhr(String phr) {
        this.phr = phr;
    }

    public String getEmr() {
        return emr;
    }

    public void setEmr(String emr) {
        this.emr = emr;
    }

    public UserInfoDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDto userInfo) {
        this.userInfo = userInfo;
    }
}
