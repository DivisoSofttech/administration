package com.diviso.graeshoppe.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Term.
 */
@Entity
@Table(name = "term")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "term")
public class Term implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "term")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SubTerm> subTerms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Term title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<SubTerm> getSubTerms() {
        return subTerms;
    }

    public Term subTerms(Set<SubTerm> subTerms) {
        this.subTerms = subTerms;
        return this;
    }

    public Term addSubTerm(SubTerm subTerm) {
        this.subTerms.add(subTerm);
        subTerm.setTerm(this);
        return this;
    }

    public Term removeSubTerm(SubTerm subTerm) {
        this.subTerms.remove(subTerm);
        subTerm.setTerm(null);
        return this;
    }

    public void setSubTerms(Set<SubTerm> subTerms) {
        this.subTerms = subTerms;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Term)) {
            return false;
        }
        return id != null && id.equals(((Term) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Term{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
