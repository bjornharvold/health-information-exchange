package com.hxcel.globalhealth.admin.controller.user.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang.StringUtils;
import com.hxcel.globalhealth.domain.platform.model.ApplicationConfigurationOverride;
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.dto.UserDto;
import com.hxcel.globalhealth.domain.common.UserManager;
import com.hxcel.globalhealth.domain.DomainException;

/**
 * User: bjorn
 * Date: Sep 25, 2008
 * Time: 1:32:16 PM
 */
public class UserValidator implements Validator {
    public boolean supports(Class clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        User user = (User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userInfo.firstName", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userInfo.lastName", "error.form.field.required", null, "Required field");

        // check for username availability
        try {
            // the first case is for a new user
            if (StringUtils.isBlank(user.getId()) && userManager.isUsernameTaken(user.getUsername())) {
                errors.rejectValue("username", "error.username.taken", null, "Username is already taken");
            }
            // the second case is for an existing user
            else if (StringUtils.isNotBlank(user.getId()) && !userManager.isUserUnique(user.getId(), user.getUsername())) {
                errors.rejectValue("username", "error.username.taken", null, "Username is already taken");
            }
        } catch (DomainException e) {
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
    private UserManager userManager;
}