package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.AdministrationApp;
import com.diviso.graeshoppe.config.TestSecurityConfiguration;
import com.diviso.graeshoppe.domain.TicketIdGenerator;
import com.diviso.graeshoppe.repository.TicketIdGeneratorRepository;
import com.diviso.graeshoppe.repository.search.TicketIdGeneratorSearchRepository;
import com.diviso.graeshoppe.service.TicketIdGeneratorService;
import com.diviso.graeshoppe.service.dto.TicketIdGeneratorDTO;
import com.diviso.graeshoppe.service.mapper.TicketIdGeneratorMapper;
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
 * Integration tests for the {@link TicketIdGeneratorResource} REST controller.
 */
@SpringBootTest(classes = {AdministrationApp.class, TestSecurityConfiguration.class})
public class TicketIdGeneratorResourceIT {

    @Autowired
    private TicketIdGeneratorRepository ticketIdGeneratorRepository;

    @Autowired
    private TicketIdGeneratorMapper ticketIdGeneratorMapper;

    @Autowired
    private TicketIdGeneratorService ticketIdGeneratorService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.repository.search test package.
     *
     * @see com.diviso.graeshoppe.repository.search.TicketIdGeneratorSearchRepositoryMockConfiguration
     */
    @Autowired
    private TicketIdGeneratorSearchRepository mockTicketIdGeneratorSearchRepository;

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

    private MockMvc restTicketIdGeneratorMockMvc;

    private TicketIdGenerator ticketIdGenerator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TicketIdGeneratorResource ticketIdGeneratorResource = new TicketIdGeneratorResource(ticketIdGeneratorService);
        this.restTicketIdGeneratorMockMvc = MockMvcBuilders.standaloneSetup(ticketIdGeneratorResource)
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
    public static TicketIdGenerator createEntity(EntityManager em) {
        TicketIdGenerator ticketIdGenerator = new TicketIdGenerator();
        return ticketIdGenerator;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketIdGenerator createUpdatedEntity(EntityManager em) {
        TicketIdGenerator ticketIdGenerator = new TicketIdGenerator();
        return ticketIdGenerator;
    }

    @BeforeEach
    public void initTest() {
        ticketIdGenerator = createEntity(em);
    }

    @Test
    @Transactional
    public void createTicketIdGenerator() throws Exception {
        int databaseSizeBeforeCreate = ticketIdGeneratorRepository.findAll().size();

        // Create the TicketIdGenerator
        TicketIdGeneratorDTO ticketIdGeneratorDTO = ticketIdGeneratorMapper.toDto(ticketIdGenerator);
        restTicketIdGeneratorMockMvc.perform(post("/api/ticket-id-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketIdGeneratorDTO)))
            .andExpect(status().isCreated());

        // Validate the TicketIdGenerator in the database
        List<TicketIdGenerator> ticketIdGeneratorList = ticketIdGeneratorRepository.findAll();
        assertThat(ticketIdGeneratorList).hasSize(databaseSizeBeforeCreate + 1);
        TicketIdGenerator testTicketIdGenerator = ticketIdGeneratorList.get(ticketIdGeneratorList.size() - 1);

        // Validate the TicketIdGenerator in Elasticsearch
        verify(mockTicketIdGeneratorSearchRepository, times(1)).save(testTicketIdGenerator);
    }

    @Test
    @Transactional
    public void createTicketIdGeneratorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ticketIdGeneratorRepository.findAll().size();

        // Create the TicketIdGenerator with an existing ID
        ticketIdGenerator.setId(1L);
        TicketIdGeneratorDTO ticketIdGeneratorDTO = ticketIdGeneratorMapper.toDto(ticketIdGenerator);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketIdGeneratorMockMvc.perform(post("/api/ticket-id-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketIdGeneratorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TicketIdGenerator in the database
        List<TicketIdGenerator> ticketIdGeneratorList = ticketIdGeneratorRepository.findAll();
        assertThat(ticketIdGeneratorList).hasSize(databaseSizeBeforeCreate);

        // Validate the TicketIdGenerator in Elasticsearch
        verify(mockTicketIdGeneratorSearchRepository, times(0)).save(ticketIdGenerator);
    }


    @Test
    @Transactional
    public void getAllTicketIdGenerators() throws Exception {
        // Initialize the database
        ticketIdGeneratorRepository.saveAndFlush(ticketIdGenerator);

        // Get all the ticketIdGeneratorList
        restTicketIdGeneratorMockMvc.perform(get("/api/ticket-id-generators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketIdGenerator.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getTicketIdGenerator() throws Exception {
        // Initialize the database
        ticketIdGeneratorRepository.saveAndFlush(ticketIdGenerator);

        // Get the ticketIdGenerator
        restTicketIdGeneratorMockMvc.perform(get("/api/ticket-id-generators/{id}", ticketIdGenerator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ticketIdGenerator.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTicketIdGenerator() throws Exception {
        // Get the ticketIdGenerator
        restTicketIdGeneratorMockMvc.perform(get("/api/ticket-id-generators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTicketIdGenerator() throws Exception {
        // Initialize the database
        ticketIdGeneratorRepository.saveAndFlush(ticketIdGenerator);

        int databaseSizeBeforeUpdate = ticketIdGeneratorRepository.findAll().size();

        // Update the ticketIdGenerator
        TicketIdGenerator updatedTicketIdGenerator = ticketIdGeneratorRepository.findById(ticketIdGenerator.getId()).get();
        // Disconnect from session so that the updates on updatedTicketIdGenerator are not directly saved in db
        em.detach(updatedTicketIdGenerator);
        TicketIdGeneratorDTO ticketIdGeneratorDTO = ticketIdGeneratorMapper.toDto(updatedTicketIdGenerator);

        restTicketIdGeneratorMockMvc.perform(put("/api/ticket-id-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketIdGeneratorDTO)))
            .andExpect(status().isOk());

        // Validate the TicketIdGenerator in the database
        List<TicketIdGenerator> ticketIdGeneratorList = ticketIdGeneratorRepository.findAll();
        assertThat(ticketIdGeneratorList).hasSize(databaseSizeBeforeUpdate);
        TicketIdGenerator testTicketIdGenerator = ticketIdGeneratorList.get(ticketIdGeneratorList.size() - 1);

        // Validate the TicketIdGenerator in Elasticsearch
        verify(mockTicketIdGeneratorSearchRepository, times(1)).save(testTicketIdGenerator);
    }

    @Test
    @Transactional
    public void updateNonExistingTicketIdGenerator() throws Exception {
        int databaseSizeBeforeUpdate = ticketIdGeneratorRepository.findAll().size();

        // Create the TicketIdGenerator
        TicketIdGeneratorDTO ticketIdGeneratorDTO = ticketIdGeneratorMapper.toDto(ticketIdGenerator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketIdGeneratorMockMvc.perform(put("/api/ticket-id-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketIdGeneratorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TicketIdGenerator in the database
        List<TicketIdGenerator> ticketIdGeneratorList = ticketIdGeneratorRepository.findAll();
        assertThat(ticketIdGeneratorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TicketIdGenerator in Elasticsearch
        verify(mockTicketIdGeneratorSearchRepository, times(0)).save(ticketIdGenerator);
    }

    @Test
    @Transactional
    public void deleteTicketIdGenerator() throws Exception {
        // Initialize the database
        ticketIdGeneratorRepository.saveAndFlush(ticketIdGenerator);

        int databaseSizeBeforeDelete = ticketIdGeneratorRepository.findAll().size();

        // Delete the ticketIdGenerator
        restTicketIdGeneratorMockMvc.perform(delete("/api/ticket-id-generators/{id}", ticketIdGenerator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TicketIdGenerator> ticketIdGeneratorList = ticketIdGeneratorRepository.findAll();
        assertThat(ticketIdGeneratorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TicketIdGenerator in Elasticsearch
        verify(mockTicketIdGeneratorSearchRepository, times(1)).deleteById(ticketIdGenerator.getId());
    }

    @Test
    @Transactional
    public void searchTicketIdGenerator() throws Exception {
        // Initialize the database
        ticketIdGeneratorRepository.saveAndFlush(ticketIdGenerator);
        when(mockTicketIdGeneratorSearchRepository.search(queryStringQuery("id:" + ticketIdGenerator.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ticketIdGenerator), PageRequest.of(0, 1), 1));
        // Search the ticketIdGenerator
        restTicketIdGeneratorMockMvc.perform(get("/api/_search/ticket-id-generators?query=id:" + ticketIdGenerator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketIdGenerator.getId().intValue())));
    }
}
