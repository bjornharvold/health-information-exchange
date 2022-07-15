package com.hxcel.globalhealth.platform.controller.country.validator;

import com.hxcel.globalhealth.platform.spec.dto.IRegulationOverrideDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * User: bjorn
 * Date: Nov 10, 2008
 * Time: 2:15:46 AM
 */
public class RegulationOverrideValidator implements Validator {
    public boolean supports(Class aClass) {
        return IRegulationOverrideDto.class.getClass().isAssignableFrom(aClass);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "original", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "error.form.field.required", null, "Required field");
    }
}
