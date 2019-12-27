package com.diviso.graeshoppe.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * Task Banner.
 * @author Neeraja.M.
 */
@Entity
@Table(name = "banner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "banner")
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @Column(name = "cost")
    private Double cost;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public Banner storeId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public Banner imageLink(String imageLink) {
        this.imageLink = imageLink;
        return this;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Banner startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public Banner expiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Double getCost() {
        return cost;
    }

    public Banner cost(Double cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Banner)) {
            return false;
        }
        return id != null && id.equals(((Banner) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Banner{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", imageLink='" + getImageLink() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", cost=" + getCost() +
            "}";
    }
}
