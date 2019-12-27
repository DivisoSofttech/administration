package com.diviso.graeshoppe.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RefoundDetails entity.
 */
public class RefoundDetailsDTO implements Serializable {

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

        RefoundDetailsDTO refoundDetailsDTO = (RefoundDetailsDTO) o;
        if (refoundDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refoundDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefoundDetailsDTO{" +
            "id=" + getId() +
            ", refundId='" + getRefundId() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
