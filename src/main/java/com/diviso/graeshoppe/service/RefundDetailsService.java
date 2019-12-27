package com.diviso.graeshoppe.service;

import com.diviso.graeshoppe.service.dto.RefundDetailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.diviso.graeshoppe.domain.RefoundDetails}.
 */
public interface RefundDetailsService {

    /**
     * Save a refoundDetails.
     *
     * @param refoundDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    RefundDetailsDTO save(RefundDetailsDTO refoundDetailsDTO);

    /**
     * Get all the refoundDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RefundDetailsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" refoundDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RefundDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" refoundDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the refoundDetails corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RefundDetailsDTO> search(String query, Pageable pageable);

	RefundDetailsDTO save(RefundDetailsDTO refundDetailsDTO, String oderId);
}
