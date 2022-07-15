package com.hxcel.globalhealth.platform.controller.organization.validator;

import com.hxcel.globalhealth.platform.controller.form.validator.AbstractValidator;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * User: bjorn
 * Date: Sep 25, 2008
 * Time: 1:32:16 PM
 */
public class OrganizationValidator extends AbstractValidator implements Validator {
    public boolean supports(Class clazz) {
        return IOrganizationDto.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        IOrganizationDto org = (IOrganizationDto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organizationStatus", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organizationType", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "error.form.field.required", null, "Required field");
    }
}