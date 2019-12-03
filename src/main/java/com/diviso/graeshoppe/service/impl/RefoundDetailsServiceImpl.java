package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.RefoundDetailsService;
import com.diviso.graeshoppe.domain.RefoundDetails;
import com.diviso.graeshoppe.repository.RefoundDetailsRepository;
import com.diviso.graeshoppe.repository.search.RefoundDetailsSearchRepository;
import com.diviso.graeshoppe.service.dto.RefoundDetailsDTO;
import com.diviso.graeshoppe.service.mapper.RefoundDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefoundDetails.
 */
@Service
@Transactional
public class RefoundDetailsServiceImpl implements RefoundDetailsService {

    private final Logger log = LoggerFactory.getLogger(RefoundDetailsServiceImpl.class);

    private final RefoundDetailsRepository refoundDetailsRepository;

    private final RefoundDetailsMapper refoundDetailsMapper;

    private final RefoundDetailsSearchRepository refoundDetailsSearchRepository;

    public RefoundDetailsServiceImpl(RefoundDetailsRepository refoundDetailsRepository, RefoundDetailsMapper refoundDetailsMapper, RefoundDetailsSearchRepository refoundDetailsSearchRepository) {
        this.refoundDetailsRepository = refoundDetailsRepository;
        this.refoundDetailsMapper = refoundDetailsMapper;
        this.refoundDetailsSearchRepository = refoundDetailsSearchRepository;
    }

    /**
     * Save a refoundDetails.
     *
     * @param refoundDetailsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefoundDetailsDTO save(RefoundDetailsDTO refoundDetailsDTO) {
        log.debug("Request to save RefoundDetails : {}", refoundDetailsDTO);
        RefoundDetails refoundDetails = refoundDetailsMapper.toEntity(refoundDetailsDTO);
        refoundDetails = refoundDetailsRepository.save(refoundDetails);
        RefoundDetailsDTO result = refoundDetailsMapper.toDto(refoundDetails);
        refoundDetailsSearchRepository.save(refoundDetails);
        return result;
    }

    /**
     * Get all the refoundDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefoundDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefoundDetails");
        return refoundDetailsRepository.findAll(pageable)
            .map(refoundDetailsMapper::toDto);
    }


    /**
     * Get one refoundDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RefoundDetailsDTO> findOne(Long id) {
        log.debug("Request to get RefoundDetails : {}", id);
        return refoundDetailsRepository.findById(id)
            .map(refoundDetailsMapper::toDto);
    }

    /**
     * Delete the refoundDetails by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefoundDetails : {}", id);
        refoundDetailsRepository.deleteById(id);
        refoundDetailsSearchRepository.deleteById(id);
    }

    /**
     * Search for the refoundDetails corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefoundDetailsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefoundDetails for query {}", query);
        return refoundDetailsSearchRepository.search(queryStringQuery(query), pageable)
            .map(refoundDetailsMapper::toDto);
    }
}
