package com.hxcel.globalhealth.domain.patient.converter;

import com.hxcel.globalhealth.domain.common.converter.AbstractConverter;
import com.hxcel.globalhealth.domain.common.dto.*;
import com.hxcel.globalhealth.domain.common.model.*;
import com.hxcel.globalhealth.domain.patient.model.Patient;
import com.hxcel.globalhealth.domain.patient.dto.PatientDto;
import net.sf.dozer.util.mapping.MappingException;

/**
 * User: bjorn
 * Date: Sep 7, 2008
 * Time: 3:50:45 PM
 */
public class PatientCustomConverter extends AbstractConverter {
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Patient && dto instanceof PatientDto) {
            Patient entity = (Patient) source;

            // flatten entity here
            if (entity.getEmr() != null) {
                ((PatientDto) dto).setEmr(entity.getEmr().getId());
            }
            if (entity.getPhr() != null) {
                ((PatientDto) dto).setPhr(entity.getPhr().getId());
            }

            ((PatientDto) dto).setPatientStatus(entity.getPatientStatus());
            ((PatientDto) dto).setPatientType(entity.getPatientType());

            // this will only be there when doctor created the record
            if (entity.getUserInfo() != null) {
                ((PatientDto) dto).setUserInfo((UserInfoDto) mapperIF.map(entity.getUserInfo(), UserInfoDto.class));
            }
        } else {
            throw new MappingException("Converter: " + this.getClass().getCanonicalName() + " was used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {
        if (source instanceof PatientDto && entity instanceof Patient) {
            PatientDto dto = (PatientDto) source;

            // hydrate entity
            ((Patient) entity).setPatientStatus(dto.getPatientStatus());
            ((Patient) entity).setPatientType(dto.getPatientType());

            // this happens only if the professional created the patient
            if (dto.getUserInfo() != null) {
                ((Patient) entity).setUserInfo((UserInfo) mapperIF.map(dto.getUserInfo(), UserInfo.class));
            }

        } else {
            throw new MappingException("Converter PatientCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }

}
