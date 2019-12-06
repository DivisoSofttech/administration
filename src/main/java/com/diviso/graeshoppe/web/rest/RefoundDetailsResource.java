package com.diviso.graeshoppe.web.rest;
import com.diviso.graeshoppe.service.RefoundDetailsService;
import com.diviso.graeshoppe.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.service.dto.RefoundDetailsDTO;
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
 * REST controller for managing RefoundDetails.
 */
@RestController
@RequestMapping("/api")
public class RefoundDetailsResource {

    private final Logger log = LoggerFactory.getLogger(RefoundDetailsResource.class);

    private static final String ENTITY_NAME = "administrationRefoundDetails";

    private final RefoundDetailsService refoundDetailsService;

    public RefoundDetailsResource(RefoundDetailsService refoundDetailsService) {
        this.refoundDetailsService = refoundDetailsService;
    }

    /**
     * POST  /refound-details : Create a new refoundDetails.
     *
     * @param refoundDetailsDTO the refoundDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refoundDetailsDTO, or with status 400 (Bad Request) if the refoundDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/refound-details/{orderId}")
    public ResponseEntity<RefoundDetailsDTO> createRefoundDetails(@RequestBody RefoundDetailsDTO refoundDetailsDTO,@PathVariable String orderId) throws URISyntaxException {
        log.debug("REST request to save RefoundDetails : {}", refoundDetailsDTO);
        if (refoundDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new refoundDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RefoundDetailsDTO result = refoundDetailsService.save(refoundDetailsDTO,orderId);
        return ResponseEntity.created(new URI("/api/refound-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /refound-details : Updates an existing refoundDetails.
     *
     * @param refoundDetailsDTO the refoundDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refoundDetailsDTO,
     * or with status 400 (Bad Request) if the refoundDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the refoundDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/refound-details")
    public ResponseEntity<RefoundDetailsDTO> updateRefoundDetails(@RequestBody RefoundDetailsDTO refoundDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update RefoundDetails : {}", refoundDetailsDTO);
        if (refoundDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RefoundDetailsDTO result = refoundDetailsService.save(refoundDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refoundDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /refound-details : get all the refoundDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refoundDetails in body
     */
    @GetMapping("/refound-details")
    public ResponseEntity<List<RefoundDetailsDTO>> getAllRefoundDetails(Pageable pageable) {
        log.debug("REST request to get a page of RefoundDetails");
        Page<RefoundDetailsDTO> page = refoundDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/refound-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /refound-details/:id : get the "id" refoundDetails.
     *
     * @param id the id of the refoundDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refoundDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/refound-details/{id}")
    public ResponseEntity<RefoundDetailsDTO> getRefoundDetails(@PathVariable Long id) {
        log.debug("REST request to get RefoundDetails : {}", id);
        Optional<RefoundDetailsDTO> refoundDetailsDTO = refoundDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(refoundDetailsDTO);
    }

    /**
     * DELETE  /refound-details/:id : delete the "id" refoundDetails.
     *
     * @param id the id of the refoundDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/refound-details/{id}")
    public ResponseEntity<Void> deleteRefoundDetails(@PathVariable Long id) {
        log.debug("REST request to delete RefoundDetails : {}", id);
        refoundDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/refound-details?query=:query : search for the refoundDetails corresponding
     * to the query.
     *
     * @param query the query of the refoundDetails search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/refound-details")
    public ResponseEntity<List<RefoundDetailsDTO>> searchRefoundDetails(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RefoundDetails for query {}", query);
        Page<RefoundDetailsDTO> page = refoundDetailsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/refound-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
