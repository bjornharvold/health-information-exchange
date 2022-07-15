package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.dto.IAbstractReferenceDataDto;

import java.util.Date;
import java.math.BigDecimal;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:42:45 PM
 * Responsibility:
 */
public interface ICountryDto extends IAbstractReferenceDataDto {
    String getCountryCode();

    void setCountryCode(String value);

    String getLanguage();

    void setLanguage(String value);

    String getCurrencyCode();

    void setCurrencyCode(String value);

    Integer getCurrencyCodeNumber();

    void setCurrencyCodeNumber(Integer value);

    String getCurrencyName();

    void setCurrencyName(String value);

    Date getExchangeDate();

    void setExchangeDate(Date value);

    BigDecimal getExchangeRate();

    void setExchangeRate(BigDecimal value);

    void setDateFormatPattern(String dateFormatPattern);

    void setTimeFormatPattern(String timeFormatPattern);

    void setTimestampFormatPattern(String timestampFormatPattern);

    String getDateFormatPattern();

    String getTimeFormatPattern();

    String getTimestampFormatPattern();
}
