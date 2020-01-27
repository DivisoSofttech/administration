package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.AboutService;
import com.diviso.graeshoppe.domain.About;
import com.diviso.graeshoppe.repository.AboutRepository;
import com.diviso.graeshoppe.repository.search.AboutSearchRepository;
import com.diviso.graeshoppe.service.dto.AboutDTO;
import com.diviso.graeshoppe.service.mapper.AboutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link About}.
 */
@Service
@Transactional
public class AboutServiceImpl implements AboutService {

    private final Logger log = LoggerFactory.getLogger(AboutServiceImpl.class);

    private final AboutRepository aboutRepository;

    private final AboutMapper aboutMapper;

    private final AboutSearchRepository aboutSearchRepository;

    public AboutServiceImpl(AboutRepository aboutRepository, AboutMapper aboutMapper, AboutSearchRepository aboutSearchRepository) {
        this.aboutRepository = aboutRepository;
        this.aboutMapper = aboutMapper;
        this.aboutSearchRepository = aboutSearchRepository;
    }

    /**
     * Save a about.
     *
     * @param aboutDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AboutDTO save(AboutDTO aboutDTO) {
        log.debug("Request to save About : {}", aboutDTO);
        About about = aboutMapper.toEntity(aboutDTO);
        about = aboutRepository.save(about);
        AboutDTO result = aboutMapper.toDto(about);
        aboutSearchRepository.save(about);
        return result;
    }

    /**
     * Get all the abouts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AboutDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Abouts");
        return aboutRepository.findAll(pageable)
            .map(aboutMapper::toDto);
    }


    /**
     * Get one about by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AboutDTO> findOne(Long id) {
        log.debug("Request to get About : {}", id);
        return aboutRepository.findById(id)
            .map(aboutMapper::toDto);
    }

    /**
     * Delete the about by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete About : {}", id);
        aboutRepository.deleteById(id);
        aboutSearchRepository.deleteById(id);
    }

    /**
     * Search for the about corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AboutDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Abouts for query {}", query);
        return aboutSearchRepository.search(queryStringQuery(query), pageable)
            .map(aboutMapper::toDto);
    }
}
