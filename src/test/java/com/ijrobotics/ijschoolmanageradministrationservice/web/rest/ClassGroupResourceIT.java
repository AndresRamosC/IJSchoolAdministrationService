package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.IjSchoolManagerAdministrationServiceApp;
import com.ijrobotics.ijschoolmanageradministrationservice.config.TestSecurityConfiguration;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.ClassGroup;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.ClassGroupRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.ClassGroupService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassGroupDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.ClassGroupMapper;
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
 * Integration tests for the {@link ClassGroupResource} REST controller.
 */
@SpringBootTest(classes = {IjSchoolManagerAdministrationServiceApp.class, TestSecurityConfiguration.class})
public class ClassGroupResourceIT {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_GROUP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_HOUR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_HOUR = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_HOUR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_HOUR = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CLASS_ROOM = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_ROOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer UPDATED_SIZE = 2;

    @Autowired
    private ClassGroupRepository classGroupRepository;

    @Autowired
    private ClassGroupMapper classGroupMapper;

    @Autowired
    private ClassGroupService classGroupService;

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

    private MockMvc restClassGroupMockMvc;

    private ClassGroup classGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassGroupResource classGroupResource = new ClassGroupResource(classGroupService);
        this.restClassGroupMockMvc = MockMvcBuilders.standaloneSetup(classGroupResource)
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
    public static ClassGroup createEntity(EntityManager em) {
        ClassGroup classGroup = new ClassGroup()
            .creationDate(DEFAULT_CREATION_DATE)
            .groupCode(DEFAULT_GROUP_CODE)
            .startHour(DEFAULT_START_HOUR)
            .endHour(DEFAULT_END_HOUR)
            .classRoom(DEFAULT_CLASS_ROOM)
            .size(DEFAULT_SIZE);
        return classGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassGroup createUpdatedEntity(EntityManager em) {
        ClassGroup classGroup = new ClassGroup()
            .creationDate(UPDATED_CREATION_DATE)
            .groupCode(UPDATED_GROUP_CODE)
            .startHour(UPDATED_START_HOUR)
            .endHour(UPDATED_END_HOUR)
            .classRoom(UPDATED_CLASS_ROOM)
            .size(UPDATED_SIZE);
        return classGroup;
    }

    @BeforeEach
    public void initTest() {
        classGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassGroup() throws Exception {
        int databaseSizeBeforeCreate = classGroupRepository.findAll().size();

        // Create the ClassGroup
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(classGroup);
        restClassGroupMockMvc.perform(post("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassGroup in the database
        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ClassGroup testClassGroup = classGroupList.get(classGroupList.size() - 1);
        assertThat(testClassGroup.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testClassGroup.getGroupCode()).isEqualTo(DEFAULT_GROUP_CODE);
        assertThat(testClassGroup.getStartHour()).isEqualTo(DEFAULT_START_HOUR);
        assertThat(testClassGroup.getEndHour()).isEqualTo(DEFAULT_END_HOUR);
        assertThat(testClassGroup.getClassRoom()).isEqualTo(DEFAULT_CLASS_ROOM);
        assertThat(testClassGroup.getSize()).isEqualTo(DEFAULT_SIZE);
    }

    @Test
    @Transactional
    public void createClassGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classGroupRepository.findAll().size();

        // Create the ClassGroup with an existing ID
        classGroup.setId(1L);
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(classGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassGroupMockMvc.perform(post("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassGroup in the database
        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClassGroups() throws Exception {
        // Initialize the database
        classGroupRepository.saveAndFlush(classGroup);

        // Get all the classGroupList
        restClassGroupMockMvc.perform(get("/api/class-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].groupCode").value(hasItem(DEFAULT_GROUP_CODE)))
            .andExpect(jsonPath("$.[*].startHour").value(hasItem(sameInstant(DEFAULT_START_HOUR))))
            .andExpect(jsonPath("$.[*].endHour").value(hasItem(sameInstant(DEFAULT_END_HOUR))))
            .andExpect(jsonPath("$.[*].classRoom").value(hasItem(DEFAULT_CLASS_ROOM)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)));
    }
    
    @Test
    @Transactional
    public void getClassGroup() throws Exception {
        // Initialize the database
        classGroupRepository.saveAndFlush(classGroup);

        // Get the classGroup
        restClassGroupMockMvc.perform(get("/api/class-groups/{id}", classGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classGroup.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.groupCode").value(DEFAULT_GROUP_CODE))
            .andExpect(jsonPath("$.startHour").value(sameInstant(DEFAULT_START_HOUR)))
            .andExpect(jsonPath("$.endHour").value(sameInstant(DEFAULT_END_HOUR)))
            .andExpect(jsonPath("$.classRoom").value(DEFAULT_CLASS_ROOM))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE));
    }

    @Test
    @Transactional
    public void getNonExistingClassGroup() throws Exception {
        // Get the classGroup
        restClassGroupMockMvc.perform(get("/api/class-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassGroup() throws Exception {
        // Initialize the database
        classGroupRepository.saveAndFlush(classGroup);

        int databaseSizeBeforeUpdate = classGroupRepository.findAll().size();

        // Update the classGroup
        ClassGroup updatedClassGroup = classGroupRepository.findById(classGroup.getId()).get();
        // Disconnect from session so that the updates on updatedClassGroup are not directly saved in db
        em.detach(updatedClassGroup);
        updatedClassGroup
            .creationDate(UPDATED_CREATION_DATE)
            .groupCode(UPDATED_GROUP_CODE)
            .startHour(UPDATED_START_HOUR)
            .endHour(UPDATED_END_HOUR)
            .classRoom(UPDATED_CLASS_ROOM)
            .size(UPDATED_SIZE);
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(updatedClassGroup);

        restClassGroupMockMvc.perform(put("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isOk());

        // Validate the ClassGroup in the database
        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeUpdate);
        ClassGroup testClassGroup = classGroupList.get(classGroupList.size() - 1);
        assertThat(testClassGroup.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testClassGroup.getGroupCode()).isEqualTo(UPDATED_GROUP_CODE);
        assertThat(testClassGroup.getStartHour()).isEqualTo(UPDATED_START_HOUR);
        assertThat(testClassGroup.getEndHour()).isEqualTo(UPDATED_END_HOUR);
        assertThat(testClassGroup.getClassRoom()).isEqualTo(UPDATED_CLASS_ROOM);
        assertThat(testClassGroup.getSize()).isEqualTo(UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingClassGroup() throws Exception {
        int databaseSizeBeforeUpdate = classGroupRepository.findAll().size();

        // Create the ClassGroup
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(classGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassGroupMockMvc.perform(put("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassGroup in the database
        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassGroup() throws Exception {
        // Initialize the database
        classGroupRepository.saveAndFlush(classGroup);

        int databaseSizeBeforeDelete = classGroupRepository.findAll().size();

        // Delete the classGroup
        restClassGroupMockMvc.perform(delete("/api/class-groups/{id}", classGroup.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
