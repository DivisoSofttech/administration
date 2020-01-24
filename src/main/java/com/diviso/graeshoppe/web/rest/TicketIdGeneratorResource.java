package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.service.TicketIdGeneratorService;
import com.diviso.graeshoppe.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.service.dto.TicketIdGeneratorDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.diviso.graeshoppe.domain.TicketIdGenerator}.
 */
@RestController
@RequestMapping("/api")
public class TicketIdGeneratorResource {

    private final Logger log = LoggerFactory.getLogger(TicketIdGeneratorResource.class);

    private static final String ENTITY_NAME = "administrationTicketIdGenerator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TicketIdGeneratorService ticketIdGeneratorService;

    public TicketIdGeneratorResource(TicketIdGeneratorService ticketIdGeneratorService) {
        this.ticketIdGeneratorService = ticketIdGeneratorService;
    }

    /**
     * {@code POST  /ticket-id-generators} : Create a new ticketIdGenerator.
     *
     * @param ticketIdGeneratorDTO the ticketIdGeneratorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketIdGeneratorDTO, or with status {@code 400 (Bad Request)} if the ticketIdGenerator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ticket-id-generators")
    public ResponseEntity<TicketIdGeneratorDTO> createTicketIdGenerator(@RequestBody TicketIdGeneratorDTO ticketIdGeneratorDTO) throws URISyntaxException {
        log.debug("REST request to save TicketIdGenerator : {}", ticketIdGeneratorDTO);
        if (ticketIdGeneratorDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticketIdGenerator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TicketIdGeneratorDTO result = ticketIdGeneratorService.save(ticketIdGeneratorDTO);
        return ResponseEntity.created(new URI("/api/ticket-id-generators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ticket-id-generators} : Updates an existing ticketIdGenerator.
     *
     * @param ticketIdGeneratorDTO the ticketIdGeneratorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketIdGeneratorDTO,
     * or with status {@code 400 (Bad Request)} if the ticketIdGeneratorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ticketIdGeneratorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ticket-id-generators")
    public ResponseEntity<TicketIdGeneratorDTO> updateTicketIdGenerator(@RequestBody TicketIdGeneratorDTO ticketIdGeneratorDTO) throws URISyntaxException {
        log.debug("REST request to update TicketIdGenerator : {}", ticketIdGeneratorDTO);
        if (ticketIdGeneratorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TicketIdGeneratorDTO result = ticketIdGeneratorService.save(ticketIdGeneratorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ticketIdGeneratorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ticket-id-generators} : get all the ticketIdGenerators.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ticketIdGenerators in body.
     */
    @GetMapping("/ticket-id-generators")
    public ResponseEntity<List<TicketIdGeneratorDTO>> getAllTicketIdGenerators(Pageable pageable) {
        log.debug("REST request to get a page of TicketIdGenerators");
        Page<TicketIdGeneratorDTO> page = ticketIdGeneratorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ticket-id-generators/:id} : get the "id" ticketIdGenerator.
     *
     * @param id the id of the ticketIdGeneratorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketIdGeneratorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ticket-id-generators/{id}")
    public ResponseEntity<TicketIdGeneratorDTO> getTicketIdGenerator(@PathVariable Long id) {
        log.debug("REST request to get TicketIdGenerator : {}", id);
        Optional<TicketIdGeneratorDTO> ticketIdGeneratorDTO = ticketIdGeneratorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ticketIdGeneratorDTO);
    }

    /**
     * {@code DELETE  /ticket-id-generators/:id} : delete the "id" ticketIdGenerator.
     *
     * @param id the id of the ticketIdGeneratorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ticket-id-generators/{id}")
    public ResponseEntity<Void> deleteTicketIdGenerator(@PathVariable Long id) {
        log.debug("REST request to delete TicketIdGenerator : {}", id);
        ticketIdGeneratorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/ticket-id-generators?query=:query} : search for the ticketIdGenerator corresponding
     * to the query.
     *
     * @param query the query of the ticketIdGenerator search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ticket-id-generators")
    public ResponseEntity<List<TicketIdGeneratorDTO>> searchTicketIdGenerators(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TicketIdGenerators for query {}", query);
        Page<TicketIdGeneratorDTO> page = ticketIdGeneratorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
