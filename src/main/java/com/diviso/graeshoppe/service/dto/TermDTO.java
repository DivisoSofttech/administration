package com.diviso.graeshoppe.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.diviso.graeshoppe.domain.Term} entity.
 */
public class TermDTO implements Serializable {

    private Long id;

    private Long termId;

    private String title;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TermDTO termDTO = (TermDTO) o;
        if (termDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), termDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TermDTO{" +
            "id=" + getId() +
            ", termId=" + getTermId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
