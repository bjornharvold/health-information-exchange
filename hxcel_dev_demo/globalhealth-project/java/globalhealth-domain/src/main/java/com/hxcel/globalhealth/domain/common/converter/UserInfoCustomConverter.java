package com.hxcel.globalhealth.domain.common.converter;

import com.hxcel.globalhealth.domain.common.dto.*;
import com.hxcel.globalhealth.domain.common.model.*;

/**
 * User: bjorn
 * Date: Sep 7, 2008
 * Time: 4:02:51 PM
 */
public class UserInfoCustomConverter extends AbstractConverter {
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof UserInfo && dto instanceof UserInfoDto) {
            UserInfo ui = (UserInfo) source;


            if (ui.getEmails() != null) {
                for (Email email : ui.getEmails()) {
                    ((UserInfoDto) dto).addEmail((EmailDto) mapperIF.map(email, EmailDto.class));
                }
            }
            if (ui.getInstantMessages() != null) {
                for (InstantMessage instantMessage : ui.getInstantMessages()) {
                    ((UserInfoDto) dto).addInstantMessage((InstantMessageDto) mapperIF.map(instantMessage, InstantMessageDto.class));
                }
            }
            if (ui.getPhones() != null) {
                for (Phone phone : ui.getPhones()) {
                    ((UserInfoDto) dto).addPhone((PhoneDto) mapperIF.map(phone, PhoneDto.class));
                }
            }
            ((UserInfoDto) dto).setFirstName(ui.getFirstName());
            ((UserInfoDto) dto).setLastName(ui.getLastName());
            ((UserInfoDto) dto).setMiddleName(ui.getMiddleName());
            ((UserInfoDto) dto).setSalutationCd(ui.getSalutation());
            ((UserInfoDto) dto).setSexCd(ui.getSex());
        }

        return dto;
    }

    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {
        if (source instanceof UserInfoDto && entity instanceof UserInfo) {
            UserInfoDto dto = (UserInfoDto) source;

            if (dto.getEmails() != null && !dto.getEmails().isEmpty()) {
                // remove any null elements
                dto.getEmails().remove(null);
                for (EmailDto email : dto.getEmails()) {
                    ((UserInfo) entity).addEmail((Email) mapperIF.map(email, Email.class));
                }
            }

            if (dto.getInstantMessages() != null && !dto.getInstantMessages().isEmpty()) {
                // remove any null elements
                dto.getInstantMessages().remove(null);
                for (InstantMessageDto email : dto.getInstantMessages()) {
                    ((UserInfo) entity).addInstantMessage((InstantMessage) mapperIF.map(email, InstantMessage.class));
                }
            }

            if (dto.getPhones() != null && !dto.getPhones().isEmpty()) {
                // remove any null elements
                dto.getPhones().remove(null);
                for (PhoneDto phone : dto.getPhones()) {
                    ((UserInfo) entity).addPhone((Phone) mapperIF.map(phone, Phone.class));
                }
            }

            ((UserInfo) entity).setFirstName(dto.getFirstName());
            ((UserInfo) entity).setLastName(dto.getLastName());
            ((UserInfo) entity).setMiddleName(dto.getMiddleName());
            ((UserInfo) entity).setSalutation(dto.getSalutationCd());
            ((UserInfo) entity).setSex(dto.getSexCd());
        }

        return entity;
    }
}
