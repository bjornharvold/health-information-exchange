package com.hxcel.globalhealth.admin.controller.form;

import org.springframework.web.multipart.MultipartFile;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2008
 * Time: 2:34:08 PM
 * Description:
 */
public class FileUploadForm {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}