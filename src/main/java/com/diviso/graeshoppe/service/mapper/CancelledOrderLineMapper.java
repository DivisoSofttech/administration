package com.diviso.graeshoppe.service.mapper;

import com.diviso.graeshoppe.domain.*;
import com.diviso.graeshoppe.service.dto.CancelledOrderLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CancelledOrderLine} and its DTO {@link CancelledOrderLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {CancellationRequestMapper.class})
public interface CancelledOrderLineMapper extends EntityMapper<CancelledOrderLineDTO, CancelledOrderLine> {

    @Mapping(source = "cancellationRequest.id", target = "cancellationRequestId")
    CancelledOrderLineDTO toDto(CancelledOrderLine cancelledOrderLine);

    @Mapping(source = "cancellationRequestId", target = "cancellationRequest")
    CancelledOrderLine toEntity(CancelledOrderLineDTO cancelledOrderLineDTO);

    default CancelledOrderLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        CancelledOrderLine cancelledOrderLine = new CancelledOrderLine();
        cancelledOrderLine.setId(id);
        return cancelledOrderLine;
    }
}
