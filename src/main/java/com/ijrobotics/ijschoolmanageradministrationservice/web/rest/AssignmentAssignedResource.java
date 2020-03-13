package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.AssignmentAssignedService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.ClassGroupService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.StudentService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassGroupDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AssignmentAssignedDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.AssignmentAssigned}.
 */
@RestController
@RequestMapping("/api")
public class AssignmentAssignedResource {

    private final Logger log = LoggerFactory.getLogger(AssignmentAssignedResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceAssignmentAssigned";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    ClassGroupService classGroupService;
    @Autowired
    StudentService studentService;

    private final AssignmentAssignedService assignmentAssignedService;

    public AssignmentAssignedResource(AssignmentAssignedService assignmentAssignedService) {
        this.assignmentAssignedService = assignmentAssignedService;
    }

    /**
     * {@code POST  /assignment-assigneds} : Create a new assignmentAssigned.
     *
     * @param assignmentAssignedDTO the assignmentAssignedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assignmentAssignedDTO, or with status {@code 400 (Bad Request)} if the assignmentAssigned has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/assignment-assigneds")
    public ResponseEntity<AssignmentAssignedDTO> createAssignmentAssigned(@RequestBody AssignmentAssignedDTO assignmentAssignedDTO) throws URISyntaxException {
        log.debug("REST request to save AssignmentAssigned : {}", assignmentAssignedDTO);
        if (assignmentAssignedDTO.getId() != null) {
            throw new BadRequestAlertException("A new assignmentAssigned cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssignmentAssignedDTO result = assignmentAssignedService.save(assignmentAssignedDTO);
        return ResponseEntity.created(new URI("/api/assignment-assigneds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    /**
     * {@code POST  /assignment-assigneds} : Create a new assignmentAssigned for each student in a classGroup.
     *
     * @param assignmentId the assignment to assign.
     * @param classGroupId the classGroup to assign.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assignmentAssignedDTO, or with status {@code 400 (Bad Request)} if the assignmentAssigned has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/assignAssignmentToGroup/{assignmentId}/{classGroupId}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<AssignmentAssignedDTO> assignAssignmentToGroup(@PathVariable Long assignmentId,@PathVariable Long classGroupId) throws URISyntaxException {
        Optional<ClassGroupDTO> classGroupDTO= classGroupService.findOne(classGroupId);
        List<AssignmentAssignedDTO> assignmentAssignedDTOS= new ArrayList<>();
        classGroupDTO.ifPresent(groupDTO -> groupDTO.getStudents().forEach(studentDTO -> {
            AssignmentAssignedDTO assignmentAssignedDTO = new AssignmentAssignedDTO();
            assignmentAssignedDTO.setCreationDate(ZonedDateTime.now());
            assignmentAssignedDTO.setAssignmentId(assignmentId);
            assignmentAssignedDTO.setStudentId(studentDTO.getId());
            assignmentAssignedDTO.setDone(false);
            assignmentAssignedDTO.setGrade((float) 0.0);
            assignmentAssignedDTOS.add(assignmentAssignedService.save(assignmentAssignedDTO));
        }));
        return assignmentAssignedDTOS;
    }
    /**
     * {@code POST  /assignment-assigneds} : Create a new assignmentAssigned for each student in a classGroup.
     *
     * @param assignmentId the assignment to assign.
     * @param studentsIds the students to assign.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assignmentAssignedDTO, or with status {@code 400 (Bad Request)} if the assignmentAssigned has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/assignAssignmentToStudents/{assignmentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<AssignmentAssignedDTO> assignAssignmentToGroup(@PathVariable Long assignmentId,@RequestBody List<Long> studentsIds) throws URISyntaxException {
        List<AssignmentAssignedDTO> assignmentAssignedDTOS= new ArrayList<>();
        studentsIds.forEach(studentId -> {
            AssignmentAssignedDTO assignmentAssignedDTO = new AssignmentAssignedDTO();
            assignmentAssignedDTO.setCreationDate(ZonedDateTime.now());
            assignmentAssignedDTO.setAssignmentId(assignmentId);
            assignmentAssignedDTO.setStudentId(studentId);
            assignmentAssignedDTO.setDone(false);
            assignmentAssignedDTO.setGrade((float) 0.0);
            assignmentAssignedDTOS.add(assignmentAssignedService.save(assignmentAssignedDTO));
        });
        return assignmentAssignedDTOS;
    }

    /**
     * {@code PUT  /assignment-assigneds} : Updates an existing assignmentAssigned.
     *
     * @param assignmentAssignedDTO the assignmentAssignedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assignmentAssignedDTO,
     * or with status {@code 400 (Bad Request)} if the assignmentAssignedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assignmentAssignedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/assignment-assigneds")
    public ResponseEntity<AssignmentAssignedDTO> updateAssignmentAssigned(@RequestBody AssignmentAssignedDTO assignmentAssignedDTO) throws URISyntaxException {
        log.debug("REST request to update AssignmentAssigned : {}", assignmentAssignedDTO);
        if (assignmentAssignedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AssignmentAssignedDTO result = assignmentAssignedService.save(assignmentAssignedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assignmentAssignedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /assignment-assigneds} : get all the assignmentAssigneds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assignmentAssigneds in body.
     */
    @GetMapping("/assignment-assigneds")
    public List<AssignmentAssignedDTO> getAllAssignmentAssigneds() {
        log.debug("REST request to get all AssignmentAssigneds");
        return assignmentAssignedService.findAll();
    }

    /**
     * {@code GET  /assignment-assigneds/:id} : get the "id" assignmentAssigned.
     *
     * @param id the id of the assignmentAssignedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assignmentAssignedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/assignment-assigneds/{id}")
    public ResponseEntity<AssignmentAssignedDTO> getAssignmentAssigned(@PathVariable Long id) {
        log.debug("REST request to get AssignmentAssigned : {}", id);
        Optional<AssignmentAssignedDTO> assignmentAssignedDTO = assignmentAssignedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assignmentAssignedDTO);
    }

    /**
     * {@code DELETE  /assignment-assigneds/:id} : delete the "id" assignmentAssigned.
     *
     * @param id the id of the assignmentAssignedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/assignment-assigneds/{id}")
    public ResponseEntity<Void> deleteAssignmentAssigned(@PathVariable Long id) {
        log.debug("REST request to delete AssignmentAssigned : {}", id);
        assignmentAssignedService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
