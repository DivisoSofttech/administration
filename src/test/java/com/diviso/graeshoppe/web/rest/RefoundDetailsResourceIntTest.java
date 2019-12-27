package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.AdministrationApp;

import com.diviso.graeshoppe.domain.RefoundDetails;
import com.diviso.graeshoppe.repository.RefoundDetailsRepository;
import com.diviso.graeshoppe.repository.search.RefoundDetailsSearchRepository;
import com.diviso.graeshoppe.service.RefoundDetailsService;
import com.diviso.graeshoppe.service.dto.RefoundDetailsDTO;
import com.diviso.graeshoppe.service.mapper.RefoundDetailsMapper;
import com.diviso.graeshoppe.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
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
 * Test class for the RefoundDetailsResource REST controller.
 *
 * @see RefoundDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdministrationApp.class)
public class RefoundDetailsResourceIntTest {

    private static final String DEFAULT_REFUND_ID = "AAAAAAAAAA";
    private static final String UPDATED_REFUND_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private RefoundDetailsRepository refoundDetailsRepository;

    @Autowired
    private RefoundDetailsMapper refoundDetailsMapper;

    @Autowired
    private RefoundDetailsService refoundDetailsService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.repository.search test package.
     *
     * @see com.diviso.graeshoppe.repository.search.RefoundDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private RefoundDetailsSearchRepository mockRefoundDetailsSearchRepository;

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

    private MockMvc restRefoundDetailsMockMvc;

    private RefoundDetails refoundDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RefoundDetailsResource refoundDetailsResource = new RefoundDetailsResource(refoundDetailsService);
        this.restRefoundDetailsMockMvc = MockMvcBuilders.standaloneSetup(refoundDetailsResource)
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
    public static RefoundDetails createEntity(EntityManager em) {
        RefoundDetails refoundDetails = new RefoundDetails()
            .refundId(DEFAULT_REFUND_ID)
            .status(DEFAULT_STATUS);
        return refoundDetails;
    }

    @Before
    public void initTest() {
        refoundDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefoundDetails() throws Exception {
        int databaseSizeBeforeCreate = refoundDetailsRepository.findAll().size();

        // Create the RefoundDetails
        RefoundDetailsDTO refoundDetailsDTO = refoundDetailsMapper.toDto(refoundDetails);
        restRefoundDetailsMockMvc.perform(post("/api/refound-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refoundDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the RefoundDetails in the database
        List<RefoundDetails> refoundDetailsList = refoundDetailsRepository.findAll();
        assertThat(refoundDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        RefoundDetails testRefoundDetails = refoundDetailsList.get(refoundDetailsList.size() - 1);
        assertThat(testRefoundDetails.getRefundId()).isEqualTo(DEFAULT_REFUND_ID);
        assertThat(testRefoundDetails.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the RefoundDetails in Elasticsearch
        verify(mockRefoundDetailsSearchRepository, times(1)).save(testRefoundDetails);
    }

    @Test
    @Transactional
    public void createRefoundDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refoundDetailsRepository.findAll().size();

        // Create the RefoundDetails with an existing ID
        refoundDetails.setId(1L);
        RefoundDetailsDTO refoundDetailsDTO = refoundDetailsMapper.toDto(refoundDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefoundDetailsMockMvc.perform(post("/api/refound-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refoundDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefoundDetails in the database
        List<RefoundDetails> refoundDetailsList = refoundDetailsRepository.findAll();
        assertThat(refoundDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the RefoundDetails in Elasticsearch
        verify(mockRefoundDetailsSearchRepository, times(0)).save(refoundDetails);
    }

    @Test
    @Transactional
    public void getAllRefoundDetails() throws Exception {
        // Initialize the database
        refoundDetailsRepository.saveAndFlush(refoundDetails);

        // Get all the refoundDetailsList
        restRefoundDetailsMockMvc.perform(get("/api/refound-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refoundDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].refundId").value(hasItem(DEFAULT_REFUND_ID.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getRefoundDetails() throws Exception {
        // Initialize the database
        refoundDetailsRepository.saveAndFlush(refoundDetails);

        // Get the refoundDetails
        restRefoundDetailsMockMvc.perform(get("/api/refound-details/{id}", refoundDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refoundDetails.getId().intValue()))
            .andExpect(jsonPath("$.refundId").value(DEFAULT_REFUND_ID.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRefoundDetails() throws Exception {
        // Get the refoundDetails
        restRefoundDetailsMockMvc.perform(get("/api/refound-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefoundDetails() throws Exception {
        // Initialize the database
        refoundDetailsRepository.saveAndFlush(refoundDetails);

        int databaseSizeBeforeUpdate = refoundDetailsRepository.findAll().size();

        // Update the refoundDetails
        RefoundDetails updatedRefoundDetails = refoundDetailsRepository.findById(refoundDetails.getId()).get();
        // Disconnect from session so that the updates on updatedRefoundDetails are not directly saved in db
        em.detach(updatedRefoundDetails);
        updatedRefoundDetails
            .refundId(UPDATED_REFUND_ID)
            .status(UPDATED_STATUS);
        RefoundDetailsDTO refoundDetailsDTO = refoundDetailsMapper.toDto(updatedRefoundDetails);

        restRefoundDetailsMockMvc.perform(put("/api/refound-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refoundDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the RefoundDetails in the database
        List<RefoundDetails> refoundDetailsList = refoundDetailsRepository.findAll();
        assertThat(refoundDetailsList).hasSize(databaseSizeBeforeUpdate);
        RefoundDetails testRefoundDetails = refoundDetailsList.get(refoundDetailsList.size() - 1);
        assertThat(testRefoundDetails.getRefundId()).isEqualTo(UPDATED_REFUND_ID);
        assertThat(testRefoundDetails.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the RefoundDetails in Elasticsearch
        verify(mockRefoundDetailsSearchRepository, times(1)).save(testRefoundDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingRefoundDetails() throws Exception {
        int databaseSizeBeforeUpdate = refoundDetailsRepository.findAll().size();

        // Create the RefoundDetails
        RefoundDetailsDTO refoundDetailsDTO = refoundDetailsMapper.toDto(refoundDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefoundDetailsMockMvc.perform(put("/api/refound-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refoundDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefoundDetails in the database
        List<RefoundDetails> refoundDetailsList = refoundDetailsRepository.findAll();
        assertThat(refoundDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RefoundDetails in Elasticsearch
        verify(mockRefoundDetailsSearchRepository, times(0)).save(refoundDetails);
    }

    @Test
    @Transactional
    public void deleteRefoundDetails() throws Exception {
        // Initialize the database
        refoundDetailsRepository.saveAndFlush(refoundDetails);

        int databaseSizeBeforeDelete = refoundDetailsRepository.findAll().size();

        // Delete the refoundDetails
        restRefoundDetailsMockMvc.perform(delete("/api/refound-details/{id}", refoundDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RefoundDetails> refoundDetailsList = refoundDetailsRepository.findAll();
        assertThat(refoundDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RefoundDetails in Elasticsearch
        verify(mockRefoundDetailsSearchRepository, times(1)).deleteById(refoundDetails.getId());
    }

    @Test
    @Transactional
    public void searchRefoundDetails() throws Exception {
        // Initialize the database
        refoundDetailsRepository.saveAndFlush(refoundDetails);
        when(mockRefoundDetailsSearchRepository.search(queryStringQuery("id:" + refoundDetails.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(refoundDetails), PageRequest.of(0, 1), 1));
        // Search the refoundDetails
        restRefoundDetailsMockMvc.perform(get("/api/_search/refound-details?query=id:" + refoundDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refoundDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].refundId").value(hasItem(DEFAULT_REFUND_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefoundDetails.class);
        RefoundDetails refoundDetails1 = new RefoundDetails();
        refoundDetails1.setId(1L);
        RefoundDetails refoundDetails2 = new RefoundDetails();
        refoundDetails2.setId(refoundDetails1.getId());
        assertThat(refoundDetails1).isEqualTo(refoundDetails2);
        refoundDetails2.setId(2L);
        assertThat(refoundDetails1).isNotEqualTo(refoundDetails2);
        refoundDetails1.setId(null);
        assertThat(refoundDetails1).isNotEqualTo(refoundDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefoundDetailsDTO.class);
        RefoundDetailsDTO refoundDetailsDTO1 = new RefoundDetailsDTO();
        refoundDetailsDTO1.setId(1L);
        RefoundDetailsDTO refoundDetailsDTO2 = new RefoundDetailsDTO();
        assertThat(refoundDetailsDTO1).isNotEqualTo(refoundDetailsDTO2);
        refoundDetailsDTO2.setId(refoundDetailsDTO1.getId());
        assertThat(refoundDetailsDTO1).isEqualTo(refoundDetailsDTO2);
        refoundDetailsDTO2.setId(2L);
        assertThat(refoundDetailsDTO1).isNotEqualTo(refoundDetailsDTO2);
        refoundDetailsDTO1.setId(null);
        assertThat(refoundDetailsDTO1).isNotEqualTo(refoundDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refoundDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refoundDetailsMapper.fromId(null)).isNull();
    }
}
