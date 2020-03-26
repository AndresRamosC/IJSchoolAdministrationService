package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.IjSchoolManagerAdministrationServiceApp;
import com.ijrobotics.ijschoolmanageradministrationservice.config.TestSecurityConfiguration;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.ExculpatoryAttContent;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.ExculpatoryAttContentRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.ExculpatoryAttContentService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ExculpatoryAttContentDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.ExculpatoryAttContentMapper;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil.sameInstant;
import static com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ExculpatoryAttContentResource} REST controller.
 */
@SpringBootTest(classes = {IjSchoolManagerAdministrationServiceApp.class, TestSecurityConfiguration.class})
public class ExculpatoryAttContentResourceIT {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final byte[] DEFAULT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_TYPE = "image/png";

    @Autowired
    private ExculpatoryAttContentRepository exculpatoryAttContentRepository;

    @Autowired
    private ExculpatoryAttContentMapper exculpatoryAttContentMapper;

    @Autowired
    private ExculpatoryAttContentService exculpatoryAttContentService;

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

    private MockMvc restExculpatoryAttContentMockMvc;

    private ExculpatoryAttContent exculpatoryAttContent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExculpatoryAttContentResource exculpatoryAttContentResource = new ExculpatoryAttContentResource(exculpatoryAttContentService);
        this.restExculpatoryAttContentMockMvc = MockMvcBuilders.standaloneSetup(exculpatoryAttContentResource)
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
    public static ExculpatoryAttContent createEntity(EntityManager em) {
        ExculpatoryAttContent exculpatoryAttContent = new ExculpatoryAttContent()
            .creationDate(DEFAULT_CREATION_DATE)
            .data(DEFAULT_DATA)
            .dataContentType(DEFAULT_DATA_CONTENT_TYPE);
        return exculpatoryAttContent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExculpatoryAttContent createUpdatedEntity(EntityManager em) {
        ExculpatoryAttContent exculpatoryAttContent = new ExculpatoryAttContent()
            .creationDate(UPDATED_CREATION_DATE)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE);
        return exculpatoryAttContent;
    }

    @BeforeEach
    public void initTest() {
        exculpatoryAttContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createExculpatoryAttContent() throws Exception {
        int databaseSizeBeforeCreate = exculpatoryAttContentRepository.findAll().size();

        // Create the ExculpatoryAttContent
        ExculpatoryAttContentDTO exculpatoryAttContentDTO = exculpatoryAttContentMapper.toDto(exculpatoryAttContent);
        restExculpatoryAttContentMockMvc.perform(post("/api/exculpatory-att-contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exculpatoryAttContentDTO)))
            .andExpect(status().isCreated());

        // Validate the ExculpatoryAttContent in the database
        List<ExculpatoryAttContent> exculpatoryAttContentList = exculpatoryAttContentRepository.findAll();
        assertThat(exculpatoryAttContentList).hasSize(databaseSizeBeforeCreate + 1);
        ExculpatoryAttContent testExculpatoryAttContent = exculpatoryAttContentList.get(exculpatoryAttContentList.size() - 1);
        assertThat(testExculpatoryAttContent.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testExculpatoryAttContent.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testExculpatoryAttContent.getDataContentType()).isEqualTo(DEFAULT_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createExculpatoryAttContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exculpatoryAttContentRepository.findAll().size();

        // Create the ExculpatoryAttContent with an existing ID
        exculpatoryAttContent.setId(1L);
        ExculpatoryAttContentDTO exculpatoryAttContentDTO = exculpatoryAttContentMapper.toDto(exculpatoryAttContent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExculpatoryAttContentMockMvc.perform(post("/api/exculpatory-att-contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exculpatoryAttContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExculpatoryAttContent in the database
        List<ExculpatoryAttContent> exculpatoryAttContentList = exculpatoryAttContentRepository.findAll();
        assertThat(exculpatoryAttContentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExculpatoryAttContents() throws Exception {
        // Initialize the database
        exculpatoryAttContentRepository.saveAndFlush(exculpatoryAttContent);

        // Get all the exculpatoryAttContentList
        restExculpatoryAttContentMockMvc.perform(get("/api/exculpatory-att-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exculpatoryAttContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA))));
    }
    
    @Test
    @Transactional
    public void getExculpatoryAttContent() throws Exception {
        // Initialize the database
        exculpatoryAttContentRepository.saveAndFlush(exculpatoryAttContent);

        // Get the exculpatoryAttContent
        restExculpatoryAttContentMockMvc.perform(get("/api/exculpatory-att-contents/{id}", exculpatoryAttContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exculpatoryAttContent.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.dataContentType").value(DEFAULT_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.data").value(Base64Utils.encodeToString(DEFAULT_DATA)));
    }

    @Test
    @Transactional
    public void getNonExistingExculpatoryAttContent() throws Exception {
        // Get the exculpatoryAttContent
        restExculpatoryAttContentMockMvc.perform(get("/api/exculpatory-att-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExculpatoryAttContent() throws Exception {
        // Initialize the database
        exculpatoryAttContentRepository.saveAndFlush(exculpatoryAttContent);

        int databaseSizeBeforeUpdate = exculpatoryAttContentRepository.findAll().size();

        // Update the exculpatoryAttContent
        ExculpatoryAttContent updatedExculpatoryAttContent = exculpatoryAttContentRepository.findById(exculpatoryAttContent.getId()).get();
        // Disconnect from session so that the updates on updatedExculpatoryAttContent are not directly saved in db
        em.detach(updatedExculpatoryAttContent);
        updatedExculpatoryAttContent
            .creationDate(UPDATED_CREATION_DATE)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE);
        ExculpatoryAttContentDTO exculpatoryAttContentDTO = exculpatoryAttContentMapper.toDto(updatedExculpatoryAttContent);

        restExculpatoryAttContentMockMvc.perform(put("/api/exculpatory-att-contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exculpatoryAttContentDTO)))
            .andExpect(status().isOk());

        // Validate the ExculpatoryAttContent in the database
        List<ExculpatoryAttContent> exculpatoryAttContentList = exculpatoryAttContentRepository.findAll();
        assertThat(exculpatoryAttContentList).hasSize(databaseSizeBeforeUpdate);
        ExculpatoryAttContent testExculpatoryAttContent = exculpatoryAttContentList.get(exculpatoryAttContentList.size() - 1);
        assertThat(testExculpatoryAttContent.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testExculpatoryAttContent.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testExculpatoryAttContent.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingExculpatoryAttContent() throws Exception {
        int databaseSizeBeforeUpdate = exculpatoryAttContentRepository.findAll().size();

        // Create the ExculpatoryAttContent
        ExculpatoryAttContentDTO exculpatoryAttContentDTO = exculpatoryAttContentMapper.toDto(exculpatoryAttContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExculpatoryAttContentMockMvc.perform(put("/api/exculpatory-att-contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exculpatoryAttContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExculpatoryAttContent in the database
        List<ExculpatoryAttContent> exculpatoryAttContentList = exculpatoryAttContentRepository.findAll();
        assertThat(exculpatoryAttContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExculpatoryAttContent() throws Exception {
        // Initialize the database
        exculpatoryAttContentRepository.saveAndFlush(exculpatoryAttContent);

        int databaseSizeBeforeDelete = exculpatoryAttContentRepository.findAll().size();

        // Delete the exculpatoryAttContent
        restExculpatoryAttContentMockMvc.perform(delete("/api/exculpatory-att-contents/{id}", exculpatoryAttContent.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExculpatoryAttContent> exculpatoryAttContentList = exculpatoryAttContentRepository.findAll();
        assertThat(exculpatoryAttContentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
