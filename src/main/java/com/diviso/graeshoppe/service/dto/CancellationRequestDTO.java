package com.diviso.graeshoppe.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.diviso.graeshoppe.domain.CancellationRequest} entity.
 */
public class CancellationRequestDTO implements Serializable {

    private Long id;

    private String status;

    private String orderId;

    private String paymentId;

    private Integer phoneCode;

    private String customerEmail;

    private Long customerPhone;

    private String storeEmail;

    private Long storePhone;

    private Instant date;

    private Double amount;

    private String reference;


    private Long refoundDetailsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Long getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(Long customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getStoreEmail() {
        return storeEmail;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }

    public Long getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(Long storePhone) {
        this.storePhone = storePhone;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getRefoundDetailsId() {
        return refoundDetailsId;
    }

    public void setRefoundDetailsId(Long refoundDetailsId) {
        this.refoundDetailsId = refoundDetailsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CancellationRequestDTO cancellationRequestDTO = (CancellationRequestDTO) o;
        if (cancellationRequestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cancellationRequestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CancellationRequestDTO{" +
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
            ", refoundDetailsId=" + getRefoundDetailsId() +
            "}";
    }
}
