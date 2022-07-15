/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.model;

import com.hxcel.globalhealth.common.spec.model.enums.SexCd;
import com.hxcel.globalhealth.common.spec.model.enums.SalutationCd;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.platform.utils.EnumUserType;
import org.apache.commons.lang.StringUtils;
import org.jasypt.hibernate.type.EncryptedStringType;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@Entity
@TypeDefs(
        {
        @TypeDef(name = "salutation",
                typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.common.spec.model.enums.SalutationCd")}
        ),
        @TypeDef(name = "sex",
                typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.common.spec.model.enums.SexCd")}
        ),
        @TypeDef(
                name = "encryptedString",
                typeClass = EncryptedStringType.class,
                parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
                        }
        )
                }
)
public class UserInfo extends AbstractEntity implements Serializable {
    private SexCd sex;
    private SalutationCd salutation;
    private Image image;
    private String firstName;
    private String lastName;
    private String middleName;
    private List<Email> emails;
    private List<Phone> phones;
    private List<InstantMessage> instantMessages;

    @Type(type = "sex")
    public SexCd getSex() {
        return this.sex;
    }

    public void setSex(SexCd value) {
        this.sex = value;
    }

    @Type(type = "salutation")
    public SalutationCd getSalutation() {
        return this.salutation;
    }


    public void setSalutation(SalutationCd value) {
        this.salutation = value;
    }

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    public Image getImage() {
        return this.image;
    }


    public void setImage(Image value) {
        this.image = value;
    }

    public String getFirstName() {
        return this.firstName;
    }


    public void setFirstName(String value) {
        this.firstName = value;
    }

    public String getLastName() {
        return this.lastName;
    }


    public void setLastName(String value) {
        this.lastName = value;
    }

    public String getMiddleName() {
        return this.middleName;
    }


    public void setMiddleName(String value) {
        this.middleName = value;
    }


    @OneToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    public List<Email> getEmails() {
        return emails;
    }


    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }


    @OneToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    public List<Phone> getPhones() {
        return phones;
    }


    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }


    @OneToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    public List<InstantMessage> getInstantMessages() {
        return instantMessages;
    }


    public void setInstantMessages(List<InstantMessage> instantMessages) {
        this.instantMessages = instantMessages;
    }

    @Transient
    public void addEmail(Email email) {
        Email dupe = null;

        if (emails == null) {
            emails = new ArrayList<Email>();
        }

        // check for duplicates
        for (Email l : emails) {
            if (StringUtils.equals(l.getId(), email.getId())) {
                dupe = l;
            }
        }

        // remove it if dupe (older version) was found
        if (dupe != null) {
            emails.remove(dupe);
        }

        emails.add(email);
    }

    @Transient
    public void addPhone(Phone phone) {
        Phone dupe = null;

        if (phones == null) {
            phones = new ArrayList<Phone>();
        }

        // check for duplicates
        for (Phone l : phones) {
            if (StringUtils.equals(l.getId(), phone.getId())) {
                dupe = l;
            }
        }

        // remove it if dupe (older version) was found
        if (dupe != null) {
            phones.remove(dupe);
        }

        phones.add(phone);
    }

    @Transient
    public void addInstantMessage(InstantMessage instantMessage) {
        InstantMessage dupe = null;

        if (instantMessages == null) {
            instantMessages = new ArrayList<InstantMessage>();
        }

        // check for duplicates
        for (InstantMessage l : instantMessages) {
            if (StringUtils.equals(l.getId(), instantMessage.getId())) {
                dupe = l;
            }
        }

        // remove it if dupe (older version) was found
        if (dupe != null) {
            instantMessages.remove(dupe);
        }

        instantMessages.add(instantMessage);
    }
}
