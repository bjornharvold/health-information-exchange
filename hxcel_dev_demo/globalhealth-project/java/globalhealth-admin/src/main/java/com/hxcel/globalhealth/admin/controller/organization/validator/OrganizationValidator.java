package com.hxcel.globalhealth.admin.controller.organization.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.admin.controller.form.validator.AbstractValidator;

/**
 * User: bjorn
 * Date: Sep 25, 2008
 * Time: 1:32:16 PM
 */
public class OrganizationValidator extends AbstractValidator implements Validator {
    public boolean supports(Class clazz) {
        return Organization.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        Organization org = (Organization) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organizationStatus", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organizationType", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "error.form.field.required", null, "Required field");
    }
}