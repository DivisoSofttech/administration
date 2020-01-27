package com.diviso.graeshoppe.service.mapper;

import com.diviso.graeshoppe.domain.*;
import com.diviso.graeshoppe.service.dto.SubTermDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubTerm} and its DTO {@link SubTermDTO}.
 */
@Mapper(componentModel = "spring", uses = {TermMapper.class})
public interface SubTermMapper extends EntityMapper<SubTermDTO, SubTerm> {

    @Mapping(source = "term.id", target = "termId")
    SubTermDTO toDto(SubTerm subTerm);

    @Mapping(source = "termId", target = "term")
    SubTerm toEntity(SubTermDTO subTermDTO);

    default SubTerm fromId(Long id) {
        if (id == null) {
            return null;
        }
        SubTerm subTerm = new SubTerm();
        subTerm.setId(id);
        return subTerm;
    }
}
