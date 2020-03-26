package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.IjSchoolManagerAdministrationServiceApp;
import com.ijrobotics.ijschoolmanageradministrationservice.config.TestSecurityConfiguration;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.ClassSchedule;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.ClassScheduleRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.ClassScheduleService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassScheduleDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.ClassScheduleMapper;
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
 * Integration tests for the {@link ClassScheduleResource} REST controller.
 */
@SpringBootTest(classes = {IjSchoolManagerAdministrationServiceApp.class, TestSecurityConfiguration.class})
public class ClassScheduleResourceIT {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_START_HOUR = "AAAAAAAAAA";
    private static final String UPDATED_START_HOUR = "BBBBBBBBBB";

    private static final String DEFAULT_END_HOUR = "AAAAAAAAAA";
    private static final String UPDATED_END_HOUR = "BBBBBBBBBB";

    private static final Integer DEFAULT_WEEK_DAYS = 1;
    private static final Integer UPDATED_WEEK_DAYS = 2;

    @Autowired
    private ClassScheduleRepository classScheduleRepository;

    @Autowired
    private ClassScheduleMapper classScheduleMapper;

    @Autowired
    private ClassScheduleService classScheduleService;

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

    private MockMvc restClassScheduleMockMvc;

    private ClassSchedule classSchedule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassScheduleResource classScheduleResource = new ClassScheduleResource(classScheduleService);
        this.restClassScheduleMockMvc = MockMvcBuilders.standaloneSetup(classScheduleResource)
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
    public static ClassSchedule createEntity(EntityManager em) {
        ClassSchedule classSchedule = new ClassSchedule()
            .creationDate(DEFAULT_CREATION_DATE)
            .startHour(DEFAULT_START_HOUR)
            .endHour(DEFAULT_END_HOUR)
            .weekDays(DEFAULT_WEEK_DAYS);
        return classSchedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassSchedule createUpdatedEntity(EntityManager em) {
        ClassSchedule classSchedule = new ClassSchedule()
            .creationDate(UPDATED_CREATION_DATE)
            .startHour(UPDATED_START_HOUR)
            .endHour(UPDATED_END_HOUR)
            .weekDays(UPDATED_WEEK_DAYS);
        return classSchedule;
    }

    @BeforeEach
    public void initTest() {
        classSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassSchedule() throws Exception {
        int databaseSizeBeforeCreate = classScheduleRepository.findAll().size();

        // Create the ClassSchedule
        ClassScheduleDTO classScheduleDTO = classScheduleMapper.toDto(classSchedule);
        restClassScheduleMockMvc.perform(post("/api/class-schedules")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classScheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassSchedule in the database
        List<ClassSchedule> classScheduleList = classScheduleRepository.findAll();
        assertThat(classScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        ClassSchedule testClassSchedule = classScheduleList.get(classScheduleList.size() - 1);
        assertThat(testClassSchedule.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testClassSchedule.getStartHour()).isEqualTo(DEFAULT_START_HOUR);
        assertThat(testClassSchedule.getEndHour()).isEqualTo(DEFAULT_END_HOUR);
        assertThat(testClassSchedule.getWeekDays()).isEqualTo(DEFAULT_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void createClassScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classScheduleRepository.findAll().size();

        // Create the ClassSchedule with an existing ID
        classSchedule.setId(1L);
        ClassScheduleDTO classScheduleDTO = classScheduleMapper.toDto(classSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassScheduleMockMvc.perform(post("/api/class-schedules")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassSchedule in the database
        List<ClassSchedule> classScheduleList = classScheduleRepository.findAll();
        assertThat(classScheduleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClassSchedules() throws Exception {
        // Initialize the database
        classScheduleRepository.saveAndFlush(classSchedule);

        // Get all the classScheduleList
        restClassScheduleMockMvc.perform(get("/api/class-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].startHour").value(hasItem(DEFAULT_START_HOUR)))
            .andExpect(jsonPath("$.[*].endHour").value(hasItem(DEFAULT_END_HOUR)))
            .andExpect(jsonPath("$.[*].weekDays").value(hasItem(DEFAULT_WEEK_DAYS)));
    }
    
    @Test
    @Transactional
    public void getClassSchedule() throws Exception {
        // Initialize the database
        classScheduleRepository.saveAndFlush(classSchedule);

        // Get the classSchedule
        restClassScheduleMockMvc.perform(get("/api/class-schedules/{id}", classSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classSchedule.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.startHour").value(DEFAULT_START_HOUR))
            .andExpect(jsonPath("$.endHour").value(DEFAULT_END_HOUR))
            .andExpect(jsonPath("$.weekDays").value(DEFAULT_WEEK_DAYS));
    }

    @Test
    @Transactional
    public void getNonExistingClassSchedule() throws Exception {
        // Get the classSchedule
        restClassScheduleMockMvc.perform(get("/api/class-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassSchedule() throws Exception {
        // Initialize the database
        classScheduleRepository.saveAndFlush(classSchedule);

        int databaseSizeBeforeUpdate = classScheduleRepository.findAll().size();

        // Update the classSchedule
        ClassSchedule updatedClassSchedule = classScheduleRepository.findById(classSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedClassSchedule are not directly saved in db
        em.detach(updatedClassSchedule);
        updatedClassSchedule
            .creationDate(UPDATED_CREATION_DATE)
            .startHour(UPDATED_START_HOUR)
            .endHour(UPDATED_END_HOUR)
            .weekDays(UPDATED_WEEK_DAYS);
        ClassScheduleDTO classScheduleDTO = classScheduleMapper.toDto(updatedClassSchedule);

        restClassScheduleMockMvc.perform(put("/api/class-schedules")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classScheduleDTO)))
            .andExpect(status().isOk());

        // Validate the ClassSchedule in the database
        List<ClassSchedule> classScheduleList = classScheduleRepository.findAll();
        assertThat(classScheduleList).hasSize(databaseSizeBeforeUpdate);
        ClassSchedule testClassSchedule = classScheduleList.get(classScheduleList.size() - 1);
        assertThat(testClassSchedule.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testClassSchedule.getStartHour()).isEqualTo(UPDATED_START_HOUR);
        assertThat(testClassSchedule.getEndHour()).isEqualTo(UPDATED_END_HOUR);
        assertThat(testClassSchedule.getWeekDays()).isEqualTo(UPDATED_WEEK_DAYS);
    }

    @Test
    @Transactional
    public void updateNonExistingClassSchedule() throws Exception {
        int databaseSizeBeforeUpdate = classScheduleRepository.findAll().size();

        // Create the ClassSchedule
        ClassScheduleDTO classScheduleDTO = classScheduleMapper.toDto(classSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassScheduleMockMvc.perform(put("/api/class-schedules")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassSchedule in the database
        List<ClassSchedule> classScheduleList = classScheduleRepository.findAll();
        assertThat(classScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassSchedule() throws Exception {
        // Initialize the database
        classScheduleRepository.saveAndFlush(classSchedule);

        int databaseSizeBeforeDelete = classScheduleRepository.findAll().size();

        // Delete the classSchedule
        restClassScheduleMockMvc.perform(delete("/api/class-schedules/{id}", classSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassSchedule> classScheduleList = classScheduleRepository.findAll();
        assertThat(classScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
