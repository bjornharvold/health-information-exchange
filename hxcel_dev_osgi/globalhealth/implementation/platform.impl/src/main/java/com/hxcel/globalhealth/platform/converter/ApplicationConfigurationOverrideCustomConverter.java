/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.dto.ApplicationConfigurationDto;
import com.hxcel.globalhealth.platform.dto.ApplicationConfigurationOverrideDto;
import com.hxcel.globalhealth.platform.dto.OrganizationLicenseDto;
import com.hxcel.globalhealth.platform.model.ApplicationConfiguration;
import com.hxcel.globalhealth.platform.model.ApplicationConfigurationOverride;
import com.hxcel.globalhealth.platform.model.OrganizationLicense;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationConfigurationDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationLicenseDto;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class ApplicationConfigurationOverrideCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(ApplicationConfigurationOverrideCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof ApplicationConfigurationOverride && dto instanceof ApplicationConfigurationOverrideDto) {
            ApplicationConfigurationOverride entity = (ApplicationConfigurationOverride) source;

            ((ApplicationConfigurationOverrideDto) dto).setOriginal((IApplicationConfigurationDto) mapperIF.map(entity.getOriginal(), ApplicationConfigurationDto.class));
            ((ApplicationConfigurationOverrideDto) dto).setOrganizationLicense((IOrganizationLicenseDto) mapperIF.map(entity.getOrganizationLicense(), OrganizationLicenseDto.class));
            ((ApplicationConfigurationOverrideDto) dto).setDescription(entity.getDescription());
            ((ApplicationConfigurationOverrideDto) dto).setValue(entity.getValue());
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
        if (source instanceof ApplicationConfigurationOverrideDto && entity instanceof ApplicationConfigurationOverride) {
            ApplicationConfigurationOverrideDto dto = (ApplicationConfigurationOverrideDto) source;

            ((ApplicationConfigurationOverride) entity).setOriginal((ApplicationConfiguration) mapperIF.map(dto.getOriginal(), ApplicationConfiguration.class));
            ((ApplicationConfigurationOverride) entity).setOrganizationLicense((OrganizationLicense) mapperIF.map(dto.getOrganizationLicense(), OrganizationLicense.class));
            ((ApplicationConfigurationOverride) entity).setDescription(dto.getDescription());
            ((ApplicationConfigurationOverride) entity).setValue(dto.getValue());

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }
}