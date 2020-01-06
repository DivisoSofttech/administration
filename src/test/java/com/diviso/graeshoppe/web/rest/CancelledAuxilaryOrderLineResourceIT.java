package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.AdministrationApp;
import com.diviso.graeshoppe.config.TestSecurityConfiguration;
import com.diviso.graeshoppe.domain.CancelledAuxilaryOrderLine;
import com.diviso.graeshoppe.repository.CancelledAuxilaryOrderLineRepository;
import com.diviso.graeshoppe.repository.search.CancelledAuxilaryOrderLineSearchRepository;
import com.diviso.graeshoppe.service.CancelledAuxilaryOrderLineService;
import com.diviso.graeshoppe.service.dto.CancelledAuxilaryOrderLineDTO;
import com.diviso.graeshoppe.service.mapper.CancelledAuxilaryOrderLineMapper;
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
 * Integration tests for the {@link CancelledAuxilaryOrderLineResource} REST controller.
 */
@SpringBootTest(classes = {AdministrationApp.class, TestSecurityConfiguration.class})
public class CancelledAuxilaryOrderLineResourceIT {

    private static final Long DEFAULT_ORDER_LINE_ID = 1L;
    private static final Long UPDATED_ORDER_LINE_ID = 2L;

    private static final Double DEFAULT_PRICE_PER_UNIT = 1D;
    private static final Double UPDATED_PRICE_PER_UNIT = 2D;

    private static final Double DEFAULT_AMMOUNT = 1D;
    private static final Double UPDATED_AMMOUNT = 2D;

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    @Autowired
    private CancelledAuxilaryOrderLineRepository cancelledAuxilaryOrderLineRepository;

    @Autowired
    private CancelledAuxilaryOrderLineMapper cancelledAuxilaryOrderLineMapper;

    @Autowired
    private CancelledAuxilaryOrderLineService cancelledAuxilaryOrderLineService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.repository.search test package.
     *
     * @see com.diviso.graeshoppe.repository.search.CancelledAuxilaryOrderLineSearchRepositoryMockConfiguration
     */
    @Autowired
    private CancelledAuxilaryOrderLineSearchRepository mockCancelledAuxilaryOrderLineSearchRepository;

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

    private MockMvc restCancelledAuxilaryOrderLineMockMvc;

    private CancelledAuxilaryOrderLine cancelledAuxilaryOrderLine;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CancelledAuxilaryOrderLineResource cancelledAuxilaryOrderLineResource = new CancelledAuxilaryOrderLineResource(cancelledAuxilaryOrderLineService);
        this.restCancelledAuxilaryOrderLineMockMvc = MockMvcBuilders.standaloneSetup(cancelledAuxilaryOrderLineResource)
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
    public static CancelledAuxilaryOrderLine createEntity(EntityManager em) {
        CancelledAuxilaryOrderLine cancelledAuxilaryOrderLine = new CancelledAuxilaryOrderLine()
            .orderLineId(DEFAULT_ORDER_LINE_ID)
            .pricePerUnit(DEFAULT_PRICE_PER_UNIT)
            .ammount(DEFAULT_AMMOUNT)
            .quantity(DEFAULT_QUANTITY)
            .itemName(DEFAULT_ITEM_NAME)
            .productId(DEFAULT_PRODUCT_ID);
        return cancelledAuxilaryOrderLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CancelledAuxilaryOrderLine createUpdatedEntity(EntityManager em) {
        CancelledAuxilaryOrderLine cancelledAuxilaryOrderLine = new CancelledAuxilaryOrderLine()
            .orderLineId(UPDATED_ORDER_LINE_ID)
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .ammount(UPDATED_AMMOUNT)
            .quantity(UPDATED_QUANTITY)
            .itemName(UPDATED_ITEM_NAME)
            .productId(UPDATED_PRODUCT_ID);
        return cancelledAuxilaryOrderLine;
    }

    @BeforeEach
    public void initTest() {
        cancelledAuxilaryOrderLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createCancelledAuxilaryOrderLine() throws Exception {
        int databaseSizeBeforeCreate = cancelledAuxilaryOrderLineRepository.findAll().size();

        // Create the CancelledAuxilaryOrderLine
        CancelledAuxilaryOrderLineDTO cancelledAuxilaryOrderLineDTO = cancelledAuxilaryOrderLineMapper.toDto(cancelledAuxilaryOrderLine);
        restCancelledAuxilaryOrderLineMockMvc.perform(post("/api/cancelled-auxilary-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cancelledAuxilaryOrderLineDTO)))
            .andExpect(status().isCreated());

        // Validate the CancelledAuxilaryOrderLine in the database
        List<CancelledAuxilaryOrderLine> cancelledAuxilaryOrderLineList = cancelledAuxilaryOrderLineRepository.findAll();
        assertThat(cancelledAuxilaryOrderLineList).hasSize(databaseSizeBeforeCreate + 1);
        CancelledAuxilaryOrderLine testCancelledAuxilaryOrderLine = cancelledAuxilaryOrderLineList.get(cancelledAuxilaryOrderLineList.size() - 1);
        assertThat(testCancelledAuxilaryOrderLine.getOrderLineId()).isEqualTo(DEFAULT_ORDER_LINE_ID);
        assertThat(testCancelledAuxilaryOrderLine.getPricePerUnit()).isEqualTo(DEFAULT_PRICE_PER_UNIT);
        assertThat(testCancelledAuxilaryOrderLine.getAmmount()).isEqualTo(DEFAULT_AMMOUNT);
        assertThat(testCancelledAuxilaryOrderLine.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testCancelledAuxilaryOrderLine.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testCancelledAuxilaryOrderLine.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);

        // Validate the CancelledAuxilaryOrderLine in Elasticsearch
        verify(mockCancelledAuxilaryOrderLineSearchRepository, times(1)).save(testCancelledAuxilaryOrderLine);
    }

    @Test
    @Transactional
    public void createCancelledAuxilaryOrderLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cancelledAuxilaryOrderLineRepository.findAll().size();

        // Create the CancelledAuxilaryOrderLine with an existing ID
        cancelledAuxilaryOrderLine.setId(1L);
        CancelledAuxilaryOrderLineDTO cancelledAuxilaryOrderLineDTO = cancelledAuxilaryOrderLineMapper.toDto(cancelledAuxilaryOrderLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCancelledAuxilaryOrderLineMockMvc.perform(post("/api/cancelled-auxilary-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cancelledAuxilaryOrderLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CancelledAuxilaryOrderLine in the database
        List<CancelledAuxilaryOrderLine> cancelledAuxilaryOrderLineList = cancelledAuxilaryOrderLineRepository.findAll();
        assertThat(cancelledAuxilaryOrderLineList).hasSize(databaseSizeBeforeCreate);

        // Validate the CancelledAuxilaryOrderLine in Elasticsearch
        verify(mockCancelledAuxilaryOrderLineSearchRepository, times(0)).save(cancelledAuxilaryOrderLine);
    }


    @Test
    @Transactional
    public void getAllCancelledAuxilaryOrderLines() throws Exception {
        // Initialize the database
        cancelledAuxilaryOrderLineRepository.saveAndFlush(cancelledAuxilaryOrderLine);

        // Get all the cancelledAuxilaryOrderLineList
        restCancelledAuxilaryOrderLineMockMvc.perform(get("/api/cancelled-auxilary-order-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cancelledAuxilaryOrderLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderLineId").value(hasItem(DEFAULT_ORDER_LINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].pricePerUnit").value(hasItem(DEFAULT_PRICE_PER_UNIT.doubleValue())))
            .andExpect(jsonPath("$.[*].ammount").value(hasItem(DEFAULT_AMMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getCancelledAuxilaryOrderLine() throws Exception {
        // Initialize the database
        cancelledAuxilaryOrderLineRepository.saveAndFlush(cancelledAuxilaryOrderLine);

        // Get the cancelledAuxilaryOrderLine
        restCancelledAuxilaryOrderLineMockMvc.perform(get("/api/cancelled-auxilary-order-lines/{id}", cancelledAuxilaryOrderLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cancelledAuxilaryOrderLine.getId().intValue()))
            .andExpect(jsonPath("$.orderLineId").value(DEFAULT_ORDER_LINE_ID.intValue()))
            .andExpect(jsonPath("$.pricePerUnit").value(DEFAULT_PRICE_PER_UNIT.doubleValue()))
            .andExpect(jsonPath("$.ammount").value(DEFAULT_AMMOUNT.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCancelledAuxilaryOrderLine() throws Exception {
        // Get the cancelledAuxilaryOrderLine
        restCancelledAuxilaryOrderLineMockMvc.perform(get("/api/cancelled-auxilary-order-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCancelledAuxilaryOrderLine() throws Exception {
        // Initialize the database
        cancelledAuxilaryOrderLineRepository.saveAndFlush(cancelledAuxilaryOrderLine);

        int databaseSizeBeforeUpdate = cancelledAuxilaryOrderLineRepository.findAll().size();

        // Update the cancelledAuxilaryOrderLine
        CancelledAuxilaryOrderLine updatedCancelledAuxilaryOrderLine = cancelledAuxilaryOrderLineRepository.findById(cancelledAuxilaryOrderLine.getId()).get();
        // Disconnect from session so that the updates on updatedCancelledAuxilaryOrderLine are not directly saved in db
        em.detach(updatedCancelledAuxilaryOrderLine);
        updatedCancelledAuxilaryOrderLine
            .orderLineId(UPDATED_ORDER_LINE_ID)
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .ammount(UPDATED_AMMOUNT)
            .quantity(UPDATED_QUANTITY)
            .itemName(UPDATED_ITEM_NAME)
            .productId(UPDATED_PRODUCT_ID);
        CancelledAuxilaryOrderLineDTO cancelledAuxilaryOrderLineDTO = cancelledAuxilaryOrderLineMapper.toDto(updatedCancelledAuxilaryOrderLine);

        restCancelledAuxilaryOrderLineMockMvc.perform(put("/api/cancelled-auxilary-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cancelledAuxilaryOrderLineDTO)))
            .andExpect(status().isOk());

        // Validate the CancelledAuxilaryOrderLine in the database
        List<CancelledAuxilaryOrderLine> cancelledAuxilaryOrderLineList = cancelledAuxilaryOrderLineRepository.findAll();
        assertThat(cancelledAuxilaryOrderLineList).hasSize(databaseSizeBeforeUpdate);
        CancelledAuxilaryOrderLine testCancelledAuxilaryOrderLine = cancelledAuxilaryOrderLineList.get(cancelledAuxilaryOrderLineList.size() - 1);
        assertThat(testCancelledAuxilaryOrderLine.getOrderLineId()).isEqualTo(UPDATED_ORDER_LINE_ID);
        assertThat(testCancelledAuxilaryOrderLine.getPricePerUnit()).isEqualTo(UPDATED_PRICE_PER_UNIT);
        assertThat(testCancelledAuxilaryOrderLine.getAmmount()).isEqualTo(UPDATED_AMMOUNT);
        assertThat(testCancelledAuxilaryOrderLine.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCancelledAuxilaryOrderLine.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testCancelledAuxilaryOrderLine.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);

        // Validate the CancelledAuxilaryOrderLine in Elasticsearch
        verify(mockCancelledAuxilaryOrderLineSearchRepository, times(1)).save(testCancelledAuxilaryOrderLine);
    }

    @Test
    @Transactional
    public void updateNonExistingCancelledAuxilaryOrderLine() throws Exception {
        int databaseSizeBeforeUpdate = cancelledAuxilaryOrderLineRepository.findAll().size();

        // Create the CancelledAuxilaryOrderLine
        CancelledAuxilaryOrderLineDTO cancelledAuxilaryOrderLineDTO = cancelledAuxilaryOrderLineMapper.toDto(cancelledAuxilaryOrderLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCancelledAuxilaryOrderLineMockMvc.perform(put("/api/cancelled-auxilary-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cancelledAuxilaryOrderLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CancelledAuxilaryOrderLine in the database
        List<CancelledAuxilaryOrderLine> cancelledAuxilaryOrderLineList = cancelledAuxilaryOrderLineRepository.findAll();
        assertThat(cancelledAuxilaryOrderLineList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CancelledAuxilaryOrderLine in Elasticsearch
        verify(mockCancelledAuxilaryOrderLineSearchRepository, times(0)).save(cancelledAuxilaryOrderLine);
    }

    @Test
    @Transactional
    public void deleteCancelledAuxilaryOrderLine() throws Exception {
        // Initialize the database
        cancelledAuxilaryOrderLineRepository.saveAndFlush(cancelledAuxilaryOrderLine);

        int databaseSizeBeforeDelete = cancelledAuxilaryOrderLineRepository.findAll().size();

        // Delete the cancelledAuxilaryOrderLine
        restCancelledAuxilaryOrderLineMockMvc.perform(delete("/api/cancelled-auxilary-order-lines/{id}", cancelledAuxilaryOrderLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CancelledAuxilaryOrderLine> cancelledAuxilaryOrderLineList = cancelledAuxilaryOrderLineRepository.findAll();
        assertThat(cancelledAuxilaryOrderLineList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CancelledAuxilaryOrderLine in Elasticsearch
        verify(mockCancelledAuxilaryOrderLineSearchRepository, times(1)).deleteById(cancelledAuxilaryOrderLine.getId());
    }

    @Test
    @Transactional
    public void searchCancelledAuxilaryOrderLine() throws Exception {
        // Initialize the database
        cancelledAuxilaryOrderLineRepository.saveAndFlush(cancelledAuxilaryOrderLine);
        when(mockCancelledAuxilaryOrderLineSearchRepository.search(queryStringQuery("id:" + cancelledAuxilaryOrderLine.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cancelledAuxilaryOrderLine), PageRequest.of(0, 1), 1));
        // Search the cancelledAuxilaryOrderLine
        restCancelledAuxilaryOrderLineMockMvc.perform(get("/api/_search/cancelled-auxilary-order-lines?query=id:" + cancelledAuxilaryOrderLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cancelledAuxilaryOrderLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderLineId").value(hasItem(DEFAULT_ORDER_LINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].pricePerUnit").value(hasItem(DEFAULT_PRICE_PER_UNIT.doubleValue())))
            .andExpect(jsonPath("$.[*].ammount").value(hasItem(DEFAULT_AMMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())));
    }
}
