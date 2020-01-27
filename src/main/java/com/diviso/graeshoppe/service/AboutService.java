package com.diviso.graeshoppe.service;

import com.diviso.graeshoppe.service.dto.AboutDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.diviso.graeshoppe.domain.About}.
 */
public interface AboutService {

    /**
     * Save a about.
     *
     * @param aboutDTO the entity to save.
     * @return the persisted entity.
     */
    AboutDTO save(AboutDTO aboutDTO);

    /**
     * Get all the abouts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AboutDTO> findAll(Pageable pageable);


    /**
     * Get the "id" about.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AboutDTO> findOne(Long id);

    /**
     * Delete the "id" about.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the about corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AboutDTO> search(String query, Pageable pageable);
}
