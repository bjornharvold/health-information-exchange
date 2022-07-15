package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.spec.model.enums.SexCd;
import com.hxcel.globalhealth.common.spec.model.enums.SalutationCd;
import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.*;

import java.util.List;
import java.util.ArrayList;

/**
 * User: bjorn
 * Date: Sep 7, 2008
 * Time: 4:05:54 PM
 */
public class UserInfoDto extends AbstractDto implements IUserInfoDto {
    private SexCd sexCd;
    private SalutationCd salutationCd;
    private IImageDto image;
    private String firstName;
    private String lastName;
    private String middleName;
    private List<IEmailDto> emails;
    private List<IPhoneDto> phones;
    private List<IInstantMessageDto> instantMessages;

    public SexCd getSexCd() {
        return sexCd;
    }

    public void setSexCd(SexCd sexCd) {
        this.sexCd = sexCd;
    }

    public SalutationCd getSalutationCd() {
        return salutationCd;
    }

    public void setSalutationCd(SalutationCd salutationCd) {
        this.salutationCd = salutationCd;
    }

    public IImageDto getImage() {
        return image;
    }

    public void setImage(IImageDto image) {
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public List<IEmailDto> getEmails() {
        return emails;
    }

    public void setEmails(List<IEmailDto> emails) {
        this.emails = emails;
    }

    public List<IPhoneDto> getPhones() {
        return phones;
    }

    public void setPhones(List<IPhoneDto> phones) {
        this.phones = phones;
    }

    public List<IInstantMessageDto> getInstantMessages() {
        return instantMessages;
    }

    public void setInstantMessages(List<IInstantMessageDto> instantMessages) {
        this.instantMessages = instantMessages;
    }

    public void addEmail(IEmailDto dto) {
        if (emails == null) {
            emails = new ArrayList<IEmailDto>();
        }

        emails.add(dto);
    }

    public void addInstantMessage(IInstantMessageDto dto) {
        if (instantMessages == null) {
            instantMessages = new ArrayList<IInstantMessageDto>();
        }

        instantMessages.add(dto);
    }

    public void addPhone(IPhoneDto dto) {
        if (phones == null) {
            phones = new ArrayList<IPhoneDto>();
        }

        phones.add(dto);
    }
}
