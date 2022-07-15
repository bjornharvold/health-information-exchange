package com.hxcel.globalhealth.platform.spec;

import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;
import com.hxcel.globalhealth.common.spec.model.enums.CountryCd;
import com.hxcel.globalhealth.platform.spec.PlatformException;
import com.hxcel.globalhealth.platform.spec.dto.IRegulationDto;
import com.hxcel.globalhealth.platform.spec.dto.IRegulationOverrideDto;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

/**
 * User: Bjorn Harvold
 * Date: Jan 9, 2009
 * Time: 3:34:57 PM
 * Responsibility:
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface CountryService {
    List<ICountryDto> searchForCountries(String name, Integer index, Integer maxResults) throws PlatformException;

    Integer searchForCountriesCount(String name) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    ICountryDto saveOrUpdateCountry(ICountryDto country) throws PlatformException;

    ICountryDto getCountry(String countryId) throws PlatformException;

    ICountryDto getCountry(CountryCd country) throws PlatformException;

    List<IRegulationDto> searchForRegulations(String name, Integer index, Integer maxResults) throws PlatformException;

    Integer searchForRegulationsCount(String name) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IRegulationDto saveOrUpdateRegulation(IRegulationDto regulation) throws PlatformException;

    IRegulationDto getRegulation(String regulationId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IRegulationDto deleteRegulation(String regulationId) throws PlatformException;

    List<IRegulationOverrideDto> searchForRegulationOverridesByCountry(String countryId, String name, Integer index, Integer maxResults) throws PlatformException;

    Integer searchForRegulationOverridesByCountryCount(String countryId, String name) throws PlatformException;

    IRegulationOverrideDto getRegulationOverride(String regulationOverrideId) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IRegulationOverrideDto saveOrUpdateRegulationOverride(IRegulationOverrideDto override) throws PlatformException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    IRegulationOverrideDto deleteRegulationOverride(String regulationOverrideId) throws PlatformException;

    List<IRegulationDto> getLastModifiedRegulations(Integer maxResults) throws PlatformException;

    List<ICountryDto> getLastModifiedCountries(Integer maxResults) throws PlatformException;

    ICountryDto createCountry();
    IRegulationOverrideDto createRegulationOverride();
    IRegulationDto createRegulation();
}
