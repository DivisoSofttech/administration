package com.diviso.graeshoppe.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A About.
 */
@Entity
@Table(name = "about")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "about")
public class About implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "support_mail")
    private String supportMail;

    @Column(name = "support_phone")
    private Long supportPhone;

    @Column(name = "add_on_1")
    private String addOn1;

    @Column(name = "add_on_2")
    private String addOn2;

    @Column(name = "add_on_3")
    private String addOn3;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public About description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupportMail() {
        return supportMail;
    }

    public About supportMail(String supportMail) {
        this.supportMail = supportMail;
        return this;
    }

    public void setSupportMail(String supportMail) {
        this.supportMail = supportMail;
    }

    public Long getSupportPhone() {
        return supportPhone;
    }

    public About supportPhone(Long supportPhone) {
        this.supportPhone = supportPhone;
        return this;
    }

    public void setSupportPhone(Long supportPhone) {
        this.supportPhone = supportPhone;
    }

    public String getAddOn1() {
        return addOn1;
    }

    public About addOn1(String addOn1) {
        this.addOn1 = addOn1;
        return this;
    }

    public void setAddOn1(String addOn1) {
        this.addOn1 = addOn1;
    }

    public String getAddOn2() {
        return addOn2;
    }

    public About addOn2(String addOn2) {
        this.addOn2 = addOn2;
        return this;
    }

    public void setAddOn2(String addOn2) {
        this.addOn2 = addOn2;
    }

    public String getAddOn3() {
        return addOn3;
    }

    public About addOn3(String addOn3) {
        this.addOn3 = addOn3;
        return this;
    }

    public void setAddOn3(String addOn3) {
        this.addOn3 = addOn3;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof About)) {
            return false;
        }
        return id != null && id.equals(((About) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "About{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", supportMail='" + getSupportMail() + "'" +
            ", supportPhone=" + getSupportPhone() +
            ", addOn1='" + getAddOn1() + "'" +
            ", addOn2='" + getAddOn2() + "'" +
            ", addOn3='" + getAddOn3() + "'" +
            "}";
    }
}
