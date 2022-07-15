package com.hxcel.globalhealth.domain.socialnetwork.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

/**
 * User: bjorn
 * Date: Aug 14, 2008
 * Time: 1:02:42 PM
 * This is for the practitioner to fill out. This is public unencrypted information.
 */
@Entity
public class ProfessionalProfile extends AbstractProfile implements Serializable {
    private List<Association> associations;
    private List<Certification> certifications;
    private List<Specialty> specialties;
    private List<Publication> publications;

    @OneToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    public List<Association> getAssociations() {
        return associations;
    }

    public void setAssociations(List<Association> associations) {
        this.associations = associations;
    }

    @OneToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications = certifications;
    }

    @OneToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    public List<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Specialty> specialties) {
        this.specialties = specialties;
    }

    @OneToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }
}
