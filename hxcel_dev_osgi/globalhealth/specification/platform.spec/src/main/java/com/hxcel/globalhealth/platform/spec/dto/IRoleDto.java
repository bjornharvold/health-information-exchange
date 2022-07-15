package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.common.spec.dto.IAbstractReferenceDataDto;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:34:22 PM
 * Responsibility:
 */
public interface IRoleDto extends IAbstractReferenceDataDto {
    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);
}
