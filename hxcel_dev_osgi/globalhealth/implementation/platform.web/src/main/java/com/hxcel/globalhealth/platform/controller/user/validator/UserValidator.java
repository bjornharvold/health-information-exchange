package com.hxcel.globalhealth.platform.controller.user.validator;

import com.hxcel.globalhealth.platform.spec.PlatformException;
import com.hxcel.globalhealth.platform.spec.UserService;
import com.hxcel.globalhealth.platform.spec.dto.IUserDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * User: bjorn
 * Date: Sep 25, 2008
 * Time: 1:32:16 PM
 */
public class UserValidator implements Validator {
    public boolean supports(Class clazz) {
        return IUserDto.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        IUserDto user = (IUserDto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userInfo.firstName", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userInfo.lastName", "error.form.field.required", null, "Required field");

        // check for username availability
        try {
            // the first case is for a new user
            if (StringUtils.isBlank(user.getId()) && userService.isUsernameTaken(user.getUsername())) {
                errors.rejectValue("username", "error.username.taken", null, "Username is already taken");
            }
            // the second case is for an existing user
            else if (StringUtils.isNotBlank(user.getId()) && !userService.isUserUnique(user.getId(), user.getUsername())) {
                errors.rejectValue("username", "error.username.taken", null, "Username is already taken");
            }
        } catch (PlatformException e) {
            errors.rejectValue("username", "error.username.unknown", null, "Unknown username error");
        }

        if (StringUtils.isBlank(user.getId())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.form.field.required", null, "Required field");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "error.form.field.required", null, "Required field");

            // check for password match
            if (StringUtils.isNotBlank(user.getPassword()) && StringUtils.isNotBlank(user.getPasswordConfirm())) {
                if (!StringUtils.equals(user.getPassword(), user.getPasswordConfirm())) {
                    errors.rejectValue("passwordConfirm", "error.password.mismatch", null, "Passwords are not the same");
                }
            }
        }
    }

    // Spring IoC
    @Autowired
    private UserService userService;
}