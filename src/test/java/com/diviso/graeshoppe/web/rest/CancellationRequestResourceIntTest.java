package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.AdministrationApp;

import com.diviso.graeshoppe.domain.CancellationRequest;
import com.diviso.graeshoppe.repository.CancellationRequestRepository;
import com.diviso.graeshoppe.repository.search.CancellationRequestSearchRepository;
import com.diviso.graeshoppe.service.CancellationRequestService;
import com.diviso.graeshoppe.service.dto.CancellationRequestDTO;
import com.diviso.graeshoppe.service.mapper.CancellationRequestMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Test class for the CancellationRequestResource REST controller.
 *
 * @see CancellationRequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdministrationApp.class)
public class CancellationRequestResourceIntTest {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOMER_PHONE = 1L;
    private static final Long UPDATED_CUSTOMER_PHONE = 2L;

    private static final String DEFAULT_STORE_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_STORE_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_STORE_PHONE = 1L;
    private static final Long UPDATED_STORE_PHONE = 2L;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CancellationRequestRepository cancellationRequestRepository;

    @Autowired
    private CancellationRequestMapper cancellationRequestMapper;

    @Autowired
    private CancellationRequestService cancellationRequestService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.repository.search test package.
     *
     * @see com.diviso.graeshoppe.repository.search.CancellationRequestSearchRepositoryMockConfiguration
     */
    @Autowired
    private CancellationRequestSearchRepository mockCancellationRequestSearchRepository;

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

    private MockMvc restCancellationRequestMockMvc;

    private CancellationRequest cancellationRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CancellationRequestResource cancellationRequestResource = new CancellationRequestResource(cancellationRequestService);
        this.restCancellationRequestMockMvc = MockMvcBuilders.standaloneSetup(cancellationRequestResource)
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
    public static CancellationRequest createEntity(EntityManager em) {
        CancellationRequest cancellationRequest = new CancellationRequest()
            .status(DEFAULT_STATUS)
            .orderId(DEFAULT_ORDER_ID)
            .paymentId(DEFAULT_PAYMENT_ID)
            .customerEmail(DEFAULT_CUSTOMER_EMAIL)
            .customerPhone(DEFAULT_CUSTOMER_PHONE)
            .storeEmail(DEFAULT_STORE_EMAIL)
            .storePhone(DEFAULT_STORE_PHONE)
            .date(DEFAULT_DATE);
        return cancellationRequest;
    }

    @Before
    public void initTest() {
        cancellationRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createCancellationRequest() throws Exception {
        int databaseSizeBeforeCreate = cancellationRequestRepository.findAll().size();

        // Create the CancellationRequest
        CancellationRequestDTO cancellationRequestDTO = cancellationRequestMapper.toDto(cancellationRequest);
        restCancellationRequestMockMvc.perform(post("/api/cancellation-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cancellationRequestDTO)))
            .andExpect(status().isCreated());

        // Validate the CancellationRequest in the database
        List<CancellationRequest> cancellationRequestList = cancellationRequestRepository.findAll();
        assertThat(cancellationRequestList).hasSize(databaseSizeBeforeCreate + 1);
        CancellationRequest testCancellationRequest = cancellationRequestList.get(cancellationRequestList.size() - 1);
        assertThat(testCancellationRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCancellationRequest.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testCancellationRequest.getPaymentId()).isEqualTo(DEFAULT_PAYMENT_ID);
        assertThat(testCancellationRequest.getCustomerEmail()).isEqualTo(DEFAULT_CUSTOMER_EMAIL);
        assertThat(testCancellationRequest.getCustomerPhone()).isEqualTo(DEFAULT_CUSTOMER_PHONE);
        assertThat(testCancellationRequest.getStoreEmail()).isEqualTo(DEFAULT_STORE_EMAIL);
        assertThat(testCancellationRequest.getStorePhone()).isEqualTo(DEFAULT_STORE_PHONE);
        assertThat(testCancellationRequest.getDate()).isEqualTo(DEFAULT_DATE);

        // Validate the CancellationRequest in Elasticsearch
        verify(mockCancellationRequestSearchRepository, times(1)).save(testCancellationRequest);
    }

    @Test
    @Transactional
    public void createCancellationRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cancellationRequestRepository.findAll().size();

        // Create the CancellationRequest with an existing ID
        cancellationRequest.setId(1L);
        CancellationRequestDTO cancellationRequestDTO = cancellationRequestMapper.toDto(cancellationRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCancellationRequestMockMvc.perform(post("/api/cancellation-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cancellationRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CancellationRequest in the database
        List<CancellationRequest> cancellationRequestList = cancellationRequestRepository.findAll();
        assertThat(cancellationRequestList).hasSize(databaseSizeBeforeCreate);

        // Validate the CancellationRequest in Elasticsearch
        verify(mockCancellationRequestSearchRepository, times(0)).save(cancellationRequest);
    }

    @Test
    @Transactional
    public void getAllCancellationRequests() throws Exception {
        // Initialize the database
        cancellationRequestRepository.saveAndFlush(cancellationRequest);

        // Get all the cancellationRequestList
        restCancellationRequestMockMvc.perform(get("/api/cancellation-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cancellationRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID.toString())))
            .andExpect(jsonPath("$.[*].paymentId").value(hasItem(DEFAULT_PAYMENT_ID.toString())))
            .andExpect(jsonPath("$.[*].customerEmail").value(hasItem(DEFAULT_CUSTOMER_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].customerPhone").value(hasItem(DEFAULT_CUSTOMER_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].storeEmail").value(hasItem(DEFAULT_STORE_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].storePhone").value(hasItem(DEFAULT_STORE_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCancellationRequest() throws Exception {
        // Initialize the database
        cancellationRequestRepository.saveAndFlush(cancellationRequest);

        // Get the cancellationRequest
        restCancellationRequestMockMvc.perform(get("/api/cancellation-requests/{id}", cancellationRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cancellationRequest.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID.toString()))
            .andExpect(jsonPath("$.paymentId").value(DEFAULT_PAYMENT_ID.toString()))
            .andExpect(jsonPath("$.customerEmail").value(DEFAULT_CUSTOMER_EMAIL.toString()))
            .andExpect(jsonPath("$.customerPhone").value(DEFAULT_CUSTOMER_PHONE.intValue()))
            .andExpect(jsonPath("$.storeEmail").value(DEFAULT_STORE_EMAIL.toString()))
            .andExpect(jsonPath("$.storePhone").value(DEFAULT_STORE_PHONE.intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCancellationRequest() throws Exception {
        // Get the cancellationRequest
        restCancellationRequestMockMvc.perform(get("/api/cancellation-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCancellationRequest() throws Exception {
        // Initialize the database
        cancellationRequestRepository.saveAndFlush(cancellationRequest);

        int databaseSizeBeforeUpdate = cancellationRequestRepository.findAll().size();

        // Update the cancellationRequest
        CancellationRequest updatedCancellationRequest = cancellationRequestRepository.findById(cancellationRequest.getId()).get();
        // Disconnect from session so that the updates on updatedCancellationRequest are not directly saved in db
        em.detach(updatedCancellationRequest);
        updatedCancellationRequest
            .status(UPDATED_STATUS)
            .orderId(UPDATED_ORDER_ID)
            .paymentId(UPDATED_PAYMENT_ID)
            .customerEmail(UPDATED_CUSTOMER_EMAIL)
            .customerPhone(UPDATED_CUSTOMER_PHONE)
            .storeEmail(UPDATED_STORE_EMAIL)
            .storePhone(UPDATED_STORE_PHONE)
            .date(UPDATED_DATE);
        CancellationRequestDTO cancellationRequestDTO = cancellationRequestMapper.toDto(updatedCancellationRequest);

        restCancellationRequestMockMvc.perform(put("/api/cancellation-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cancellationRequestDTO)))
            .andExpect(status().isOk());

        // Validate the CancellationRequest in the database
        List<CancellationRequest> cancellationRequestList = cancellationRequestRepository.findAll();
        assertThat(cancellationRequestList).hasSize(databaseSizeBeforeUpdate);
        CancellationRequest testCancellationRequest = cancellationRequestList.get(cancellationRequestList.size() - 1);
        assertThat(testCancellationRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCancellationRequest.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testCancellationRequest.getPaymentId()).isEqualTo(UPDATED_PAYMENT_ID);
        assertThat(testCancellationRequest.getCustomerEmail()).isEqualTo(UPDATED_CUSTOMER_EMAIL);
        assertThat(testCancellationRequest.getCustomerPhone()).isEqualTo(UPDATED_CUSTOMER_PHONE);
        assertThat(testCancellationRequest.getStoreEmail()).isEqualTo(UPDATED_STORE_EMAIL);
        assertThat(testCancellationRequest.getStorePhone()).isEqualTo(UPDATED_STORE_PHONE);
        assertThat(testCancellationRequest.getDate()).isEqualTo(UPDATED_DATE);

        // Validate the CancellationRequest in Elasticsearch
        verify(mockCancellationRequestSearchRepository, times(1)).save(testCancellationRequest);
    }

    @Test
    @Transactional
    public void updateNonExistingCancellationRequest() throws Exception {
        int databaseSizeBeforeUpdate = cancellationRequestRepository.findAll().size();

        // Create the CancellationRequest
        CancellationRequestDTO cancellationRequestDTO = cancellationRequestMapper.toDto(cancellationRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCancellationRequestMockMvc.perform(put("/api/cancellation-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cancellationRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CancellationRequest in the database
        List<CancellationRequest> cancellationRequestList = cancellationRequestRepository.findAll();
        assertThat(cancellationRequestList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CancellationRequest in Elasticsearch
        verify(mockCancellationRequestSearchRepository, times(0)).save(cancellationRequest);
    }

    @Test
    @Transactional
    public void deleteCancellationRequest() throws Exception {
        // Initialize the database
        cancellationRequestRepository.saveAndFlush(cancellationRequest);

        int databaseSizeBeforeDelete = cancellationRequestRepository.findAll().size();

        // Delete the cancellationRequest
        restCancellationRequestMockMvc.perform(delete("/api/cancellation-requests/{id}", cancellationRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CancellationRequest> cancellationRequestList = cancellationRequestRepository.findAll();
        assertThat(cancellationRequestList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CancellationRequest in Elasticsearch
        verify(mockCancellationRequestSearchRepository, times(1)).deleteById(cancellationRequest.getId());
    }

    @Test
    @Transactional
    public void searchCancellationRequest() throws Exception {
        // Initialize the database
        cancellationRequestRepository.saveAndFlush(cancellationRequest);
        when(mockCancellationRequestSearchRepository.search(queryStringQuery("id:" + cancellationRequest.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cancellationRequest), PageRequest.of(0, 1), 1));
        // Search the cancellationRequest
        restCancellationRequestMockMvc.perform(get("/api/_search/cancellation-requests?query=id:" + cancellationRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cancellationRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].paymentId").value(hasItem(DEFAULT_PAYMENT_ID)))
            .andExpect(jsonPath("$.[*].customerEmail").value(hasItem(DEFAULT_CUSTOMER_EMAIL)))
            .andExpect(jsonPath("$.[*].customerPhone").value(hasItem(DEFAULT_CUSTOMER_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].storeEmail").value(hasItem(DEFAULT_STORE_EMAIL)))
            .andExpect(jsonPath("$.[*].storePhone").value(hasItem(DEFAULT_STORE_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CancellationRequest.class);
        CancellationRequest cancellationRequest1 = new CancellationRequest();
        cancellationRequest1.setId(1L);
        CancellationRequest cancellationRequest2 = new CancellationRequest();
        cancellationRequest2.setId(cancellationRequest1.getId());
        assertThat(cancellationRequest1).isEqualTo(cancellationRequest2);
        cancellationRequest2.setId(2L);
        assertThat(cancellationRequest1).isNotEqualTo(cancellationRequest2);
        cancellationRequest1.setId(null);
        assertThat(cancellationRequest1).isNotEqualTo(cancellationRequest2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CancellationRequestDTO.class);
        CancellationRequestDTO cancellationRequestDTO1 = new CancellationRequestDTO();
        cancellationRequestDTO1.setId(1L);
        CancellationRequestDTO cancellationRequestDTO2 = new CancellationRequestDTO();
        assertThat(cancellationRequestDTO1).isNotEqualTo(cancellationRequestDTO2);
        cancellationRequestDTO2.setId(cancellationRequestDTO1.getId());
        assertThat(cancellationRequestDTO1).isEqualTo(cancellationRequestDTO2);
        cancellationRequestDTO2.setId(2L);
        assertThat(cancellationRequestDTO1).isNotEqualTo(cancellationRequestDTO2);
        cancellationRequestDTO1.setId(null);
        assertThat(cancellationRequestDTO1).isNotEqualTo(cancellationRequestDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cancellationRequestMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cancellationRequestMapper.fromId(null)).isNull();
    }
}
