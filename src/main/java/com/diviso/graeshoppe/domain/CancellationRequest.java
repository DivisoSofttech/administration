package com.diviso.graeshoppe.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CancellationRequest.
 */
@Entity
@Table(name = "cancellation_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cancellationrequest")
public class CancellationRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "phone_code")
    private Integer phoneCode;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_phone")
    private Long customerPhone;

    @Column(name = "store_email")
    private String storeEmail;

    @Column(name = "store_phone")
    private Long storePhone;

    @Column(name = "jhi_date")
    private Instant date;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "reference")
    private String reference;

    @OneToOne
    @JoinColumn(unique = true)
    private RefoundDetails refoundDetails;

    @OneToMany(mappedBy = "cancellationRequest")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CancelledOrderLine> cancelledOrderLines = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public CancellationRequest status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public CancellationRequest orderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public CancellationRequest paymentId(String paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getPhoneCode() {
        return phoneCode;
    }

    public CancellationRequest phoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
        return this;
    }

    public void setPhoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public CancellationRequest customerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Long getCustomerPhone() {
        return customerPhone;
    }

    public CancellationRequest customerPhone(Long customerPhone) {
        this.customerPhone = customerPhone;
        return this;
    }

    public void setCustomerPhone(Long customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getStoreEmail() {
        return storeEmail;
    }

    public CancellationRequest storeEmail(String storeEmail) {
        this.storeEmail = storeEmail;
        return this;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }

    public Long getStorePhone() {
        return storePhone;
    }

    public CancellationRequest storePhone(Long storePhone) {
        this.storePhone = storePhone;
        return this;
    }

    public void setStorePhone(Long storePhone) {
        this.storePhone = storePhone;
    }

    public Instant getDate() {
        return date;
    }

    public CancellationRequest date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public CancellationRequest amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public CancellationRequest reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public RefoundDetails getRefoundDetails() {
        return refoundDetails;
    }

    public CancellationRequest refoundDetails(RefoundDetails refoundDetails) {
        this.refoundDetails = refoundDetails;
        return this;
    }

    public void setRefoundDetails(RefoundDetails refoundDetails) {
        this.refoundDetails = refoundDetails;
    }

    public Set<CancelledOrderLine> getCancelledOrderLines() {
        return cancelledOrderLines;
    }

    public CancellationRequest cancelledOrderLines(Set<CancelledOrderLine> cancelledOrderLines) {
        this.cancelledOrderLines = cancelledOrderLines;
        return this;
    }

    public CancellationRequest addCancelledOrderLines(CancelledOrderLine cancelledOrderLine) {
        this.cancelledOrderLines.add(cancelledOrderLine);
        cancelledOrderLine.setCancellationRequest(this);
        return this;
    }

    public CancellationRequest removeCancelledOrderLines(CancelledOrderLine cancelledOrderLine) {
        this.cancelledOrderLines.remove(cancelledOrderLine);
        cancelledOrderLine.setCancellationRequest(null);
        return this;
    }

    public void setCancelledOrderLines(Set<CancelledOrderLine> cancelledOrderLines) {
        this.cancelledOrderLines = cancelledOrderLines;
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
        CancellationRequest cancellationRequest = (CancellationRequest) o;
        if (cancellationRequest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cancellationRequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CancellationRequest{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", orderId='" + getOrderId() + "'" +
            ", paymentId='" + getPaymentId() + "'" +
            ", phoneCode=" + getPhoneCode() +
            ", customerEmail='" + getCustomerEmail() + "'" +
            ", customerPhone=" + getCustomerPhone() +
            ", storeEmail='" + getStoreEmail() + "'" +
            ", storePhone=" + getStorePhone() +
            ", date='" + getDate() + "'" +
            ", amount=" + getAmount() +
            ", reference='" + getReference() + "'" +
            "}";
    }
}
