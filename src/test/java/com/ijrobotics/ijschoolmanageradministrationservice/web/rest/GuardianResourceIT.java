package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.IjSchoolManagerAdministrationServiceApp;
import com.ijrobotics.ijschoolmanageradministrationservice.config.TestSecurityConfiguration;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.Guardian;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.GuardianRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.ContactService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.GuardianService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.PersonService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GuardianDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.GuardianMapper;
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

import com.ijrobotics.ijschoolmanageradministrationservice.domain.enumeration.EducationLevel;
/**
 * Integration tests for the {@link GuardianResource} REST controller.
 */
@SpringBootTest(classes = {IjSchoolManagerAdministrationServiceApp.class, TestSecurityConfiguration.class})
public class GuardianResourceIT {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final EducationLevel DEFAULT_EDUCATION_LEVEL = EducationLevel.MIDDLE;
    private static final EducationLevel UPDATED_EDUCATION_LEVEL = EducationLevel.HIGH;

    private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_WORK_ADRESS = "BBBBBBBBBB";

    @Autowired
    private GuardianRepository guardianRepository;

    @Autowired
    private GuardianMapper guardianMapper;

    @Autowired
    private GuardianService guardianService;

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

    private MockMvc restGuardianMockMvc;

    private Guardian guardian;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GuardianResource guardianResource = new GuardianResource(guardianService);
        this.restGuardianMockMvc = MockMvcBuilders.standaloneSetup(guardianResource)
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
    public static Guardian createEntity(EntityManager em) {
        Guardian guardian = new Guardian()
            .creationDate(DEFAULT_CREATION_DATE)
            .educationLevel(DEFAULT_EDUCATION_LEVEL)
            .occupation(DEFAULT_OCCUPATION)
            .workAdress(DEFAULT_WORK_ADRESS);
        return guardian;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Guardian createUpdatedEntity(EntityManager em) {
        Guardian guardian = new Guardian()
            .creationDate(UPDATED_CREATION_DATE)
            .educationLevel(UPDATED_EDUCATION_LEVEL)
            .occupation(UPDATED_OCCUPATION)
            .workAdress(UPDATED_WORK_ADRESS);
        return guardian;
    }

    @BeforeEach
    public void initTest() {
        guardian = createEntity(em);
    }

    @Test
    @Transactional
    public void createGuardian() throws Exception {
        int databaseSizeBeforeCreate = guardianRepository.findAll().size();

        // Create the Guardian
        GuardianDTO guardianDTO = guardianMapper.toDto(guardian);
        restGuardianMockMvc.perform(post("/api/guardians")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guardianDTO)))
            .andExpect(status().isCreated());

        // Validate the Guardian in the database
        List<Guardian> guardianList = guardianRepository.findAll();
        assertThat(guardianList).hasSize(databaseSizeBeforeCreate + 1);
        Guardian testGuardian = guardianList.get(guardianList.size() - 1);
        assertThat(testGuardian.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testGuardian.getEducationLevel()).isEqualTo(DEFAULT_EDUCATION_LEVEL);
        assertThat(testGuardian.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testGuardian.getWorkAdress()).isEqualTo(DEFAULT_WORK_ADRESS);
    }

    @Test
    @Transactional
    public void createGuardianWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = guardianRepository.findAll().size();

        // Create the Guardian with an existing ID
        guardian.setId(1L);
        GuardianDTO guardianDTO = guardianMapper.toDto(guardian);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuardianMockMvc.perform(post("/api/guardians")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guardianDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Guardian in the database
        List<Guardian> guardianList = guardianRepository.findAll();
        assertThat(guardianList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGuardians() throws Exception {
        // Initialize the database
        guardianRepository.saveAndFlush(guardian);

        // Get all the guardianList
        restGuardianMockMvc.perform(get("/api/guardians?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guardian.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].educationLevel").value(hasItem(DEFAULT_EDUCATION_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION)))
            .andExpect(jsonPath("$.[*].workAdress").value(hasItem(DEFAULT_WORK_ADRESS)));
    }

    @Test
    @Transactional
    public void getGuardian() throws Exception {
        // Initialize the database
        guardianRepository.saveAndFlush(guardian);

        // Get the guardian
        restGuardianMockMvc.perform(get("/api/guardians/{id}", guardian.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(guardian.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.educationLevel").value(DEFAULT_EDUCATION_LEVEL.toString()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION))
            .andExpect(jsonPath("$.workAdress").value(DEFAULT_WORK_ADRESS));
    }

    @Test
    @Transactional
    public void getNonExistingGuardian() throws Exception {
        // Get the guardian
        restGuardianMockMvc.perform(get("/api/guardians/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGuardian() throws Exception {
        // Initialize the database
        guardianRepository.saveAndFlush(guardian);

        int databaseSizeBeforeUpdate = guardianRepository.findAll().size();

        // Update the guardian
        Guardian updatedGuardian = guardianRepository.findById(guardian.getId()).get();
        // Disconnect from session so that the updates on updatedGuardian are not directly saved in db
        em.detach(updatedGuardian);
        updatedGuardian
            .creationDate(UPDATED_CREATION_DATE)
            .educationLevel(UPDATED_EDUCATION_LEVEL)
            .occupation(UPDATED_OCCUPATION)
            .workAdress(UPDATED_WORK_ADRESS);
        GuardianDTO guardianDTO = guardianMapper.toDto(updatedGuardian);

        restGuardianMockMvc.perform(put("/api/guardians")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guardianDTO)))
            .andExpect(status().isOk());

        // Validate the Guardian in the database
        List<Guardian> guardianList = guardianRepository.findAll();
        assertThat(guardianList).hasSize(databaseSizeBeforeUpdate);
        Guardian testGuardian = guardianList.get(guardianList.size() - 1);
        assertThat(testGuardian.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testGuardian.getEducationLevel()).isEqualTo(UPDATED_EDUCATION_LEVEL);
        assertThat(testGuardian.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testGuardian.getWorkAdress()).isEqualTo(UPDATED_WORK_ADRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingGuardian() throws Exception {
        int databaseSizeBeforeUpdate = guardianRepository.findAll().size();

        // Create the Guardian
        GuardianDTO guardianDTO = guardianMapper.toDto(guardian);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuardianMockMvc.perform(put("/api/guardians")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guardianDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Guardian in the database
        List<Guardian> guardianList = guardianRepository.findAll();
        assertThat(guardianList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGuardian() throws Exception {
        // Initialize the database
        guardianRepository.saveAndFlush(guardian);

        int databaseSizeBeforeDelete = guardianRepository.findAll().size();

        // Delete the guardian
        restGuardianMockMvc.perform(delete("/api/guardians/{id}", guardian.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Guardian> guardianList = guardianRepository.findAll();
        assertThat(guardianList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
