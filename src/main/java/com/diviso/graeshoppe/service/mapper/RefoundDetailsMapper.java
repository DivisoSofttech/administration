package com.diviso.graeshoppe.service.mapper;

import com.diviso.graeshoppe.domain.*;
import com.diviso.graeshoppe.service.dto.RefoundDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RefoundDetails} and its DTO {@link RefoundDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefoundDetailsMapper extends EntityMapper<RefoundDetailsDTO, RefoundDetails> {



    default RefoundDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefoundDetails refoundDetails = new RefoundDetails();
        refoundDetails.setId(id);
        return refoundDetails;
    }
}
