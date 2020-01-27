package com.diviso.graeshoppe.service;

import com.diviso.graeshoppe.service.dto.TermDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.diviso.graeshoppe.domain.Term}.
 */
public interface TermService {

    /**
     * Save a term.
     *
     * @param termDTO the entity to save.
     * @return the persisted entity.
     */
    TermDTO save(TermDTO termDTO);

    /**
     * Get all the terms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TermDTO> findAll(Pageable pageable);


    /**
     * Get the "id" term.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TermDTO> findOne(Long id);

    /**
     * Delete the "id" term.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the term corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TermDTO> search(String query, Pageable pageable);
}
