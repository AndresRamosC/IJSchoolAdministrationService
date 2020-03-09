package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.IjSchoolManagerAdministrationServiceApp;
import com.ijrobotics.ijschoolmanageradministrationservice.config.TestSecurityConfiguration;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.AttachmentsContent;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.AttachmentsContentRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.AttachmentsContentService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AttachmentsContentDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AttachmentsContentMapper;
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
 * Integration tests for the {@link AttachmentsContentResource} REST controller.
 */
@SpringBootTest(classes = {IjSchoolManagerAdministrationServiceApp.class, TestSecurityConfiguration.class})
public class AttachmentsContentResourceIT {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final byte[] DEFAULT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_TYPE = "image/png";

    @Autowired
    private AttachmentsContentRepository attachmentsContentRepository;

    @Autowired
    private AttachmentsContentMapper attachmentsContentMapper;

    @Autowired
    private AttachmentsContentService attachmentsContentService;

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

    private MockMvc restAttachmentsContentMockMvc;

    private AttachmentsContent attachmentsContent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AttachmentsContentResource attachmentsContentResource = new AttachmentsContentResource(attachmentsContentService);
        this.restAttachmentsContentMockMvc = MockMvcBuilders.standaloneSetup(attachmentsContentResource)
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
    public static AttachmentsContent createEntity(EntityManager em) {
        AttachmentsContent attachmentsContent = new AttachmentsContent()
            .creationDate(DEFAULT_CREATION_DATE)
            .data(DEFAULT_DATA)
            .dataContentType(DEFAULT_DATA_CONTENT_TYPE);
        return attachmentsContent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttachmentsContent createUpdatedEntity(EntityManager em) {
        AttachmentsContent attachmentsContent = new AttachmentsContent()
            .creationDate(UPDATED_CREATION_DATE)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE);
        return attachmentsContent;
    }

    @BeforeEach
    public void initTest() {
        attachmentsContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttachmentsContent() throws Exception {
        int databaseSizeBeforeCreate = attachmentsContentRepository.findAll().size();

        // Create the AttachmentsContent
        AttachmentsContentDTO attachmentsContentDTO = attachmentsContentMapper.toDto(attachmentsContent);
        restAttachmentsContentMockMvc.perform(post("/api/attachments-contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentsContentDTO)))
            .andExpect(status().isCreated());

        // Validate the AttachmentsContent in the database
        List<AttachmentsContent> attachmentsContentList = attachmentsContentRepository.findAll();
        assertThat(attachmentsContentList).hasSize(databaseSizeBeforeCreate + 1);
        AttachmentsContent testAttachmentsContent = attachmentsContentList.get(attachmentsContentList.size() - 1);
        assertThat(testAttachmentsContent.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAttachmentsContent.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testAttachmentsContent.getDataContentType()).isEqualTo(DEFAULT_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAttachmentsContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attachmentsContentRepository.findAll().size();

        // Create the AttachmentsContent with an existing ID
        attachmentsContent.setId(1L);
        AttachmentsContentDTO attachmentsContentDTO = attachmentsContentMapper.toDto(attachmentsContent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachmentsContentMockMvc.perform(post("/api/attachments-contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentsContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttachmentsContent in the database
        List<AttachmentsContent> attachmentsContentList = attachmentsContentRepository.findAll();
        assertThat(attachmentsContentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAttachmentsContents() throws Exception {
        // Initialize the database
        attachmentsContentRepository.saveAndFlush(attachmentsContent);

        // Get all the attachmentsContentList
        restAttachmentsContentMockMvc.perform(get("/api/attachments-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachmentsContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA))));
    }
    
    @Test
    @Transactional
    public void getAttachmentsContent() throws Exception {
        // Initialize the database
        attachmentsContentRepository.saveAndFlush(attachmentsContent);

        // Get the attachmentsContent
        restAttachmentsContentMockMvc.perform(get("/api/attachments-contents/{id}", attachmentsContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attachmentsContent.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.dataContentType").value(DEFAULT_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.data").value(Base64Utils.encodeToString(DEFAULT_DATA)));
    }

    @Test
    @Transactional
    public void getNonExistingAttachmentsContent() throws Exception {
        // Get the attachmentsContent
        restAttachmentsContentMockMvc.perform(get("/api/attachments-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttachmentsContent() throws Exception {
        // Initialize the database
        attachmentsContentRepository.saveAndFlush(attachmentsContent);

        int databaseSizeBeforeUpdate = attachmentsContentRepository.findAll().size();

        // Update the attachmentsContent
        AttachmentsContent updatedAttachmentsContent = attachmentsContentRepository.findById(attachmentsContent.getId()).get();
        // Disconnect from session so that the updates on updatedAttachmentsContent are not directly saved in db
        em.detach(updatedAttachmentsContent);
        updatedAttachmentsContent
            .creationDate(UPDATED_CREATION_DATE)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE);
        AttachmentsContentDTO attachmentsContentDTO = attachmentsContentMapper.toDto(updatedAttachmentsContent);

        restAttachmentsContentMockMvc.perform(put("/api/attachments-contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentsContentDTO)))
            .andExpect(status().isOk());

        // Validate the AttachmentsContent in the database
        List<AttachmentsContent> attachmentsContentList = attachmentsContentRepository.findAll();
        assertThat(attachmentsContentList).hasSize(databaseSizeBeforeUpdate);
        AttachmentsContent testAttachmentsContent = attachmentsContentList.get(attachmentsContentList.size() - 1);
        assertThat(testAttachmentsContent.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAttachmentsContent.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testAttachmentsContent.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAttachmentsContent() throws Exception {
        int databaseSizeBeforeUpdate = attachmentsContentRepository.findAll().size();

        // Create the AttachmentsContent
        AttachmentsContentDTO attachmentsContentDTO = attachmentsContentMapper.toDto(attachmentsContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachmentsContentMockMvc.perform(put("/api/attachments-contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentsContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttachmentsContent in the database
        List<AttachmentsContent> attachmentsContentList = attachmentsContentRepository.findAll();
        assertThat(attachmentsContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttachmentsContent() throws Exception {
        // Initialize the database
        attachmentsContentRepository.saveAndFlush(attachmentsContent);

        int databaseSizeBeforeDelete = attachmentsContentRepository.findAll().size();

        // Delete the attachmentsContent
        restAttachmentsContentMockMvc.perform(delete("/api/attachments-contents/{id}", attachmentsContent.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttachmentsContent> attachmentsContentList = attachmentsContentRepository.findAll();
        assertThat(attachmentsContentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
