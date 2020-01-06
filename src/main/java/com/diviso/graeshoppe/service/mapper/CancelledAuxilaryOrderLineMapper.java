package com.diviso.graeshoppe.service.mapper;

import com.diviso.graeshoppe.domain.*;
import com.diviso.graeshoppe.service.dto.CancelledAuxilaryOrderLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CancelledAuxilaryOrderLine} and its DTO {@link CancelledAuxilaryOrderLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {CancellationRequestMapper.class})
public interface CancelledAuxilaryOrderLineMapper extends EntityMapper<CancelledAuxilaryOrderLineDTO, CancelledAuxilaryOrderLine> {

    @Mapping(source = "cancellationRequest.id", target = "cancellationRequestId")
    CancelledAuxilaryOrderLineDTO toDto(CancelledAuxilaryOrderLine cancelledAuxilaryOrderLine);

    @Mapping(source = "cancellationRequestId", target = "cancellationRequest")
    CancelledAuxilaryOrderLine toEntity(CancelledAuxilaryOrderLineDTO cancelledAuxilaryOrderLineDTO);

    default CancelledAuxilaryOrderLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        CancelledAuxilaryOrderLine cancelledAuxilaryOrderLine = new CancelledAuxilaryOrderLine();
        cancelledAuxilaryOrderLine.setId(id);
        return cancelledAuxilaryOrderLine;
    }
}
