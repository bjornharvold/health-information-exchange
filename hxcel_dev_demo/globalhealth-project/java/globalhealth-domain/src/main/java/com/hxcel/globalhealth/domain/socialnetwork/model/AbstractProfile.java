package com.hxcel.globalhealth.domain.socialnetwork.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.UserInfo;

import javax.persistence.MappedSuperclass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.ArrayList;

/**
 * User: bjorn
 * Date: Aug 14, 2008
 * Time: 1:11:39 PM
 */
@MappedSuperclass
public class AbstractProfile extends AbstractEntity {
    private List<Blog> blogs;

    @OneToMany
    @Cascade({CascadeType.SAVE_UPDATE})
    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public void addBlog(Blog blog) {
        Blog dupe = null;
        if (blogs == null) {
            blogs = new ArrayList<Blog>();
        }

        for (Blog b : blogs) {
            if (StringUtils.equals(b.getId(), blog.getId())) {
                dupe = b;
            }
        }

        if (dupe != null) {
            blogs.remove(dupe);
        }

        blogs.add(blog);
    }
}
