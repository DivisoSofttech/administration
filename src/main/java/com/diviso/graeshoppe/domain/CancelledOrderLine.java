package com.diviso.graeshoppe.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A CancelledOrderLine.
 */
@Entity
@Table(name = "cancelled_order_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cancelledorderline")
public class CancelledOrderLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "order_line_id")
    private Long orderLineId;

    @Column(name = "price_per_unit")
    private Double pricePerUnit;

    @Column(name = "ammount")
    private Double ammount;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "product_id")
    private Long productId;

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

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public CancelledOrderLine pricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
        return this;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Double getAmmount() {
        return ammount;
    }

    public CancelledOrderLine ammount(Double ammount) {
        this.ammount = ammount;
        return this;
    }

    public void setAmmount(Double ammount) {
        this.ammount = ammount;
    }

    public Long getQuantity() {
        return quantity;
    }

    public CancelledOrderLine quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public CancelledOrderLine itemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getProductId() {
        return productId;
    }

    public CancelledOrderLine productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
        if (!(o instanceof CancelledOrderLine)) {
            return false;
        }
        return id != null && id.equals(((CancelledOrderLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CancelledOrderLine{" +
            "id=" + getId() +
            ", orderLineId=" + getOrderLineId() +
            ", pricePerUnit=" + getPricePerUnit() +
            ", ammount=" + getAmmount() +
            ", quantity=" + getQuantity() +
            ", itemName='" + getItemName() + "'" +
            ", productId=" + getProductId() +
            "}";
    }
}
