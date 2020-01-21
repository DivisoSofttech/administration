package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.CancelledAuxilaryOrderLineService;
import com.diviso.graeshoppe.domain.CancelledAuxilaryOrderLine;
import com.diviso.graeshoppe.repository.CancelledAuxilaryOrderLineRepository;
import com.diviso.graeshoppe.repository.search.CancelledAuxilaryOrderLineSearchRepository;
import com.diviso.graeshoppe.service.dto.CancelledAuxilaryOrderLineDTO;
import com.diviso.graeshoppe.service.mapper.CancelledAuxilaryOrderLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CancelledAuxilaryOrderLine}.
 */
@Service
@Transactional
public class CancelledAuxilaryOrderLineServiceImpl implements CancelledAuxilaryOrderLineService {

    private final Logger log = LoggerFactory.getLogger(CancelledAuxilaryOrderLineServiceImpl.class);

    private final CancelledAuxilaryOrderLineRepository cancelledAuxilaryOrderLineRepository;

    private final CancelledAuxilaryOrderLineMapper cancelledAuxilaryOrderLineMapper;

    private final CancelledAuxilaryOrderLineSearchRepository cancelledAuxilaryOrderLineSearchRepository;

    public CancelledAuxilaryOrderLineServiceImpl(CancelledAuxilaryOrderLineRepository cancelledAuxilaryOrderLineRepository, CancelledAuxilaryOrderLineMapper cancelledAuxilaryOrderLineMapper, CancelledAuxilaryOrderLineSearchRepository cancelledAuxilaryOrderLineSearchRepository) {
        this.cancelledAuxilaryOrderLineRepository = cancelledAuxilaryOrderLineRepository;
        this.cancelledAuxilaryOrderLineMapper = cancelledAuxilaryOrderLineMapper;
        this.cancelledAuxilaryOrderLineSearchRepository = cancelledAuxilaryOrderLineSearchRepository;
    }

    /**
     * Save a cancelledAuxilaryOrderLine.
     *
     * @param cancelledAuxilaryOrderLineDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CancelledAuxilaryOrderLineDTO save(CancelledAuxilaryOrderLineDTO cancelledAuxilaryOrderLineDTO) {
        log.debug("Request to save CancelledAuxilaryOrderLine : {}", cancelledAuxilaryOrderLineDTO);
        CancelledAuxilaryOrderLine cancelledAuxilaryOrderLine = cancelledAuxilaryOrderLineMapper.toEntity(cancelledAuxilaryOrderLineDTO);
        cancelledAuxilaryOrderLine = cancelledAuxilaryOrderLineRepository.save(cancelledAuxilaryOrderLine);
        CancelledAuxilaryOrderLineDTO result = cancelledAuxilaryOrderLineMapper.toDto(cancelledAuxilaryOrderLine);
        cancelledAuxilaryOrderLineSearchRepository.save(cancelledAuxilaryOrderLine);
        return updateToEs(result);
    }
    
    private CancelledAuxilaryOrderLineDTO updateToEs(CancelledAuxilaryOrderLineDTO cancelledAuxilaryOrderLineDTO) {
    	log.debug("<<<<<<<<<<< updateToEs >>>>>>>>>{}",cancelledAuxilaryOrderLineDTO);
    	CancelledAuxilaryOrderLine cancelledAuxilaryOrderLine = cancelledAuxilaryOrderLineMapper.toEntity(cancelledAuxilaryOrderLineDTO);
    	cancelledAuxilaryOrderLine = cancelledAuxilaryOrderLineRepository.save(cancelledAuxilaryOrderLine);
    	CancelledAuxilaryOrderLineDTO result = cancelledAuxilaryOrderLineMapper.toDto(cancelledAuxilaryOrderLine);
		return result;
    }

    /**
     * Get all the cancelledAuxilaryOrderLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CancelledAuxilaryOrderLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CancelledAuxilaryOrderLines");
        return cancelledAuxilaryOrderLineRepository.findAll(pageable)
            .map(cancelledAuxilaryOrderLineMapper::toDto);
    }


    /**
     * Get one cancelledAuxilaryOrderLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CancelledAuxilaryOrderLineDTO> findOne(Long id) {
        log.debug("Request to get CancelledAuxilaryOrderLine : {}", id);
        return cancelledAuxilaryOrderLineRepository.findById(id)
            .map(cancelledAuxilaryOrderLineMapper::toDto);
    }

    /**
     * Delete the cancelledAuxilaryOrderLine by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CancelledAuxilaryOrderLine : {}", id);
        cancelledAuxilaryOrderLineRepository.deleteById(id);
        cancelledAuxilaryOrderLineSearchRepository.deleteById(id);
    }

    /**
     * Search for the cancelledAuxilaryOrderLine corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CancelledAuxilaryOrderLineDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CancelledAuxilaryOrderLines for query {}", query);
        return cancelledAuxilaryOrderLineSearchRepository.search(queryStringQuery(query), pageable)
            .map(cancelledAuxilaryOrderLineMapper::toDto);
    }
}
