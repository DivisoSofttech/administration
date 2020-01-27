package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.AdministrationApp;
import com.diviso.graeshoppe.config.TestSecurityConfiguration;
import com.diviso.graeshoppe.domain.About;
import com.diviso.graeshoppe.repository.AboutRepository;
import com.diviso.graeshoppe.repository.search.AboutSearchRepository;
import com.diviso.graeshoppe.service.AboutService;
import com.diviso.graeshoppe.service.dto.AboutDTO;
import com.diviso.graeshoppe.service.mapper.AboutMapper;
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
 * Integration tests for the {@link AboutResource} REST controller.
 */
@SpringBootTest(classes = {AdministrationApp.class, TestSecurityConfiguration.class})
public class AboutResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPORT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_SUPPORT_MAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_SUPPORT_PHONE = 1L;
    private static final Long UPDATED_SUPPORT_PHONE = 2L;

    private static final String DEFAULT_ADD_ON_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADD_ON_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADD_ON_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADD_ON_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADD_ON_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADD_ON_3 = "BBBBBBBBBB";

    @Autowired
    private AboutRepository aboutRepository;

    @Autowired
    private AboutMapper aboutMapper;

    @Autowired
    private AboutService aboutService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.repository.search test package.
     *
     * @see com.diviso.graeshoppe.repository.search.AboutSearchRepositoryMockConfiguration
     */
    @Autowired
    private AboutSearchRepository mockAboutSearchRepository;

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

    private MockMvc restAboutMockMvc;

    private About about;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AboutResource aboutResource = new AboutResource(aboutService);
        this.restAboutMockMvc = MockMvcBuilders.standaloneSetup(aboutResource)
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
    public static About createEntity(EntityManager em) {
        About about = new About()
            .description(DEFAULT_DESCRIPTION)
            .supportMail(DEFAULT_SUPPORT_MAIL)
            .supportPhone(DEFAULT_SUPPORT_PHONE)
            .addOn1(DEFAULT_ADD_ON_1)
            .addOn2(DEFAULT_ADD_ON_2)
            .addOn3(DEFAULT_ADD_ON_3);
        return about;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static About createUpdatedEntity(EntityManager em) {
        About about = new About()
            .description(UPDATED_DESCRIPTION)
            .supportMail(UPDATED_SUPPORT_MAIL)
            .supportPhone(UPDATED_SUPPORT_PHONE)
            .addOn1(UPDATED_ADD_ON_1)
            .addOn2(UPDATED_ADD_ON_2)
            .addOn3(UPDATED_ADD_ON_3);
        return about;
    }

    @BeforeEach
    public void initTest() {
        about = createEntity(em);
    }

    @Test
    @Transactional
    public void createAbout() throws Exception {
        int databaseSizeBeforeCreate = aboutRepository.findAll().size();

        // Create the About
        AboutDTO aboutDTO = aboutMapper.toDto(about);
        restAboutMockMvc.perform(post("/api/abouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aboutDTO)))
            .andExpect(status().isCreated());

        // Validate the About in the database
        List<About> aboutList = aboutRepository.findAll();
        assertThat(aboutList).hasSize(databaseSizeBeforeCreate + 1);
        About testAbout = aboutList.get(aboutList.size() - 1);
        assertThat(testAbout.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAbout.getSupportMail()).isEqualTo(DEFAULT_SUPPORT_MAIL);
        assertThat(testAbout.getSupportPhone()).isEqualTo(DEFAULT_SUPPORT_PHONE);
        assertThat(testAbout.getAddOn1()).isEqualTo(DEFAULT_ADD_ON_1);
        assertThat(testAbout.getAddOn2()).isEqualTo(DEFAULT_ADD_ON_2);
        assertThat(testAbout.getAddOn3()).isEqualTo(DEFAULT_ADD_ON_3);

        // Validate the About in Elasticsearch
        verify(mockAboutSearchRepository, times(1)).save(testAbout);
    }

    @Test
    @Transactional
    public void createAboutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aboutRepository.findAll().size();

        // Create the About with an existing ID
        about.setId(1L);
        AboutDTO aboutDTO = aboutMapper.toDto(about);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAboutMockMvc.perform(post("/api/abouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aboutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the About in the database
        List<About> aboutList = aboutRepository.findAll();
        assertThat(aboutList).hasSize(databaseSizeBeforeCreate);

        // Validate the About in Elasticsearch
        verify(mockAboutSearchRepository, times(0)).save(about);
    }


    @Test
    @Transactional
    public void getAllAbouts() throws Exception {
        // Initialize the database
        aboutRepository.saveAndFlush(about);

        // Get all the aboutList
        restAboutMockMvc.perform(get("/api/abouts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(about.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].supportMail").value(hasItem(DEFAULT_SUPPORT_MAIL)))
            .andExpect(jsonPath("$.[*].supportPhone").value(hasItem(DEFAULT_SUPPORT_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].addOn1").value(hasItem(DEFAULT_ADD_ON_1)))
            .andExpect(jsonPath("$.[*].addOn2").value(hasItem(DEFAULT_ADD_ON_2)))
            .andExpect(jsonPath("$.[*].addOn3").value(hasItem(DEFAULT_ADD_ON_3)));
    }
    
    @Test
    @Transactional
    public void getAbout() throws Exception {
        // Initialize the database
        aboutRepository.saveAndFlush(about);

        // Get the about
        restAboutMockMvc.perform(get("/api/abouts/{id}", about.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(about.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.supportMail").value(DEFAULT_SUPPORT_MAIL))
            .andExpect(jsonPath("$.supportPhone").value(DEFAULT_SUPPORT_PHONE.intValue()))
            .andExpect(jsonPath("$.addOn1").value(DEFAULT_ADD_ON_1))
            .andExpect(jsonPath("$.addOn2").value(DEFAULT_ADD_ON_2))
            .andExpect(jsonPath("$.addOn3").value(DEFAULT_ADD_ON_3));
    }

    @Test
    @Transactional
    public void getNonExistingAbout() throws Exception {
        // Get the about
        restAboutMockMvc.perform(get("/api/abouts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbout() throws Exception {
        // Initialize the database
        aboutRepository.saveAndFlush(about);

        int databaseSizeBeforeUpdate = aboutRepository.findAll().size();

        // Update the about
        About updatedAbout = aboutRepository.findById(about.getId()).get();
        // Disconnect from session so that the updates on updatedAbout are not directly saved in db
        em.detach(updatedAbout);
        updatedAbout
            .description(UPDATED_DESCRIPTION)
            .supportMail(UPDATED_SUPPORT_MAIL)
            .supportPhone(UPDATED_SUPPORT_PHONE)
            .addOn1(UPDATED_ADD_ON_1)
            .addOn2(UPDATED_ADD_ON_2)
            .addOn3(UPDATED_ADD_ON_3);
        AboutDTO aboutDTO = aboutMapper.toDto(updatedAbout);

        restAboutMockMvc.perform(put("/api/abouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aboutDTO)))
            .andExpect(status().isOk());

        // Validate the About in the database
        List<About> aboutList = aboutRepository.findAll();
        assertThat(aboutList).hasSize(databaseSizeBeforeUpdate);
        About testAbout = aboutList.get(aboutList.size() - 1);
        assertThat(testAbout.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAbout.getSupportMail()).isEqualTo(UPDATED_SUPPORT_MAIL);
        assertThat(testAbout.getSupportPhone()).isEqualTo(UPDATED_SUPPORT_PHONE);
        assertThat(testAbout.getAddOn1()).isEqualTo(UPDATED_ADD_ON_1);
        assertThat(testAbout.getAddOn2()).isEqualTo(UPDATED_ADD_ON_2);
        assertThat(testAbout.getAddOn3()).isEqualTo(UPDATED_ADD_ON_3);

        // Validate the About in Elasticsearch
        verify(mockAboutSearchRepository, times(1)).save(testAbout);
    }

    @Test
    @Transactional
    public void updateNonExistingAbout() throws Exception {
        int databaseSizeBeforeUpdate = aboutRepository.findAll().size();

        // Create the About
        AboutDTO aboutDTO = aboutMapper.toDto(about);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAboutMockMvc.perform(put("/api/abouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aboutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the About in the database
        List<About> aboutList = aboutRepository.findAll();
        assertThat(aboutList).hasSize(databaseSizeBeforeUpdate);

        // Validate the About in Elasticsearch
        verify(mockAboutSearchRepository, times(0)).save(about);
    }

    @Test
    @Transactional
    public void deleteAbout() throws Exception {
        // Initialize the database
        aboutRepository.saveAndFlush(about);

        int databaseSizeBeforeDelete = aboutRepository.findAll().size();

        // Delete the about
        restAboutMockMvc.perform(delete("/api/abouts/{id}", about.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<About> aboutList = aboutRepository.findAll();
        assertThat(aboutList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the About in Elasticsearch
        verify(mockAboutSearchRepository, times(1)).deleteById(about.getId());
    }

    @Test
    @Transactional
    public void searchAbout() throws Exception {
        // Initialize the database
        aboutRepository.saveAndFlush(about);
        when(mockAboutSearchRepository.search(queryStringQuery("id:" + about.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(about), PageRequest.of(0, 1), 1));
        // Search the about
        restAboutMockMvc.perform(get("/api/_search/abouts?query=id:" + about.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(about.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].supportMail").value(hasItem(DEFAULT_SUPPORT_MAIL)))
            .andExpect(jsonPath("$.[*].supportPhone").value(hasItem(DEFAULT_SUPPORT_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].addOn1").value(hasItem(DEFAULT_ADD_ON_1)))
            .andExpect(jsonPath("$.[*].addOn2").value(hasItem(DEFAULT_ADD_ON_2)))
            .andExpect(jsonPath("$.[*].addOn3").value(hasItem(DEFAULT_ADD_ON_3)));
    }
}
