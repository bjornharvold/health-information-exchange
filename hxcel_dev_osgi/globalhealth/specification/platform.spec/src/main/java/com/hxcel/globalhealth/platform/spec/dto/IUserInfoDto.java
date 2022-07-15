package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.model.enums.SexCd;
import com.hxcel.globalhealth.common.spec.model.enums.SalutationCd;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 1:15:22 PM
 * Responsibility:
 */
public interface IUserInfoDto {
    SexCd getSexCd();

    void setSexCd(SexCd sexCd);

    SalutationCd getSalutationCd();

    void setSalutationCd(SalutationCd salutationCd);

    IImageDto getImage();

    void setImage(IImageDto image);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getMiddleName();

    void setMiddleName(String middleName);

    List<IEmailDto> getEmails();

    void setEmails(List<IEmailDto> emails);

    List<IPhoneDto> getPhones();

    void setPhones(List<IPhoneDto> phones);

    List<IInstantMessageDto> getInstantMessages();

    void setInstantMessages(List<IInstantMessageDto> instantMessages);

    void addEmail(IEmailDto dto);

    void addInstantMessage(IInstantMessageDto dto);

    void addPhone(IPhoneDto dto);
}
