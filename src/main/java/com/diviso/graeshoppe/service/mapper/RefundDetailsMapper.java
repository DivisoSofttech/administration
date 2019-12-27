package com.diviso.graeshoppe.service.mapper;

import com.diviso.graeshoppe.domain.*;
import com.diviso.graeshoppe.service.dto.RefundDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RefundDetails} and its DTO {@link RefundDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefundDetailsMapper extends EntityMapper<RefundDetailsDTO, RefundDetails> {



    default RefundDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefundDetails refundDetails = new RefundDetails();
        refundDetails.setId(id);
        return refundDetails;
    }
}
