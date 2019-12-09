package com.diviso.graeshoppe.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RefoundDetails.
 */
@Entity
@Table(name = "refound_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "refounddetails")
public class RefoundDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refund_id")
    private String refundId;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefundId() {
        return refundId;
    }

    public RefoundDetails refundId(String refundId) {
        this.refundId = refundId;
        return this;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getStatus() {
        return status;
    }

    public RefoundDetails status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefoundDetails refoundDetails = (RefoundDetails) o;
        if (refoundDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refoundDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefoundDetails{" +
            "id=" + getId() +
            ", refundId='" + getRefundId() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}