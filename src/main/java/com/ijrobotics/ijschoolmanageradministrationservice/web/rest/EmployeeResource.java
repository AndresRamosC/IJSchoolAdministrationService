package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.EmployeeService;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.EmployeeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Employee}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceEmployee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * {@code POST  /employees} : Create a new employee.
     *
     * @param employeeDTO the employeeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeDTO, or with status {@code 400 (Bad Request)} if the employee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employeeDTO);
        if (employeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new employee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeDTO result = employeeService.save(employeeDTO);
        return ResponseEntity.created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employees} : Updates an existing employee.
     *
     * @param employeeDTO the employeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeDTO,
     * or with status {@code 400 (Bad Request)} if the employeeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO,@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update Employee : {}", employeeDTO);
        Optional<EmployeeDTO> employeeDTOUpdate = employeeService.findOne(id);
        if ( !employeeDTOUpdate.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (employeeDTO.getEducationLevel()!=null){
            employeeDTOUpdate.get().setEducationLevel(employeeDTO.getEducationLevel());
        }
        if (employeeDTO.getDepartment()!=null){
            employeeDTOUpdate.get().setDepartment(employeeDTO.getDepartment());
        }
        if (employeeDTO.getControlNumber()!=null){
            employeeDTOUpdate.get().setControlNumber(employeeDTO.getControlNumber());
        }
        if (employeeDTO.getEmploymentType()!=null){
            employeeDTOUpdate.get().setEmploymentType(employeeDTO.getEmploymentType());
        }
        if (employeeDTO.getEmployedSince()!=null){
            employeeDTOUpdate.get().setEmployedSince(employeeDTO.getEmployedSince());
        }
        if (employeeDTO.getEmployedUntil()!=null){
            employeeDTOUpdate.get().setEmployedUntil(employeeDTO.getEmployedUntil());
        }
        if (employeeDTO.getPersonId()!=null){
            employeeDTOUpdate.get().setPersonId(employeeDTO.getPersonId());
        }
        EmployeeDTO result = employeeService.save(employeeDTOUpdate.get());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeDTOUpdate.get().getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employees} : get all the employees.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employees in body.
     */
    @GetMapping("/employees")
    public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false) String filter) {
        if ("teacher-is-null".equals(filter)) {
            log.debug("REST request to get all Employees where teacher is null");
            return employeeService.findAllWhereTeacherIsNull();
        }
        log.debug("REST request to get all Employees");
        return employeeService.findAll();
    }

    /**
     * {@code GET  /employees/:id} : get the "id" employee.
     *
     * @param id the id of the employeeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        log.debug("REST request to get Employee : {}", id);
        Optional<EmployeeDTO> employeeDTO = employeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeDTO);
    }

    /**
     * {@code DELETE  /employees/:id} : delete the "id" employee.
     *
     * @param id the id of the employeeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
