package com.diviso.graeshoppe.service.mapper;

import com.diviso.graeshoppe.domain.*;
import com.diviso.graeshoppe.service.dto.TermDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Term} and its DTO {@link TermDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TermMapper extends EntityMapper<TermDTO, Term> {


    @Mapping(target = "subTerms", ignore = true)
    @Mapping(target = "removeSubTerm", ignore = true)
    Term toEntity(TermDTO termDTO);

    default Term fromId(Long id) {
        if (id == null) {
            return null;
        }
        Term term = new Term();
        term.setId(id);
        return term;
    }
}
