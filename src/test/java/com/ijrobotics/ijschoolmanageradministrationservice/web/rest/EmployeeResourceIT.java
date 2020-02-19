package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.IjSchoolManagerAdministrationServiceApp;
import com.ijrobotics.ijschoolmanageradministrationservice.config.TestSecurityConfiguration;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.Employee;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.EmployeeRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.EmployeeService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.EmployeeDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.EmployeeMapper;
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
import java.time.LocalDate;
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
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@SpringBootTest(classes = {IjSchoolManagerAdministrationServiceApp.class, TestSecurityConfiguration.class})
public class EmployeeResourceIT {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_EDUCATION_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTROL_NUMBER = 1L;
    private static final Long UPDATED_CONTROL_NUMBER = 2L;

    private static final String DEFAULT_EMPLOYMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYMENT_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EMPLOYED_SINCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EMPLOYED_SINCE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EMPLOYED_UNTIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EMPLOYED_UNTIL = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeService employeeService;

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

    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeResource employeeResource = new EmployeeResource(employeeService);
        this.restEmployeeMockMvc = MockMvcBuilders.standaloneSetup(employeeResource)
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
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .creationDate(DEFAULT_CREATION_DATE)
            .educationLevel(DEFAULT_EDUCATION_LEVEL)
            .department(DEFAULT_DEPARTMENT)
            .controlNumber(DEFAULT_CONTROL_NUMBER)
            .employmentType(DEFAULT_EMPLOYMENT_TYPE)
            .employedSince(DEFAULT_EMPLOYED_SINCE)
            .employedUntil(DEFAULT_EMPLOYED_UNTIL);
        return employee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .creationDate(UPDATED_CREATION_DATE)
            .educationLevel(UPDATED_EDUCATION_LEVEL)
            .department(UPDATED_DEPARTMENT)
            .controlNumber(UPDATED_CONTROL_NUMBER)
            .employmentType(UPDATED_EMPLOYMENT_TYPE)
            .employedSince(UPDATED_EMPLOYED_SINCE)
            .employedUntil(UPDATED_EMPLOYED_UNTIL);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testEmployee.getEducationLevel()).isEqualTo(DEFAULT_EDUCATION_LEVEL);
        assertThat(testEmployee.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testEmployee.getControlNumber()).isEqualTo(DEFAULT_CONTROL_NUMBER);
        assertThat(testEmployee.getEmploymentType()).isEqualTo(DEFAULT_EMPLOYMENT_TYPE);
        assertThat(testEmployee.getEmployedSince()).isEqualTo(DEFAULT_EMPLOYED_SINCE);
        assertThat(testEmployee.getEmployedUntil()).isEqualTo(DEFAULT_EMPLOYED_UNTIL);
    }

    @Test
    @Transactional
    public void createEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee with an existing ID
        employee.setId(1L);
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].educationLevel").value(hasItem(DEFAULT_EDUCATION_LEVEL)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].controlNumber").value(hasItem(DEFAULT_CONTROL_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].employmentType").value(hasItem(DEFAULT_EMPLOYMENT_TYPE)))
            .andExpect(jsonPath("$.[*].employedSince").value(hasItem(DEFAULT_EMPLOYED_SINCE.toString())))
            .andExpect(jsonPath("$.[*].employedUntil").value(hasItem(DEFAULT_EMPLOYED_UNTIL.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.educationLevel").value(DEFAULT_EDUCATION_LEVEL))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.controlNumber").value(DEFAULT_CONTROL_NUMBER.intValue()))
            .andExpect(jsonPath("$.employmentType").value(DEFAULT_EMPLOYMENT_TYPE))
            .andExpect(jsonPath("$.employedSince").value(DEFAULT_EMPLOYED_SINCE.toString()))
            .andExpect(jsonPath("$.employedUntil").value(DEFAULT_EMPLOYED_UNTIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .creationDate(UPDATED_CREATION_DATE)
            .educationLevel(UPDATED_EDUCATION_LEVEL)
            .department(UPDATED_DEPARTMENT)
            .controlNumber(UPDATED_CONTROL_NUMBER)
            .employmentType(UPDATED_EMPLOYMENT_TYPE)
            .employedSince(UPDATED_EMPLOYED_SINCE)
            .employedUntil(UPDATED_EMPLOYED_UNTIL);
        EmployeeDTO employeeDTO = employeeMapper.toDto(updatedEmployee);

        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testEmployee.getEducationLevel()).isEqualTo(UPDATED_EDUCATION_LEVEL);
        assertThat(testEmployee.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testEmployee.getControlNumber()).isEqualTo(UPDATED_CONTROL_NUMBER);
        assertThat(testEmployee.getEmploymentType()).isEqualTo(UPDATED_EMPLOYMENT_TYPE);
        assertThat(testEmployee.getEmployedSince()).isEqualTo(UPDATED_EMPLOYED_SINCE);
        assertThat(testEmployee.getEmployedUntil()).isEqualTo(UPDATED_EMPLOYED_UNTIL);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
