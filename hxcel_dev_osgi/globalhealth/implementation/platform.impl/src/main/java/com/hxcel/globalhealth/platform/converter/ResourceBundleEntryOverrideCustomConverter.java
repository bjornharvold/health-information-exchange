/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.converter;

import com.hxcel.globalhealth.common.converter.AbstractConverter;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.dto.ResourceBundleEntryDto;
import com.hxcel.globalhealth.platform.dto.ResourceBundleEntryOverrideDto;
import com.hxcel.globalhealth.platform.dto.OrganizationLicenseDto;
import com.hxcel.globalhealth.platform.model.ResourceBundleEntry;
import com.hxcel.globalhealth.platform.model.ResourceBundleEntryOverride;
import com.hxcel.globalhealth.platform.model.OrganizationLicense;
import com.hxcel.globalhealth.platform.spec.dto.IResourceBundleEntryDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationLicenseDto;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class ResourceBundleEntryOverrideCustomConverter extends AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(ResourceBundleEntryOverrideCustomConverter.class);

    protected IAbstractDto toDto(IAbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof ResourceBundleEntryOverride && dto instanceof ResourceBundleEntryOverrideDto) {
            ResourceBundleEntryOverride entity = (ResourceBundleEntryOverride) source;

            ((ResourceBundleEntryOverrideDto) dto).setOriginal((IResourceBundleEntryDto) mapperIF.map(entity.getOriginal(), ResourceBundleEntryDto.class));
            ((ResourceBundleEntryOverrideDto) dto).setOrganizationLicense((IOrganizationLicenseDto) mapperIF.map(entity.getOrganizationLicense(), OrganizationLicenseDto.class));
            ((ResourceBundleEntryOverrideDto) dto).setDescription(entity.getDescription());
            ((ResourceBundleEntryOverrideDto) dto).setValue(entity.getValue());
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
        if (source instanceof ResourceBundleEntryOverrideDto && entity instanceof ResourceBundleEntryOverride) {
            ResourceBundleEntryOverrideDto dto = (ResourceBundleEntryOverrideDto) source;

            ((ResourceBundleEntryOverride) entity).setOriginal((ResourceBundleEntry) mapperIF.map(dto.getOriginal(), ResourceBundleEntry.class));
            ((ResourceBundleEntryOverride) entity).setOrganizationLicense((OrganizationLicense) mapperIF.map(dto.getOrganizationLicense(), OrganizationLicense.class));
            ((ResourceBundleEntryOverride) entity).setDescription(dto.getDescription());
            ((ResourceBundleEntryOverride) entity).setValue(dto.getValue());

        } else {
            throw new MappingException("Converter " + this.getClass().getCanonicalName() + " used incorrectly. Arguments passed in were: " + entity + " and " + source);
        }

        return entity;
    }
}