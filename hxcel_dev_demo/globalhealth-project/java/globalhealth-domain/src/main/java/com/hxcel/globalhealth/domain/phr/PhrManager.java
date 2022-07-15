/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr;


import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.phr.dto.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jan 22, 2007
 * Time: 4:27:43 PM
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface PhrManager {
    List<PhrLocationDto> getPhrLocations(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    PhrLocationDto savePhrLocation(PhrLocationDto phrLocation) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removePhrLocation(String id) throws DomainException;

    List<AdvanceDirectiveDto> getAdvanceDirectives(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    AdvanceDirectiveDto saveAdvanceDirective(AdvanceDirectiveDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeAdvanceDirective(String advanceDirectiveId) throws DomainException;

    List<AllergyDto> getAllergies(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    AllergyDto saveAllergy(AllergyDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeAllergy(String allergyId) throws DomainException;

    List<AlternativeTreatmentDto> getAlternativeTreatments(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    AlternativeTreatmentDto saveAlternativeTreatment(AlternativeTreatmentDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeAlternativeTreatment(String alternativeTreatmentId) throws DomainException;

    List<DentalDto> getDentals(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    DentalDto saveDental(DentalDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeDental(String dentalId) throws DomainException;

    List<EmployerDto> getEmployers(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    EmployerDto saveEmployer(EmployerDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeEmployer(String employerId) throws DomainException;

    List<FamilyHistoryDto> getFamilyHistories(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    FamilyHistoryDto saveFamilyHistory(FamilyHistoryDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeFamilyHistory(String familyHistoryId) throws DomainException;

    List<HospitalizedDto> getHospitalizeds(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    HospitalizedDto saveHospitalized(HospitalizedDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeHospitalized(String hospitalizedId) throws DomainException;

    List<ImmunizationDto> getImmunizations(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ImmunizationDto saveImmunization(ImmunizationDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeImmunization(String immunizationId) throws DomainException;

    List<InsuranceDto> getInsurances(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    InsuranceDto saveInsurance(InsuranceDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeInsurance(String inusranceId) throws DomainException;

    List<MedicalConditionDto> getMedicalConditions(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    MedicalConditionDto saveMedicalCondition(MedicalConditionDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeMedicalCondition(String medicalConditionId) throws DomainException;

    List<MedicationDto> getMedications(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    MedicationDto saveMedication(MedicationDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeMedication(String medicationId) throws DomainException;

    List<PhrContactDto> getPhrContacts(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    PhrContactDto savePhrContact(PhrContactDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removePhrContact(String personalProfileContactId) throws DomainException;

    PersonalProfileDto getPersonalProfile(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    PersonalProfileDto savePersonalProfile(PersonalProfileDto value) throws DomainException;

    List<PhrFileDto> getFiles(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    PhrFileDto saveFile(PhrFileDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removePhrFile(String phrFileId, String phrId, String filename) throws DomainException;

    List<PractitionerDto> getPractitioners(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    PractitionerDto savePractitioner(PractitionerDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removePractitioner(String practitionerId) throws DomainException;

    List<SurgeryDto> getSurgeries(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    SurgeryDto saveSurgery(SurgeryDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeSurgery(String surgeryId) throws DomainException;

    List<TravelImmunizationDto> getTravelImmunizations(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    TravelImmunizationDto saveTravelImmunization(TravelImmunizationDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeTravelImmunization(String travelImmunizationId) throws DomainException;

    List<VisionContactLensesDto> getVisionContactLenses(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    VisionContactLensesDto saveVisionContactLenses(VisionContactLensesDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeVisionContactLenses(String visionContactLensesId) throws DomainException;

    List<VisionExamDto> getVisionExams(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    VisionExamDto saveVisionExam(VisionExamDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeVisionExam(String visionExamId) throws DomainException;

    List<VisionGlassesDto> getVisionGlasses(String phrId) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    VisionGlassesDto saveVisionGlasses(VisionGlassesDto value) throws DomainException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void removeVisionGlasses(String visionGlassesId) throws DomainException;
}
