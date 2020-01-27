package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.service.SubTermService;
import com.diviso.graeshoppe.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.service.dto.SubTermDTO;

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
 * REST controller for managing {@link com.diviso.graeshoppe.domain.SubTerm}.
 */
@RestController
@RequestMapping("/api")
public class SubTermResource {

    private final Logger log = LoggerFactory.getLogger(SubTermResource.class);

    private static final String ENTITY_NAME = "administrationSubTerm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubTermService subTermService;

    public SubTermResource(SubTermService subTermService) {
        this.subTermService = subTermService;
    }

    /**
     * {@code POST  /sub-terms} : Create a new subTerm.
     *
     * @param subTermDTO the subTermDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subTermDTO, or with status {@code 400 (Bad Request)} if the subTerm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-terms")
    public ResponseEntity<SubTermDTO> createSubTerm(@RequestBody SubTermDTO subTermDTO) throws URISyntaxException {
        log.debug("REST request to save SubTerm : {}", subTermDTO);
        if (subTermDTO.getId() != null) {
            throw new BadRequestAlertException("A new subTerm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubTermDTO result = subTermService.save(subTermDTO);
        return ResponseEntity.created(new URI("/api/sub-terms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-terms} : Updates an existing subTerm.
     *
     * @param subTermDTO the subTermDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subTermDTO,
     * or with status {@code 400 (Bad Request)} if the subTermDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subTermDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-terms")
    public ResponseEntity<SubTermDTO> updateSubTerm(@RequestBody SubTermDTO subTermDTO) throws URISyntaxException {
        log.debug("REST request to update SubTerm : {}", subTermDTO);
        if (subTermDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubTermDTO result = subTermService.save(subTermDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subTermDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sub-terms} : get all the subTerms.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subTerms in body.
     */
    @GetMapping("/sub-terms")
    public ResponseEntity<List<SubTermDTO>> getAllSubTerms(Pageable pageable) {
        log.debug("REST request to get a page of SubTerms");
        Page<SubTermDTO> page = subTermService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sub-terms/:id} : get the "id" subTerm.
     *
     * @param id the id of the subTermDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subTermDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-terms/{id}")
    public ResponseEntity<SubTermDTO> getSubTerm(@PathVariable Long id) {
        log.debug("REST request to get SubTerm : {}", id);
        Optional<SubTermDTO> subTermDTO = subTermService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subTermDTO);
    }

    /**
     * {@code DELETE  /sub-terms/:id} : delete the "id" subTerm.
     *
     * @param id the id of the subTermDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-terms/{id}")
    public ResponseEntity<Void> deleteSubTerm(@PathVariable Long id) {
        log.debug("REST request to delete SubTerm : {}", id);
        subTermService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/sub-terms?query=:query} : search for the subTerm corresponding
     * to the query.
     *
     * @param query the query of the subTerm search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/sub-terms")
    public ResponseEntity<List<SubTermDTO>> searchSubTerms(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SubTerms for query {}", query);
        Page<SubTermDTO> page = subTermService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
