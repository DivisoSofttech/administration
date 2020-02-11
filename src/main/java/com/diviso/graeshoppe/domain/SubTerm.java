package com.diviso.graeshoppe.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A SubTerm.
 */
@Entity
@Table(name = "sub_term")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "subterm")
public class SubTerm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "sub_term_id")
    private Long subTermId;

    @Column(name = "term_description")
    private String termDescription;

    @ManyToOne
    @JsonIgnoreProperties("subTerms")
    private Term term;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubTermId() {
        return subTermId;
    }

    public SubTerm subTermId(Long subTermId) {
        this.subTermId = subTermId;
        return this;
    }

    public void setSubTermId(Long subTermId) {
        this.subTermId = subTermId;
    }

    public String getTermDescription() {
        return termDescription;
    }

    public SubTerm termDescription(String termDescription) {
        this.termDescription = termDescription;
        return this;
    }

    public void setTermDescription(String termDescription) {
        this.termDescription = termDescription;
    }

    public Term getTerm() {
        return term;
    }

    public SubTerm term(Term term) {
        this.term = term;
        return this;
    }

    public void setTerm(Term term) {
        this.term = term;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubTerm)) {
            return false;
        }
        return id != null && id.equals(((SubTerm) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SubTerm{" +
            "id=" + getId() +
            ", subTermId=" + getSubTermId() +
            ", termDescription='" + getTermDescription() + "'" +
            "}";
    }
}
