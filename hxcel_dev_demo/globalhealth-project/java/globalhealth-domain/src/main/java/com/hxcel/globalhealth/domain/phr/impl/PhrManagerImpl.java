/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.impl;

import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.phr.PhrManager;
import com.hxcel.globalhealth.domain.phr.dao.*;
import com.hxcel.globalhealth.domain.phr.dto.*;
import com.hxcel.globalhealth.domain.phr.model.*;
import net.sf.dozer.util.mapping.MapperIF;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jan 22, 2007
 * Time: 4:47:40 PM
 * The manager is responsible for converting dtos to entities and vice versa when the need arises.
 */
public class PhrManagerImpl implements PhrManager {
    private final static Logger log = LoggerFactory.getLogger(PhrManagerImpl.class);

    /**
     * Grabs all phr locations and converts entities to dtos before returning it
     *
     * @param phrId
     * @return
     * @throws DomainException
     */
    public List<PhrLocationDto> getPhrLocations(String phrId) throws DomainException {
        List<PhrLocationDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<PhrLocation> list = phrLocationDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<PhrLocationDto>();

                    for (PhrLocation pl : list) {
                        result.add((PhrLocationDto) mapperIF.map(pl, PhrLocationDto.class));
                    }
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }

        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    /**
     * Converts dto to entity, saves it and returns the new dto
     *
     * @param dto
     * @return
     * @throws DomainException
     */
    public PhrLocationDto savePhrLocation(PhrLocationDto dto) throws DomainException {
        try {
            if (dto != null) {
                PhrLocation pl = (PhrLocation) mapperIF.map(dto, PhrLocation.class);
                pl = phrLocationDAO.saveOrUpdate(pl);
                dto = (PhrLocationDto) mapperIF.map(pl, PhrLocationDto.class);
            } else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
        return dto;
    }

    /**
     * Remove phr location
     *
     * @param id
     * @throws DomainException
     */
    public void removePhrLocation(String id) throws DomainException {
        try {
            if (StringUtils.isNotBlank(id)) {
                phrLocationDAO.delete(id);
            } else {
                throw new DomainException("error.missing.data", "id");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<AdvanceDirectiveDto> getAdvanceDirectives(String phrId) throws DomainException {
        List<AdvanceDirectiveDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<AdvanceDirective> list = advanceDirectiveDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<AdvanceDirectiveDto>();

                    for (AdvanceDirective ad : list) {
                        result.add((AdvanceDirectiveDto) mapperIF.map(ad, AdvanceDirectiveDto.class));
                    }
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public AdvanceDirectiveDto saveAdvanceDirective(AdvanceDirectiveDto dto) throws DomainException {

        try {
            if (dto != null) {
                AdvanceDirective ad = (AdvanceDirective) mapperIF.map(dto, AdvanceDirective.class);
                ad = advanceDirectiveDAO.saveOrUpdate(ad);
                dto = (AdvanceDirectiveDto) mapperIF.map(ad, AdvanceDirectiveDto.class);
            } else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removeAdvanceDirective(String advanceDirectiveId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(advanceDirectiveId)) {
                advanceDirectiveDAO.delete(advanceDirectiveId);
            } else {
                throw new DomainException("error.missing.data", "advanceDirectiveId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<AllergyDto> getAllergies(String phrId) throws DomainException {
        List<AllergyDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<Allergy> list = allergyDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<AllergyDto>();
                    for (Allergy a : list) {
                        result.add((AllergyDto) mapperIF.map(a, AllergyDto.class));
                    }
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public AllergyDto saveAllergy(AllergyDto dto) throws DomainException {
        try {
            if (dto != null) {
                Allergy a = (Allergy) mapperIF.map(dto, Allergy.class);
                a = allergyDAO.saveOrUpdate(a);
                dto = (AllergyDto) mapperIF.map(a, AllergyDto.class);
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removeAllergy(String allergyId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(allergyId)) {
                allergyDAO.delete(allergyId);
            } else {
                throw new DomainException("error.missing.data", "allergyId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<AlternativeTreatmentDto> getAlternativeTreatments(String phrId) throws DomainException {
        List<AlternativeTreatmentDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<AlternativeTreatment> list = alternativeTreatmentDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<AlternativeTreatmentDto>();
                    for (AlternativeTreatment at : list) {
                        result.add((AlternativeTreatmentDto) mapperIF.map(at, AlternativeTreatmentDto.class));
                    }
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public AlternativeTreatmentDto saveAlternativeTreatment(AlternativeTreatmentDto dto) throws DomainException {
        try {
            if (dto != null) {
                AlternativeTreatment at = (AlternativeTreatment) mapperIF.map(dto, AlternativeTreatment.class);
                at = alternativeTreatmentDAO.saveOrUpdate(at);
                dto = (AlternativeTreatmentDto) mapperIF.map(at, AlternativeTreatmentDto.class);
            } else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removeAlternativeTreatment(String alternativeTreatmentId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(alternativeTreatmentId)) {
                alternativeTreatmentDAO.delete(alternativeTreatmentId);
            } else {
                throw new DomainException("error.missing.data", "alternativeTreatmentId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<DentalDto> getDentals(String phrId) throws DomainException {
        List<DentalDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<Dental> list = dentalDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<DentalDto>();

                    for (Dental d : list) {
                        result.add((DentalDto) mapperIF.map(d, DentalDto.class));
                    }
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public DentalDto saveDental(DentalDto dto) throws DomainException {
        try {
            if (dto != null) {
                Dental d = (Dental) mapperIF.map(dto, Dental.class);
                d = dentalDAO.saveOrUpdate(d);
                dto = (DentalDto) mapperIF.map(d, DentalDto.class);
            } else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removeDental(String dentalId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(dentalId)) {
                dentalDAO.delete(dentalId);
            } else {
                throw new DomainException("error.missing.data", "dentalId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<EmployerDto> getEmployers(String phrId) throws DomainException {
        List<EmployerDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<Employer> list = employerDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<EmployerDto>();

                    for (Employer e : list) {
                        result.add((EmployerDto) mapperIF.map(e, EmployerDto.class));
                    }
                }

            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public EmployerDto saveEmployer(EmployerDto dto) throws DomainException {
        try {
            if (dto != null) {
                Employer e = (Employer) mapperIF.map(dto, Employer.class);
                e = employerDAO.saveOrUpdate(e);
                dto = (EmployerDto) mapperIF.map(e, EmployerDto.class);
            } else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removeEmployer(String employerId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(employerId)) {
                employerDAO.delete(employerId);
            } else {
                throw new DomainException("error.missing.data", "employerId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<FamilyHistoryDto> getFamilyHistories(String phrId) throws DomainException {
        List<FamilyHistoryDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<FamilyHistory> list = familyHistoryDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<FamilyHistoryDto>();
                    for (FamilyHistory fh : list) {
                        result.add((FamilyHistoryDto) mapperIF.map(fh, FamilyHistoryDto.class));
                    }
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public FamilyHistoryDto saveFamilyHistory(FamilyHistoryDto dto) throws DomainException {
        try {
            if (dto != null) {
                FamilyHistory fh = (FamilyHistory) mapperIF.map(dto, FamilyHistory.class);
                fh = familyHistoryDAO.saveOrUpdate(fh);
                dto = (FamilyHistoryDto) mapperIF.map(fh, FamilyHistoryDto.class);
            } else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removeFamilyHistory(String familyHistoryId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(familyHistoryId)) {
                familyHistoryDAO.delete(familyHistoryId);
            } else {
                throw new DomainException("error.missing.data", "familyHistoryId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<HospitalizedDto> getHospitalizeds(String phrId) throws DomainException {
        List<HospitalizedDto> result = null;
        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<Hospitalized> list = hospitalizedDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<HospitalizedDto>();
                    for (Hospitalized h : list) {
                        result.add((HospitalizedDto) mapperIF.map(h, HospitalizedDto.class));
                    }
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public HospitalizedDto saveHospitalized(HospitalizedDto dto) throws DomainException {
        try {
            if (dto != null) {
                Hospitalized h = (Hospitalized) mapperIF.map(dto, Hospitalized.class);
                h = hospitalizedDAO.saveOrUpdate(h);
                dto = (HospitalizedDto) mapperIF.map(h, HospitalizedDto.class);
            } else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removeHospitalized(String hospitalizedId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(hospitalizedId)) {
                hospitalizedDAO.delete(hospitalizedId);
            } else {
                throw new DomainException("error.missing.data", "hospitalizedId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<ImmunizationDto> getImmunizations(String phrId) throws DomainException {
        List<ImmunizationDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<Immunization> list = immunizationDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<ImmunizationDto>();

                    for (Immunization i : list) {
                        result.add((ImmunizationDto) mapperIF.map(i, ImmunizationDto.class));
                    }
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public ImmunizationDto saveImmunization(ImmunizationDto dto) throws DomainException {
        try {
            if (dto != null) {
                Immunization i = (Immunization) mapperIF.map(dto, Immunization.class);
                i = immunizationDAO.saveOrUpdate(i);
                dto = (ImmunizationDto) mapperIF.map(i, ImmunizationDto.class);
            } else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removeImmunization(String immunizationId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(immunizationId)) {
                immunizationDAO.delete(immunizationId);
            } else {
                throw new DomainException("error.missing.data", "immunizationId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<InsuranceDto> getInsurances(String phrId) throws DomainException {
        List<InsuranceDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<Insurance> list = insuranceDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<InsuranceDto>();

                    for (Insurance i : list) {
                        result.add((InsuranceDto) mapperIF.map(i, InsuranceDto.class));
                    }
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public InsuranceDto saveInsurance(InsuranceDto dto) throws DomainException {
        try {
            if (dto != null) {
                Insurance i = (Insurance) mapperIF.map(dto, Insurance.class);
                i = insuranceDAO.saveOrUpdate(i);
                dto = (InsuranceDto) mapperIF.map(i, InsuranceDto.class);
            } else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removeInsurance(String insuranceId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(insuranceId)) {
                insuranceDAO.delete(insuranceId);
            } else {
                throw new DomainException("error.missing.data", "insuranceId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<MedicalConditionDto> getMedicalConditions(String phrId) throws DomainException {
        List<MedicalConditionDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<MedicalCondition> list = medicalConditionDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<MedicalConditionDto>();

                    for (MedicalCondition mc : list) {
                        result.add((MedicalConditionDto) mapperIF.map(mc, MedicalConditionDto.class));
                    }
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public MedicalConditionDto saveMedicalCondition(MedicalConditionDto dto) throws DomainException {
        try {
            if (dto != null) {
                MedicalCondition mc = (MedicalCondition) mapperIF.map(dto, MedicalCondition.class);
                mc = medicalConditionDAO.saveOrUpdate(mc);
                dto = (MedicalConditionDto) mapperIF.map(mc, MedicalConditionDto.class);
            } else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
        return dto;
    }

    public void removeMedicalCondition(String medicalConditionId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(medicalConditionId)) {
                medicalConditionDAO.delete(medicalConditionId);
            } else {
                throw new DomainException("error.missing.data", "medicalConditionId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<MedicationDto> getMedications(String phrId) throws DomainException {
        List<MedicationDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<Medication> list = medicationDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<MedicationDto>();

                    for (Medication m : list) {
                        result.add((MedicationDto) mapperIF.map(m, MedicationDto.class));
                    }
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public MedicationDto saveMedication(MedicationDto dto) throws DomainException {
        try {
            if (dto != null) {
                Medication m = (Medication) mapperIF.map(dto, Medication.class);
                m = medicationDAO.saveOrUpdate(m);
                dto = (MedicationDto) mapperIF.map(m, MedicationDto.class);
            } else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removeMedication(String medicationId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(medicationId)) {
                medicationDAO.delete(medicationId);
            } else {
                throw new DomainException("error.missing.data", "medicationId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<PhrContactDto> getPhrContacts(String phrId) throws DomainException {
        List<PhrContactDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<PhrContact> list = phrContactDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<PhrContactDto>();

                    for (PhrContact pc : list) {
                        result.add((PhrContactDto) mapperIF.map(pc, PhrContactDto.class));
                    }
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public PhrContactDto savePhrContact(PhrContactDto dto) throws DomainException {
        try {
            if (dto != null) {
                PhrContact pc = (PhrContact) mapperIF.map(dto, PhrContact.class);
                pc = phrContactDAO.saveOrUpdate(pc);
                dto = (PhrContactDto) mapperIF.map(pc, PhrContactDto.class);
            } else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removePhrContact(String phrContactId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(phrContactId)) {
                phrContactDAO.delete(phrContactId);
            } else {
                throw new DomainException("error.missing.data", "phrContactId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public PersonalProfileDto getPersonalProfile(String phrId) throws DomainException {
        PersonalProfileDto result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                PersonalProfile pp = personalProfileDAO.getRecordsByPhrId(phrId);

                if (pp != null) {
                    result = (PersonalProfileDto) mapperIF.map(pp, PersonalProfileDto.class);
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public PersonalProfileDto savePersonalProfile(PersonalProfileDto dto) throws DomainException {
        try {
            if (dto != null) {
                PersonalProfile pp = (PersonalProfile) mapperIF.map(dto, PersonalProfile.class);
                pp = personalProfileDAO.saveOrUpdate(pp);
                dto = (PersonalProfileDto) mapperIF.map(pp, PersonalProfileDto.class);
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public List<PhrFileDto> getFiles(String phrId) throws DomainException {
        List<PhrFileDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<PhrFile> list = phrFileDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<PhrFileDto>();

                    for (PhrFile pf : list) {
                        result.add((PhrFileDto) mapperIF.map(pf, PhrFileDto.class));
                    }
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public PhrFileDto saveFile(PhrFileDto dto) throws DomainException {
        try {
            if (dto != null) {
                PhrFile pf = (PhrFile) mapperIF.map(dto, PhrFile.class);
                pf = phrFileDAO.saveOrUpdate(pf);
                dto = (PhrFileDto) mapperIF.map(pf, PhrFileDto.class);
            } else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
        return dto;
    }

    public void removePhrFile(String phrFileId, String phrId, String filename) throws DomainException {
        try {
            if (StringUtils.isNotBlank(phrFileId) && StringUtils.isNotBlank(phrId) && StringUtils.isNotBlank(filename)) {
                // db delete
                phrFileDAO.delete(phrFileId);
                // jcr delete
                // phrFileJcrDAO.remove(phrId, filename);
            } else {
                throw new DomainException("error.missing.data", "phrFileId, phrId, filename");
            }
        } catch (PersistenceException e) {
            if (log.isTraceEnabled()) {
                log.trace("Caught PersistenceException: " + e.getMessage(), e);
            }
            throw new DomainException(e.getMessage(), e.getParams());
        }
//        catch (CMSException e) {
//            if (log.isTraceEnabled()) {
//                log.trace("Caught CMSException: " + e.getMessage(), e);
//            }
//            throw new DomainException(e.getMessage(), e.getParams());
//        }
    }

    public List<PractitionerDto> getPractitioners(String phrId) throws DomainException {
        List<PractitionerDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<Practitioner> list = practitionerDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<PractitionerDto>();

                    for (Practitioner p : list) {
                        result.add((PractitionerDto) mapperIF.map(p, PractitionerDto.class));
                    }
                }
            }  else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public PractitionerDto savePractitioner(PractitionerDto dto) throws DomainException {
        try {
            if (dto != null) {
                Practitioner p = (Practitioner) mapperIF.map(dto, Practitioner.class);
                p = practitionerDAO.saveOrUpdate(p);
                dto = (PractitionerDto) mapperIF.map(p, PractitionerDto.class);
            }  else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removePractitioner(String practitionerId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(practitionerId)) {
                practitionerDAO.delete(practitionerId);
            } else {
                throw new DomainException("error.missing.data", "practitionerId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<SurgeryDto> getSurgeries(String phrId) throws DomainException {
        List<SurgeryDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<Surgery> list = surgeryDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<SurgeryDto>();

                    for (Surgery s : list) {
                        result.add((SurgeryDto) mapperIF.map(s, SurgeryDto.class));
                    }
                }
            }  else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public SurgeryDto saveSurgery(SurgeryDto dto) throws DomainException {
        try {
            if (dto != null) {
                Surgery s = (Surgery) mapperIF.map(dto, Surgery.class);
                s = surgeryDAO.saveOrUpdate(s);
                dto = (SurgeryDto) mapperIF.map(s, SurgeryDto.class);
            }  else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
        return dto;
    }

    public void removeSurgery(String surgeryId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(surgeryId)) {
                surgeryDAO.delete(surgeryId);
            }  else {
                throw new DomainException("error.missing.data", "surgeryId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<TravelImmunizationDto> getTravelImmunizations(String phrId) throws DomainException {
        List<TravelImmunizationDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<TravelImmunization> list = travelImmunizationDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<TravelImmunizationDto>();

                    for (TravelImmunization ti : list) {
                        result.add((TravelImmunizationDto) mapperIF.map(ti, TravelImmunizationDto.class));
                    }
                }
            }  else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public TravelImmunizationDto saveTravelImmunization(TravelImmunizationDto dto) throws DomainException {
        try {
            if (dto != null) {
                TravelImmunization ti = (TravelImmunization) mapperIF.map(dto, TravelImmunization.class);
                ti = travelImmunizationDAO.saveOrUpdate(ti);
                dto = (TravelImmunizationDto) mapperIF.map(ti, TravelImmunizationDto.class);
            } else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removeTravelImmunization(String travelImmunizationId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(travelImmunizationId)) {
                travelImmunizationDAO.delete(travelImmunizationId);
            }  else {
                throw new DomainException("error.missing.data", "travelImmunizationId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<VisionContactLensesDto> getVisionContactLenses(String phrId) throws DomainException {
        List<VisionContactLensesDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<VisionContactLenses> list = visionContactLensDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<VisionContactLensesDto>();

                    for (VisionContactLenses v : list) {
                        result.add((VisionContactLensesDto) mapperIF.map(v, VisionContactLensesDto.class));
                    }
                }
            }  else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public VisionContactLensesDto saveVisionContactLenses(VisionContactLensesDto dto) throws DomainException {
        try {
            if (dto != null) {
                VisionContactLenses v = (VisionContactLenses) mapperIF.map(dto, VisionContactLenses.class);
                v = visionContactLensDAO.saveOrUpdate(v);
                dto = (VisionContactLensesDto) mapperIF.map(v, VisionContactLensesDto.class);
            }  else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removeVisionContactLenses(String visionContactLensesId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(visionContactLensesId)) {
                visionContactLensDAO.delete(visionContactLensesId);
            } else {
                throw new DomainException("error.missing.data", "visionContactLensesId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<VisionExamDto> getVisionExams(String phrId) throws DomainException {
        List<VisionExamDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<VisionExam> list = visionExamDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<VisionExamDto>();

                    for (VisionExam v : list) {
                        result.add((VisionExamDto) mapperIF.map(v, VisionExamDto.class));
                    }
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public VisionExamDto saveVisionExam(VisionExamDto dto) throws DomainException {
        try {
            if (dto != null) {
                VisionExam v = (VisionExam) mapperIF.map(dto, VisionExam.class);
                v = visionExamDAO.saveOrUpdate(v);
                dto = (VisionExamDto) mapperIF.map(v, VisionExamDto.class);
            }  else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removeVisionExam(String visionExamId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(visionExamId)) {
                visionExamDAO.delete(visionExamId);
            }  else {
                throw new DomainException("error.missing.data", "visionExamId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    public List<VisionGlassesDto> getVisionGlasses(String phrId) throws DomainException {
        List<VisionGlassesDto> result = null;

        try {
            if (StringUtils.isNotBlank(phrId)) {
                List<VisionGlasses> list = visionGlassesDAO.getRecordsByPhrId(phrId);

                if (list != null && list.size() > 0) {
                    result = new ArrayList<VisionGlassesDto>();

                    for (VisionGlasses v : list) {
                        result.add((VisionGlassesDto) mapperIF.map(v, VisionGlassesDto.class));
                    }
                }
            } else {
                throw new DomainException("error.missing.data", "phrId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return result;
    }

    public VisionGlassesDto saveVisionGlasses(VisionGlassesDto dto) throws DomainException {
        try {
            if (dto != null) {
                VisionGlasses v = (VisionGlasses) mapperIF.map(dto, VisionGlasses.class);
                v = visionGlassesDAO.saveOrUpdate(v);
                dto = (VisionGlassesDto) mapperIF.map(v, VisionGlassesDto.class);
            } else {
                throw new DomainException("error.missing.data", "dto object");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }

        return dto;
    }

    public void removeVisionGlasses(String visionGlassesId) throws DomainException {
        try {
            if (StringUtils.isNotBlank(visionGlassesId)) {
                visionGlassesDAO.delete(visionGlassesId);
            } else {
                throw new DomainException("error.missing.data", "visionGlassesId");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
    }

    // Spring IoC
    @Autowired
    private MapperIF mapperIF;

    @Autowired
    private PhrLocationDAO phrLocationDAO;

    @Autowired
    private AdvanceDirectiveDAO advanceDirectiveDAO;

    @Autowired
    private AllergyDAO allergyDAO;

    @Autowired
    private AlternativeTreatmentDAO alternativeTreatmentDAO;

    @Autowired
    private DentalDAO dentalDAO;

    @Autowired
    private EmployerDAO employerDAO;

    @Autowired
    private FamilyHistoryDAO familyHistoryDAO;

    @Autowired
    private HospitalizedDAO hospitalizedDAO;

    @Autowired
    private ImmunizationDAO immunizationDAO;

    @Autowired
    private InsuranceDAO insuranceDAO;

    @Autowired
    private MedicalConditionDAO medicalConditionDAO;

    @Autowired
    private MedicationDAO medicationDAO;

    @Autowired
    private PhrContactDAO phrContactDAO;

    @Autowired
    private PersonalProfileDAO personalProfileDAO;

    @Autowired
    private PhrFileDAO phrFileDAO;

    @Autowired
    private PractitionerDAO practitionerDAO;

    @Autowired
    private SurgeryDAO surgeryDAO;

    @Autowired
    private TravelImmunizationDAO travelImmunizationDAO;

    @Autowired
    private VisionContactLensDAO visionContactLensDAO;

    @Autowired
    private VisionExamDAO visionExamDAO;

    @Autowired
    private VisionGlassesDAO visionGlassesDAO;

    /*
    @Autowired
    private PHRFileJcrDAO phrFileJcrDAO;
    */
    
    @Autowired
    private PhrDAO phrDAO;


}
