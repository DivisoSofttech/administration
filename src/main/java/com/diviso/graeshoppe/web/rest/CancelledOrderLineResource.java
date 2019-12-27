package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.service.CancelledOrderLineService;
import com.diviso.graeshoppe.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.service.dto.CancelledOrderLineDTO;

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
 * REST controller for managing {@link com.diviso.graeshoppe.domain.CancelledOrderLine}.
 */
@RestController
@RequestMapping("/api")
public class CancelledOrderLineResource {

    private final Logger log = LoggerFactory.getLogger(CancelledOrderLineResource.class);

    private static final String ENTITY_NAME = "administrationCancelledOrderLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CancelledOrderLineService cancelledOrderLineService;

    public CancelledOrderLineResource(CancelledOrderLineService cancelledOrderLineService) {
        this.cancelledOrderLineService = cancelledOrderLineService;
    }

    /**
     * {@code POST  /cancelled-order-lines} : Create a new cancelledOrderLine.
     *
     * @param cancelledOrderLineDTO the cancelledOrderLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cancelledOrderLineDTO, or with status {@code 400 (Bad Request)} if the cancelledOrderLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cancelled-order-lines")
    public ResponseEntity<CancelledOrderLineDTO> createCancelledOrderLine(@RequestBody CancelledOrderLineDTO cancelledOrderLineDTO) throws URISyntaxException {
        log.debug("REST request to save CancelledOrderLine : {}", cancelledOrderLineDTO);
        if (cancelledOrderLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new cancelledOrderLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CancelledOrderLineDTO result = cancelledOrderLineService.save(cancelledOrderLineDTO);
        return ResponseEntity.created(new URI("/api/cancelled-order-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cancelled-order-lines} : Updates an existing cancelledOrderLine.
     *
     * @param cancelledOrderLineDTO the cancelledOrderLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cancelledOrderLineDTO,
     * or with status {@code 400 (Bad Request)} if the cancelledOrderLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cancelledOrderLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cancelled-order-lines")
    public ResponseEntity<CancelledOrderLineDTO> updateCancelledOrderLine(@RequestBody CancelledOrderLineDTO cancelledOrderLineDTO) throws URISyntaxException {
        log.debug("REST request to update CancelledOrderLine : {}", cancelledOrderLineDTO);
        if (cancelledOrderLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CancelledOrderLineDTO result = cancelledOrderLineService.save(cancelledOrderLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cancelledOrderLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cancelled-order-lines} : get all the cancelledOrderLines.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cancelledOrderLines in body.
     */
    @GetMapping("/cancelled-order-lines")
    public ResponseEntity<List<CancelledOrderLineDTO>> getAllCancelledOrderLines(Pageable pageable) {
        log.debug("REST request to get a page of CancelledOrderLines");
        Page<CancelledOrderLineDTO> page = cancelledOrderLineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cancelled-order-lines/:id} : get the "id" cancelledOrderLine.
     *
     * @param id the id of the cancelledOrderLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cancelledOrderLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cancelled-order-lines/{id}")
    public ResponseEntity<CancelledOrderLineDTO> getCancelledOrderLine(@PathVariable Long id) {
        log.debug("REST request to get CancelledOrderLine : {}", id);
        Optional<CancelledOrderLineDTO> cancelledOrderLineDTO = cancelledOrderLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cancelledOrderLineDTO);
    }

    /**
     * {@code DELETE  /cancelled-order-lines/:id} : delete the "id" cancelledOrderLine.
     *
     * @param id the id of the cancelledOrderLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cancelled-order-lines/{id}")
    public ResponseEntity<Void> deleteCancelledOrderLine(@PathVariable Long id) {
        log.debug("REST request to delete CancelledOrderLine : {}", id);
        cancelledOrderLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/cancelled-order-lines?query=:query} : search for the cancelledOrderLine corresponding
     * to the query.
     *
     * @param query the query of the cancelledOrderLine search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cancelled-order-lines")
    public ResponseEntity<List<CancelledOrderLineDTO>> searchCancelledOrderLines(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CancelledOrderLines for query {}", query);
        Page<CancelledOrderLineDTO> page = cancelledOrderLineService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
