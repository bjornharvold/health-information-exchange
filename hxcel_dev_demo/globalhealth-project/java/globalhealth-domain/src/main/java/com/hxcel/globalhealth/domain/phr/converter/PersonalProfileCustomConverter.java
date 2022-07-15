/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.common.dao.UserInfoDAO;
import com.hxcel.globalhealth.domain.common.dao.CountryDAO;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.PersonalProfileDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.PersonalProfile;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import net.sf.dozer.util.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * Custom converter for PHR Employer entity
 */
public class PersonalProfileCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(PersonalProfileCustomConverter.class);

    /**
     * Responsible for converting entity data specific to PersonalProfile to PersonaProfileDto
     *
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrDto toDto(AbstractPhrDto dto, AbstractPhrEntity source, Class destClass, Class sourceClass) {

        if (source instanceof PersonalProfile && dto instanceof PersonalProfileDto) {
            PersonalProfile entity = (PersonalProfile) source;

            // flatten entity here
            ((PersonalProfileDto) dto).setBirthPlace(entity.getBirthPlace());
            ((PersonalProfileDto) dto).setBloodTypeCd(entity.getBloodTypeCd());
            ((PersonalProfileDto) dto).setBloodTypeOther(entity.getBloodTypeOther());
            ((PersonalProfileDto) dto).setChildrenNo(entity.getChildrenNo());
            ((PersonalProfileDto) dto).setDob(entity.getDob());
            ((PersonalProfileDto) dto).setDriverLicenseNo(entity.getDriverLicenseNo());
            ((PersonalProfileDto) dto).setEyeColor(entity.getEyeColor());
            ((PersonalProfileDto) dto).setHairColor(entity.getHairColor());
            ((PersonalProfileDto) dto).setHeight(entity.getHeight());
            ((PersonalProfileDto) dto).setIdentifyingMarks(entity.getIdentifyingMarks());
            ((PersonalProfileDto) dto).setMaritalStatusCd(entity.getMaritalStatusCd());
            ((PersonalProfileDto) dto).setMotherMaidenName(entity.getMotherMaidenName());

            if (entity.getNationalityCd() != null) {
                ((PersonalProfileDto) dto).setNationalityCd(CountryCd.valueOf(entity.getNationalityCd().getStatusCode()));
            }

            ((PersonalProfileDto) dto).setPassportNo(entity.getPassportNo());
            ((PersonalProfileDto) dto).setPrimaryLanguageCd(entity.getPrimaryLanguageCd());
            ((PersonalProfileDto) dto).setRaceCd(entity.getRaceCd());
            ((PersonalProfileDto) dto).setReligionCd(entity.getReligionCd());
            ((PersonalProfileDto) dto).setUniqueId(entity.getUniqueId());
            ((PersonalProfileDto) dto).setWeight(entity.getWeight());

        } else {
            throw new MappingException("Converter PersonalProfileCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
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
    protected AbstractPhrEntity toEntity(AbstractPhrEntity entity, AbstractPhrDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof PersonalProfileDto && entity instanceof PersonalProfile) {
                PersonalProfileDto dto = (PersonalProfileDto) source;

                ((PersonalProfile) entity).setBirthPlace(dto.getBirthPlace());
                ((PersonalProfile) entity).setBloodTypeCd(dto.getBloodTypeCd());
                ((PersonalProfile) entity).setBloodTypeOther(dto.getBloodTypeOther());
                ((PersonalProfile) entity).setChildrenNo(dto.getChildrenNo());
                ((PersonalProfile) entity).setDob(dto.getDob());
                ((PersonalProfile) entity).setDriverLicenseNo(dto.getDriverLicenseNo());
                ((PersonalProfile) entity).setEyeColor(dto.getEyeColor());
                ((PersonalProfile) entity).setHairColor(dto.getHairColor());
                ((PersonalProfile) entity).setHeight(dto.getHeight());
                ((PersonalProfile) entity).setIdentifyingMarks(dto.getIdentifyingMarks());
                ((PersonalProfile) entity).setMaritalStatusCd(dto.getMaritalStatusCd());
                ((PersonalProfile) entity).setMotherMaidenName(dto.getMotherMaidenName());

                if (dto.getNationalityCd() != null) {
                    ((PersonalProfile) entity).setNationalityCd(countryDAO.getCountry(dto.getNationalityCd()));
                }

                ((PersonalProfile) entity).setPassportNo(dto.getPassportNo());
                ((PersonalProfile) entity).setPrimaryLanguageCd(dto.getPrimaryLanguageCd());
                ((PersonalProfile) entity).setRaceCd(dto.getRaceCd());
                ((PersonalProfile) entity).setReligionCd(dto.getReligionCd());
                ((PersonalProfile) entity).setUniqueId(dto.getUniqueId());
                ((PersonalProfile) entity).setWeight(dto.getWeight());

            } else {
                throw new MappingException("Converter PersonalProfileCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
            }
        } catch (PersistenceException e) {
            log.error("Could not retrieve userInfo entity from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }
        return entity;
    }

    // Spring IoC
    @Autowired
    private UserInfoDAO userInfoDAO;

    @Autowired
    private CountryDAO countryDAO;
}