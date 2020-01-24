package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.TicketIdGeneratorService;
import com.diviso.graeshoppe.domain.TicketIdGenerator;
import com.diviso.graeshoppe.repository.TicketIdGeneratorRepository;
import com.diviso.graeshoppe.repository.search.TicketIdGeneratorSearchRepository;
import com.diviso.graeshoppe.service.dto.TicketIdGeneratorDTO;
import com.diviso.graeshoppe.service.mapper.TicketIdGeneratorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TicketIdGenerator}.
 */
@Service
@Transactional
public class TicketIdGeneratorServiceImpl implements TicketIdGeneratorService {

    private final Logger log = LoggerFactory.getLogger(TicketIdGeneratorServiceImpl.class);

    private final TicketIdGeneratorRepository ticketIdGeneratorRepository;

    private final TicketIdGeneratorMapper ticketIdGeneratorMapper;

    private final TicketIdGeneratorSearchRepository ticketIdGeneratorSearchRepository;

    public TicketIdGeneratorServiceImpl(TicketIdGeneratorRepository ticketIdGeneratorRepository, TicketIdGeneratorMapper ticketIdGeneratorMapper, TicketIdGeneratorSearchRepository ticketIdGeneratorSearchRepository) {
        this.ticketIdGeneratorRepository = ticketIdGeneratorRepository;
        this.ticketIdGeneratorMapper = ticketIdGeneratorMapper;
        this.ticketIdGeneratorSearchRepository = ticketIdGeneratorSearchRepository;
    }

    /**
     * Save a ticketIdGenerator.
     *
     * @param ticketIdGeneratorDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TicketIdGeneratorDTO save(TicketIdGeneratorDTO ticketIdGeneratorDTO) {
        log.debug("Request to save TicketIdGenerator : {}", ticketIdGeneratorDTO);
        TicketIdGenerator ticketIdGenerator = ticketIdGeneratorMapper.toEntity(ticketIdGeneratorDTO);
        ticketIdGenerator = ticketIdGeneratorRepository.save(ticketIdGenerator);
        TicketIdGeneratorDTO result = ticketIdGeneratorMapper.toDto(ticketIdGenerator);
        ticketIdGeneratorSearchRepository.save(ticketIdGenerator);
        return result;
    }

    /**
     * Get all the ticketIdGenerators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TicketIdGeneratorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TicketIdGenerators");
        return ticketIdGeneratorRepository.findAll(pageable)
            .map(ticketIdGeneratorMapper::toDto);
    }


    /**
     * Get one ticketIdGenerator by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TicketIdGeneratorDTO> findOne(Long id) {
        log.debug("Request to get TicketIdGenerator : {}", id);
        return ticketIdGeneratorRepository.findById(id)
            .map(ticketIdGeneratorMapper::toDto);
    }

    /**
     * Delete the ticketIdGenerator by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TicketIdGenerator : {}", id);
        ticketIdGeneratorRepository.deleteById(id);
        ticketIdGeneratorSearchRepository.deleteById(id);
    }

    /**
     * Search for the ticketIdGenerator corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TicketIdGeneratorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TicketIdGenerators for query {}", query);
        return ticketIdGeneratorSearchRepository.search(queryStringQuery(query), pageable)
            .map(ticketIdGeneratorMapper::toDto);
    }
}
