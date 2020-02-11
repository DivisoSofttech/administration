package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.AdministrationApp;
import com.diviso.graeshoppe.config.TestSecurityConfiguration;
import com.diviso.graeshoppe.domain.Term;
import com.diviso.graeshoppe.repository.TermRepository;
import com.diviso.graeshoppe.repository.search.TermSearchRepository;
import com.diviso.graeshoppe.service.TermService;
import com.diviso.graeshoppe.service.dto.TermDTO;
import com.diviso.graeshoppe.service.mapper.TermMapper;
import com.diviso.graeshoppe.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.diviso.graeshoppe.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TermResource} REST controller.
 */
@SpringBootTest(classes = {AdministrationApp.class, TestSecurityConfiguration.class})
public class TermResourceIT {

    private static final Long DEFAULT_TERM_ID = 1L;
    private static final Long UPDATED_TERM_ID = 2L;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private TermMapper termMapper;

    @Autowired
    private TermService termService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.repository.search test package.
     *
     * @see com.diviso.graeshoppe.repository.search.TermSearchRepositoryMockConfiguration
     */
    @Autowired
    private TermSearchRepository mockTermSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTermMockMvc;

    private Term term;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TermResource termResource = new TermResource(termService);
        this.restTermMockMvc = MockMvcBuilders.standaloneSetup(termResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Term createEntity(EntityManager em) {
        Term term = new Term()
            .termId(DEFAULT_TERM_ID)
            .title(DEFAULT_TITLE);
        return term;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Term createUpdatedEntity(EntityManager em) {
        Term term = new Term()
            .termId(UPDATED_TERM_ID)
            .title(UPDATED_TITLE);
        return term;
    }

    @BeforeEach
    public void initTest() {
        term = createEntity(em);
    }

    @Test
    @Transactional
    public void createTerm() throws Exception {
        int databaseSizeBeforeCreate = termRepository.findAll().size();

        // Create the Term
        TermDTO termDTO = termMapper.toDto(term);
        restTermMockMvc.perform(post("/api/terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(termDTO)))
            .andExpect(status().isCreated());

        // Validate the Term in the database
        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeCreate + 1);
        Term testTerm = termList.get(termList.size() - 1);
        assertThat(testTerm.getTermId()).isEqualTo(DEFAULT_TERM_ID);
        assertThat(testTerm.getTitle()).isEqualTo(DEFAULT_TITLE);

        // Validate the Term in Elasticsearch
        verify(mockTermSearchRepository, times(1)).save(testTerm);
    }

    @Test
    @Transactional
    public void createTermWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = termRepository.findAll().size();

        // Create the Term with an existing ID
        term.setId(1L);
        TermDTO termDTO = termMapper.toDto(term);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTermMockMvc.perform(post("/api/terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(termDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Term in the database
        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeCreate);

        // Validate the Term in Elasticsearch
        verify(mockTermSearchRepository, times(0)).save(term);
    }


    @Test
    @Transactional
    public void getAllTerms() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList
        restTermMockMvc.perform(get("/api/terms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(term.getId().intValue())))
            .andExpect(jsonPath("$.[*].termId").value(hasItem(DEFAULT_TERM_ID.intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }
    
    @Test
    @Transactional
    public void getTerm() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get the term
        restTermMockMvc.perform(get("/api/terms/{id}", term.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(term.getId().intValue()))
            .andExpect(jsonPath("$.termId").value(DEFAULT_TERM_ID.intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    public void getNonExistingTerm() throws Exception {
        // Get the term
        restTermMockMvc.perform(get("/api/terms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTerm() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        int databaseSizeBeforeUpdate = termRepository.findAll().size();

        // Update the term
        Term updatedTerm = termRepository.findById(term.getId()).get();
        // Disconnect from session so that the updates on updatedTerm are not directly saved in db
        em.detach(updatedTerm);
        updatedTerm
            .termId(UPDATED_TERM_ID)
            .title(UPDATED_TITLE);
        TermDTO termDTO = termMapper.toDto(updatedTerm);

        restTermMockMvc.perform(put("/api/terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(termDTO)))
            .andExpect(status().isOk());

        // Validate the Term in the database
        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeUpdate);
        Term testTerm = termList.get(termList.size() - 1);
        assertThat(testTerm.getTermId()).isEqualTo(UPDATED_TERM_ID);
        assertThat(testTerm.getTitle()).isEqualTo(UPDATED_TITLE);

        // Validate the Term in Elasticsearch
        verify(mockTermSearchRepository, times(1)).save(testTerm);
    }

    @Test
    @Transactional
    public void updateNonExistingTerm() throws Exception {
        int databaseSizeBeforeUpdate = termRepository.findAll().size();

        // Create the Term
        TermDTO termDTO = termMapper.toDto(term);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermMockMvc.perform(put("/api/terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(termDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Term in the database
        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Term in Elasticsearch
        verify(mockTermSearchRepository, times(0)).save(term);
    }

    @Test
    @Transactional
    public void deleteTerm() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        int databaseSizeBeforeDelete = termRepository.findAll().size();

        // Delete the term
        restTermMockMvc.perform(delete("/api/terms/{id}", term.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Term in Elasticsearch
        verify(mockTermSearchRepository, times(1)).deleteById(term.getId());
    }

    @Test
    @Transactional
    public void searchTerm() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);
        when(mockTermSearchRepository.search(queryStringQuery("id:" + term.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(term), PageRequest.of(0, 1), 1));
        // Search the term
        restTermMockMvc.perform(get("/api/_search/terms?query=id:" + term.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(term.getId().intValue())))
            .andExpect(jsonPath("$.[*].termId").value(hasItem(DEFAULT_TERM_ID.intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }
}
