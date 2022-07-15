package com.hxcel.globalhealth.admin.controller.regulation.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.hxcel.globalhealth.domain.platform.model.Regulation;

/**
 * User: bjorn
 * Date: Nov 7, 2008
 * Time: 2:52:05 PM
 */
public class RegulationValidator implements Validator {
    public boolean supports(Class aClass) {
        return Regulation.class.getClass().isAssignableFrom(aClass);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "key", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "error.form.field.required", null, "Required field");
    }
}