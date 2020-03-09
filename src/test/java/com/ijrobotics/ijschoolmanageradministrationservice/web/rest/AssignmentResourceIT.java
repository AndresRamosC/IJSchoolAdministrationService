package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.IjSchoolManagerAdministrationServiceApp;
import com.ijrobotics.ijschoolmanageradministrationservice.config.TestSecurityConfiguration;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.Assignment;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.AssignmentRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.AssignmentService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AssignmentDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AssignmentMapper;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AttachmentFileMapper;
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
 * Integration tests for the {@link AssignmentResource} REST controller.
 */
@SpringBootTest(classes = {IjSchoolManagerAdministrationServiceApp.class, TestSecurityConfiguration.class})
public class AssignmentResourceIT {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DUE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_DONE = false;
    private static final Boolean UPDATED_DONE = true;

    private static final Float DEFAULT_GRADE = 1F;
    private static final Float UPDATED_GRADE = 2F;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private AssignmentService assignmentService;

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

    @Autowired
    private AttachmentFileMapper attachmentFileMapper;

    private MockMvc restAssignmentMockMvc;

    private Assignment assignment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssignmentResource assignmentResource = new AssignmentResource(assignmentService,attachmentFileMapper,assignmentMapper);
        this.restAssignmentMockMvc = MockMvcBuilders.standaloneSetup(assignmentResource)
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
    public static Assignment createEntity(EntityManager em) {
        Assignment assignment = new Assignment()
            .creationDate(DEFAULT_CREATION_DATE)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .dueDate(DEFAULT_DUE_DATE)
            .done(DEFAULT_DONE)
            .grade(DEFAULT_GRADE);
        return assignment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assignment createUpdatedEntity(EntityManager em) {
        Assignment assignment = new Assignment()
            .creationDate(UPDATED_CREATION_DATE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .dueDate(UPDATED_DUE_DATE)
            .done(UPDATED_DONE)
            .grade(UPDATED_GRADE);
        return assignment;
    }

    @BeforeEach
    public void initTest() {
        assignment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssignment() throws Exception {
        int databaseSizeBeforeCreate = assignmentRepository.findAll().size();

        // Create the Assignment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);
        restAssignmentMockMvc.perform(post("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeCreate + 1);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAssignment.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAssignment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssignment.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testAssignment.isDone()).isEqualTo(DEFAULT_DONE);
        assertThat(testAssignment.getGrade()).isEqualTo(DEFAULT_GRADE);
    }

    @Test
    @Transactional
    public void createAssignmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assignmentRepository.findAll().size();

        // Create the Assignment with an existing ID
        assignment.setId(1L);
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssignmentMockMvc.perform(post("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAssignments() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        // Get all the assignmentList
        restAssignmentMockMvc.perform(get("/api/assignments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assignment.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(sameInstant(DEFAULT_DUE_DATE))))
            .andExpect(jsonPath("$.[*].done").value(hasItem(DEFAULT_DONE.booleanValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.doubleValue())));
    }

    @Test
    @Transactional
    public void getAssignment() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        // Get the assignment
        restAssignmentMockMvc.perform(get("/api/assignments/{id}", assignment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assignment.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dueDate").value(sameInstant(DEFAULT_DUE_DATE)))
            .andExpect(jsonPath("$.done").value(DEFAULT_DONE.booleanValue()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAssignment() throws Exception {
        // Get the assignment
        restAssignmentMockMvc.perform(get("/api/assignments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssignment() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // Update the assignment
        Assignment updatedAssignment = assignmentRepository.findById(assignment.getId()).get();
        // Disconnect from session so that the updates on updatedAssignment are not directly saved in db
        em.detach(updatedAssignment);
        updatedAssignment
            .creationDate(UPDATED_CREATION_DATE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .dueDate(UPDATED_DUE_DATE)
            .done(UPDATED_DONE)
            .grade(UPDATED_GRADE);
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(updatedAssignment);

        restAssignmentMockMvc.perform(put("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isOk());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAssignment.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAssignment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssignment.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testAssignment.isDone()).isEqualTo(UPDATED_DONE);
        assertThat(testAssignment.getGrade()).isEqualTo(UPDATED_GRADE);
    }

    @Test
    @Transactional
    public void updateNonExistingAssignment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // Create the Assignment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssignmentMockMvc.perform(put("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssignment() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        int databaseSizeBeforeDelete = assignmentRepository.findAll().size();

        // Delete the assignment
        restAssignmentMockMvc.perform(delete("/api/assignments/{id}", assignment.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
