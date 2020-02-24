package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.IjSchoolManagerAdministrationServiceApp;
import com.ijrobotics.ijschoolmanageradministrationservice.config.TestSecurityConfiguration;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.Exculpatory;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.ExculpatoryRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.ExculpatoryService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ExculpatoryDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.ExculpatoryMapper;
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
 * Integration tests for the {@link ExculpatoryResource} REST controller.
 */
@SpringBootTest(classes = {IjSchoolManagerAdministrationServiceApp.class, TestSecurityConfiguration.class})
public class ExculpatoryResourceIT {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LEAVE_DATE_AND_HOUR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LEAVE_DATE_AND_HOUR = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_FULL_DAY = false;
    private static final Boolean UPDATED_FULL_DAY = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ExculpatoryRepository exculpatoryRepository;

    @Autowired
    private ExculpatoryMapper exculpatoryMapper;

    @Autowired
    private ExculpatoryService exculpatoryService;

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

    private MockMvc restExculpatoryMockMvc;

    private Exculpatory exculpatory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExculpatoryResource exculpatoryResource = new ExculpatoryResource(exculpatoryService);
        this.restExculpatoryMockMvc = MockMvcBuilders.standaloneSetup(exculpatoryResource)
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
    public static Exculpatory createEntity(EntityManager em) {
        Exculpatory exculpatory = new Exculpatory()
            .creationDate(DEFAULT_CREATION_DATE)
            .leaveDateAndHour(DEFAULT_LEAVE_DATE_AND_HOUR)
            .fullDay(DEFAULT_FULL_DAY)
            .description(DEFAULT_DESCRIPTION);
        return exculpatory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exculpatory createUpdatedEntity(EntityManager em) {
        Exculpatory exculpatory = new Exculpatory()
            .creationDate(UPDATED_CREATION_DATE)
            .leaveDateAndHour(UPDATED_LEAVE_DATE_AND_HOUR)
            .fullDay(UPDATED_FULL_DAY)
            .description(UPDATED_DESCRIPTION);
        return exculpatory;
    }

    @BeforeEach
    public void initTest() {
        exculpatory = createEntity(em);
    }

    @Test
    @Transactional
    public void createExculpatory() throws Exception {
        int databaseSizeBeforeCreate = exculpatoryRepository.findAll().size();

        // Create the Exculpatory
        ExculpatoryDTO exculpatoryDTO = exculpatoryMapper.toDto(exculpatory);
        restExculpatoryMockMvc.perform(post("/api/exculpatories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exculpatoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Exculpatory in the database
        List<Exculpatory> exculpatoryList = exculpatoryRepository.findAll();
        assertThat(exculpatoryList).hasSize(databaseSizeBeforeCreate + 1);
        Exculpatory testExculpatory = exculpatoryList.get(exculpatoryList.size() - 1);
        assertThat(testExculpatory.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testExculpatory.getLeaveDateAndHour()).isEqualTo(DEFAULT_LEAVE_DATE_AND_HOUR);
        assertThat(testExculpatory.isFullDay()).isEqualTo(DEFAULT_FULL_DAY);
        assertThat(testExculpatory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createExculpatoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exculpatoryRepository.findAll().size();

        // Create the Exculpatory with an existing ID
        exculpatory.setId(1L);
        ExculpatoryDTO exculpatoryDTO = exculpatoryMapper.toDto(exculpatory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExculpatoryMockMvc.perform(post("/api/exculpatories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exculpatoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exculpatory in the database
        List<Exculpatory> exculpatoryList = exculpatoryRepository.findAll();
        assertThat(exculpatoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExculpatories() throws Exception {
        // Initialize the database
        exculpatoryRepository.saveAndFlush(exculpatory);

        // Get all the exculpatoryList
        restExculpatoryMockMvc.perform(get("/api/exculpatories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exculpatory.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].leaveDateAndHour").value(hasItem(sameInstant(DEFAULT_LEAVE_DATE_AND_HOUR))))
            .andExpect(jsonPath("$.[*].fullDay").value(hasItem(DEFAULT_FULL_DAY.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getExculpatory() throws Exception {
        // Initialize the database
        exculpatoryRepository.saveAndFlush(exculpatory);

        // Get the exculpatory
        restExculpatoryMockMvc.perform(get("/api/exculpatories/{id}", exculpatory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exculpatory.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.leaveDateAndHour").value(sameInstant(DEFAULT_LEAVE_DATE_AND_HOUR)))
            .andExpect(jsonPath("$.fullDay").value(DEFAULT_FULL_DAY.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingExculpatory() throws Exception {
        // Get the exculpatory
        restExculpatoryMockMvc.perform(get("/api/exculpatories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExculpatory() throws Exception {
        // Initialize the database
        exculpatoryRepository.saveAndFlush(exculpatory);

        int databaseSizeBeforeUpdate = exculpatoryRepository.findAll().size();

        // Update the exculpatory
        Exculpatory updatedExculpatory = exculpatoryRepository.findById(exculpatory.getId()).get();
        // Disconnect from session so that the updates on updatedExculpatory are not directly saved in db
        em.detach(updatedExculpatory);
        updatedExculpatory
            .creationDate(UPDATED_CREATION_DATE)
            .leaveDateAndHour(UPDATED_LEAVE_DATE_AND_HOUR)
            .fullDay(UPDATED_FULL_DAY)
            .description(UPDATED_DESCRIPTION);
        ExculpatoryDTO exculpatoryDTO = exculpatoryMapper.toDto(updatedExculpatory);

        restExculpatoryMockMvc.perform(put("/api/exculpatories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exculpatoryDTO)))
            .andExpect(status().isOk());

        // Validate the Exculpatory in the database
        List<Exculpatory> exculpatoryList = exculpatoryRepository.findAll();
        assertThat(exculpatoryList).hasSize(databaseSizeBeforeUpdate);
        Exculpatory testExculpatory = exculpatoryList.get(exculpatoryList.size() - 1);
        assertThat(testExculpatory.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testExculpatory.getLeaveDateAndHour()).isEqualTo(UPDATED_LEAVE_DATE_AND_HOUR);
        assertThat(testExculpatory.isFullDay()).isEqualTo(UPDATED_FULL_DAY);
        assertThat(testExculpatory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingExculpatory() throws Exception {
        int databaseSizeBeforeUpdate = exculpatoryRepository.findAll().size();

        // Create the Exculpatory
        ExculpatoryDTO exculpatoryDTO = exculpatoryMapper.toDto(exculpatory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExculpatoryMockMvc.perform(put("/api/exculpatories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exculpatoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exculpatory in the database
        List<Exculpatory> exculpatoryList = exculpatoryRepository.findAll();
        assertThat(exculpatoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExculpatory() throws Exception {
        // Initialize the database
        exculpatoryRepository.saveAndFlush(exculpatory);

        int databaseSizeBeforeDelete = exculpatoryRepository.findAll().size();

        // Delete the exculpatory
        restExculpatoryMockMvc.perform(delete("/api/exculpatories/{id}", exculpatory.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Exculpatory> exculpatoryList = exculpatoryRepository.findAll();
        assertThat(exculpatoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
