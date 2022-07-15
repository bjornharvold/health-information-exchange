package com.hxcel.globalhealth.domain.common.dto;

import com.hxcel.globalhealth.domain.common.model.enums.SexCd;
import com.hxcel.globalhealth.domain.common.model.enums.SalutationCd;
import com.hxcel.globalhealth.domain.common.model.Image;
import com.hxcel.globalhealth.domain.common.model.Email;
import com.hxcel.globalhealth.domain.common.model.Phone;
import com.hxcel.globalhealth.domain.common.model.InstantMessage;

import java.util.List;
import java.util.ArrayList;

/**
 * User: bjorn
 * Date: Sep 7, 2008
 * Time: 4:05:54 PM
 */
public class UserInfoDto extends AbstractDto {
    private SexCd sexCd;
    private SalutationCd salutationCd;
    private ImageDto image;
    private String firstName;
    private String lastName;
    private String middleName;
    private List<EmailDto> emails;
    private List<PhoneDto> phones;
    private List<InstantMessageDto> instantMessages;

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

    public ImageDto getImage() {
        return image;
    }

    public void setImage(ImageDto image) {
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

    public List<EmailDto> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailDto> emails) {
        this.emails = emails;
    }

    public List<PhoneDto> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDto> phones) {
        this.phones = phones;
    }

    public List<InstantMessageDto> getInstantMessages() {
        return instantMessages;
    }

    public void setInstantMessages(List<InstantMessageDto> instantMessages) {
        this.instantMessages = instantMessages;
    }

    public void addEmail(EmailDto dto) {
        if (emails == null) {
            emails = new ArrayList<EmailDto>();
        }

        emails.add(dto);
    }

    public void addInstantMessage(InstantMessageDto dto) {
        if (instantMessages == null) {
            instantMessages = new ArrayList<InstantMessageDto>();
        }

        instantMessages.add(dto);
    }

    public void addPhone(PhoneDto dto) {
        if (phones == null) {
            phones = new ArrayList<PhoneDto>();
        }

        phones.add(dto);
    }
}
