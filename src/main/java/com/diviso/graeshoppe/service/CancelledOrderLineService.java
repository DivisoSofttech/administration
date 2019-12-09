package com.diviso.graeshoppe.service;

import com.diviso.graeshoppe.service.dto.CancelledOrderLineDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CancelledOrderLine.
 */
public interface CancelledOrderLineService {

    /**
     * Save a cancelledOrderLine.
     *
     * @param cancelledOrderLineDTO the entity to save
     * @return the persisted entity
     */
    CancelledOrderLineDTO save(CancelledOrderLineDTO cancelledOrderLineDTO);

    /**
     * Get all the cancelledOrderLines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CancelledOrderLineDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cancelledOrderLine.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CancelledOrderLineDTO> findOne(Long id);

    /**
     * Delete the "id" cancelledOrderLine.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cancelledOrderLine corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CancelledOrderLineDTO> search(String query, Pageable pageable);
}
