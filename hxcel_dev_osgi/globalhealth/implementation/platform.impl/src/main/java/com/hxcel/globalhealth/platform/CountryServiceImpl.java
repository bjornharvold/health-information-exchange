/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform;

import com.hxcel.globalhealth.platform.dto.*;
import com.hxcel.globalhealth.platform.model.*;
import com.hxcel.globalhealth.platform.spec.PlatformException;
import com.hxcel.globalhealth.platform.spec.CountryService;
import com.hxcel.globalhealth.platform.spec.dto.*;
import com.hxcel.globalhealth.platform.dao.*;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;
import com.hxcel.globalhealth.common.spec.model.enums.CountryCd;
import com.hxcel.globalhealth.platform.model.Country;
import com.hxcel.globalhealth.platform.dao.CountryDAO;
import com.hxcel.globalhealth.platform.dto.CountryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

import net.sf.dozer.util.mapping.MapperIF;

/**
 * User: bjorn
 * Date: Jun 5, 2008
 * Time: 6:05:18 PM
 */
@SuppressWarnings(value = "unchecked")
public class CountryServiceImpl implements CountryService {
    private final static Logger log = LoggerFactory.getLogger(CountryServiceImpl.class);

    @Override
    public List<ICountryDto> searchForCountries(String name, Integer index, Integer maxResults) throws PlatformException {
        List<ICountryDto> result = null;

        try {
            List<Country> list = countryDAO.searchForCountries(name, index, maxResults);

            if (list != null) {
                result = new ArrayList<ICountryDto>();

                for (Country c : list) {
                    result.add((ICountryDto) mapperIF.map(c, CountryDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchForCountriesCount(String name) throws PlatformException {
        Integer result = null;

        try {
            result = countryDAO.searchForCountriesCount(name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public ICountryDto saveOrUpdateCountry(ICountryDto country) throws PlatformException {
        try {
            // convert to entity
            Country c = (Country) mapperIF.map(country, Country.class);

            // save entity
            c = countryDAO.saveOrUpdate(c);

            // convert back to dto
            country = (ICountryDto) mapperIF.map(c, CountryDto.class);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return country;
    }

    @Override
    public ICountryDto getCountry(String countryId) throws PlatformException {
        ICountryDto result = null;

        if (StringUtils.isBlank(countryId)) {
            log.error("countryId is null");
            throw new PlatformException("error.missing.argument.exception", "countryId");
        }

        try {
            Country c = countryDAO.get(Country.class, countryId);

            // convert back to dto
            result = (ICountryDto) mapperIF.map(c, CountryDto.class);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public ICountryDto getCountry(CountryCd country) throws PlatformException {
        ICountryDto result = null;

        if (country == null) {
            log.error("country is null");
            throw new PlatformException("error.missing.argument.exception", "country");
        }

        try {
            Country c = countryDAO.getCountry(country);

            if (c != null) {
                // convert back to dto
                result = (ICountryDto) mapperIF.map(c, CountryDto.class);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IRegulationDto> searchForRegulations(String name, Integer index, Integer maxResults) throws PlatformException {
        List<IRegulationDto> result = null;

        try {
            List<Regulation> list = regulationDAO.searchForRegulations(name, index, maxResults);

            if (list != null) {
                result = new ArrayList<IRegulationDto>();

                for (Regulation r : list) {
                    result.add((IRegulationDto) mapperIF.map(r, RegulationDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchForRegulationsCount(String name) throws PlatformException {
        Integer result = null;

        try {
            result = regulationDAO.searchForRegulationsCount(name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IRegulationDto saveOrUpdateRegulation(IRegulationDto regulation) throws PlatformException {
        try {
            // convert to entity first
            Regulation r = (Regulation) mapperIF.map(regulation, Regulation.class);

            r = regulationDAO.saveOrUpdate(r);

            // convert back to dto here
            regulation = (IRegulationDto) mapperIF.map(r, RegulationDto.class);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return regulation;
    }

    @Override
    public IRegulationDto getRegulation(String regulationId) throws PlatformException {
        IRegulationDto result = null;

        if (StringUtils.isBlank(regulationId)) {
            log.error("regulationId is null");
            throw new PlatformException("error.missing.argument.exception", "regulationId");
        }

        try {
            Regulation r = regulationDAO.get(Regulation.class, regulationId);

            if (r != null) {
                result = (IRegulationDto) mapperIF.map(r, RegulationDto.class);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IRegulationDto deleteRegulation(String regulationId) throws PlatformException {
        IRegulationDto result = null;

        if (StringUtils.isBlank(regulationId)) {
            log.error("regulationId is null");
            throw new PlatformException("error.missing.argument.exception", "regulationId");
        }

        try {
            Regulation r = regulationDAO.get(Regulation.class, regulationId);

            if (r != null) {
                // first we have to delete the overrides
                List<RegulationOverride> list = regulationOverrideDAO.getOverridesByRegulationId(regulationId);
                regulationOverrideDAO.deleteAll(list);

                // convert to dto here
                result = (IRegulationDto) mapperIF.map(r, RegulationDto.class);

                // now we can delete the regulation
                regulationDAO.delete(r);
            } else {
                log.error("Regulation with ID: " + regulationId + " does not exist");
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;

    }

    @Override
    public List<IRegulationOverrideDto> searchForRegulationOverridesByCountry(String countryId, String name, Integer index, Integer maxResults) throws PlatformException {
        List<IRegulationOverrideDto> result = null;

        if (StringUtils.isBlank(countryId)) {
            log.error("countryId is null");
            throw new PlatformException("error.missing.argument.exception", "countryId");
        }

        try {
            List<RegulationOverride> list = regulationOverrideDAO.searchForRegulationOverridesByCountry(countryId, name, index, maxResults);

            if (list != null) {
                result = new ArrayList<IRegulationOverrideDto>();

                for (RegulationOverride ru : list) {
                    result.add((IRegulationOverrideDto) mapperIF.map(ru, RegulationOverrideDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Integer searchForRegulationOverridesByCountryCount(String countryId, String name) throws PlatformException {
        Integer result = null;

        if (StringUtils.isBlank(countryId)) {
            log.error("countryId is null");
            throw new PlatformException("error.missing.argument.exception", "countryId");
        }

        try {
            result = regulationOverrideDAO.searchForRegulationOverridesByCountryCount(countryId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IRegulationOverrideDto getRegulationOverride(String regulationOverrideId) throws PlatformException {
        IRegulationOverrideDto result = null;

        if (StringUtils.isBlank(regulationOverrideId)) {
            log.error("regulationOverrideId is null");
            throw new PlatformException("error.missing.argument.exception", "regulationOverrideId");
        }

        try {
            RegulationOverride r = regulationOverrideDAO.get(RegulationOverride.class, regulationOverrideId);

            // convert to dto here
            result = (IRegulationOverrideDto) mapperIF.map(r, RegulationOverrideDto.class);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public IRegulationOverrideDto saveOrUpdateRegulationOverride(IRegulationOverrideDto override) throws PlatformException {
        if (override == null) {
            log.error("regulationOverride is null");
            throw new PlatformException("error.missing.argument.exception", "regulationOverride");
        }

        try {
            // convert to entity here
            RegulationOverride ro = (RegulationOverride) mapperIF.map(override, RegulationOverride.class);

            ro = regulationOverrideDAO.saveOrUpdate(ro);

            // convert to dto here
            override = (IRegulationOverrideDto) mapperIF.map(ro, RegulationOverrideDto.class);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return override;
    }

    @Override
    public IRegulationOverrideDto deleteRegulationOverride(String regulationOverrideId) throws PlatformException {
        IRegulationOverrideDto result = null;

        if (StringUtils.isBlank(regulationOverrideId)) {
            log.error("regulationOverrideId is null");
            throw new PlatformException("error.missing.argument.exception", "regulationOverrideId");
        }

        try {
            RegulationOverride ro = regulationOverrideDAO.get(RegulationOverride.class, regulationOverrideId);

            if (result != null) {
                // convert to dto here
                result = (IRegulationOverrideDto) mapperIF.map(ro, RegulationOverrideDto.class);

                // delete entity here
                regulationOverrideDAO.delete(ro);
            } else {
                log.error("Cannot delete regulation override with ID: " + regulationOverrideId);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<IRegulationDto> getLastModifiedRegulations(Integer maxResults) throws PlatformException {
        List<IRegulationDto> result = null;

        try {
            List<Regulation> list = regulationDAO.getLastModifiedRegulations(maxResults);

            if (list != null) {
                result = new ArrayList<IRegulationDto>(list.size());

                for (Regulation regulation : list) {
                    result.add((IRegulationDto) mapperIF.map(regulation, RegulationDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<ICountryDto> getLastModifiedCountries(Integer maxResults) throws PlatformException {
        List<ICountryDto> result = null;

        try {
            List<Country> list = countryDAO.getLastModifiedCountries(maxResults);

            if (list != null) {
                result = new ArrayList<ICountryDto>();

                for (Country country : list) {
                    result.add((ICountryDto) mapperIF.map(country, CountryDto.class));
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new PlatformException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public ICountryDto createCountry() {
        return new CountryDto();
    }

    @Override
    public IRegulationOverrideDto createRegulationOverride() {
        return new RegulationOverrideDto();
    }

    @Override
    public IRegulationDto createRegulation() {
        return new RegulationDto();
    }

    // Spring IoC
    @Autowired
    private MapperIF mapperIF;

    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private RegulationDAO regulationDAO;

    @Autowired
    private RegulationOverrideDAO regulationOverrideDAO;

}
