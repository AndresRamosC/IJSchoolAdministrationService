package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.IjSchoolManagerAdministrationServiceApp;
import com.ijrobotics.ijschoolmanageradministrationservice.config.TestSecurityConfiguration;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.UserExtend;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.UserExtendRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.UserExtendService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.UserExtendDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.UserExtendMapper;
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

import com.ijrobotics.ijschoolmanageradministrationservice.domain.enumeration.UserType;
/**
 * Integration tests for the {@link UserExtendResource} REST controller.
 */
@SpringBootTest(classes = {IjSchoolManagerAdministrationServiceApp.class, TestSecurityConfiguration.class})
public class UserExtendResourceIT {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final UserType DEFAULT_TYPE = UserType.GUARDIAN;
    private static final UserType UPDATED_TYPE = UserType.EMPLOYEE;

    private static final String DEFAULT_KEYCLOAK_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_KEYCLOAK_USER_ID = "BBBBBBBBBB";

    @Autowired
    private UserExtendRepository userExtendRepository;

    @Autowired
    private UserExtendMapper userExtendMapper;

    @Autowired
    private UserExtendService userExtendService;

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

    private MockMvc restUserExtendMockMvc;

    private UserExtend userExtend;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserExtendResource userExtendResource = new UserExtendResource(userExtendService);
        this.restUserExtendMockMvc = MockMvcBuilders.standaloneSetup(userExtendResource)
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
    public static UserExtend createEntity(EntityManager em) {
        UserExtend userExtend = new UserExtend()
            .creationDate(DEFAULT_CREATION_DATE)
            .userName(DEFAULT_USER_NAME)
            .password(DEFAULT_PASSWORD)
            .enabled(DEFAULT_ENABLED)
            .type(DEFAULT_TYPE)
            .keycloakUserId(DEFAULT_KEYCLOAK_USER_ID);
        return userExtend;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtend createUpdatedEntity(EntityManager em) {
        UserExtend userExtend = new UserExtend()
            .creationDate(UPDATED_CREATION_DATE)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .enabled(UPDATED_ENABLED)
            .type(UPDATED_TYPE)
            .keycloakUserId(UPDATED_KEYCLOAK_USER_ID);
        return userExtend;
    }

    @BeforeEach
    public void initTest() {
        userExtend = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserExtend() throws Exception {
        int databaseSizeBeforeCreate = userExtendRepository.findAll().size();

        // Create the UserExtend
        UserExtendDTO userExtendDTO = userExtendMapper.toDto(userExtend);
        restUserExtendMockMvc.perform(post("/api/user-extends")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExtendDTO)))
            .andExpect(status().isCreated());

        // Validate the UserExtend in the database
        List<UserExtend> userExtendList = userExtendRepository.findAll();
        assertThat(userExtendList).hasSize(databaseSizeBeforeCreate + 1);
        UserExtend testUserExtend = userExtendList.get(userExtendList.size() - 1);
        assertThat(testUserExtend.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testUserExtend.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testUserExtend.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUserExtend.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testUserExtend.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testUserExtend.getKeycloakUserId()).isEqualTo(DEFAULT_KEYCLOAK_USER_ID);
    }

    @Test
    @Transactional
    public void createUserExtendWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userExtendRepository.findAll().size();

        // Create the UserExtend with an existing ID
        userExtend.setId(1L);
        UserExtendDTO userExtendDTO = userExtendMapper.toDto(userExtend);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtendMockMvc.perform(post("/api/user-extends")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExtendDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtend in the database
        List<UserExtend> userExtendList = userExtendRepository.findAll();
        assertThat(userExtendList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserExtends() throws Exception {
        // Initialize the database
        userExtendRepository.saveAndFlush(userExtend);

        // Get all the userExtendList
        restUserExtendMockMvc.perform(get("/api/user-extends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtend.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].keycloakUserId").value(hasItem(DEFAULT_KEYCLOAK_USER_ID)));
    }
    
    @Test
    @Transactional
    public void getUserExtend() throws Exception {
        // Initialize the database
        userExtendRepository.saveAndFlush(userExtend);

        // Get the userExtend
        restUserExtendMockMvc.perform(get("/api/user-extends/{id}", userExtend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userExtend.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.keycloakUserId").value(DEFAULT_KEYCLOAK_USER_ID));
    }

    @Test
    @Transactional
    public void getNonExistingUserExtend() throws Exception {
        // Get the userExtend
        restUserExtendMockMvc.perform(get("/api/user-extends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserExtend() throws Exception {
        // Initialize the database
        userExtendRepository.saveAndFlush(userExtend);

        int databaseSizeBeforeUpdate = userExtendRepository.findAll().size();

        // Update the userExtend
        UserExtend updatedUserExtend = userExtendRepository.findById(userExtend.getId()).get();
        // Disconnect from session so that the updates on updatedUserExtend are not directly saved in db
        em.detach(updatedUserExtend);
        updatedUserExtend
            .creationDate(UPDATED_CREATION_DATE)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .enabled(UPDATED_ENABLED)
            .type(UPDATED_TYPE)
            .keycloakUserId(UPDATED_KEYCLOAK_USER_ID);
        UserExtendDTO userExtendDTO = userExtendMapper.toDto(updatedUserExtend);

        restUserExtendMockMvc.perform(put("/api/user-extends")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExtendDTO)))
            .andExpect(status().isOk());

        // Validate the UserExtend in the database
        List<UserExtend> userExtendList = userExtendRepository.findAll();
        assertThat(userExtendList).hasSize(databaseSizeBeforeUpdate);
        UserExtend testUserExtend = userExtendList.get(userExtendList.size() - 1);
        assertThat(testUserExtend.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testUserExtend.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUserExtend.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserExtend.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testUserExtend.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testUserExtend.getKeycloakUserId()).isEqualTo(UPDATED_KEYCLOAK_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserExtend() throws Exception {
        int databaseSizeBeforeUpdate = userExtendRepository.findAll().size();

        // Create the UserExtend
        UserExtendDTO userExtendDTO = userExtendMapper.toDto(userExtend);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtendMockMvc.perform(put("/api/user-extends")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExtendDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtend in the database
        List<UserExtend> userExtendList = userExtendRepository.findAll();
        assertThat(userExtendList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserExtend() throws Exception {
        // Initialize the database
        userExtendRepository.saveAndFlush(userExtend);

        int databaseSizeBeforeDelete = userExtendRepository.findAll().size();

        // Delete the userExtend
        restUserExtendMockMvc.perform(delete("/api/user-extends/{id}", userExtend.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserExtend> userExtendList = userExtendRepository.findAll();
        assertThat(userExtendList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
