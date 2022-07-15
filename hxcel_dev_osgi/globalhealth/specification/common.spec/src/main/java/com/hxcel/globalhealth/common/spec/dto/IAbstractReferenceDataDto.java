package com.hxcel.globalhealth.common.spec.dto;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:45:20 PM
 * Responsibility:
 */
public interface IAbstractReferenceDataDto extends IAbstractDto {
    String getStatusCode();

    void setStatusCode(String statusCode);
}
