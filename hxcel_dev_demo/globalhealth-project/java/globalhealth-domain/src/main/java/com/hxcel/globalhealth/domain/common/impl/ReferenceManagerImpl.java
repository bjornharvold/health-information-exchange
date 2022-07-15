/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.impl;

import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.SpecialtyTypeCd;
import com.hxcel.globalhealth.domain.patient.model.enums.PatientStatusCd;
import com.hxcel.globalhealth.domain.common.ReferenceManager;
import com.hxcel.globalhealth.domain.common.dao.CountryDAO;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.common.model.enums.*;
import com.hxcel.globalhealth.domain.emr.model.enums.DiagnoserTypeCd;
import com.hxcel.globalhealth.domain.emr.model.enums.EmrStatusCd;
import com.hxcel.globalhealth.domain.phr.model.enums.*;
import com.hxcel.globalhealth.domain.professional.model.enums.*;
import com.hxcel.globalhealth.domain.professional.model.enums.WaitinglistPriorityCd;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Jan 28, 2007
 * Time: 6:58:52 PM
 */
public class ReferenceManagerImpl implements ReferenceManager {

    public AdvanceDirectiveTypeCd[] getAdvanceDirectiveTypes() {

        return AdvanceDirectiveTypeCd.values();

    }

    public AllergyReactionTypeCd[] getAllergyReactionTypes() {

        return AllergyReactionTypeCd.values();

    }

    public AllergyTypeCd[] getAllergyTypes() {

        return AllergyTypeCd.values();

    }

    public AlternativeTreatmentTypeCd[] getAlternativeTreatmentTypes() {

        return AlternativeTreatmentTypeCd.values();

    }

    public BloodTypeCd[] getBloodTypes() {

        return BloodTypeCd.values();

    }

    public EmrStatusCd[] getCaseStatuses() {

        return EmrStatusCd.values();

    }

    public PatientStatusCd[] getClientStatuses() {

        return PatientStatusCd.values();

    }

    public ContactTypeCd[] getContactTypes() {

        return ContactTypeCd.values();

    }

    public CountryCd[] getCountries() {

        return CountryCd.values();
    }

    public Country getCountry(CountryCd country) throws DomainException {

        try {
            return countryDAO.getCountry(country);
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e, e.getParams());
        }
    }

    public Integer getCountryCount() throws DomainException {

        try {
            return countryDAO.getCountryCount();
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e, e.getParams());
        }
    }

    public CreditcardTypeCd[] getCreditcardTypes() {

        return CreditcardTypeCd.values();

    }

    public DentalExamReasonTypeCd[] getDentalExamReasonTypes() {

        return DentalExamReasonTypeCd.values();

    }

    public DiagnoserTypeCd[] getDiagnosisByTypes() {

        return DiagnoserTypeCd.values();

    }

    public EmailTypeCd[] getEmailTypes() {

        return EmailTypeCd.values();

    }

    public FrequencyTypeCd[] getFrequencyTypes() {

        return FrequencyTypeCd.values();

    }

    public InstantMessageTypeCd[] getInstantMessageTypes() {

        return InstantMessageTypeCd.values();

    }

    public ImmunizationTypeCd[] getImmunizationTypes() {

        return ImmunizationTypeCd.values();

    }

    public LanguageCd[] getLanguages() {

        return LanguageCd.values();

    }

    public LocationTypeCd[] getLocationTypes() {

        return LocationTypeCd.values();

    }

    public MaritalStatusCd[] getMaritalStatuses() {

        return MaritalStatusCd.values();

    }

    public MedicalConditionTypeCd[] getMedicalConditionTypes() {

        return MedicalConditionTypeCd.values();

    }

    public MedicationFrequencyCd[] getMedicationFrequencies() {

        return MedicationFrequencyCd.values();

    }

    public MedicationTypeCd[] getMedicationTypes() {

        return MedicationTypeCd.values();

    }

    public PhoneTypeCd[] getPhoneTypes() {

        return PhoneTypeCd.values();

    }

    public RelationshipStatusCd[] getProfessionalClientStatuses() {

        return RelationshipStatusCd.values();

    }


    public ProfessionalStatusCd[] getProfessionalStatuses() {

        return ProfessionalStatusCd.values();

    }


    public ProfessionalTypeCd[] getProfessionalTypes() {

        return ProfessionalTypeCd.values();

    }


    public WaitinglistPriorityCd[] getProfessionalWaitinglistPriorities() {

        return WaitinglistPriorityCd.values();

    }

    public ProfessionCd[] getProfessions() {

        return ProfessionCd.values();

    }


    public RaceCd[] getRaces() {

        return RaceCd.values();

    }


    public RecordTypeCd[] getRecordTypes() {

        return RecordTypeCd.values();


    }


    public RelativeConditionTypeCd[] getRelativeConditionTypes() {


        return RelativeConditionTypeCd.values();


    }


    public RelativeTypeCd[] getRelativeTypes() {


        return RelativeTypeCd.values();


    }


    public ReligionCd[] getReligions() {


        return ReligionCd.values();


    }


    public SalutationCd[] getSalutations() {


        return SalutationCd.values();


    }


    public SexCd[] getSexes() {


        return SexCd.values();


    }


    public SpecialtyTypeCd[] getSpecialtyTypes() {


        return SpecialtyTypeCd.values();


    }


    public SurgeryTypeCd[] getSurgeryTypes() {


        return SurgeryTypeCd.values();


    }


    public TreatmentTypeCd[] getTreatmentTypes() {


        return TreatmentTypeCd.values();


    }


    public UserStatusCd[] getUserStatus() {


        return UserStatusCd.values();


    }


    public VisionExamReasonTypeCd[] getVisionExamReasonTypes() {


        return VisionExamReasonTypeCd.values();


    }


    public VisionNonPrescriptionGlassesTypeCd[] getVisionNonPrescriptionGlassesTypes() {


        return VisionNonPrescriptionGlassesTypeCd.values();


    }


    public VisionPrescriptionGlassesTypeCd[] getVisionPrescriptionGlassesTypes() {


        return VisionPrescriptionGlassesTypeCd.values();


    }


    public VisionContactLensTypeCd[] getVisionContactLensTypes() {


        return VisionContactLensTypeCd.values();


    }

    public Country saveCountry(Country country) throws PersistenceException {
        return countryDAO.save(country);
    }

    public void saveCountries(List<Country> countries) throws PersistenceException {
        countryDAO.saveOrUpdateAll(countries);
    }

    // Spring IoC
    @Autowired
    private CountryDAO countryDAO;
}
