package com.diviso.graeshoppe.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.diviso.graeshoppe.domain.SubTerm} entity.
 */
public class SubTermDTO implements Serializable {

    private Long id;

    private Long subTermId;

    private String termDescription;


    private Long termId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubTermId() {
        return subTermId;
    }

    public void setSubTermId(Long subTermId) {
        this.subTermId = subTermId;
    }

    public String getTermDescription() {
        return termDescription;
    }

    public void setTermDescription(String termDescription) {
        this.termDescription = termDescription;
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubTermDTO subTermDTO = (SubTermDTO) o;
        if (subTermDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subTermDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubTermDTO{" +
            "id=" + getId() +
            ", subTermId=" + getSubTermId() +
            ", termDescription='" + getTermDescription() + "'" +
            ", termId=" + getTermId() +
            "}";
    }
}
