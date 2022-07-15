package com.hxcel.globalhealth.admin.controller.application.form;

import com.hxcel.globalhealth.domain.platform.model.Application;
import org.springframework.web.multipart.MultipartFile;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2008
 * Time: 2:34:08 PM
 * Description:
 */
public class ApplicationForm {
    private MultipartFile swf;
    private MultipartFile thumbnail;
    private Application application;

    public ApplicationForm(){
        application = new Application();
    }
    
    public ApplicationForm(Application app){
        application = app;
    }

    public MultipartFile getSwf() {
        return swf;
    }

    public void setSwf(MultipartFile swf) {
        this.swf = swf;
    }

    public MultipartFile getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MultipartFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
