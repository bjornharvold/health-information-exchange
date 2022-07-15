/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common;

import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.SpecialtyTypeCd;
import com.hxcel.globalhealth.domain.patient.model.enums.PatientStatusCd;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.common.model.enums.*;
import com.hxcel.globalhealth.domain.emr.model.enums.DiagnoserTypeCd;
import com.hxcel.globalhealth.domain.emr.model.enums.EmrStatusCd;
import com.hxcel.globalhealth.domain.phr.model.enums.*;
import com.hxcel.globalhealth.domain.professional.model.enums.*;
import com.hxcel.globalhealth.domain.professional.model.enums.WaitinglistPriorityCd;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jul 6, 2007
 * Time: 4:48:50 PM
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface ReferenceManager {
    AdvanceDirectiveTypeCd[] getAdvanceDirectiveTypes();

    AllergyReactionTypeCd[] getAllergyReactionTypes();

    AllergyTypeCd[] getAllergyTypes();

    AlternativeTreatmentTypeCd[] getAlternativeTreatmentTypes();

    BloodTypeCd[] getBloodTypes();

    EmrStatusCd[] getCaseStatuses();

    PatientStatusCd[] getClientStatuses();

    ContactTypeCd[] getContactTypes();

    CountryCd[] getCountries();

    Integer getCountryCount() throws DomainException;

    Country getCountry(CountryCd country) throws DomainException;

    CreditcardTypeCd[] getCreditcardTypes();

    DentalExamReasonTypeCd[] getDentalExamReasonTypes();

    DiagnoserTypeCd[] getDiagnosisByTypes();

    EmailTypeCd[] getEmailTypes();

    FrequencyTypeCd[] getFrequencyTypes();

    InstantMessageTypeCd[] getInstantMessageTypes();

    ImmunizationTypeCd[] getImmunizationTypes();

    LanguageCd[] getLanguages();

    LocationTypeCd[] getLocationTypes();

    MaritalStatusCd[] getMaritalStatuses();

    MedicalConditionTypeCd[] getMedicalConditionTypes();

    MedicationFrequencyCd[] getMedicationFrequencies();

    MedicationTypeCd[] getMedicationTypes();

    PhoneTypeCd[] getPhoneTypes();

    RelationshipStatusCd[] getProfessionalClientStatuses();

    ProfessionalStatusCd[] getProfessionalStatuses();

    ProfessionalTypeCd[] getProfessionalTypes();

    WaitinglistPriorityCd[] getProfessionalWaitinglistPriorities();

    ProfessionCd[] getProfessions();

    RaceCd[] getRaces();

    RecordTypeCd[] getRecordTypes();

    RelativeConditionTypeCd[] getRelativeConditionTypes();

    RelativeTypeCd[] getRelativeTypes();

    ReligionCd[] getReligions();

    SalutationCd[] getSalutations();

    SexCd[] getSexes();

    SpecialtyTypeCd[] getSpecialtyTypes();

    SurgeryTypeCd[] getSurgeryTypes();

    TreatmentTypeCd[] getTreatmentTypes();

    UserStatusCd[] getUserStatus();

    VisionExamReasonTypeCd[] getVisionExamReasonTypes();

    VisionNonPrescriptionGlassesTypeCd[] getVisionNonPrescriptionGlassesTypes();

    VisionPrescriptionGlassesTypeCd[] getVisionPrescriptionGlassesTypes();

    VisionContactLensTypeCd[] getVisionContactLensTypes();

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Country saveCountry(Country country) throws PersistenceException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveCountries(List<Country> countries) throws PersistenceException;
}
