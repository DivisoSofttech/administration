package com.diviso.graeshoppe.service.mapper;

import com.diviso.graeshoppe.domain.*;
import com.diviso.graeshoppe.service.dto.CancellationRequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CancellationRequest} and its DTO {@link CancellationRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = {RefundDetailsMapper.class})
public interface CancellationRequestMapper extends EntityMapper<CancellationRequestDTO, CancellationRequest> {

    @Mapping(source = "refundDetails.id", target = "refundDetailsId")
    CancellationRequestDTO toDto(CancellationRequest cancellationRequest);

    @Mapping(source = "refundDetailsId", target = "refundDetails")
    @Mapping(target = "cancelledOrderLines", ignore = true)
    @Mapping(target = "removeCancelledOrderLines", ignore = true)
    @Mapping(target = "cancelledAuxilaryOrderLines", ignore = true)
    @Mapping(target = "removeCancelledAuxilaryOrderLine", ignore = true)
    CancellationRequest toEntity(CancellationRequestDTO cancellationRequestDTO);

    default CancellationRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        CancellationRequest cancellationRequest = new CancellationRequest();
        cancellationRequest.setId(id);
        return cancellationRequest;
    }
}
