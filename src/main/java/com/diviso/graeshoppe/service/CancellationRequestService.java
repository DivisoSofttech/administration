package com.diviso.graeshoppe.service;

import com.diviso.graeshoppe.service.dto.CancellationRequestDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CancellationRequest.
 */
public interface CancellationRequestService {

    /**
     * Save a cancellationRequest.
     *
     * @param cancellationRequestDTO the entity to save
     * @return the persisted entity
     */
    CancellationRequestDTO save(CancellationRequestDTO cancellationRequestDTO);

    /**
     * Get all the cancellationRequests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CancellationRequestDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cancellationRequest.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CancellationRequestDTO> findOne(Long id);

    /**
     * Delete the "id" cancellationRequest.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cancellationRequest corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CancellationRequestDTO> search(String query, Pageable pageable);
}
