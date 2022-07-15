/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.phr.dao.PhrContactDAO;
import com.hxcel.globalhealth.domain.phr.dao.PractitionerDAO;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.dto.MedicationDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.Medication;
import com.hxcel.globalhealth.domain.phr.model.PhrContact;
import com.hxcel.globalhealth.domain.phr.model.Practitioner;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import net.sf.dozer.util.mapping.MappingException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 4:21:09 AM
 * I LOVE MY LIFE!!!!!!!!!!!!!!!!!!!
 */
public class MedicationCustomConverter extends AbstractPhrCustomConverter {
    private final static Logger log = LoggerFactory.getLogger(MedicationCustomConverter.class);

    /**
     * Responsible for converting entity data specific to Medication to MedicationDto
     *
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrDto toDto(AbstractPhrDto dto, AbstractPhrEntity source, Class destClass, Class sourceClass) {

        if (source instanceof Medication && dto instanceof MedicationDto) {
            Medication entity = (Medication) source;

            // flatten entity here
            ((MedicationDto) dto).setConditionType(entity.getConditionType());
            ((MedicationDto) dto).setConditionTypeOther(entity.getConditionTypeOther());
            ((MedicationDto) dto).setFrequency(entity.getFrequency());
            ((MedicationDto) dto).setFrequencyOther(entity.getFrequencyOther());
            ((MedicationDto) dto).setMedicationDosage(entity.getMedicationDosage());
            ((MedicationDto) dto).setMedicationTypeCd(entity.getMedicationTypeCd());
            ((MedicationDto) dto).setPrescriptionNo(entity.getPrescriptionNo());
            ((MedicationDto) dto).setTypeOther(entity.getTypeOther());

            if (entity.getPractitioner() != null) {
                ((MedicationDto) dto).setPractitioner(entity.getPractitioner().getId());
            }

            if (entity.getPhrContact() != null) {
                ((MedicationDto) dto).setPhrContact(entity.getPhrContact().getId());
            }


        } else {
            throw new MappingException("Converter MedicationCustomConverter used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * Responsible for converting dto data specific to MedicationDto to Medication
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractPhrEntity toEntity(AbstractPhrEntity entity, AbstractPhrDto source, Class destClass, Class sourceClass) {

        try {

            if (source instanceof MedicationDto && entity instanceof Medication) {
                MedicationDto dto = (MedicationDto) source;

                // hydrate entity
                ((Medication) entity).setConditionType(dto.getConditionType());
                ((Medication) entity).setConditionTypeOther(dto.getConditionTypeOther());
                ((Medication) entity).setFrequency(dto.getFrequency());
                ((Medication) entity).setFrequencyOther(dto.getFrequencyOther());
                ((Medication) entity).setMedicationDosage(dto.getMedicationDosage());
                ((Medication) entity).setMedicationTypeCd(dto.getMedicationTypeCd());
                ((Medication) entity).setPrescriptionNo(dto.getPrescriptionNo());
                ((Medication) entity).setTypeOther(dto.getTypeOther());

                if (StringUtils.isNotBlank(dto.getPractitioner())) {
                    ((Medication) entity).setPractitioner(practitionerDAO.get(Practitioner.class, dto.getPractitioner()));
                }
                if (StringUtils.isNotBlank(dto.getPhrContact())) {
                    ((Medication) entity).setPhrContact(phrContactDAO.get(PhrContact.class, dto.getPhrContact()));
                }
            } else {
                throw new MappingException("Converter MedicationCustomConverter used incorrectly. Arguments passed in were: " + entity + " and " + source);
            }

        } catch (PersistenceException e) {
            log.error("Could not retrieve permission entity from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }

        return entity;
    }


    // Spring IoC
    @Autowired
    private PractitionerDAO practitionerDAO;

    @Autowired
    private PhrContactDAO phrContactDAO;
}