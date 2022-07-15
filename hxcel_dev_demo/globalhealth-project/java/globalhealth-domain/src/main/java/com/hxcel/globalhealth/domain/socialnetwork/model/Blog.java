package com.hxcel.globalhealth.domain.socialnetwork.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * User: bjorn
 * Date: Aug 14, 2008
 * Time: 1:00:49 PM
 */
@Entity
public class Blog extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 5423626515972224345L;
    private String name;
    private String tags;
    private List<BlogEntry> entries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @OneToMany
    @Cascade({CascadeType.SAVE_UPDATE})
    public List<BlogEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<BlogEntry> entries) {
        this.entries = entries;
    }

}
