package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.SubTermService;
import com.diviso.graeshoppe.domain.SubTerm;
import com.diviso.graeshoppe.repository.SubTermRepository;
import com.diviso.graeshoppe.repository.search.SubTermSearchRepository;
import com.diviso.graeshoppe.service.dto.SubTermDTO;
import com.diviso.graeshoppe.service.mapper.SubTermMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link SubTerm}.
 */
@Service
@Transactional
public class SubTermServiceImpl implements SubTermService {

    private final Logger log = LoggerFactory.getLogger(SubTermServiceImpl.class);

    private final SubTermRepository subTermRepository;

    private final SubTermMapper subTermMapper;

    private final SubTermSearchRepository subTermSearchRepository;

    public SubTermServiceImpl(SubTermRepository subTermRepository, SubTermMapper subTermMapper, SubTermSearchRepository subTermSearchRepository) {
        this.subTermRepository = subTermRepository;
        this.subTermMapper = subTermMapper;
        this.subTermSearchRepository = subTermSearchRepository;
    }

    /**
     * Save a subTerm.
     *
     * @param subTermDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SubTermDTO save(SubTermDTO subTermDTO) {
        log.debug("Request to save SubTerm : {}", subTermDTO);
        SubTerm subTerm = subTermMapper.toEntity(subTermDTO);
        subTerm = subTermRepository.save(subTerm);
        SubTermDTO result = subTermMapper.toDto(subTerm);
        subTermSearchRepository.save(subTerm);
        return result;
    }

    /**
     * Get all the subTerms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SubTermDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubTerms");
        return subTermRepository.findAll(pageable)
            .map(subTermMapper::toDto);
    }


    /**
     * Get one subTerm by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SubTermDTO> findOne(Long id) {
        log.debug("Request to get SubTerm : {}", id);
        return subTermRepository.findById(id)
            .map(subTermMapper::toDto);
    }

    /**
     * Delete the subTerm by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubTerm : {}", id);
        subTermRepository.deleteById(id);
        subTermSearchRepository.deleteById(id);
    }

    /**
     * Search for the subTerm corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SubTermDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SubTerms for query {}", query);
        return subTermSearchRepository.search(queryStringQuery(query), pageable)
            .map(subTermMapper::toDto);
    }
}
