package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:27:23 PM
 * Responsibility:
 */
public interface IApplicationConfigurationDto extends IAbstractDto {
    IApplicationDto getApplication();

    void setApplication(IApplicationDto application);

    String getKey();

    void setKey(String key);

    String getValue();

    void setValue(String value);

    String getDescription();

    void setDescription(String description);

    Boolean getExportable();

    void setExportable(Boolean exportable);

    String getLabel();
}
