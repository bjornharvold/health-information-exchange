package com.hxcel.globalhealth.platform.controller.form.validator;

import com.hxcel.globalhealth.platform.controller.form.ImageFileUploadForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * User: bjorn
 * Date: Sep 23, 2008
 * Time: 5:56:18 PM
 */
public class ImageFileUploadValidator extends FileUploadValidator implements Validator {
    private final static String JPEG = "jpg";
    private final static String PNG = "png";
    private final static String GIF = "gif";

    public ImageFileUploadValidator() {
        super(new String[]{JPEG, PNG, GIF});
    }

    public boolean supports(Class clazz) {
        return ImageFileUploadForm.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        super.validate(target, errors);
    }
}