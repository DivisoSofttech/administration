package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.AdministrationApp;

import com.diviso.graeshoppe.domain.CancelledOrderLine;
import com.diviso.graeshoppe.repository.CancelledOrderLineRepository;
import com.diviso.graeshoppe.repository.search.CancelledOrderLineSearchRepository;
import com.diviso.graeshoppe.service.CancelledOrderLineService;
import com.diviso.graeshoppe.service.dto.CancelledOrderLineDTO;
import com.diviso.graeshoppe.service.mapper.CancelledOrderLineMapper;
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
 * Test class for the CancelledOrderLineResource REST controller.
 *
 * @see CancelledOrderLineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdministrationApp.class)
public class CancelledOrderLineResourceIntTest {

    private static final Long DEFAULT_ORDER_LINE_ID = 1L;
    private static final Long UPDATED_ORDER_LINE_ID = 2L;

    @Autowired
    private CancelledOrderLineRepository cancelledOrderLineRepository;

    @Autowired
    private CancelledOrderLineMapper cancelledOrderLineMapper;

    @Autowired
    private CancelledOrderLineService cancelledOrderLineService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.repository.search test package.
     *
     * @see com.diviso.graeshoppe.repository.search.CancelledOrderLineSearchRepositoryMockConfiguration
     */
    @Autowired
    private CancelledOrderLineSearchRepository mockCancelledOrderLineSearchRepository;

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

    private MockMvc restCancelledOrderLineMockMvc;

    private CancelledOrderLine cancelledOrderLine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CancelledOrderLineResource cancelledOrderLineResource = new CancelledOrderLineResource(cancelledOrderLineService);
        this.restCancelledOrderLineMockMvc = MockMvcBuilders.standaloneSetup(cancelledOrderLineResource)
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
    public static CancelledOrderLine createEntity(EntityManager em) {
        CancelledOrderLine cancelledOrderLine = new CancelledOrderLine()
            .orderLineId(DEFAULT_ORDER_LINE_ID);
        return cancelledOrderLine;
    }

    @Before
    public void initTest() {
        cancelledOrderLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createCancelledOrderLine() throws Exception {
        int databaseSizeBeforeCreate = cancelledOrderLineRepository.findAll().size();

        // Create the CancelledOrderLine
        CancelledOrderLineDTO cancelledOrderLineDTO = cancelledOrderLineMapper.toDto(cancelledOrderLine);
        restCancelledOrderLineMockMvc.perform(post("/api/cancelled-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cancelledOrderLineDTO)))
            .andExpect(status().isCreated());

        // Validate the CancelledOrderLine in the database
        List<CancelledOrderLine> cancelledOrderLineList = cancelledOrderLineRepository.findAll();
        assertThat(cancelledOrderLineList).hasSize(databaseSizeBeforeCreate + 1);
        CancelledOrderLine testCancelledOrderLine = cancelledOrderLineList.get(cancelledOrderLineList.size() - 1);
        assertThat(testCancelledOrderLine.getOrderLineId()).isEqualTo(DEFAULT_ORDER_LINE_ID);

        // Validate the CancelledOrderLine in Elasticsearch
        verify(mockCancelledOrderLineSearchRepository, times(1)).save(testCancelledOrderLine);
    }

    @Test
    @Transactional
    public void createCancelledOrderLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cancelledOrderLineRepository.findAll().size();

        // Create the CancelledOrderLine with an existing ID
        cancelledOrderLine.setId(1L);
        CancelledOrderLineDTO cancelledOrderLineDTO = cancelledOrderLineMapper.toDto(cancelledOrderLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCancelledOrderLineMockMvc.perform(post("/api/cancelled-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cancelledOrderLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CancelledOrderLine in the database
        List<CancelledOrderLine> cancelledOrderLineList = cancelledOrderLineRepository.findAll();
        assertThat(cancelledOrderLineList).hasSize(databaseSizeBeforeCreate);

        // Validate the CancelledOrderLine in Elasticsearch
        verify(mockCancelledOrderLineSearchRepository, times(0)).save(cancelledOrderLine);
    }

    @Test
    @Transactional
    public void getAllCancelledOrderLines() throws Exception {
        // Initialize the database
        cancelledOrderLineRepository.saveAndFlush(cancelledOrderLine);

        // Get all the cancelledOrderLineList
        restCancelledOrderLineMockMvc.perform(get("/api/cancelled-order-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cancelledOrderLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderLineId").value(hasItem(DEFAULT_ORDER_LINE_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getCancelledOrderLine() throws Exception {
        // Initialize the database
        cancelledOrderLineRepository.saveAndFlush(cancelledOrderLine);

        // Get the cancelledOrderLine
        restCancelledOrderLineMockMvc.perform(get("/api/cancelled-order-lines/{id}", cancelledOrderLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cancelledOrderLine.getId().intValue()))
            .andExpect(jsonPath("$.orderLineId").value(DEFAULT_ORDER_LINE_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCancelledOrderLine() throws Exception {
        // Get the cancelledOrderLine
        restCancelledOrderLineMockMvc.perform(get("/api/cancelled-order-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCancelledOrderLine() throws Exception {
        // Initialize the database
        cancelledOrderLineRepository.saveAndFlush(cancelledOrderLine);

        int databaseSizeBeforeUpdate = cancelledOrderLineRepository.findAll().size();

        // Update the cancelledOrderLine
        CancelledOrderLine updatedCancelledOrderLine = cancelledOrderLineRepository.findById(cancelledOrderLine.getId()).get();
        // Disconnect from session so that the updates on updatedCancelledOrderLine are not directly saved in db
        em.detach(updatedCancelledOrderLine);
        updatedCancelledOrderLine
            .orderLineId(UPDATED_ORDER_LINE_ID);
        CancelledOrderLineDTO cancelledOrderLineDTO = cancelledOrderLineMapper.toDto(updatedCancelledOrderLine);

        restCancelledOrderLineMockMvc.perform(put("/api/cancelled-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cancelledOrderLineDTO)))
            .andExpect(status().isOk());

        // Validate the CancelledOrderLine in the database
        List<CancelledOrderLine> cancelledOrderLineList = cancelledOrderLineRepository.findAll();
        assertThat(cancelledOrderLineList).hasSize(databaseSizeBeforeUpdate);
        CancelledOrderLine testCancelledOrderLine = cancelledOrderLineList.get(cancelledOrderLineList.size() - 1);
        assertThat(testCancelledOrderLine.getOrderLineId()).isEqualTo(UPDATED_ORDER_LINE_ID);

        // Validate the CancelledOrderLine in Elasticsearch
        verify(mockCancelledOrderLineSearchRepository, times(1)).save(testCancelledOrderLine);
    }

    @Test
    @Transactional
    public void updateNonExistingCancelledOrderLine() throws Exception {
        int databaseSizeBeforeUpdate = cancelledOrderLineRepository.findAll().size();

        // Create the CancelledOrderLine
        CancelledOrderLineDTO cancelledOrderLineDTO = cancelledOrderLineMapper.toDto(cancelledOrderLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCancelledOrderLineMockMvc.perform(put("/api/cancelled-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cancelledOrderLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CancelledOrderLine in the database
        List<CancelledOrderLine> cancelledOrderLineList = cancelledOrderLineRepository.findAll();
        assertThat(cancelledOrderLineList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CancelledOrderLine in Elasticsearch
        verify(mockCancelledOrderLineSearchRepository, times(0)).save(cancelledOrderLine);
    }

    @Test
    @Transactional
    public void deleteCancelledOrderLine() throws Exception {
        // Initialize the database
        cancelledOrderLineRepository.saveAndFlush(cancelledOrderLine);

        int databaseSizeBeforeDelete = cancelledOrderLineRepository.findAll().size();

        // Delete the cancelledOrderLine
        restCancelledOrderLineMockMvc.perform(delete("/api/cancelled-order-lines/{id}", cancelledOrderLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CancelledOrderLine> cancelledOrderLineList = cancelledOrderLineRepository.findAll();
        assertThat(cancelledOrderLineList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CancelledOrderLine in Elasticsearch
        verify(mockCancelledOrderLineSearchRepository, times(1)).deleteById(cancelledOrderLine.getId());
    }

    @Test
    @Transactional
    public void searchCancelledOrderLine() throws Exception {
        // Initialize the database
        cancelledOrderLineRepository.saveAndFlush(cancelledOrderLine);
        when(mockCancelledOrderLineSearchRepository.search(queryStringQuery("id:" + cancelledOrderLine.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cancelledOrderLine), PageRequest.of(0, 1), 1));
        // Search the cancelledOrderLine
        restCancelledOrderLineMockMvc.perform(get("/api/_search/cancelled-order-lines?query=id:" + cancelledOrderLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cancelledOrderLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderLineId").value(hasItem(DEFAULT_ORDER_LINE_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CancelledOrderLine.class);
        CancelledOrderLine cancelledOrderLine1 = new CancelledOrderLine();
        cancelledOrderLine1.setId(1L);
        CancelledOrderLine cancelledOrderLine2 = new CancelledOrderLine();
        cancelledOrderLine2.setId(cancelledOrderLine1.getId());
        assertThat(cancelledOrderLine1).isEqualTo(cancelledOrderLine2);
        cancelledOrderLine2.setId(2L);
        assertThat(cancelledOrderLine1).isNotEqualTo(cancelledOrderLine2);
        cancelledOrderLine1.setId(null);
        assertThat(cancelledOrderLine1).isNotEqualTo(cancelledOrderLine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CancelledOrderLineDTO.class);
        CancelledOrderLineDTO cancelledOrderLineDTO1 = new CancelledOrderLineDTO();
        cancelledOrderLineDTO1.setId(1L);
        CancelledOrderLineDTO cancelledOrderLineDTO2 = new CancelledOrderLineDTO();
        assertThat(cancelledOrderLineDTO1).isNotEqualTo(cancelledOrderLineDTO2);
        cancelledOrderLineDTO2.setId(cancelledOrderLineDTO1.getId());
        assertThat(cancelledOrderLineDTO1).isEqualTo(cancelledOrderLineDTO2);
        cancelledOrderLineDTO2.setId(2L);
        assertThat(cancelledOrderLineDTO1).isNotEqualTo(cancelledOrderLineDTO2);
        cancelledOrderLineDTO1.setId(null);
        assertThat(cancelledOrderLineDTO1).isNotEqualTo(cancelledOrderLineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cancelledOrderLineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cancelledOrderLineMapper.fromId(null)).isNull();
    }
}
