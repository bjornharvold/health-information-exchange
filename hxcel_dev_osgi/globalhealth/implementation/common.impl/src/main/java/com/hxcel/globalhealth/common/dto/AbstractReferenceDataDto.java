/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.common.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hxcel.globalhealth.common.spec.dto.IAbstractReferenceDataDto;

public abstract class AbstractReferenceDataDto extends AbstractDto implements IAbstractReferenceDataDto {
    private final static Logger log = LoggerFactory.getLogger(AbstractReferenceDataDto.class);
    private String statusCode = null;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}