package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.IjSchoolManagerAdministrationServiceApp;
import com.ijrobotics.ijschoolmanageradministrationservice.config.TestSecurityConfiguration;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.Attachments;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.AttachmentsRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.AttachmentsService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AttachmentsDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AttachmentsMapper;
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
 * Integration tests for the {@link AttachmentsResource} REST controller.
 */
@SpringBootTest(classes = {IjSchoolManagerAdministrationServiceApp.class, TestSecurityConfiguration.class})
public class AttachmentsResourceIT {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_SIZE = 1L;
    private static final Long UPDATED_SIZE = 2L;

    private static final String DEFAULT_MIME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MIME_TYPE = "BBBBBBBBBB";

    @Autowired
    private AttachmentsRepository attachmentsRepository;

    @Autowired
    private AttachmentsMapper attachmentsMapper;

    @Autowired
    private AttachmentsService attachmentsService;

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

    private MockMvc restAttachmentsMockMvc;

    private Attachments attachments;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AttachmentsResource attachmentsResource = new AttachmentsResource(attachmentsService,attachmentsMapper);
        this.restAttachmentsMockMvc = MockMvcBuilders.standaloneSetup(attachmentsResource)
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
    public static Attachments createEntity(EntityManager em) {
        Attachments attachments = new Attachments()
            .creationDate(DEFAULT_CREATION_DATE)
            .title(DEFAULT_TITLE)
            .size(DEFAULT_SIZE)
            .mimeType(DEFAULT_MIME_TYPE);
        return attachments;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attachments createUpdatedEntity(EntityManager em) {
        Attachments attachments = new Attachments()
            .creationDate(UPDATED_CREATION_DATE)
            .title(UPDATED_TITLE)
            .size(UPDATED_SIZE)
            .mimeType(UPDATED_MIME_TYPE);
        return attachments;
    }

    @BeforeEach
    public void initTest() {
        attachments = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttachments() throws Exception {
        int databaseSizeBeforeCreate = attachmentsRepository.findAll().size();

        // Create the Attachments
        AttachmentsDTO attachmentsDTO = attachmentsMapper.toDto(attachments);
        restAttachmentsMockMvc.perform(post("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Attachments in the database
        List<Attachments> attachmentsList = attachmentsRepository.findAll();
        assertThat(attachmentsList).hasSize(databaseSizeBeforeCreate + 1);
        Attachments testAttachments = attachmentsList.get(attachmentsList.size() - 1);
        assertThat(testAttachments.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAttachments.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAttachments.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testAttachments.getMimeType()).isEqualTo(DEFAULT_MIME_TYPE);
    }

    @Test
    @Transactional
    public void createAttachmentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attachmentsRepository.findAll().size();

        // Create the Attachments with an existing ID
        attachments.setId(1L);
        AttachmentsDTO attachmentsDTO = attachmentsMapper.toDto(attachments);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachmentsMockMvc.perform(post("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attachments in the database
        List<Attachments> attachmentsList = attachmentsRepository.findAll();
        assertThat(attachmentsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAttachments() throws Exception {
        // Initialize the database
        attachmentsRepository.saveAndFlush(attachments);

        // Get all the attachmentsList
        restAttachmentsMockMvc.perform(get("/api/attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachments.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE)));
    }

    @Test
    @Transactional
    public void getAttachments() throws Exception {
        // Initialize the database
        attachmentsRepository.saveAndFlush(attachments);

        // Get the attachments
        restAttachmentsMockMvc.perform(get("/api/attachments/{id}", attachments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attachments.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.intValue()))
            .andExpect(jsonPath("$.mimeType").value(DEFAULT_MIME_TYPE));
    }

    @Test
    @Transactional
    public void getNonExistingAttachments() throws Exception {
        // Get the attachments
        restAttachmentsMockMvc.perform(get("/api/attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttachments() throws Exception {
        // Initialize the database
        attachmentsRepository.saveAndFlush(attachments);

        int databaseSizeBeforeUpdate = attachmentsRepository.findAll().size();

        // Update the attachments
        Attachments updatedAttachments = attachmentsRepository.findById(attachments.getId()).get();
        // Disconnect from session so that the updates on updatedAttachments are not directly saved in db
        em.detach(updatedAttachments);
        updatedAttachments
            .creationDate(UPDATED_CREATION_DATE)
            .title(UPDATED_TITLE)
            .size(UPDATED_SIZE)
            .mimeType(UPDATED_MIME_TYPE);
        AttachmentsDTO attachmentsDTO = attachmentsMapper.toDto(updatedAttachments);

        restAttachmentsMockMvc.perform(put("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentsDTO)))
            .andExpect(status().isOk());

        // Validate the Attachments in the database
        List<Attachments> attachmentsList = attachmentsRepository.findAll();
        assertThat(attachmentsList).hasSize(databaseSizeBeforeUpdate);
        Attachments testAttachments = attachmentsList.get(attachmentsList.size() - 1);
        assertThat(testAttachments.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAttachments.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAttachments.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testAttachments.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAttachments() throws Exception {
        int databaseSizeBeforeUpdate = attachmentsRepository.findAll().size();

        // Create the Attachments
        AttachmentsDTO attachmentsDTO = attachmentsMapper.toDto(attachments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachmentsMockMvc.perform(put("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attachments in the database
        List<Attachments> attachmentsList = attachmentsRepository.findAll();
        assertThat(attachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttachments() throws Exception {
        // Initialize the database
        attachmentsRepository.saveAndFlush(attachments);

        int databaseSizeBeforeDelete = attachmentsRepository.findAll().size();

        // Delete the attachments
        restAttachmentsMockMvc.perform(delete("/api/attachments/{id}", attachments.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attachments> attachmentsList = attachmentsRepository.findAll();
        assertThat(attachmentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
