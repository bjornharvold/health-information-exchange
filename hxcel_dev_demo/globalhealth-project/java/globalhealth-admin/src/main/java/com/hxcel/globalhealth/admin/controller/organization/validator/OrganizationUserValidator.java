package com.hxcel.globalhealth.admin.controller.organization.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang.StringUtils;
import com.hxcel.globalhealth.domain.platform.model.OrganizationUser;
import com.hxcel.globalhealth.domain.common.UserManager;
import com.hxcel.globalhealth.domain.DomainException;

/**
 * User: bjorn
 * Date: Sep 25, 2008
 * Time: 1:32:16 PM
 */
public class OrganizationUserValidator implements Validator {
    public boolean supports(Class clazz) {
        return OrganizationUser.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        OrganizationUser ou = (OrganizationUser) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.username", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.country", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.userInfo.firstName", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.userInfo.lastName", "error.form.field.required", null, "Required field");

        // check for username availability
        try {
            // the first case is for a new ou
            if (StringUtils.isBlank(ou.getUser().getId()) && userManager.isUsernameTaken(ou.getUser().getUsername())) {
                errors.rejectValue("user.username", "error.username.taken", null, "Username is already taken");
            }
            // the second case is for an existing ou
            else if (StringUtils.isNotBlank(ou.getUser().getId()) && !userManager.isUserUnique(ou.getUser().getId(), ou.getUser().getUsername())) {
                errors.rejectValue("user.username", "error.username.taken", null, "Username is already taken");
            }
        } catch (DomainException e) {
            errors.rejectValue("user.username", "error.username.unknown", null, "Unknown username error");
        }

        if (StringUtils.isBlank(ou.getId())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.password", "error.form.field.required", null, "Required field");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.passwordConfirm", "error.form.field.required", null, "Required field");

            // check for password match
            if (StringUtils.isNotBlank(ou.getUser().getPassword()) && StringUtils.isNotBlank(ou.getUser().getPasswordConfirm())) {
                if (!StringUtils.equals(ou.getUser().getPassword(), ou.getUser().getPasswordConfirm())) {
                    errors.rejectValue("user.passwordConfirm", "error.password.mismatch", null, "Passwords are not the same");
                }
            }
        }
    }

    // Spring IoC
    @Autowired
    private UserManager userManager;
}