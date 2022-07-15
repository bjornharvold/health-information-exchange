package com.hxcel.globalhealth.platform.controller.country.validator;

import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * User: bjorn
 * Date: Nov 7, 2008
 * Time: 2:52:05 PM
 */
public class CountryValidator implements Validator {
    public boolean supports(Class aClass) {
        return ICountryDto.class.getClass().isAssignableFrom(aClass);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "statusCode", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "countryCode", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "language", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currencyCode", "error.form.field.required", null, "Required field");
    }
}
