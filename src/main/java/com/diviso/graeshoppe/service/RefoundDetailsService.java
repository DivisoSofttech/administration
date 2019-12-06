package com.diviso.graeshoppe.service;

import com.diviso.graeshoppe.service.dto.RefoundDetailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RefoundDetails.
 */
public interface RefoundDetailsService {

    /**
     * Save a refoundDetails.
     *
     * @param refoundDetailsDTO the entity to save
     * @return the persisted entity
     */
    RefoundDetailsDTO save(RefoundDetailsDTO refoundDetailsDTO);

    /**
     * Get all the refoundDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RefoundDetailsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" refoundDetails.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RefoundDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" refoundDetails.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refoundDetails corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RefoundDetailsDTO> search(String query, Pageable pageable);

	RefoundDetailsDTO save(RefoundDetailsDTO refoundDetailsDTO, String oderId);
}
