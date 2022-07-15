package com.hxcel.globalhealth.domain.socialnetwork.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Column;
import java.io.Serializable;

/**
 * User: bjorn
 * Date: Aug 14, 2008
 * Time: 2:05:21 PM
 */
@Entity
public class BlogEntry extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -3156074736806669467L;
    private String title;
    private String entry;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(length = 10000)
    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }
}
