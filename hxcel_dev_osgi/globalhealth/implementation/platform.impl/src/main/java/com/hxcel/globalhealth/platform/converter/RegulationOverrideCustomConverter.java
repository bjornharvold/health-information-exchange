/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.platform.model.Country;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;
import com.hxcel.globalhealth.platform.dto.CountryDto;
import com.hxcel.globalhealth.platform.dto.RegulationDto;
import com.hxcel.globalhealth.platform.dto.RegulationOverrideDto;
import com.hxcel.globalhealth.platform.model.Regulation;
import com.hxcel.globalhealth.platform.model.RegulationOverride;
import com.hxcel.globalhealth.platform.spec.dto.IRegulationDto;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class RegulationOverrideCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(RegulationOverrideCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof RegulationOverride && dto instanceof RegulationOverrideDto) {
            RegulationOverride entity = (RegulationOverride) source;

            ((RegulationOverrideDto) dto).setOriginal((IRegulationDto) mapperIF.map(entity.getOriginal(), RegulationDto.class));
            ((RegulationOverrideDto) dto).setCountry((ICountryDto) mapperIF.map(entity.getCountry(), CountryDto.class));
            ((RegulationOverrideDto) dto).setDescription(entity.getDescription());
            ((RegulationOverrideDto) dto).setValue(entity.getValue());
        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + dto + " and " + source);
        }

        return dto;
    }

    /**
     * This method should only get called once by the production code (at the generation of the user with the system)
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected AbstractEntity toEntity(AbstractEntity entity, IAbstractDto source, Class destClass, Class sourceClass) {
        if (source instanceof RegulationOverrideDto && entity instanceof RegulationOverride) {
            RegulationOverrideDto dto = (RegulationOverrideDto) source;

            ((RegulationOverride) entity).setOriginal((Regulation) mapperIF.map(dto.getOriginal(), Regulation.class));
            ((RegulationOverride) entity).setCountry((Country) mapperIF.map(dto.getCountry(), Country.class));
            ((RegulationOverride) entity).setDescription(dto.getDescription());
            ((RegulationOverride) entity).setValue(dto.getValue());

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }
}