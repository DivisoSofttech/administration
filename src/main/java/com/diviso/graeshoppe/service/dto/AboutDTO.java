package com.diviso.graeshoppe.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.diviso.graeshoppe.domain.About} entity.
 */
public class AboutDTO implements Serializable {

    private Long id;

    private String description;

    private String supportMail;

    private Long supportPhone;

    private String addOn1;

    private String addOn2;

    private String addOn3;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupportMail() {
        return supportMail;
    }

    public void setSupportMail(String supportMail) {
        this.supportMail = supportMail;
    }

    public Long getSupportPhone() {
        return supportPhone;
    }

    public void setSupportPhone(Long supportPhone) {
        this.supportPhone = supportPhone;
    }

    public String getAddOn1() {
        return addOn1;
    }

    public void setAddOn1(String addOn1) {
        this.addOn1 = addOn1;
    }

    public String getAddOn2() {
        return addOn2;
    }

    public void setAddOn2(String addOn2) {
        this.addOn2 = addOn2;
    }

    public String getAddOn3() {
        return addOn3;
    }

    public void setAddOn3(String addOn3) {
        this.addOn3 = addOn3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AboutDTO aboutDTO = (AboutDTO) o;
        if (aboutDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aboutDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AboutDTO{" +
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
