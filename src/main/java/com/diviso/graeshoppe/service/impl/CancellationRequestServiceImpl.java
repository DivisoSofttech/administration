package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.CancellationRequestService;
import com.diviso.graeshoppe.domain.CancellationRequest;
import com.diviso.graeshoppe.repository.CancellationRequestRepository;
import com.diviso.graeshoppe.repository.search.CancellationRequestSearchRepository;
import com.diviso.graeshoppe.service.dto.CancellationRequestDTO;
import com.diviso.graeshoppe.service.mapper.CancellationRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CancellationRequest.
 */
@Service
@Transactional
public class CancellationRequestServiceImpl implements CancellationRequestService {

    private final Logger log = LoggerFactory.getLogger(CancellationRequestServiceImpl.class);

    private final CancellationRequestRepository cancellationRequestRepository;

    private final CancellationRequestMapper cancellationRequestMapper;

    private final CancellationRequestSearchRepository cancellationRequestSearchRepository;

    public CancellationRequestServiceImpl(CancellationRequestRepository cancellationRequestRepository, CancellationRequestMapper cancellationRequestMapper, CancellationRequestSearchRepository cancellationRequestSearchRepository) {
        this.cancellationRequestRepository = cancellationRequestRepository;
        this.cancellationRequestMapper = cancellationRequestMapper;
        this.cancellationRequestSearchRepository = cancellationRequestSearchRepository;
    }

    /**
     * Save a cancellationRequest.
     *
     * @param cancellationRequestDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CancellationRequestDTO save(CancellationRequestDTO cancellationRequestDTO) {
        log.debug("Request to save CancellationRequest : {}", cancellationRequestDTO);
        CancellationRequest cancellationRequest = cancellationRequestMapper.toEntity(cancellationRequestDTO);
        cancellationRequest = cancellationRequestRepository.save(cancellationRequest);
        CancellationRequestDTO result = cancellationRequestMapper.toDto(cancellationRequest);
        cancellationRequestSearchRepository.save(cancellationRequest);
        return result;
    }

    /**
     * Get all the cancellationRequests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CancellationRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CancellationRequests");
        return cancellationRequestRepository.findAll(pageable)
            .map(cancellationRequestMapper::toDto);
    }


    /**
     * Get one cancellationRequest by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CancellationRequestDTO> findOne(Long id) {
        log.debug("Request to get CancellationRequest : {}", id);
        return cancellationRequestRepository.findById(id)
            .map(cancellationRequestMapper::toDto);
    }

    /**
     * Delete the cancellationRequest by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CancellationRequest : {}", id);
        cancellationRequestRepository.deleteById(id);
        cancellationRequestSearchRepository.deleteById(id);
    }

    /**
     * Search for the cancellationRequest corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CancellationRequestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CancellationRequests for query {}", query);
        return cancellationRequestSearchRepository.search(queryStringQuery(query), pageable)
            .map(cancellationRequestMapper::toDto);
    }
}
