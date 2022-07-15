package com.hxcel.globalhealth.calendar.dto;

import com.hxcel.globalhealth.calendar.spec.CalendarUser;

import java.util.Date;
import java.net.URL;

/**
 * User: Bjorn Harvold
 * Date: Dec 28, 2008
 * Time: 7:56:43 PM
 * Responsibility:
 */
public class ChandlerUser implements CalendarUser {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Date created;
    private Date modified;
    private Boolean administrator;
    private URL url;
    private String homedirUrl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Boolean getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getHomedirUrl() {
        return homedirUrl;
    }

    public void setHomedirUrl(String homedirUrl) {
        this.homedirUrl = homedirUrl;
    }

    @Override
    public String toString() {
        return "ChandlerUser{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", administrator=" + administrator +
                ", url=" + url +
                ", homedirUrl='" + homedirUrl + '\'' +
                '}';
    }
}
