package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.CancelledOrderLineService;
import com.diviso.graeshoppe.domain.CancelledOrderLine;
import com.diviso.graeshoppe.repository.CancelledOrderLineRepository;
import com.diviso.graeshoppe.repository.search.CancelledOrderLineSearchRepository;
import com.diviso.graeshoppe.service.dto.CancelledOrderLineDTO;
import com.diviso.graeshoppe.service.mapper.CancelledOrderLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CancelledOrderLine}.
 */
@Service
@Transactional
public class CancelledOrderLineServiceImpl implements CancelledOrderLineService {

    private final Logger log = LoggerFactory.getLogger(CancelledOrderLineServiceImpl.class);

    private final CancelledOrderLineRepository cancelledOrderLineRepository;

    private final CancelledOrderLineMapper cancelledOrderLineMapper;

    private final CancelledOrderLineSearchRepository cancelledOrderLineSearchRepository;

    public CancelledOrderLineServiceImpl(CancelledOrderLineRepository cancelledOrderLineRepository, CancelledOrderLineMapper cancelledOrderLineMapper, CancelledOrderLineSearchRepository cancelledOrderLineSearchRepository) {
        this.cancelledOrderLineRepository = cancelledOrderLineRepository;
        this.cancelledOrderLineMapper = cancelledOrderLineMapper;
        this.cancelledOrderLineSearchRepository = cancelledOrderLineSearchRepository;
    }

    /**
     * Save a cancelledOrderLine.
     *
     * @param cancelledOrderLineDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CancelledOrderLineDTO save(CancelledOrderLineDTO cancelledOrderLineDTO) {
        log.debug("Request to save CancelledOrderLine : {}", cancelledOrderLineDTO);
        CancelledOrderLine cancelledOrderLine = cancelledOrderLineMapper.toEntity(cancelledOrderLineDTO);
        cancelledOrderLine = cancelledOrderLineRepository.save(cancelledOrderLine);
        CancelledOrderLineDTO result = cancelledOrderLineMapper.toDto(cancelledOrderLine);
        cancelledOrderLineSearchRepository.save(cancelledOrderLine);
        return result;
    }

    /**
     * Get all the cancelledOrderLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CancelledOrderLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CancelledOrderLines");
        return cancelledOrderLineRepository.findAll(pageable)
            .map(cancelledOrderLineMapper::toDto);
    }


    /**
     * Get one cancelledOrderLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CancelledOrderLineDTO> findOne(Long id) {
        log.debug("Request to get CancelledOrderLine : {}", id);
        return cancelledOrderLineRepository.findById(id)
            .map(cancelledOrderLineMapper::toDto);
    }

    /**
     * Delete the cancelledOrderLine by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CancelledOrderLine : {}", id);
        cancelledOrderLineRepository.deleteById(id);
        cancelledOrderLineSearchRepository.deleteById(id);
    }

    /**
     * Search for the cancelledOrderLine corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CancelledOrderLineDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CancelledOrderLines for query {}", query);
        return cancelledOrderLineSearchRepository.search(queryStringQuery(query), pageable)
            .map(cancelledOrderLineMapper::toDto);
    }
}
