package com.diviso.graeshoppe.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CancelledOrderLine entity.
 */
public class CancelledOrderLineDTO implements Serializable {

    private Long id;

    private Long oderLineId;


    private Long cancellationRequestId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOderLineId() {
        return oderLineId;
    }

    public void setOderLineId(Long oderLineId) {
        this.oderLineId = oderLineId;
    }

    public Long getCancellationRequestId() {
        return cancellationRequestId;
    }

    public void setCancellationRequestId(Long cancellationRequestId) {
        this.cancellationRequestId = cancellationRequestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CancelledOrderLineDTO cancelledOrderLineDTO = (CancelledOrderLineDTO) o;
        if (cancelledOrderLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cancelledOrderLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CancelledOrderLineDTO{" +
            "id=" + getId() +
            ", oderLineId=" + getOderLineId() +
            ", cancellationRequest=" + getCancellationRequestId() +
            "}";
    }
}
