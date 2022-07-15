package com.hxcel.globalhealth.platform.controller.user.validator;

import com.hxcel.globalhealth.platform.spec.dto.ILoginDto;
import com.hxcel.globalhealth.platform.spec.dto.IUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * User: Bjorn Harvold
 * Date: Jul 30, 2007
 * Time: 5:45:24 PM
 */
public class LoginValidator implements Validator {
    private final static Logger log = LoggerFactory.getLogger(LoginValidator.class);

    public boolean supports(Class clazz) {
        return ILoginDto.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required");

        ILoginDto login = (ILoginDto) target;

        if (!errors.hasErrors()) {
            // do authentication here so we KNOW the user is there before hitting the controller
            IUserDto u = null;
            /*
            try {
                u = dmUserDao.authenticate(login.getUsername(), login.getPassword());
            } catch (DmUserDaoException e) {
                log.error("Error with user authentication: " + e.getMessage(), e);
            }

            if (u == null) {
                errors.rejectValue("username1", "error.authentication.failed");
            }*/
        }
    }


}
