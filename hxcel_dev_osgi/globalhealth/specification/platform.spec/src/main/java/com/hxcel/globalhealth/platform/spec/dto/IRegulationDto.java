package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.platform.spec.model.enums.RegulationTypeCd;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:32:52 PM
 * Responsibility:
 */
public interface IRegulationDto extends IAbstractDto {
    String getName();

    void setName(String name);

    String getKey();

    void setKey(String key);

    String getValue();

    void setValue(String value);

    String getDescription();

    void setDescription(String description);

    RegulationTypeCd getType();

    void setType(RegulationTypeCd type);

    String getLabel();
}
