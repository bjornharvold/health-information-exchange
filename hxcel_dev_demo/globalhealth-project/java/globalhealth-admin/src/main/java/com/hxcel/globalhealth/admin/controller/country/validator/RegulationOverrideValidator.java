package com.hxcel.globalhealth.admin.controller.country.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.hxcel.globalhealth.domain.platform.model.RegulationOverride;

/**
 * User: bjorn
 * Date: Nov 10, 2008
 * Time: 2:15:46 AM
 */
public class RegulationOverrideValidator implements Validator {
    public boolean supports(Class aClass) {
        return RegulationOverride.class.getClass().isAssignableFrom(aClass);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "original", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "error.form.field.required", null, "Required field");
    }
}
