package com.diviso.graeshoppe.service;

import com.diviso.graeshoppe.service.dto.SubTermDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.diviso.graeshoppe.domain.SubTerm}.
 */
public interface SubTermService {

    /**
     * Save a subTerm.
     *
     * @param subTermDTO the entity to save.
     * @return the persisted entity.
     */
    SubTermDTO save(SubTermDTO subTermDTO);

    /**
     * Get all the subTerms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SubTermDTO> findAll(Pageable pageable);


    /**
     * Get the "id" subTerm.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubTermDTO> findOne(Long id);

    /**
     * Delete the "id" subTerm.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the subTerm corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SubTermDTO> search(String query, Pageable pageable);
}
