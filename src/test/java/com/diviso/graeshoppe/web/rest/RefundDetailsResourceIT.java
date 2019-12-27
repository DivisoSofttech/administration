package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.AdministrationApp;
import com.diviso.graeshoppe.config.TestSecurityConfiguration;
import com.diviso.graeshoppe.domain.RefundDetails;
import com.diviso.graeshoppe.repository.RefundDetailsRepository;
import com.diviso.graeshoppe.repository.search.RefundDetailsSearchRepository;
import com.diviso.graeshoppe.service.RefundDetailsService;
import com.diviso.graeshoppe.service.dto.RefundDetailsDTO;
import com.diviso.graeshoppe.service.mapper.RefundDetailsMapper;
import com.diviso.graeshoppe.web.rest.errors.ExceptionTranslator;
import com.diviso.graeshoppe.web.rest.RefundDetailsResource;


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
 * Integration tests for the {@link RefundDetailsResource} REST controller.
 */
@SpringBootTest(classes = {AdministrationApp.class, TestSecurityConfiguration.class})
public class RefundDetailsResourceIT {

    private static final String DEFAULT_REFUND_ID = "AAAAAAAAAA";
    private static final String UPDATED_REFUND_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private RefundDetailsRepository refundDetailsRepository;

    @Autowired
    private RefundDetailsMapper refundDetailsMapper;

    @Autowired
    private RefundDetailsService refundDetailsService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.repository.search test package.
     *
     * @see com.diviso.graeshoppe.repository.search.RefundDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private RefundDetailsSearchRepository mockRefundDetailsSearchRepository;

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

    private MockMvc restRefundDetailsMockMvc;

    private RefundDetails refundDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RefundDetailsResource refundDetailsResource = new RefundDetailsResource(refundDetailsService);
        this.restRefundDetailsMockMvc = MockMvcBuilders.standaloneSetup(refundDetailsResource)
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
    public static RefundDetails createEntity(EntityManager em) {
        RefundDetails refundDetails = new RefundDetails()
            .refundId(DEFAULT_REFUND_ID)
            .status(DEFAULT_STATUS);
        return refundDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RefundDetails createUpdatedEntity(EntityManager em) {
        RefundDetails refundDetails = new RefundDetails()
            .refundId(UPDATED_REFUND_ID)
            .status(UPDATED_STATUS);
        return refundDetails;
    }

    @BeforeEach
    public void initTest() {
        refundDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefundDetails() throws Exception {
        int databaseSizeBeforeCreate = refundDetailsRepository.findAll().size();

        // Create the RefundDetails
        RefundDetailsDTO refundDetailsDTO = refundDetailsMapper.toDto(refundDetails);
        restRefundDetailsMockMvc.perform(post("/api/refund-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refundDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the RefundDetails in the database
        List<RefundDetails> refundDetailsList = refundDetailsRepository.findAll();
        assertThat(refundDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        RefundDetails testRefundDetails = refundDetailsList.get(refundDetailsList.size() - 1);
        assertThat(testRefundDetails.getRefundId()).isEqualTo(DEFAULT_REFUND_ID);
        assertThat(testRefundDetails.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the RefundDetails in Elasticsearch
        verify(mockRefundDetailsSearchRepository, times(1)).save(testRefundDetails);
    }

    @Test
    @Transactional
    public void createRefundDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refundDetailsRepository.findAll().size();

        // Create the RefundDetails with an existing ID
        refundDetails.setId(1L);
        RefundDetailsDTO refundDetailsDTO = refundDetailsMapper.toDto(refundDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefundDetailsMockMvc.perform(post("/api/refund-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refundDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefundDetails in the database
        List<RefundDetails> refundDetailsList = refundDetailsRepository.findAll();
        assertThat(refundDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the RefundDetails in Elasticsearch
        verify(mockRefundDetailsSearchRepository, times(0)).save(refundDetails);
    }


    @Test
    @Transactional
    public void getAllRefundDetails() throws Exception {
        // Initialize the database
        refundDetailsRepository.saveAndFlush(refundDetails);

        // Get all the refundDetailsList
        restRefundDetailsMockMvc.perform(get("/api/refund-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refundDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].refundId").value(hasItem(DEFAULT_REFUND_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getRefundDetails() throws Exception {
        // Initialize the database
        refundDetailsRepository.saveAndFlush(refundDetails);

        // Get the refundDetails
        restRefundDetailsMockMvc.perform(get("/api/refund-details/{id}", refundDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refundDetails.getId().intValue()))
            .andExpect(jsonPath("$.refundId").value(DEFAULT_REFUND_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingRefundDetails() throws Exception {
        // Get the refundDetails
        restRefundDetailsMockMvc.perform(get("/api/refund-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefundDetails() throws Exception {
        // Initialize the database
        refundDetailsRepository.saveAndFlush(refundDetails);

        int databaseSizeBeforeUpdate = refundDetailsRepository.findAll().size();

        // Update the refundDetails
        RefundDetails updatedRefundDetails = refundDetailsRepository.findById(refundDetails.getId()).get();
        // Disconnect from session so that the updates on updatedRefundDetails are not directly saved in db
        em.detach(updatedRefundDetails);
        updatedRefundDetails
            .refundId(UPDATED_REFUND_ID)
            .status(UPDATED_STATUS);
        RefundDetailsDTO refundDetailsDTO = refundDetailsMapper.toDto(updatedRefundDetails);

        restRefundDetailsMockMvc.perform(put("/api/refund-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refundDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the RefundDetails in the database
        List<RefundDetails> refundDetailsList = refundDetailsRepository.findAll();
        assertThat(refundDetailsList).hasSize(databaseSizeBeforeUpdate);
        RefundDetails testRefundDetails = refundDetailsList.get(refundDetailsList.size() - 1);
        assertThat(testRefundDetails.getRefundId()).isEqualTo(UPDATED_REFUND_ID);
        assertThat(testRefundDetails.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the RefundDetails in Elasticsearch
        verify(mockRefundDetailsSearchRepository, times(1)).save(testRefundDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingRefundDetails() throws Exception {
        int databaseSizeBeforeUpdate = refundDetailsRepository.findAll().size();

        // Create the RefundDetails
        RefundDetailsDTO refundDetailsDTO = refundDetailsMapper.toDto(refundDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefundDetailsMockMvc.perform(put("/api/refund-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refundDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefundDetails in the database
        List<RefundDetails> refundDetailsList = refundDetailsRepository.findAll();
        assertThat(refundDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RefundDetails in Elasticsearch
        verify(mockRefundDetailsSearchRepository, times(0)).save(refundDetails);
    }

    @Test
    @Transactional
    public void deleteRefundDetails() throws Exception {
        // Initialize the database
        refundDetailsRepository.saveAndFlush(refundDetails);

        int databaseSizeBeforeDelete = refundDetailsRepository.findAll().size();

        // Delete the refundDetails
        restRefundDetailsMockMvc.perform(delete("/api/refund-details/{id}", refundDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RefundDetails> refundDetailsList = refundDetailsRepository.findAll();
        assertThat(refundDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RefundDetails in Elasticsearch
        verify(mockRefundDetailsSearchRepository, times(1)).deleteById(refundDetails.getId());
    }

    @Test
    @Transactional
    public void searchRefundDetails() throws Exception {
        // Initialize the database
        refundDetailsRepository.saveAndFlush(refundDetails);
        when(mockRefundDetailsSearchRepository.search(queryStringQuery("id:" + refundDetails.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(refundDetails), PageRequest.of(0, 1), 1));
        // Search the refundDetails
        restRefundDetailsMockMvc.perform(get("/api/_search/refund-details?query=id:" + refundDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refundDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].refundId").value(hasItem(DEFAULT_REFUND_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
}
