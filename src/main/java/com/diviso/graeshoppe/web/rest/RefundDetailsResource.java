package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.service.RefundDetailsService;
import com.diviso.graeshoppe.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.service.dto.RefundDetailsDTO;

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
 * REST controller for managing {@link com.diviso.graeshoppe.domain.RefoundDetails}.
 */
@RestController
@RequestMapping("/api")
public class RefundDetailsResource {

    private final Logger log = LoggerFactory.getLogger(RefundDetailsResource.class);

    private static final String ENTITY_NAME = "administrationRefoundDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RefundDetailsService refundDetailsService;

    public RefundDetailsResource(RefundDetailsService refundDetailsService) {
        this.refundDetailsService = refundDetailsService;
    }

    /**
     * {@code POST  /refound-details} : Create a new refoundDetails.
     *
     * @param refoundDetailsDTO the refoundDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new refoundDetailsDTO, or with status {@code 400 (Bad Request)} if the refoundDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/refund-details/{orderId}")
    public ResponseEntity<RefundDetailsDTO> createRefundDetails(@RequestBody RefundDetailsDTO refundDetailsDTO,@PathVariable String orderId) throws URISyntaxException {
        log.debug("REST request to save RefundDetails : {}", refundDetailsDTO);
        if (refundDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new refundDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RefundDetailsDTO result = refundDetailsService.save(refundDetailsDTO,orderId);
        return ResponseEntity.created(new URI("/api/refund-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /refound-details} : Updates an existing refoundDetails.
     *
     * @param refoundDetailsDTO the refoundDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refoundDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the refoundDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the refoundDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/refund-details")
    public ResponseEntity<RefundDetailsDTO> updateRefundDetails(@RequestBody RefundDetailsDTO refundDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update RefundDetails : {}", refundDetailsDTO);
        if (refundDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RefundDetailsDTO result = refundDetailsService.save(refundDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, refundDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /refound-details} : get all the refoundDetails.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refoundDetails in body.
     */
    @GetMapping("/refund-details")
    public ResponseEntity<List<RefundDetailsDTO>> getAllRefundDetails(Pageable pageable) {
        log.debug("REST request to get a page of RefundDetails");
        Page<RefundDetailsDTO> page = refundDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /refound-details/:id} : get the "id" refoundDetails.
     *
     * @param id the id of the refoundDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the refoundDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/refund-details/{id}")
    public ResponseEntity<RefundDetailsDTO> getRefundDetails(@PathVariable Long id) {
        log.debug("REST request to get RefundDetails : {}", id);
        Optional<RefundDetailsDTO> refundDetailsDTO = refundDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(refundDetailsDTO);
    }

    /**
     * {@code DELETE  /refound-details/:id} : delete the "id" refoundDetails.
     *
     * @param id the id of the refoundDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/refund-details/{id}")
    public ResponseEntity<Void> deleteRefundDetails(@PathVariable Long id) {
        log.debug("REST request to delete RefundDetails : {}", id);
        refundDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/refound-details?query=:query} : search for the refoundDetails corresponding
     * to the query.
     *
     * @param query the query of the refoundDetails search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/refund-details")
    public ResponseEntity<List<RefundDetailsDTO>> searchRefundDetails(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RefundDetails for query {}", query);
        Page<RefundDetailsDTO> page = refundDetailsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
