package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.service.TermService;
import com.diviso.graeshoppe.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.service.dto.TermDTO;

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
 * REST controller for managing {@link com.diviso.graeshoppe.domain.Term}.
 */
@RestController
@RequestMapping("/api")
public class TermResource {

    private final Logger log = LoggerFactory.getLogger(TermResource.class);

    private static final String ENTITY_NAME = "administrationTerm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TermService termService;

    public TermResource(TermService termService) {
        this.termService = termService;
    }

    /**
     * {@code POST  /terms} : Create a new term.
     *
     * @param termDTO the termDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new termDTO, or with status {@code 400 (Bad Request)} if the term has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/terms")
    public ResponseEntity<TermDTO> createTerm(@RequestBody TermDTO termDTO) throws URISyntaxException {
        log.debug("REST request to save Term : {}", termDTO);
        if (termDTO.getId() != null) {
            throw new BadRequestAlertException("A new term cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TermDTO result = termService.save(termDTO);
        return ResponseEntity.created(new URI("/api/terms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /terms} : Updates an existing term.
     *
     * @param termDTO the termDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termDTO,
     * or with status {@code 400 (Bad Request)} if the termDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the termDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/terms")
    public ResponseEntity<TermDTO> updateTerm(@RequestBody TermDTO termDTO) throws URISyntaxException {
        log.debug("REST request to update Term : {}", termDTO);
        if (termDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TermDTO result = termService.save(termDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /terms} : get all the terms.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of terms in body.
     */
    @GetMapping("/terms")
    public ResponseEntity<List<TermDTO>> getAllTerms(Pageable pageable) {
        log.debug("REST request to get a page of Terms");
        Page<TermDTO> page = termService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /terms/:id} : get the "id" term.
     *
     * @param id the id of the termDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the termDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/terms/{id}")
    public ResponseEntity<TermDTO> getTerm(@PathVariable Long id) {
        log.debug("REST request to get Term : {}", id);
        Optional<TermDTO> termDTO = termService.findOne(id);
        return ResponseUtil.wrapOrNotFound(termDTO);
    }

    /**
     * {@code DELETE  /terms/:id} : delete the "id" term.
     *
     * @param id the id of the termDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/terms/{id}")
    public ResponseEntity<Void> deleteTerm(@PathVariable Long id) {
        log.debug("REST request to delete Term : {}", id);
        termService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/terms?query=:query} : search for the term corresponding
     * to the query.
     *
     * @param query the query of the term search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/terms")
    public ResponseEntity<List<TermDTO>> searchTerms(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Terms for query {}", query);
        Page<TermDTO> page = termService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
