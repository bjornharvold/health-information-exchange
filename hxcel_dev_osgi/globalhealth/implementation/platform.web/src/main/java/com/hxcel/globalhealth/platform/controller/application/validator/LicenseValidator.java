package com.hxcel.globalhealth.platform.controller.application.validator;

import com.hxcel.globalhealth.platform.spec.dto.ILicenseDto;
import com.hxcel.globalhealth.platform.spec.model.enums.LicenseTypeCd;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * User: Bjorn Harvold
 * Date: Sep 17, 2008
 * Time: 4:43:00 PM
 * Description:
 */
public class LicenseValidator implements Validator {
    public boolean supports(Class clazz) {
        return ILicenseDto.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        ILicenseDto license = (ILicenseDto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "licenseType", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expirationDate", "error.form.field.required", null, "Required field");

        if ((license.getPrice() == null || license.getPrice().compareTo( (float)0) <= 0 ) && !license.getLicenseType().equals(LicenseTypeCd.FREE)) {
            errors.rejectValue("price", "error.field.value.less.than.zero", null, null);
        }
    }
    
}