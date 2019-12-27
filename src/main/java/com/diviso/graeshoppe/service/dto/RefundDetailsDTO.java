package com.diviso.graeshoppe.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.diviso.graeshoppe.domain.RefundDetails} entity.
 */
public class RefundDetailsDTO implements Serializable {

    private Long id;

    private String refundId;

    private String status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefundDetailsDTO refundDetailsDTO = (RefundDetailsDTO) o;
        if (refundDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refundDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefundDetailsDTO{" +
            "id=" + getId() +
            ", refundId='" + getRefundId() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
