package com.hxcel.globalhealth.platform.controller.form;

import com.hxcel.globalhealth.platform.spec.model.enums.IconSizeCd;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2008
 * Time: 2:34:08 PM
 * Description:
 */
public class ImageFileUploadForm extends FileUploadForm {
    private IconSizeCd size;

    public ImageFileUploadForm(IconSizeCd size) {
        this.size = size;
    }

    public IconSizeCd getSize() {
        return size;
    }

    public void setSize(IconSizeCd size) {
        this.size = size;
    }
}