package com.hxcel.globalhealth.common.spec.dto;

import java.util.Date;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:38:24 PM
 * Responsibility:
 */
public interface IAbstractDto {
    String getId();

    void setId(String id);

    Integer getVersion();

    void setVersion(Integer version);

    Date getCreatedDate();

    void setCreatedDate(Date createdDate);

    Date getLastUpdate();

    void setLastUpdate(Date lastUpdate);
}
