package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.AdministrationApp;
import com.diviso.graeshoppe.config.TestSecurityConfiguration;
import com.diviso.graeshoppe.domain.SubTerm;
import com.diviso.graeshoppe.repository.SubTermRepository;
import com.diviso.graeshoppe.repository.search.SubTermSearchRepository;
import com.diviso.graeshoppe.service.SubTermService;
import com.diviso.graeshoppe.service.dto.SubTermDTO;
import com.diviso.graeshoppe.service.mapper.SubTermMapper;
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
 * Integration tests for the {@link SubTermResource} REST controller.
 */
@SpringBootTest(classes = {AdministrationApp.class, TestSecurityConfiguration.class})
public class SubTermResourceIT {

    private static final String DEFAULT_TERM_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_TERM_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SubTermRepository subTermRepository;

    @Autowired
    private SubTermMapper subTermMapper;

    @Autowired
    private SubTermService subTermService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.repository.search test package.
     *
     * @see com.diviso.graeshoppe.repository.search.SubTermSearchRepositoryMockConfiguration
     */
    @Autowired
    private SubTermSearchRepository mockSubTermSearchRepository;

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

    private MockMvc restSubTermMockMvc;

    private SubTerm subTerm;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubTermResource subTermResource = new SubTermResource(subTermService);
        this.restSubTermMockMvc = MockMvcBuilders.standaloneSetup(subTermResource)
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
    public static SubTerm createEntity(EntityManager em) {
        SubTerm subTerm = new SubTerm()
            .termDescription(DEFAULT_TERM_DESCRIPTION);
        return subTerm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubTerm createUpdatedEntity(EntityManager em) {
        SubTerm subTerm = new SubTerm()
            .termDescription(UPDATED_TERM_DESCRIPTION);
        return subTerm;
    }

    @BeforeEach
    public void initTest() {
        subTerm = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubTerm() throws Exception {
        int databaseSizeBeforeCreate = subTermRepository.findAll().size();

        // Create the SubTerm
        SubTermDTO subTermDTO = subTermMapper.toDto(subTerm);
        restSubTermMockMvc.perform(post("/api/sub-terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subTermDTO)))
            .andExpect(status().isCreated());

        // Validate the SubTerm in the database
        List<SubTerm> subTermList = subTermRepository.findAll();
        assertThat(subTermList).hasSize(databaseSizeBeforeCreate + 1);
        SubTerm testSubTerm = subTermList.get(subTermList.size() - 1);
        assertThat(testSubTerm.getTermDescription()).isEqualTo(DEFAULT_TERM_DESCRIPTION);

        // Validate the SubTerm in Elasticsearch
        verify(mockSubTermSearchRepository, times(1)).save(testSubTerm);
    }

    @Test
    @Transactional
    public void createSubTermWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subTermRepository.findAll().size();

        // Create the SubTerm with an existing ID
        subTerm.setId(1L);
        SubTermDTO subTermDTO = subTermMapper.toDto(subTerm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubTermMockMvc.perform(post("/api/sub-terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subTermDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubTerm in the database
        List<SubTerm> subTermList = subTermRepository.findAll();
        assertThat(subTermList).hasSize(databaseSizeBeforeCreate);

        // Validate the SubTerm in Elasticsearch
        verify(mockSubTermSearchRepository, times(0)).save(subTerm);
    }


    @Test
    @Transactional
    public void getAllSubTerms() throws Exception {
        // Initialize the database
        subTermRepository.saveAndFlush(subTerm);

        // Get all the subTermList
        restSubTermMockMvc.perform(get("/api/sub-terms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subTerm.getId().intValue())))
            .andExpect(jsonPath("$.[*].termDescription").value(hasItem(DEFAULT_TERM_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getSubTerm() throws Exception {
        // Initialize the database
        subTermRepository.saveAndFlush(subTerm);

        // Get the subTerm
        restSubTermMockMvc.perform(get("/api/sub-terms/{id}", subTerm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subTerm.getId().intValue()))
            .andExpect(jsonPath("$.termDescription").value(DEFAULT_TERM_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingSubTerm() throws Exception {
        // Get the subTerm
        restSubTermMockMvc.perform(get("/api/sub-terms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubTerm() throws Exception {
        // Initialize the database
        subTermRepository.saveAndFlush(subTerm);

        int databaseSizeBeforeUpdate = subTermRepository.findAll().size();

        // Update the subTerm
        SubTerm updatedSubTerm = subTermRepository.findById(subTerm.getId()).get();
        // Disconnect from session so that the updates on updatedSubTerm are not directly saved in db
        em.detach(updatedSubTerm);
        updatedSubTerm
            .termDescription(UPDATED_TERM_DESCRIPTION);
        SubTermDTO subTermDTO = subTermMapper.toDto(updatedSubTerm);

        restSubTermMockMvc.perform(put("/api/sub-terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subTermDTO)))
            .andExpect(status().isOk());

        // Validate the SubTerm in the database
        List<SubTerm> subTermList = subTermRepository.findAll();
        assertThat(subTermList).hasSize(databaseSizeBeforeUpdate);
        SubTerm testSubTerm = subTermList.get(subTermList.size() - 1);
        assertThat(testSubTerm.getTermDescription()).isEqualTo(UPDATED_TERM_DESCRIPTION);

        // Validate the SubTerm in Elasticsearch
        verify(mockSubTermSearchRepository, times(1)).save(testSubTerm);
    }

    @Test
    @Transactional
    public void updateNonExistingSubTerm() throws Exception {
        int databaseSizeBeforeUpdate = subTermRepository.findAll().size();

        // Create the SubTerm
        SubTermDTO subTermDTO = subTermMapper.toDto(subTerm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubTermMockMvc.perform(put("/api/sub-terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subTermDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubTerm in the database
        List<SubTerm> subTermList = subTermRepository.findAll();
        assertThat(subTermList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SubTerm in Elasticsearch
        verify(mockSubTermSearchRepository, times(0)).save(subTerm);
    }

    @Test
    @Transactional
    public void deleteSubTerm() throws Exception {
        // Initialize the database
        subTermRepository.saveAndFlush(subTerm);

        int databaseSizeBeforeDelete = subTermRepository.findAll().size();

        // Delete the subTerm
        restSubTermMockMvc.perform(delete("/api/sub-terms/{id}", subTerm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubTerm> subTermList = subTermRepository.findAll();
        assertThat(subTermList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SubTerm in Elasticsearch
        verify(mockSubTermSearchRepository, times(1)).deleteById(subTerm.getId());
    }

    @Test
    @Transactional
    public void searchSubTerm() throws Exception {
        // Initialize the database
        subTermRepository.saveAndFlush(subTerm);
        when(mockSubTermSearchRepository.search(queryStringQuery("id:" + subTerm.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(subTerm), PageRequest.of(0, 1), 1));
        // Search the subTerm
        restSubTermMockMvc.perform(get("/api/_search/sub-terms?query=id:" + subTerm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subTerm.getId().intValue())))
            .andExpect(jsonPath("$.[*].termDescription").value(hasItem(DEFAULT_TERM_DESCRIPTION)));
    }
}
