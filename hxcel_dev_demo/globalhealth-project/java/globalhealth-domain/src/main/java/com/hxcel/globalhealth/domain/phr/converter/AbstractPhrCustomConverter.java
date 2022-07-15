/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.converter;

import com.hxcel.globalhealth.domain.common.dao.UserDAO;
import com.hxcel.globalhealth.domain.common.dto.PermissionDto;
import com.hxcel.globalhealth.domain.common.model.Permission;
import com.hxcel.globalhealth.domain.phr.dao.PhrDAO;
import com.hxcel.globalhealth.domain.phr.dto.AbstractPhrDto;
import com.hxcel.globalhealth.domain.phr.model.AbstractPhrEntity;
import com.hxcel.globalhealth.domain.phr.model.Phr;
import com.hxcel.globalhealth.domain.professional.dao.ProfessionalDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.MapperIF;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 5:58:22 AM
 * This class is the base class for all our phr converters. It will take care of converting anything
 * that's common to AbstractPhrEntities. The rest can be mapped by the extending class.
 */
public abstract class AbstractPhrCustomConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(AbstractPhrCustomConverter.class);


    /**
     * There is so much commonality between phr converters we're doing most of it here
     *
     * @param destination
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {
        AbstractPhrDto dto = null;
        AbstractPhrEntity entity = null;

        if (source == null) {
            return null;
        }

        try {
            if (source instanceof AbstractPhrEntity) {
                entity = (AbstractPhrEntity) source;
                // check to see if the object already exists
                if (destination == null) {
                    try {
                        dto = (AbstractPhrDto) destClass.newInstance();
                    } catch (InstantiationException e) {
                        log.error("Cannot instantiate destination class: " + e.getMessage(), e);
                        throw new MappingException("Cannot instantiate destination class: " + e.getMessage(), e);
                    } catch (IllegalAccessException e) {
                        log.error("Cannot instantiate destination class: " + e.getMessage(), e);
                        throw new MappingException("Cannot instantiate destination class: " + e.getMessage(), e);
                    }
                } else {
                    dto = (AbstractPhrDto) destination;
                }

                dto = toDto(dto, (AbstractPhrEntity) source, destClass, sourceClass);

                // flatten entity here
                dto.setEmergency(entity.getEmergency());
                dto.setId(entity.getId());
                dto.setNotes(entity.getNotes());

                if (entity.getPermissions() != null) {
                    for (Permission p : entity.getPermissions()) {
                        dto.addPermission((PermissionDto) mapperIF.map(p, PermissionDto.class));
                    }
                }

                dto.setPhr(entity.getPhr().getId());

                if (entity.getRecordCreatorId() != null) {
                    dto.setRecordCreator(entity.getRecordCreatorId());
                }
                dto.setRecordType(entity.getRecordType());
                dto.setRecordStatus(entity.getRecordStatus());
                dto.setType(entity.getPhrType());

                return dto;
            } else if (source instanceof AbstractPhrDto) {
                dto = (AbstractPhrDto) source;
                if (StringUtils.isNotBlank(dto.getId())) {
                    // load up entity from db
                    entity = (AbstractPhrEntity) dao.get(destClass, dto.getId());
                } else if (destination == null) {
                    try {
                        entity = (AbstractPhrEntity) destClass.newInstance();
                    } catch (InstantiationException e) {
                        log.error("Cannot instantiate destination class: " + e.getMessage(), e);
                        throw new MappingException("Cannot instantiate destination class: " + e.getMessage(), e);
                    } catch (IllegalAccessException e) {
                        log.error("Cannot instantiate destination class: " + e.getMessage(), e);
                        throw new MappingException("Cannot instantiate destination class: " + e.getMessage(), e);
                    }
                } else {
                    entity = (AbstractPhrEntity) destination;
                }

                entity = toEntity(entity, dto, destClass, sourceClass);
                entity.setEmergency(dto.getEmergency());
                entity.setNotes(dto.getNotes());

                if (dto.getPermissions() != null && !dto.getPermissions().isEmpty()) {
                    // remove any null elements
                    dto.getPermissions().remove(null);
                    for (PermissionDto pDto : dto.getPermissions()) {
                        entity.addPermission((Permission) mapperIF.map(pDto, Permission.class));
                    }
                }

                if (StringUtils.isNotBlank(dto.getPhr())) {
                    Phr phr = phrDAO.get(Phr.class, dto.getPhr());
                    if (phr == null) {
                        String error = "PHR has an ID: " + dto.getPhr() + " but no entity could be found.";
                        log.error(error);
                        throw new MappingException(error);
                    }

                    entity.setPhr(phr);
                }
                if (StringUtils.isNotBlank(dto.getRecordCreator())) {
                    entity.setRecordCreatorId(dto.getRecordCreator());
                }
                entity.setRecordType(dto.getRecordType());
                entity.setRecordStatus(dto.getRecordStatus());
                entity.setPhrType(dto.getType());

                return entity;
            } else {
                throw new MappingException("Converter CustomConverter used incorrectly. Arguments passed in were: " + destination + " and " + source);
            }
        } catch (PersistenceException e) {
            log.error("Could not retrieve entity from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }
    }

    /**
     * Needs to be implemented by the extending class
     *
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected abstract AbstractPhrDto toDto(AbstractPhrDto dto, AbstractPhrEntity source, Class destClass, Class sourceClass);

    /**
     * Needs to be implemented by the extending class
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected abstract AbstractPhrEntity toEntity(AbstractPhrEntity entity, AbstractPhrDto source, Class destClass, Class sourceClass);
    
    // Spring IoC
    @Autowired
    protected ProfessionalDAO professionalDAO;

    @Autowired
    private PhrDAO phrDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    protected MapperIF mapperIF;

    private GenericDAO dao;

    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }
}
