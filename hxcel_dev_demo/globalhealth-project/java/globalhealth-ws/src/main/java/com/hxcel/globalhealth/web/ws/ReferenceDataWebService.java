/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws;

import com.hxcel.globalhealth.domain.patient.model.enums.PatientStatusCd;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.common.model.enums.*;
import com.hxcel.globalhealth.domain.emr.model.enums.DiagnoserTypeCd;
import com.hxcel.globalhealth.domain.emr.model.enums.EmrStatusCd;
import com.hxcel.globalhealth.domain.phr.model.enums.*;
import com.hxcel.globalhealth.domain.professional.model.enums.*;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.SpecialtyTypeCd;
import com.hxcel.globalhealth.domain.professional.model.enums.WaitinglistPriorityCd;
import org.apache.cxf.interceptor.Fault;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * User: Bjorn Harvold
 * Date: Jul 14, 2007
 * Time: 4:55:23 PM
 */
@WebService
public interface ReferenceDataWebService {
    @WebMethod
    @WebResult(name = "AdvanceDirectiveType")
    AdvanceDirectiveTypeCd[] getAdvanceDirectiveTypes();

    @WebMethod
    @WebResult(name = "AllergyReactionType")
    AllergyReactionTypeCd[] getAllergyReactionTypes();

    @WebMethod
    @WebResult(name = "AllergyType")
    AllergyTypeCd[] getAllergyTypes();

    @WebMethod
    @WebResult(name = "AlternativeTreatmentType")
    AlternativeTreatmentTypeCd[] getAlternativeTreatmentTypes();

    @WebMethod
    @WebResult(name = "BloodType")
    BloodTypeCd[] getBloodTypes();

    @WebMethod
    @WebResult(name = "CaseStatus")
    EmrStatusCd[] getEmrStatuses();

    @WebMethod
    @WebResult(name = "ClientStatus")
    PatientStatusCd[] getClientStatuses();

    @WebMethod
    @WebResult(name = "ContactType")
    ContactTypeCd[] getContactTypes();

    @WebMethod
    @WebResult(name = "Country")
    CountryCd[] getCountries() throws Fault;

    @WebMethod
    @WebResult(name = "Country")
    Country getCountry(String code) throws Fault;

    @WebMethod
    @WebResult(name = "CreditcardType")
    CreditcardTypeCd[] getCreditcardTypes();

    @WebMethod
    @WebResult(name = "DentalExamReasonType")
    DentalExamReasonTypeCd[] getDentalExamReasonTypes();

    @WebMethod
    @WebResult(name = "DiagnoserType")
    DiagnoserTypeCd[] getDiagnoserTypes();

    @WebMethod
    @WebResult(name = "EmailType")
    EmailTypeCd[] getEmailTypes();

    @WebMethod
    @WebResult(name = "FrequencyType")
    FrequencyTypeCd[] getFrequencyTypes();

    @WebMethod
    @WebResult(name = "InstantMessageType")
    InstantMessageTypeCd[] getInstantMessageTypes();

    @WebMethod
    @WebResult(name = "ImmunizationType")
    ImmunizationTypeCd[] getImmunizationTypes();

    @WebMethod
    @WebResult(name = "Language")
    LanguageCd[] getLanguages();

    @WebMethod
    @WebResult(name = "LocationType")
    LocationTypeCd[] getLocationTypes();

    @WebMethod
    @WebResult(name = "MaritalStatus")
    MaritalStatusCd[] getMaritalStatuses();

    @WebMethod
    @WebResult(name = "MedicalConditionType")
    MedicalConditionTypeCd[] getMedicalConditionTypes();

    @WebMethod
    @WebResult(name = "MedicationFrequencie")
    MedicationFrequencyCd[] getMedicationFrequencies();

    @WebMethod
    @WebResult(name = "MedicationType")
    MedicationTypeCd[] getMedicationTypes();
    
    @WebMethod
    @WebResult(name = "PhoneType")
    PhoneTypeCd[] getPhoneTypes();

    @WebMethod
    @WebResult(name = "ProfessionalClientStatus")
    RelationshipStatusCd[] getRelationshipStatuses();

    @WebMethod
    @WebResult(name = "ProfessionalStatus")
    ProfessionalStatusCd[] getProfessionalStatuses();

    @WebMethod
    @WebResult(name = "ProfessionalType")
    ProfessionalTypeCd[] getProfessionalTypes();

    @WebMethod
    @WebResult(name = "WaitinglistPriority")
    WaitinglistPriorityCd[] getWaitinglistPriorities();

    @WebMethod
    @WebResult(name = "Profession")
    ProfessionCd[] getProfessions();

    @WebMethod
    @WebResult(name = "Race")
    RaceCd[] getRaces();

    @WebMethod
    @WebResult(name = "RecordType")
    RecordTypeCd[] getRecordTypes();

    @WebMethod
    @WebResult(name = "RelativeConditionType")
    RelativeConditionTypeCd[] getRelativeConditionTypes();

    @WebMethod
    @WebResult(name = "RelativeType")
    RelativeTypeCd[] getRelativeTypes();

    @WebMethod
    @WebResult(name = "Religion")
    ReligionCd[] getReligions();

    @WebMethod
    @WebResult(name = "Salutation")
    SalutationCd[] getSalutations();

    @WebMethod
    @WebResult(name = "Sex")
    SexCd[] getSexes();

    @WebMethod
    @WebResult(name = "SpecialtyType")
    SpecialtyTypeCd[] getSpecialtyTypes();

    @WebMethod
    @WebResult(name = "SurgeryType")
    SurgeryTypeCd[] getSurgeryTypes();

    @WebMethod
    @WebResult(name = "TreatmentType")
    TreatmentTypeCd[] getTreatmentTypes();

    @WebMethod
    @WebResult(name = "UserStatus")
    UserStatusCd[] getUserStatuses();

    @WebMethod
    @WebResult(name = "VisionExamReasonType")
    VisionExamReasonTypeCd[] getVisionExamReasonTypes();

    @WebMethod
    @WebResult(name = "VisionNonPrescriptionGlassesType")
    VisionNonPrescriptionGlassesTypeCd[] getVisionNonPrescriptionGlassesTypes();

    @WebMethod
    @WebResult(name = "VisionPrescriptionGlassesType")
    VisionPrescriptionGlassesTypeCd[] getVisionPrescriptionGlassesTypes();

    @WebMethod
    @WebResult(name = "VisionContactLensType")
    VisionContactLensTypeCd[] getVisionContactLensTypes();
}
