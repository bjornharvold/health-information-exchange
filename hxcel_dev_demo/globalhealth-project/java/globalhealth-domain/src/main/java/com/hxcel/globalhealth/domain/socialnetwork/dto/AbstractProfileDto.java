package com.hxcel.globalhealth.domain.socialnetwork.dto;

import com.hxcel.globalhealth.domain.common.dto.AbstractDto;
import com.hxcel.globalhealth.domain.socialnetwork.model.Blog;

import javax.persistence.OneToMany;
import java.util.List;

/**
 * User: bjorn
 * Date: Sep 7, 2008
 * Time: 9:52:23 PM
 */
public class AbstractProfileDto extends AbstractDto {
    private List<BlogDto> blogs;

    public List<BlogDto> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<BlogDto> blogs) {
        this.blogs = blogs;
    }
}
