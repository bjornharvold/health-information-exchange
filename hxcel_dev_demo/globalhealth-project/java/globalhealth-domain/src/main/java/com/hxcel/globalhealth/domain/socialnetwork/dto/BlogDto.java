package com.hxcel.globalhealth.domain.socialnetwork.dto;

import com.hxcel.globalhealth.domain.common.dto.AbstractDto;
import java.io.Serializable;
import java.util.List;

/**
 * User: bjorn
 * Date: Sep 7, 2008
 * Time: 9:53:35 PM
 */
public class BlogDto extends AbstractDto implements Serializable {
    private static final long serialVersionUID = 1971480898500413424L;

    private String name;
    private String tags;
    private List<BlogEntryDto> entries;

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

    public List<BlogEntryDto> getEntries() {
        return entries;
    }

    public void setEntries(List<BlogEntryDto> entries) {
        this.entries = entries;
    }
}
