package com.diviso.graeshoppe.web.rest;
import com.diviso.graeshoppe.service.CancellationRequestService;
import com.diviso.graeshoppe.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.service.dto.CancellationRequestDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CancellationRequest.
 */
@RestController
@RequestMapping("/api")
public class CancellationRequestResource {

    private final Logger log = LoggerFactory.getLogger(CancellationRequestResource.class);

    private static final String ENTITY_NAME = "administrationCancellationRequest";

    private final CancellationRequestService cancellationRequestService;

    public CancellationRequestResource(CancellationRequestService cancellationRequestService) {
        this.cancellationRequestService = cancellationRequestService;
    }

    /**
     * POST  /cancellation-requests : Create a new cancellationRequest.
     *
     * @param cancellationRequestDTO the cancellationRequestDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cancellationRequestDTO, or with status 400 (Bad Request) if the cancellationRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cancellation-requests")
    public ResponseEntity<CancellationRequestDTO> createCancellationRequest(@RequestBody CancellationRequestDTO cancellationRequestDTO) throws URISyntaxException {
        log.debug("REST request to save CancellationRequest : {}", cancellationRequestDTO);
        if (cancellationRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new cancellationRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CancellationRequestDTO result = cancellationRequestService.save(cancellationRequestDTO);
        return ResponseEntity.created(new URI("/api/cancellation-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cancellation-requests : Updates an existing cancellationRequest.
     *
     * @param cancellationRequestDTO the cancellationRequestDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cancellationRequestDTO,
     * or with status 400 (Bad Request) if the cancellationRequestDTO is not valid,
     * or with status 500 (Internal Server Error) if the cancellationRequestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cancellation-requests")
    public ResponseEntity<CancellationRequestDTO> updateCancellationRequest(@RequestBody CancellationRequestDTO cancellationRequestDTO) throws URISyntaxException {
        log.debug("REST request to update CancellationRequest : {}", cancellationRequestDTO);
        if (cancellationRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CancellationRequestDTO result = cancellationRequestService.save(cancellationRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cancellationRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cancellation-requests : get all the cancellationRequests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cancellationRequests in body
     */
    @GetMapping("/cancellation-requests")
    public ResponseEntity<List<CancellationRequestDTO>> getAllCancellationRequests(Pageable pageable) {
        log.debug("REST request to get a page of CancellationRequests");
        Page<CancellationRequestDTO> page = cancellationRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cancellation-requests");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /cancellation-requests/:id : get the "id" cancellationRequest.
     *
     * @param id the id of the cancellationRequestDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cancellationRequestDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cancellation-requests/{id}")
    public ResponseEntity<CancellationRequestDTO> getCancellationRequest(@PathVariable Long id) {
        log.debug("REST request to get CancellationRequest : {}", id);
        Optional<CancellationRequestDTO> cancellationRequestDTO = cancellationRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cancellationRequestDTO);
    }

    /**
     * DELETE  /cancellation-requests/:id : delete the "id" cancellationRequest.
     *
     * @param id the id of the cancellationRequestDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cancellation-requests/{id}")
    public ResponseEntity<Void> deleteCancellationRequest(@PathVariable Long id) {
        log.debug("REST request to delete CancellationRequest : {}", id);
        cancellationRequestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cancellation-requests?query=:query : search for the cancellationRequest corresponding
     * to the query.
     *
     * @param query the query of the cancellationRequest search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/cancellation-requests")
    public ResponseEntity<List<CancellationRequestDTO>> searchCancellationRequests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CancellationRequests for query {}", query);
        Page<CancellationRequestDTO> page = cancellationRequestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/cancellation-requests");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
