package com.diviso.graeshoppe.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CancelledOrderLine.
 */
@Entity
@Table(name = "cancelled_order_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cancelledorderline")
public class CancelledOrderLine implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_line_id")
    private Long orderLineId;

    @ManyToOne
    @JsonIgnoreProperties("cancelledOrderLines")
    private CancellationRequest cancellationRequest;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderLineId() {
        return orderLineId;
    }

    public CancelledOrderLine orderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
        return this;
    }

    public void setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public CancellationRequest getCancellationRequest() {
        return cancellationRequest;
    }

    public CancelledOrderLine cancellationRequest(CancellationRequest cancellationRequest) {
        this.cancellationRequest = cancellationRequest;
        return this;
    }

    public void setCancellationRequest(CancellationRequest cancellationRequest) {
        this.cancellationRequest = cancellationRequest;
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
        CancelledOrderLine cancelledOrderLine = (CancelledOrderLine) o;
        if (cancelledOrderLine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cancelledOrderLine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CancelledOrderLine{" +
            "id=" + getId() +
            ", orderLineId=" + getOrderLineId() +
            "}";
    }
}