package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.IjSchoolManagerAdministrationServiceApp;
import com.ijrobotics.ijschoolmanageradministrationservice.config.TestSecurityConfiguration;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.ExculpatoryAttachments;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.ExculpatoryAttachmentsRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.ExculpatoryAttachmentsService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ExculpatoryAttachmentsDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.ExculpatoryAttachmentsMapper;
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
 * Integration tests for the {@link ExculpatoryAttachmentsResource} REST controller.
 */
@SpringBootTest(classes = {IjSchoolManagerAdministrationServiceApp.class, TestSecurityConfiguration.class})
public class ExculpatoryAttachmentsResourceIT {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_SIZE = 1L;
    private static final Long UPDATED_SIZE = 2L;

    private static final String DEFAULT_MIME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MIME_TYPE = "BBBBBBBBBB";

    @Autowired
    private ExculpatoryAttachmentsRepository exculpatoryAttachmentsRepository;

    @Autowired
    private ExculpatoryAttachmentsMapper exculpatoryAttachmentsMapper;

    @Autowired
    private ExculpatoryAttachmentsService exculpatoryAttachmentsService;

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

    private MockMvc restExculpatoryAttachmentsMockMvc;

    private ExculpatoryAttachments exculpatoryAttachments;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExculpatoryAttachmentsResource exculpatoryAttachmentsResource = new ExculpatoryAttachmentsResource(exculpatoryAttachmentsService);
        this.restExculpatoryAttachmentsMockMvc = MockMvcBuilders.standaloneSetup(exculpatoryAttachmentsResource)
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
    public static ExculpatoryAttachments createEntity(EntityManager em) {
        ExculpatoryAttachments exculpatoryAttachments = new ExculpatoryAttachments()
            .creationDate(DEFAULT_CREATION_DATE)
            .title(DEFAULT_TITLE)
            .size(DEFAULT_SIZE)
            .mimeType(DEFAULT_MIME_TYPE);
        return exculpatoryAttachments;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExculpatoryAttachments createUpdatedEntity(EntityManager em) {
        ExculpatoryAttachments exculpatoryAttachments = new ExculpatoryAttachments()
            .creationDate(UPDATED_CREATION_DATE)
            .title(UPDATED_TITLE)
            .size(UPDATED_SIZE)
            .mimeType(UPDATED_MIME_TYPE);
        return exculpatoryAttachments;
    }

    @BeforeEach
    public void initTest() {
        exculpatoryAttachments = createEntity(em);
    }

    @Test
    @Transactional
    public void createExculpatoryAttachments() throws Exception {
        int databaseSizeBeforeCreate = exculpatoryAttachmentsRepository.findAll().size();

        // Create the ExculpatoryAttachments
        ExculpatoryAttachmentsDTO exculpatoryAttachmentsDTO = exculpatoryAttachmentsMapper.toDto(exculpatoryAttachments);
        restExculpatoryAttachmentsMockMvc.perform(post("/api/exculpatory-attachments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exculpatoryAttachmentsDTO)))
            .andExpect(status().isCreated());

        // Validate the ExculpatoryAttachments in the database
        List<ExculpatoryAttachments> exculpatoryAttachmentsList = exculpatoryAttachmentsRepository.findAll();
        assertThat(exculpatoryAttachmentsList).hasSize(databaseSizeBeforeCreate + 1);
        ExculpatoryAttachments testExculpatoryAttachments = exculpatoryAttachmentsList.get(exculpatoryAttachmentsList.size() - 1);
        assertThat(testExculpatoryAttachments.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testExculpatoryAttachments.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testExculpatoryAttachments.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testExculpatoryAttachments.getMimeType()).isEqualTo(DEFAULT_MIME_TYPE);
    }

    @Test
    @Transactional
    public void createExculpatoryAttachmentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exculpatoryAttachmentsRepository.findAll().size();

        // Create the ExculpatoryAttachments with an existing ID
        exculpatoryAttachments.setId(1L);
        ExculpatoryAttachmentsDTO exculpatoryAttachmentsDTO = exculpatoryAttachmentsMapper.toDto(exculpatoryAttachments);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExculpatoryAttachmentsMockMvc.perform(post("/api/exculpatory-attachments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exculpatoryAttachmentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExculpatoryAttachments in the database
        List<ExculpatoryAttachments> exculpatoryAttachmentsList = exculpatoryAttachmentsRepository.findAll();
        assertThat(exculpatoryAttachmentsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExculpatoryAttachments() throws Exception {
        // Initialize the database
        exculpatoryAttachmentsRepository.saveAndFlush(exculpatoryAttachments);

        // Get all the exculpatoryAttachmentsList
        restExculpatoryAttachmentsMockMvc.perform(get("/api/exculpatory-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exculpatoryAttachments.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE)));
    }
    
    @Test
    @Transactional
    public void getExculpatoryAttachments() throws Exception {
        // Initialize the database
        exculpatoryAttachmentsRepository.saveAndFlush(exculpatoryAttachments);

        // Get the exculpatoryAttachments
        restExculpatoryAttachmentsMockMvc.perform(get("/api/exculpatory-attachments/{id}", exculpatoryAttachments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exculpatoryAttachments.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.intValue()))
            .andExpect(jsonPath("$.mimeType").value(DEFAULT_MIME_TYPE));
    }

    @Test
    @Transactional
    public void getNonExistingExculpatoryAttachments() throws Exception {
        // Get the exculpatoryAttachments
        restExculpatoryAttachmentsMockMvc.perform(get("/api/exculpatory-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExculpatoryAttachments() throws Exception {
        // Initialize the database
        exculpatoryAttachmentsRepository.saveAndFlush(exculpatoryAttachments);

        int databaseSizeBeforeUpdate = exculpatoryAttachmentsRepository.findAll().size();

        // Update the exculpatoryAttachments
        ExculpatoryAttachments updatedExculpatoryAttachments = exculpatoryAttachmentsRepository.findById(exculpatoryAttachments.getId()).get();
        // Disconnect from session so that the updates on updatedExculpatoryAttachments are not directly saved in db
        em.detach(updatedExculpatoryAttachments);
        updatedExculpatoryAttachments
            .creationDate(UPDATED_CREATION_DATE)
            .title(UPDATED_TITLE)
            .size(UPDATED_SIZE)
            .mimeType(UPDATED_MIME_TYPE);
        ExculpatoryAttachmentsDTO exculpatoryAttachmentsDTO = exculpatoryAttachmentsMapper.toDto(updatedExculpatoryAttachments);

        restExculpatoryAttachmentsMockMvc.perform(put("/api/exculpatory-attachments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exculpatoryAttachmentsDTO)))
            .andExpect(status().isOk());

        // Validate the ExculpatoryAttachments in the database
        List<ExculpatoryAttachments> exculpatoryAttachmentsList = exculpatoryAttachmentsRepository.findAll();
        assertThat(exculpatoryAttachmentsList).hasSize(databaseSizeBeforeUpdate);
        ExculpatoryAttachments testExculpatoryAttachments = exculpatoryAttachmentsList.get(exculpatoryAttachmentsList.size() - 1);
        assertThat(testExculpatoryAttachments.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testExculpatoryAttachments.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testExculpatoryAttachments.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testExculpatoryAttachments.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingExculpatoryAttachments() throws Exception {
        int databaseSizeBeforeUpdate = exculpatoryAttachmentsRepository.findAll().size();

        // Create the ExculpatoryAttachments
        ExculpatoryAttachmentsDTO exculpatoryAttachmentsDTO = exculpatoryAttachmentsMapper.toDto(exculpatoryAttachments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExculpatoryAttachmentsMockMvc.perform(put("/api/exculpatory-attachments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exculpatoryAttachmentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExculpatoryAttachments in the database
        List<ExculpatoryAttachments> exculpatoryAttachmentsList = exculpatoryAttachmentsRepository.findAll();
        assertThat(exculpatoryAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExculpatoryAttachments() throws Exception {
        // Initialize the database
        exculpatoryAttachmentsRepository.saveAndFlush(exculpatoryAttachments);

        int databaseSizeBeforeDelete = exculpatoryAttachmentsRepository.findAll().size();

        // Delete the exculpatoryAttachments
        restExculpatoryAttachmentsMockMvc.perform(delete("/api/exculpatory-attachments/{id}", exculpatoryAttachments.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExculpatoryAttachments> exculpatoryAttachmentsList = exculpatoryAttachmentsRepository.findAll();
        assertThat(exculpatoryAttachmentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
