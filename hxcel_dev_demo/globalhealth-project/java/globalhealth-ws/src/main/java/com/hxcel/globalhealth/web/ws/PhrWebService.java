/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws;

import com.hxcel.globalhealth.domain.phr.dto.*;
import org.apache.cxf.interceptor.Fault;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jul 13, 2007
 * Time: 1:41:55 PM
 */
@WebService
public interface PhrWebService {
    @WebMethod
    @WebResult(name = "Locations")
    List<PhrLocationDto> getPhrLocations(@WebParam(name = "phrId")String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "PhrLocation")
    PhrLocationDto savePhrLocation(@WebParam(name = "PhrLocation")PhrLocationDto value) throws Fault;

    @WebMethod
    String removePhrLocation(@WebParam(name = "id")String phrLocationId) throws Fault;

    @WebMethod
    @WebResult(name = "AdvanceDirective")
    List<AdvanceDirectiveDto> getAdvanceDirectives(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "AdvanceDirective")
    AdvanceDirectiveDto saveAdvanceDirective(@WebParam(name = "value") AdvanceDirectiveDto value) throws Fault;

    @WebMethod
    String removeAdvanceDirective(@WebParam(name = "id")String advanceDirectiveId) throws Fault;

    @WebMethod
    @WebResult(name = "Allergy")
    List<AllergyDto> getAllergies(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "Allergy")
    AllergyDto saveAllergy(@WebParam(name = "value")AllergyDto value) throws Fault;

    @WebMethod
    String removeAllergy(@WebParam(name = "id")String allergyId) throws Fault;

    @WebMethod
    @WebResult(name = "AlternativeTreatment")
    List<AlternativeTreatmentDto> getAlternativeTreatments(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "AlternativeTreatment")
    AlternativeTreatmentDto saveAlternativeTreatment(@WebParam(name = "value") AlternativeTreatmentDto value) throws Fault;

    @WebMethod
    String removeAlternativeTreatment(@WebParam(name = "id")String alternativeTreatmentId) throws Fault;

    @WebMethod
    @WebResult(name = "Dentals")
    List<DentalDto> getDentals(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "Dental")
    DentalDto saveDental(@WebParam(name = "value") DentalDto value) throws Fault;

    @WebMethod
    String removeDental(@WebParam(name = "id")String dentalId) throws Fault;

    @WebMethod
    @WebResult(name = "Employers")
    List<EmployerDto> getEmployers(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "Employer")
    EmployerDto saveEmployer(@WebParam(name = "value") EmployerDto value) throws Fault;

    @WebMethod
    String removeEmployer(@WebParam(name = "id")String employerId) throws Fault;

    @WebMethod
    @WebResult(name = "FamilyHistories")
    List<FamilyHistoryDto> getFamilyHistories(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "FamilyHistory")
    FamilyHistoryDto saveFamilyHistory(@WebParam(name = "value") FamilyHistoryDto value) throws Fault;

    @WebMethod
    String removeFamilyHistory(@WebParam(name = "id")String familyHistoryId) throws Fault;

    @WebMethod
    @WebResult(name = "Hospitalizeds")
    List<HospitalizedDto> getHospitalizeds(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "Hospitalized")
    HospitalizedDto saveHospitalized(@WebParam(name = "value") HospitalizedDto value) throws Fault;

    @WebMethod
    String removeHospitalized(@WebParam(name = "id")String hospitalizedId) throws Fault;

    @WebMethod
    @WebResult(name = "Immunizations")
    List<ImmunizationDto> getImmunizations(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "Immunization")
    ImmunizationDto saveImmunization(@WebParam(name = "value") ImmunizationDto value) throws Fault;

    @WebMethod
    String removeImmunization(@WebParam(name = "id")String immunizationId) throws Fault;

    @WebMethod
    @WebResult(name = "Insurances")
    List<InsuranceDto> getInsurances(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "Insurance")
    InsuranceDto saveInsurance(@WebParam(name = "value") InsuranceDto value) throws Fault;

    @WebMethod
    String removeInsurance(@WebParam(name = "id")String insuranceId) throws Fault;

    @WebMethod
    @WebResult(name = "MedicalConditions")
    List<MedicalConditionDto> getMedicalConditions(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "MedicalCondition")
    MedicalConditionDto saveMedicalCondition(@WebParam(name = "value") MedicalConditionDto value) throws Fault;

    @WebMethod
    String removeMedicalCondition(@WebParam(name = "id")String medicalConditionId) throws Fault;

    @WebMethod
    @WebResult(name = "Medications")
    List<MedicationDto> getMedications(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "Medication")
    MedicationDto saveMedication(@WebParam(name = "value") MedicationDto value) throws Fault;

    @WebMethod
    String removeMedication(@WebParam(name = "id")String medicationId) throws Fault;

    @WebMethod
    @WebResult(name = "PhrContacts")
    List<PhrContactDto> getPhrContacts(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "PhrContact")
    PhrContactDto savePhrContact(@WebParam(name = "value")PhrContactDto value) throws Fault;

    @WebMethod
    String removePhrContact(@WebParam(name = "id")String phrContactId) throws Fault;

    @WebMethod
    @WebResult(name = "PersonalProfile")
    PersonalProfileDto getPersonalProfile(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "PersonalProfile")
    PersonalProfileDto savePersonalProfile(@WebParam(name = "value") PersonalProfileDto value) throws Fault;

    @WebMethod
    @WebResult(name = "PhrFiles")
    List<PhrFileDto> getPhrFiles(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "PhrFile")
    PhrFileDto savePhrFile(@WebParam(name = "value") PhrFileDto value) throws Fault;

    @WebMethod
    String removePhrFile(@WebParam(name = "phrFileId")String phrFileId, @WebParam(name = "phrId")String phrId, @WebParam(name = "filename")String filename) throws Fault;

    @WebMethod
    @WebResult(name = "Practitioners")
    List<PractitionerDto> getPractitioners(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "Practitioner")
    PractitionerDto savePractitioner(@WebParam(name = "value") PractitionerDto value) throws Fault;

    @WebMethod
    String removePractitioner(@WebParam(name = "id")String practitionerId) throws Fault;

    @WebMethod
    @WebResult(name = "Surgeries")
    List<SurgeryDto> getSurgeries(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "Surgery")
    SurgeryDto saveSurgery(@WebParam(name = "value") SurgeryDto value) throws Fault;

    @WebMethod
    String removeSurgery(@WebParam(name = "id")String surgeryId) throws Fault;

    @WebMethod
    @WebResult(name = "TravelImmunizations")
    List<TravelImmunizationDto> getTravelImmunizations(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "TravelImmunization")
    TravelImmunizationDto saveTravelImmunization(@WebParam(name = "value") TravelImmunizationDto value) throws Fault;

    @WebMethod
    String removeTravelImmunization(@WebParam(name = "id")String travelImmunizationId) throws Fault;

    @WebMethod
    @WebResult(name = "VisionContactLenses")
    List<VisionContactLensesDto> getVisionContactLenses(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "VisionContactLenses")
    VisionContactLensesDto saveVisionContactLenses(@WebParam(name = "value") VisionContactLensesDto value) throws Fault;

    @WebMethod
    String removeVisionContactLenses(@WebParam(name = "id")String visionContactLensesId) throws Fault;

    @WebMethod
    @WebResult(name = "VisionEyeExams")
    List<VisionExamDto> getVisionExams(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "VisionEyeExam")
    VisionExamDto saveVisionExam(@WebParam(name = "value")VisionExamDto value) throws Fault;

    @WebMethod
    String removeVisionExam(@WebParam(name = "id")String visionExamId) throws Fault;

    @WebMethod
    @WebResult(name = "VisionGlasses")
    List<VisionGlassesDto> getVisionGlasses(@WebParam(name = "phrId") String phrId) throws Fault;

    @WebMethod
    @WebResult(name = "VisionGlasses")
    VisionGlassesDto saveVisionGlasses(@WebParam(name = "value")VisionGlassesDto value) throws Fault;

    @WebMethod
    String removeVisionGlasses(@WebParam(name = "id")String visionGlassesId) throws Fault;
}
