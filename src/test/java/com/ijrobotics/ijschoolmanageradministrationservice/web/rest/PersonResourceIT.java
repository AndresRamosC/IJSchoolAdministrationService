package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.IjSchoolManagerAdministrationServiceApp;
import com.ijrobotics.ijschoolmanageradministrationservice.config.TestSecurityConfiguration;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.Person;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.PersonRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.PersonService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.PersonMapper;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil.sameInstant;
import static com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.enumeration.Genders;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.enumeration.BlodTypes;
/**
 * Integration tests for the {@link PersonResource} REST controller.
 */
@SpringBootTest(classes = {IjSchoolManagerAdministrationServiceApp.class, TestSecurityConfiguration.class})
public class PersonResourceIT {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Genders DEFAULT_GENDER = Genders.MALE;
    private static final Genders UPDATED_GENDER = Genders.FEMALE;

    private static final BlodTypes DEFAULT_BLOOD_GROUP = BlodTypes.APLUS;
    private static final BlodTypes UPDATED_BLOOD_GROUP = BlodTypes.AMINUS;

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_PHOTOGRAPH = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTOGRAPH = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTOGRAPH_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTOGRAPH_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_ASSIGNED = false;
    private static final Boolean UPDATED_ASSIGNED = true;

    @Autowired
    private PersonRepository personRepository;

    @Mock
    private PersonRepository personRepositoryMock;

    @Autowired
    private PersonMapper personMapper;

    @Mock
    private PersonService personServiceMock;

    @Autowired
    private PersonService personService;

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

    private MockMvc restPersonMockMvc;

    private Person person;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonResource personResource = new PersonResource(personService);
        this.restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource)
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
    public static Person createEntity(EntityManager em) {
        Person person = new Person()
            .creationDate(DEFAULT_CREATION_DATE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .gender(DEFAULT_GENDER)
            .bloodGroup(DEFAULT_BLOOD_GROUP)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .photograph(DEFAULT_PHOTOGRAPH)
            .photographContentType(DEFAULT_PHOTOGRAPH_CONTENT_TYPE)
            .assigned(DEFAULT_ASSIGNED);
        return person;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createUpdatedEntity(EntityManager em) {
        Person person = new Person()
            .creationDate(UPDATED_CREATION_DATE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .photograph(UPDATED_PHOTOGRAPH)
            .photographContentType(UPDATED_PHOTOGRAPH_CONTENT_TYPE)
            .assigned(UPDATED_ASSIGNED);
        return person;
    }

    @BeforeEach
    public void initTest() {
        person = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);
        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testPerson.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPerson.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPerson.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPerson.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testPerson.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testPerson.getPhotograph()).isEqualTo(DEFAULT_PHOTOGRAPH);
        assertThat(testPerson.getPhotographContentType()).isEqualTo(DEFAULT_PHOTOGRAPH_CONTENT_TYPE);
        assertThat(testPerson.isAssigned()).isEqualTo(DEFAULT_ASSIGNED);
    }

    @Test
    @Transactional
    public void createPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person with an existing ID
        person.setId(1L);
        PersonDTO personDTO = personMapper.toDto(person);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc.perform(get("/api/people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].photographContentType").value(hasItem(DEFAULT_PHOTOGRAPH_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photograph").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTOGRAPH))))
            .andExpect(jsonPath("$.[*].assigned").value(hasItem(DEFAULT_ASSIGNED.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPeopleWithEagerRelationshipsIsEnabled() throws Exception {
        PersonResource personResource = new PersonResource(personServiceMock);
        when(personServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPersonMockMvc.perform(get("/api/people?eagerload=true"))
        .andExpect(status().isOk());

        verify(personServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPeopleWithEagerRelationshipsIsNotEnabled() throws Exception {
        PersonResource personResource = new PersonResource(personServiceMock);
            when(personServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPersonMockMvc.perform(get("/api/people?eagerload=true"))
        .andExpect(status().isOk());

            verify(personServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.bloodGroup").value(DEFAULT_BLOOD_GROUP.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.photographContentType").value(DEFAULT_PHOTOGRAPH_CONTENT_TYPE))
            .andExpect(jsonPath("$.photograph").value(Base64Utils.encodeToString(DEFAULT_PHOTOGRAPH)))
            .andExpect(jsonPath("$.assigned").value(DEFAULT_ASSIGNED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findById(person.getId()).get();
        // Disconnect from session so that the updates on updatedPerson are not directly saved in db
        em.detach(updatedPerson);
        updatedPerson
            .creationDate(UPDATED_CREATION_DATE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .photograph(UPDATED_PHOTOGRAPH)
            .photographContentType(UPDATED_PHOTOGRAPH_CONTENT_TYPE)
            .assigned(UPDATED_ASSIGNED);
        PersonDTO personDTO = personMapper.toDto(updatedPerson);

        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testPerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPerson.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPerson.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPerson.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testPerson.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testPerson.getPhotograph()).isEqualTo(UPDATED_PHOTOGRAPH);
        assertThat(testPerson.getPhotographContentType()).isEqualTo(UPDATED_PHOTOGRAPH_CONTENT_TYPE);
        assertThat(testPerson.isAssigned()).isEqualTo(UPDATED_ASSIGNED);
    }

    @Test
    @Transactional
    public void updateNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Delete the person
        restPersonMockMvc.perform(delete("/api/people/{id}", person.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
