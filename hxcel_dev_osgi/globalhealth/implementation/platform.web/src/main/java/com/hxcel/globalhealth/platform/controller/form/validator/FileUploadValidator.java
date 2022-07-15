package com.hxcel.globalhealth.platform.controller.form.validator;

import com.hxcel.globalhealth.platform.controller.form.FileUploadForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * User: Bjorn Harvold
 * Date: Oct 27, 2008
 * Time: 5:04:31 PM
 * Description:
 */
public class FileUploadValidator implements Validator {
    private String[] allowedFileExtensions;

    public FileUploadValidator(String[] allowedFileExtensions) {
        this.allowedFileExtensions = allowedFileExtensions;
    }

    public boolean supports(Class clazz) {
        return FileUploadForm.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        FileUploadForm ifuf = (FileUploadForm) target;
        boolean match = false;

        if (ifuf.getFile() != null) {

            if (allowedFileExtensions != null) {
                for (String extension : allowedFileExtensions) {
                    if (ifuf.getFile().getOriginalFilename().toLowerCase().endsWith(extension)) {
                        match = true;
                    }
                }
            }

            if (!match) {
                StringBuilder sb = new StringBuilder();
                for (String extension : allowedFileExtensions) {
                    sb.append(extension);
                    sb.append(", ");
                }

                sb.delete(sb.length() - 2, sb.length() - 1);
                errors.rejectValue("file", "error.form.file.extension", new String[]{sb.toString()}, "Required " + sb.toString() + " file");
            }

        }
    }
    
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("name.gif, ");
        sb.delete(sb.length() - 2, sb.length() - 1);

        System.out.println(sb.toString());

    }
}
