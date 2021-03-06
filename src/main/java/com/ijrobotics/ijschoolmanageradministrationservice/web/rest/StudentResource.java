package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.StudentService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.studentDtos.NewStudentDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.studentDtos.StudentInfoWithGuardianPhotoAndName;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.studentDtos.StudentPhotoAndName;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.StudentDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Student}.
 */
@RestController
@RequestMapping("/api")
public class StudentResource {

    private final Logger log = LoggerFactory.getLogger(StudentResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceStudent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentService studentService;

    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * {@code POST  /students} : Create a new student.
     *
     * @param studentDTO the studentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentDTO, or with status {@code 400 (Bad Request)} if the student has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/studentToGuardian/{guardianId}")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody NewStudentDto studentDTO,@PathVariable long guardianId) throws URISyntaxException {
        log.debug("REST request to save Student : {}", studentDTO.getStudentDTO());
        StudentDTO result = studentService.saveFullStudent(studentDTO,guardianId);
        return ResponseEntity.created(new URI("/api/students/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /students} : Updates an existing student.
     *
     * @param studentDTO the studentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentDTO,
     * or with status {@code 400 (Bad Request)} if the studentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/students/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO studentDTO,@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update Student : {}", studentDTO);
        Optional<StudentDTO> studentDTOUpdate = studentService.findOne(id);
        if ( !studentDTOUpdate.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (studentDTO.getAdmissionDate()!=null){
            studentDTOUpdate.get().setAdmissionDate(studentDTO.getAdmissionDate());
        }
        if (studentDTO.getAcademicYear()!=null){
            studentDTOUpdate.get().setAcademicYear(studentDTO.getAcademicYear());
        }
        if (studentDTO.getControlNumber()!=null){
            studentDTOUpdate.get().setControlNumber(studentDTO.getControlNumber());
        }
        if (studentDTO.getPersonId()!=null){
            studentDTOUpdate.get().setPersonId(studentDTO.getPersonId());
        }
        if (studentDTO.getGuardians()!=null){
            studentDTOUpdate.get().setGuardians(studentDTO.getGuardians());
        }

        StudentDTO result = studentService.save(studentDTOUpdate.get());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentDTOUpdate.get().getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /students} : get all the students.
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of students in body.
     */
    @GetMapping("/students")
    public List<StudentInfoWithGuardianPhotoAndName> getAllStudents() {
        log.debug("REST request to get all Students");
        return studentService.findAll();
    }

    /**
     * {@code GET  /students/:id} : get the "id" student.
     *
     * @param id the id of the studentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
        log.debug("REST request to get Student : {}", id);
        Optional<StudentDTO> studentDTO = studentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentDTO);
    }

    /**
     * {@code GET  /students/:id} : get the student with the "id" of the guardian.
     *
     * @param id the id of the studentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 200 (OKAY)}.
     */
    @GetMapping("/students/withGuardian/{id}")
    public List<StudentDTO> getStudentFromGuadianID(@PathVariable Long id) {
        log.debug("REST request to get Student with guardian : {}", id);
        return  studentService.findStudentsWithGuardian(id);
    }
    /**
     * {@code GET  /students/:id} : get the student basic information with the "id" of the guardian.
     *
     * @param id the id of the guardianDto to get the students.
     * @return the {@link ResponseEntity} with status {@code 200 (OKAY)}.
     */
    @GetMapping("/students/basicInfo/withGuardian/{id}")
    public List<StudentPhotoAndName> getStudentBasicInfoFromGuardianID(@PathVariable Long id) {
        log.debug("REST request to get Student with guardian : {}", id);
        return  studentService.findStudentsBasicInfoWithGuardian(id);
    }
    /**
     * {@code GET  /students/:id} : get the student basic information with the "id" of the guardian.
     *
     * @param controlNumber the id of the guardianDto to get the students.
     * @return the {@link ResponseEntity} with status {@code 200 (OKAY)}.
     */
    @GetMapping("/students/verifyControlNumber/{controlNumber}")
    public Boolean verifyStudentControlNumber(@PathVariable Long controlNumber) {
        log.debug("REST request to get Student with guardian : {}", controlNumber);
        return  studentService.verifyControlNumber(controlNumber);
    }

    /**
     * {@code DELETE  /students/:id} : delete the "id" student.
     *
     * @param  id the id of the guardianDto to get the students.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        log.debug("REST request to delete Student : {}", id);
        studentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
