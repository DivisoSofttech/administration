package com.diviso.graeshoppe.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Lob;

/**
 * A DTO for the {@link com.diviso.graeshoppe.domain.Banner} entity.
 */
@ApiModel(description = "Task Banner. @author Neeraja.M.")
public class BannerDTO implements Serializable {

    private Long id;

    private String storeId;

    private String imageLink;

    private Instant startDate;

    private Instant expiryDate;

    private Double cost;
    
    @Lob
    private byte[] image;


    public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BannerDTO bannerDTO = (BannerDTO) o;
        if (bannerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bannerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BannerDTO{" +
            "id=" + getId() +
            ", storeId='" + getStoreId() + "'" +
            ", imageLink='" + getImageLink() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", cost=" + getCost() +
            "}";
    }
}
