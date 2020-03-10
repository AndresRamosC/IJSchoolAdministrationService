package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.IjSchoolManagerAdministrationServiceApp;
import com.ijrobotics.ijschoolmanageradministrationservice.config.TestSecurityConfiguration;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.AssignmentAssigned;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.AssignmentAssignedRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.AssignmentAssignedService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AssignmentAssignedDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AssignmentAssignedMapper;
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
 * Integration tests for the {@link AssignmentAssignedResource} REST controller.
 */
@SpringBootTest(classes = {IjSchoolManagerAdministrationServiceApp.class, TestSecurityConfiguration.class})
public class AssignmentAssignedResourceIT {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_DONE = false;
    private static final Boolean UPDATED_DONE = true;

    private static final Float DEFAULT_GRADE = 1F;
    private static final Float UPDATED_GRADE = 2F;

    @Autowired
    private AssignmentAssignedRepository assignmentAssignedRepository;

    @Autowired
    private AssignmentAssignedMapper assignmentAssignedMapper;

    @Autowired
    private AssignmentAssignedService assignmentAssignedService;

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

    private MockMvc restAssignmentAssignedMockMvc;

    private AssignmentAssigned assignmentAssigned;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssignmentAssignedResource assignmentAssignedResource = new AssignmentAssignedResource(assignmentAssignedService);
        this.restAssignmentAssignedMockMvc = MockMvcBuilders.standaloneSetup(assignmentAssignedResource)
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
    public static AssignmentAssigned createEntity(EntityManager em) {
        AssignmentAssigned assignmentAssigned = new AssignmentAssigned()
            .creationDate(DEFAULT_CREATION_DATE)
            .done(DEFAULT_DONE)
            .grade(DEFAULT_GRADE);
        return assignmentAssigned;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssignmentAssigned createUpdatedEntity(EntityManager em) {
        AssignmentAssigned assignmentAssigned = new AssignmentAssigned()
            .creationDate(UPDATED_CREATION_DATE)
            .done(UPDATED_DONE)
            .grade(UPDATED_GRADE);
        return assignmentAssigned;
    }

    @BeforeEach
    public void initTest() {
        assignmentAssigned = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssignmentAssigned() throws Exception {
        int databaseSizeBeforeCreate = assignmentAssignedRepository.findAll().size();

        // Create the AssignmentAssigned
        AssignmentAssignedDTO assignmentAssignedDTO = assignmentAssignedMapper.toDto(assignmentAssigned);
        restAssignmentAssignedMockMvc.perform(post("/api/assignment-assigneds")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignmentAssignedDTO)))
            .andExpect(status().isCreated());

        // Validate the AssignmentAssigned in the database
        List<AssignmentAssigned> assignmentAssignedList = assignmentAssignedRepository.findAll();
        assertThat(assignmentAssignedList).hasSize(databaseSizeBeforeCreate + 1);
        AssignmentAssigned testAssignmentAssigned = assignmentAssignedList.get(assignmentAssignedList.size() - 1);
        assertThat(testAssignmentAssigned.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAssignmentAssigned.isDone()).isEqualTo(DEFAULT_DONE);
        assertThat(testAssignmentAssigned.getGrade()).isEqualTo(DEFAULT_GRADE);
    }

    @Test
    @Transactional
    public void createAssignmentAssignedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assignmentAssignedRepository.findAll().size();

        // Create the AssignmentAssigned with an existing ID
        assignmentAssigned.setId(1L);
        AssignmentAssignedDTO assignmentAssignedDTO = assignmentAssignedMapper.toDto(assignmentAssigned);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssignmentAssignedMockMvc.perform(post("/api/assignment-assigneds")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignmentAssignedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssignmentAssigned in the database
        List<AssignmentAssigned> assignmentAssignedList = assignmentAssignedRepository.findAll();
        assertThat(assignmentAssignedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAssignmentAssigneds() throws Exception {
        // Initialize the database
        assignmentAssignedRepository.saveAndFlush(assignmentAssigned);

        // Get all the assignmentAssignedList
        restAssignmentAssignedMockMvc.perform(get("/api/assignment-assigneds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assignmentAssigned.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].done").value(hasItem(DEFAULT_DONE.booleanValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getAssignmentAssigned() throws Exception {
        // Initialize the database
        assignmentAssignedRepository.saveAndFlush(assignmentAssigned);

        // Get the assignmentAssigned
        restAssignmentAssignedMockMvc.perform(get("/api/assignment-assigneds/{id}", assignmentAssigned.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assignmentAssigned.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.done").value(DEFAULT_DONE.booleanValue()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAssignmentAssigned() throws Exception {
        // Get the assignmentAssigned
        restAssignmentAssignedMockMvc.perform(get("/api/assignment-assigneds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssignmentAssigned() throws Exception {
        // Initialize the database
        assignmentAssignedRepository.saveAndFlush(assignmentAssigned);

        int databaseSizeBeforeUpdate = assignmentAssignedRepository.findAll().size();

        // Update the assignmentAssigned
        AssignmentAssigned updatedAssignmentAssigned = assignmentAssignedRepository.findById(assignmentAssigned.getId()).get();
        // Disconnect from session so that the updates on updatedAssignmentAssigned are not directly saved in db
        em.detach(updatedAssignmentAssigned);
        updatedAssignmentAssigned
            .creationDate(UPDATED_CREATION_DATE)
            .done(UPDATED_DONE)
            .grade(UPDATED_GRADE);
        AssignmentAssignedDTO assignmentAssignedDTO = assignmentAssignedMapper.toDto(updatedAssignmentAssigned);

        restAssignmentAssignedMockMvc.perform(put("/api/assignment-assigneds")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignmentAssignedDTO)))
            .andExpect(status().isOk());

        // Validate the AssignmentAssigned in the database
        List<AssignmentAssigned> assignmentAssignedList = assignmentAssignedRepository.findAll();
        assertThat(assignmentAssignedList).hasSize(databaseSizeBeforeUpdate);
        AssignmentAssigned testAssignmentAssigned = assignmentAssignedList.get(assignmentAssignedList.size() - 1);
        assertThat(testAssignmentAssigned.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAssignmentAssigned.isDone()).isEqualTo(UPDATED_DONE);
        assertThat(testAssignmentAssigned.getGrade()).isEqualTo(UPDATED_GRADE);
    }

    @Test
    @Transactional
    public void updateNonExistingAssignmentAssigned() throws Exception {
        int databaseSizeBeforeUpdate = assignmentAssignedRepository.findAll().size();

        // Create the AssignmentAssigned
        AssignmentAssignedDTO assignmentAssignedDTO = assignmentAssignedMapper.toDto(assignmentAssigned);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssignmentAssignedMockMvc.perform(put("/api/assignment-assigneds")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignmentAssignedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssignmentAssigned in the database
        List<AssignmentAssigned> assignmentAssignedList = assignmentAssignedRepository.findAll();
        assertThat(assignmentAssignedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssignmentAssigned() throws Exception {
        // Initialize the database
        assignmentAssignedRepository.saveAndFlush(assignmentAssigned);

        int databaseSizeBeforeDelete = assignmentAssignedRepository.findAll().size();

        // Delete the assignmentAssigned
        restAssignmentAssignedMockMvc.perform(delete("/api/assignment-assigneds/{id}", assignmentAssigned.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssignmentAssigned> assignmentAssignedList = assignmentAssignedRepository.findAll();
        assertThat(assignmentAssignedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
