package com.diviso.graeshoppe.service;

import com.diviso.graeshoppe.service.dto.CancelledAuxilaryOrderLineDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.diviso.graeshoppe.domain.CancelledAuxilaryOrderLine}.
 */
public interface CancelledAuxilaryOrderLineService {

    /**
     * Save a cancelledAuxilaryOrderLine.
     *
     * @param cancelledAuxilaryOrderLineDTO the entity to save.
     * @return the persisted entity.
     */
    CancelledAuxilaryOrderLineDTO save(CancelledAuxilaryOrderLineDTO cancelledAuxilaryOrderLineDTO);

    /**
     * Get all the cancelledAuxilaryOrderLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CancelledAuxilaryOrderLineDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cancelledAuxilaryOrderLine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CancelledAuxilaryOrderLineDTO> findOne(Long id);

    /**
     * Delete the "id" cancelledAuxilaryOrderLine.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the cancelledAuxilaryOrderLine corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CancelledAuxilaryOrderLineDTO> search(String query, Pageable pageable);
}
