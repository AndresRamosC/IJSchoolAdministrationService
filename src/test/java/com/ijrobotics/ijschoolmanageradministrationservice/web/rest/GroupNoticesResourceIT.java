package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.IjSchoolManagerAdministrationServiceApp;
import com.ijrobotics.ijschoolmanageradministrationservice.config.TestSecurityConfiguration;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.GroupNotices;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.GroupNoticesRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.GroupNoticesService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GroupNoticesDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.GroupNoticesMapper;
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
 * Integration tests for the {@link GroupNoticesResource} REST controller.
 */
@SpringBootTest(classes = {IjSchoolManagerAdministrationServiceApp.class, TestSecurityConfiguration.class})
public class GroupNoticesResourceIT {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private GroupNoticesRepository groupNoticesRepository;

    @Autowired
    private GroupNoticesMapper groupNoticesMapper;

    @Autowired
    private GroupNoticesService groupNoticesService;

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

    private MockMvc restGroupNoticesMockMvc;

    private GroupNotices groupNotices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GroupNoticesResource groupNoticesResource = new GroupNoticesResource(groupNoticesService);
        this.restGroupNoticesMockMvc = MockMvcBuilders.standaloneSetup(groupNoticesResource)
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
    public static GroupNotices createEntity(EntityManager em) {
        GroupNotices groupNotices = new GroupNotices()
            .creationDate(DEFAULT_CREATION_DATE)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION);
        return groupNotices;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupNotices createUpdatedEntity(EntityManager em) {
        GroupNotices groupNotices = new GroupNotices()
            .creationDate(UPDATED_CREATION_DATE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION);
        return groupNotices;
    }

    @BeforeEach
    public void initTest() {
        groupNotices = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupNotices() throws Exception {
        int databaseSizeBeforeCreate = groupNoticesRepository.findAll().size();

        // Create the GroupNotices
        GroupNoticesDTO groupNoticesDTO = groupNoticesMapper.toDto(groupNotices);
        restGroupNoticesMockMvc.perform(post("/api/group-notices")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupNoticesDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupNotices in the database
        List<GroupNotices> groupNoticesList = groupNoticesRepository.findAll();
        assertThat(groupNoticesList).hasSize(databaseSizeBeforeCreate + 1);
        GroupNotices testGroupNotices = groupNoticesList.get(groupNoticesList.size() - 1);
        assertThat(testGroupNotices.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testGroupNotices.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testGroupNotices.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createGroupNoticesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupNoticesRepository.findAll().size();

        // Create the GroupNotices with an existing ID
        groupNotices.setId(1L);
        GroupNoticesDTO groupNoticesDTO = groupNoticesMapper.toDto(groupNotices);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupNoticesMockMvc.perform(post("/api/group-notices")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupNoticesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupNotices in the database
        List<GroupNotices> groupNoticesList = groupNoticesRepository.findAll();
        assertThat(groupNoticesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGroupNotices() throws Exception {
        // Initialize the database
        groupNoticesRepository.saveAndFlush(groupNotices);

        // Get all the groupNoticesList
        restGroupNoticesMockMvc.perform(get("/api/group-notices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupNotices.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getGroupNotices() throws Exception {
        // Initialize the database
        groupNoticesRepository.saveAndFlush(groupNotices);

        // Get the groupNotices
        restGroupNoticesMockMvc.perform(get("/api/group-notices/{id}", groupNotices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupNotices.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingGroupNotices() throws Exception {
        // Get the groupNotices
        restGroupNoticesMockMvc.perform(get("/api/group-notices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupNotices() throws Exception {
        // Initialize the database
        groupNoticesRepository.saveAndFlush(groupNotices);

        int databaseSizeBeforeUpdate = groupNoticesRepository.findAll().size();

        // Update the groupNotices
        GroupNotices updatedGroupNotices = groupNoticesRepository.findById(groupNotices.getId()).get();
        // Disconnect from session so that the updates on updatedGroupNotices are not directly saved in db
        em.detach(updatedGroupNotices);
        updatedGroupNotices
            .creationDate(UPDATED_CREATION_DATE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION);
        GroupNoticesDTO groupNoticesDTO = groupNoticesMapper.toDto(updatedGroupNotices);

        restGroupNoticesMockMvc.perform(put("/api/group-notices")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupNoticesDTO)))
            .andExpect(status().isOk());

        // Validate the GroupNotices in the database
        List<GroupNotices> groupNoticesList = groupNoticesRepository.findAll();
        assertThat(groupNoticesList).hasSize(databaseSizeBeforeUpdate);
        GroupNotices testGroupNotices = groupNoticesList.get(groupNoticesList.size() - 1);
        assertThat(testGroupNotices.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testGroupNotices.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGroupNotices.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupNotices() throws Exception {
        int databaseSizeBeforeUpdate = groupNoticesRepository.findAll().size();

        // Create the GroupNotices
        GroupNoticesDTO groupNoticesDTO = groupNoticesMapper.toDto(groupNotices);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupNoticesMockMvc.perform(put("/api/group-notices")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupNoticesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupNotices in the database
        List<GroupNotices> groupNoticesList = groupNoticesRepository.findAll();
        assertThat(groupNoticesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupNotices() throws Exception {
        // Initialize the database
        groupNoticesRepository.saveAndFlush(groupNotices);

        int databaseSizeBeforeDelete = groupNoticesRepository.findAll().size();

        // Delete the groupNotices
        restGroupNoticesMockMvc.perform(delete("/api/group-notices/{id}", groupNotices.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupNotices> groupNoticesList = groupNoticesRepository.findAll();
        assertThat(groupNoticesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
