package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.service.CancelledAuxilaryOrderLineService;
import com.diviso.graeshoppe.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.service.dto.CancelledAuxilaryOrderLineDTO;

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
 * REST controller for managing {@link com.diviso.graeshoppe.domain.CancelledAuxilaryOrderLine}.
 */
@RestController
@RequestMapping("/api")
public class CancelledAuxilaryOrderLineResource {

    private final Logger log = LoggerFactory.getLogger(CancelledAuxilaryOrderLineResource.class);

    private static final String ENTITY_NAME = "administrationCancelledAuxilaryOrderLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CancelledAuxilaryOrderLineService cancelledAuxilaryOrderLineService;

    public CancelledAuxilaryOrderLineResource(CancelledAuxilaryOrderLineService cancelledAuxilaryOrderLineService) {
        this.cancelledAuxilaryOrderLineService = cancelledAuxilaryOrderLineService;
    }

    /**
     * {@code POST  /cancelled-auxilary-order-lines} : Create a new cancelledAuxilaryOrderLine.
     *
     * @param cancelledAuxilaryOrderLineDTO the cancelledAuxilaryOrderLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cancelledAuxilaryOrderLineDTO, or with status {@code 400 (Bad Request)} if the cancelledAuxilaryOrderLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cancelled-auxilary-order-lines")
    public ResponseEntity<CancelledAuxilaryOrderLineDTO> createCancelledAuxilaryOrderLine(@RequestBody CancelledAuxilaryOrderLineDTO cancelledAuxilaryOrderLineDTO) throws URISyntaxException {
        log.debug("REST request to save CancelledAuxilaryOrderLine : {}", cancelledAuxilaryOrderLineDTO);
        if (cancelledAuxilaryOrderLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new cancelledAuxilaryOrderLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CancelledAuxilaryOrderLineDTO result1 = cancelledAuxilaryOrderLineService.save(cancelledAuxilaryOrderLineDTO);
        if (result1 == null) {
            throw new BadRequestAlertException("A new cancelledAuxilaryOrderLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CancelledAuxilaryOrderLineDTO result2 = cancelledAuxilaryOrderLineService.save(cancelledAuxilaryOrderLineDTO);
        return ResponseEntity.created(new URI("/api/cancelled-auxilary-order-lines/" + result2.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result2.getId().toString()))
            .body(result2);
    }

    /**
     * {@code PUT  /cancelled-auxilary-order-lines} : Updates an existing cancelledAuxilaryOrderLine.
     *
     * @param cancelledAuxilaryOrderLineDTO the cancelledAuxilaryOrderLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cancelledAuxilaryOrderLineDTO,
     * or with status {@code 400 (Bad Request)} if the cancelledAuxilaryOrderLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cancelledAuxilaryOrderLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cancelled-auxilary-order-lines")
    public ResponseEntity<CancelledAuxilaryOrderLineDTO> updateCancelledAuxilaryOrderLine(@RequestBody CancelledAuxilaryOrderLineDTO cancelledAuxilaryOrderLineDTO) throws URISyntaxException {
        log.debug("REST request to update CancelledAuxilaryOrderLine : {}", cancelledAuxilaryOrderLineDTO);
        if (cancelledAuxilaryOrderLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CancelledAuxilaryOrderLineDTO result = cancelledAuxilaryOrderLineService.save(cancelledAuxilaryOrderLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cancelledAuxilaryOrderLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cancelled-auxilary-order-lines} : get all the cancelledAuxilaryOrderLines.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cancelledAuxilaryOrderLines in body.
     */
    @GetMapping("/cancelled-auxilary-order-lines")
    public ResponseEntity<List<CancelledAuxilaryOrderLineDTO>> getAllCancelledAuxilaryOrderLines(Pageable pageable) {
        log.debug("REST request to get a page of CancelledAuxilaryOrderLines");
        Page<CancelledAuxilaryOrderLineDTO> page = cancelledAuxilaryOrderLineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cancelled-auxilary-order-lines/:id} : get the "id" cancelledAuxilaryOrderLine.
     *
     * @param id the id of the cancelledAuxilaryOrderLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cancelledAuxilaryOrderLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cancelled-auxilary-order-lines/{id}")
    public ResponseEntity<CancelledAuxilaryOrderLineDTO> getCancelledAuxilaryOrderLine(@PathVariable Long id) {
        log.debug("REST request to get CancelledAuxilaryOrderLine : {}", id);
        Optional<CancelledAuxilaryOrderLineDTO> cancelledAuxilaryOrderLineDTO = cancelledAuxilaryOrderLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cancelledAuxilaryOrderLineDTO);
    }

    /**
     * {@code DELETE  /cancelled-auxilary-order-lines/:id} : delete the "id" cancelledAuxilaryOrderLine.
     *
     * @param id the id of the cancelledAuxilaryOrderLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cancelled-auxilary-order-lines/{id}")
    public ResponseEntity<Void> deleteCancelledAuxilaryOrderLine(@PathVariable Long id) {
        log.debug("REST request to delete CancelledAuxilaryOrderLine : {}", id);
        cancelledAuxilaryOrderLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/cancelled-auxilary-order-lines?query=:query} : search for the cancelledAuxilaryOrderLine corresponding
     * to the query.
     *
     * @param query the query of the cancelledAuxilaryOrderLine search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cancelled-auxilary-order-lines")
    public ResponseEntity<List<CancelledAuxilaryOrderLineDTO>> searchCancelledAuxilaryOrderLines(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CancelledAuxilaryOrderLines for query {}", query);
        Page<CancelledAuxilaryOrderLineDTO> page = cancelledAuxilaryOrderLineService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
