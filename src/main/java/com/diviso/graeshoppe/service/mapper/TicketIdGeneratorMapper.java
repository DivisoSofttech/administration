package com.diviso.graeshoppe.service.mapper;

import com.diviso.graeshoppe.domain.*;
import com.diviso.graeshoppe.service.dto.TicketIdGeneratorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TicketIdGenerator} and its DTO {@link TicketIdGeneratorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TicketIdGeneratorMapper extends EntityMapper<TicketIdGeneratorDTO, TicketIdGenerator> {



    default TicketIdGenerator fromId(Long id) {
        if (id == null) {
            return null;
        }
        TicketIdGenerator ticketIdGenerator = new TicketIdGenerator();
        ticketIdGenerator.setId(id);
        return ticketIdGenerator;
    }
}
