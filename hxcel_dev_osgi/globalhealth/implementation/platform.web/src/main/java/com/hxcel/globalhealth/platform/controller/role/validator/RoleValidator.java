package com.hxcel.globalhealth.platform.controller.role.validator;

import com.hxcel.globalhealth.platform.spec.dto.IRoleDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * User: bjorn
 * Date: Sep 25, 2008
 * Time: 1:32:16 PM
 */
public class RoleValidator implements Validator {
    private final static String ROLE_PREFIX = "ROLE_";
    public boolean supports(Class clazz) {
        return IRoleDto.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        IRoleDto role = (IRoleDto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "statusCode", "error.form.field.required", null, "Required field");

        if (StringUtils.isNotBlank(role.getStatusCode()) && !role.getStatusCode().startsWith("ROLE_")) {
            errors.rejectValue("statusCode", "error.form.field.syntax", new String[]{"must begin with ROLE_"}, "Syntax is wrong: must beging with ROLE_");
        }
    }
}