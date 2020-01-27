package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.service.AboutService;
import com.diviso.graeshoppe.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.service.dto.AboutDTO;

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
 * REST controller for managing {@link com.diviso.graeshoppe.domain.About}.
 */
@RestController
@RequestMapping("/api")
public class AboutResource {

    private final Logger log = LoggerFactory.getLogger(AboutResource.class);

    private static final String ENTITY_NAME = "administrationAbout";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AboutService aboutService;

    public AboutResource(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    /**
     * {@code POST  /abouts} : Create a new about.
     *
     * @param aboutDTO the aboutDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aboutDTO, or with status {@code 400 (Bad Request)} if the about has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/abouts")
    public ResponseEntity<AboutDTO> createAbout(@RequestBody AboutDTO aboutDTO) throws URISyntaxException {
        log.debug("REST request to save About : {}", aboutDTO);
        if (aboutDTO.getId() != null) {
            throw new BadRequestAlertException("A new about cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AboutDTO result = aboutService.save(aboutDTO);
        return ResponseEntity.created(new URI("/api/abouts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /abouts} : Updates an existing about.
     *
     * @param aboutDTO the aboutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aboutDTO,
     * or with status {@code 400 (Bad Request)} if the aboutDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aboutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/abouts")
    public ResponseEntity<AboutDTO> updateAbout(@RequestBody AboutDTO aboutDTO) throws URISyntaxException {
        log.debug("REST request to update About : {}", aboutDTO);
        if (aboutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AboutDTO result = aboutService.save(aboutDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aboutDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /abouts} : get all the abouts.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of abouts in body.
     */
    @GetMapping("/abouts")
    public ResponseEntity<List<AboutDTO>> getAllAbouts(Pageable pageable) {
        log.debug("REST request to get a page of Abouts");
        Page<AboutDTO> page = aboutService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /abouts/:id} : get the "id" about.
     *
     * @param id the id of the aboutDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aboutDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/abouts/{id}")
    public ResponseEntity<AboutDTO> getAbout(@PathVariable Long id) {
        log.debug("REST request to get About : {}", id);
        Optional<AboutDTO> aboutDTO = aboutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aboutDTO);
    }

    /**
     * {@code DELETE  /abouts/:id} : delete the "id" about.
     *
     * @param id the id of the aboutDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/abouts/{id}")
    public ResponseEntity<Void> deleteAbout(@PathVariable Long id) {
        log.debug("REST request to delete About : {}", id);
        aboutService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/abouts?query=:query} : search for the about corresponding
     * to the query.
     *
     * @param query the query of the about search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/abouts")
    public ResponseEntity<List<AboutDTO>> searchAbouts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Abouts for query {}", query);
        Page<AboutDTO> page = aboutService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
