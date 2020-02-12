package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.TermService;
import com.diviso.graeshoppe.domain.Term;
import com.diviso.graeshoppe.repository.TermRepository;
import com.diviso.graeshoppe.repository.search.TermSearchRepository;
import com.diviso.graeshoppe.service.dto.TermDTO;
import com.diviso.graeshoppe.service.mapper.TermMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Term}.
 */
@Service
@Transactional
public class TermServiceImpl implements TermService {

    private final Logger log = LoggerFactory.getLogger(TermServiceImpl.class);

    private final TermRepository termRepository;

    private final TermMapper termMapper;

    private final TermSearchRepository termSearchRepository;

    public TermServiceImpl(TermRepository termRepository, TermMapper termMapper, TermSearchRepository termSearchRepository) {
        this.termRepository = termRepository;
        this.termMapper = termMapper;
        this.termSearchRepository = termSearchRepository;
    }

    /**
     * Save a term.
     *
     * @param termDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TermDTO save(TermDTO termDTO) {
        log.debug("Request to save Term : {}", termDTO);
        Term term = termMapper.toEntity(termDTO);
        term.setTermId(termDTO.getTermId());
        term = termRepository.save(term);
        TermDTO result = termMapper.toDto(term);
        termSearchRepository.save(term);
        return result;
    }

    /**
     * Get all the terms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TermDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Terms");
        return termRepository.findAll(pageable)
            .map(termMapper::toDto);
    }


    /**
     * Get one term by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TermDTO> findOne(Long id) {
        log.debug("Request to get Term : {}", id);
        return termRepository.findById(id)
            .map(termMapper::toDto);
    }

    /**
     * Delete the term by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Term : {}", id);
        termRepository.deleteById(id);
        termSearchRepository.deleteById(id);
    }

    /**
     * Search for the term corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TermDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Terms for query {}", query);
        return termSearchRepository.search(queryStringQuery(query), pageable)
            .map(termMapper::toDto);
    }
}
