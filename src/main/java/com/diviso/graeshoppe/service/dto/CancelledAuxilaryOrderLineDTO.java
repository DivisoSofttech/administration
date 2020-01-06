package com.diviso.graeshoppe.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.diviso.graeshoppe.domain.CancelledAuxilaryOrderLine} entity.
 */
public class CancelledAuxilaryOrderLineDTO implements Serializable {

    private Long id;

    private Long orderLineId;

    private Double pricePerUnit;

    private Double ammount;

    private Long quantity;

    private String itemName;

    private Long productId;


    private Long cancellationRequestId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Double getAmmount() {
        return ammount;
    }

    public void setAmmount(Double ammount) {
        this.ammount = ammount;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

        CancelledAuxilaryOrderLineDTO cancelledAuxilaryOrderLineDTO = (CancelledAuxilaryOrderLineDTO) o;
        if (cancelledAuxilaryOrderLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cancelledAuxilaryOrderLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CancelledAuxilaryOrderLineDTO{" +
            "id=" + getId() +
            ", orderLineId=" + getOrderLineId() +
            ", pricePerUnit=" + getPricePerUnit() +
            ", ammount=" + getAmmount() +
            ", quantity=" + getQuantity() +
            ", itemName='" + getItemName() + "'" +
            ", productId=" + getProductId() +
            ", cancellationRequestId=" + getCancellationRequestId() +
            "}";
    }
}
