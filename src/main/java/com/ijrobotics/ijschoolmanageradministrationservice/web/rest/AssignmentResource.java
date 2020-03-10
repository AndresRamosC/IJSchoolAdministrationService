package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Assignment;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.Attachments;
import com.ijrobotics.ijschoolmanageradministrationservice.service.AssignmentService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.AssignmentAndAttachmentsDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.AssignmentFullDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AssignmentMapper;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AttachmentFileMapper;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AttachmentsMapper;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AssignmentDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Assignment}.
 */
@RestController
@RequestMapping("/api")
public class AssignmentResource {

    private final Logger log = LoggerFactory.getLogger(AssignmentResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceAssignment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssignmentService assignmentService;
    private final AttachmentFileMapper attachmentFileMapper;
    private final AssignmentMapper assignmentMapper;

    public AssignmentResource(AssignmentService assignmentService,AttachmentFileMapper attachmentFileMapper,AssignmentMapper assignmentMapper) {
        this.assignmentService = assignmentService;
        this.attachmentFileMapper= attachmentFileMapper;
        this.assignmentMapper=assignmentMapper;
    }

    /**
     * {@code POST  /assignments} : Create a new assignment.
     *
     * @param assignmentDTO the assignmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assignmentDTO, or with status {@code 400 (Bad Request)} if the assignment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/assignments")
    public ResponseEntity<AssignmentDTO> createAssignment(@RequestBody AssignmentDTO assignmentDTO) throws URISyntaxException {
        log.debug("REST request to save Assignment : {}", assignmentDTO);
        if (assignmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new assignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        assignmentDTO.setCreationDate(ZonedDateTime.now());
        AssignmentDTO result = assignmentService.save(assignmentDTO);
        return ResponseEntity.created(new URI("/api/assignments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    @PostMapping("/v2/assignments")
    @Timed
    public ResponseEntity<AssignmentDTO> createAssignment2(@Valid @RequestPart AssignmentDTO assignmentDTO, @RequestPart List<MultipartFile> files) throws URISyntaxException, IOException {
        log.debug("REST request to save Assignment : {}", assignmentDTO);
        if (assignmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new Assignment cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Assignment assignment=assignmentMapper.toEntity(assignmentDTO);
        Set<Attachments> documents = attachmentFileMapper.multiPartFilesToDocuments(files);
        documents.forEach(assignment::addAttachments);

        AssignmentDTO result = assignmentService.save(assignmentDTO);
        return ResponseEntity.created(new URI("/api/cars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName,false,ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /assignments} : Updates an existing assignment.
     *
     * @param assignmentDTO the assignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assignmentDTO,
     * or with status {@code 400 (Bad Request)} if the assignmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/assignments/{id}")
    public ResponseEntity<AssignmentDTO> updateAssignment(@RequestBody AssignmentDTO assignmentDTO,@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update Assignment : {}", assignmentDTO);
        Optional<AssignmentDTO> assignmentDTOUpdate = assignmentService.findOne(id);
        if (!assignmentDTOUpdate.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (assignmentDTO.getClassGroupId()!=null){
            assignmentDTOUpdate.get().setClassGroupId(assignmentDTO.getClassGroupId());
        }
        if (assignmentDTO.getDueDate()!=null){
            assignmentDTOUpdate.get().setDueDate(assignmentDTO.getDueDate());
        }
        if (assignmentDTO.getDescription()!=null){
            assignmentDTOUpdate.get().setDescription(assignmentDTO.getDescription());
        }
        if (assignmentDTO.getTitle()!=null){
            assignmentDTOUpdate.get().setTitle(assignmentDTO.getTitle());
        }
        AssignmentDTO result = assignmentService.save(assignmentDTOUpdate.get());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assignmentDTOUpdate.get().getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /assignments} : get all the assignments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assignments in body.
     */
    @GetMapping("/assignments")
    public List<AssignmentDTO> getAllAssignments() {
        log.debug("REST request to get all Assignments");
        return assignmentService.findAll();
    }
    /**
     * {@code GET  /assignments} : get all the assignments for a teacher with a specific classId.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assignments in body.
     */
    @GetMapping("/assignments/classGroup/{classGroupId}")
    public List<AssignmentAndAttachmentsDto> getAllAssignmentsFromAClassGroup(@PathVariable Long classGroupId) {
        log.debug("REST request to get all Assignments of one ClassGroup");
        return assignmentService.findAllWithClassGroupId(classGroupId);
    }
    /**
     * {@code GET  /assignments} : get all the assignments for a Guardian with a specific studentId.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assignments in body.
     */
    @GetMapping("/assignments/studentId/{studentId}")
    public List<AssignmentFullDto> getAllAssignmentsFromAStudentId(@PathVariable Long studentId) {
        log.debug("REST request to get all Assignments of one Student");
        return assignmentService.findAllWithStudentId(studentId);
    }
    /**
     * {@code GET  /assignments} : get all the assignments for a Guardian with a specific studentId and a specific groupId.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assignments in body.
     */
    @GetMapping("/assignments/studentAndGroupId/{studentId}/{classGroupId}")
    public List<AssignmentFullDto> getAllAssignmentsFromAStudentId(@PathVariable Long studentId,@PathVariable Long classGroupId) {
        log.debug("REST request to get all Assignments of one Student");
        return assignmentService.findAllWithClassAndStudentId(studentId,classGroupId);
    }
    /**
     * {@code GET  /assignments} : get all the assignments for a teacher with a specific classId.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assignments in body.
     */
    @GetMapping("/assignments/classGroup/{classGroupId}/{month}")
    public List<AssignmentAndAttachmentsDto> getAllAssignmentsFromAClassGroupAndMonth(@PathVariable Long classGroupId,@PathVariable String month) {
        log.debug("REST request to get all Assignments of one ClassGroup");
        return assignmentService.findAllWithClassGroupIdAndMonth(classGroupId,month);
    }
    /**
     * {@code GET  /assignments} : get all the assignments for a Guardian with a specific studentId.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assignments in body.
     */
    @GetMapping("/assignments/studentId/{studentId}/{month}")
    public List<AssignmentFullDto> getAllAssignmentsFromAStudentIdAndMonth(@PathVariable Long studentId,@PathVariable String month) {
        log.debug("REST request to get all Assignments of one Student");
        return assignmentService.findAllWithStudentIdAndMonth(studentId,month);
    }
    /**
     * {@code GET  /assignments} : get all the assignments for a Guardian with a specific studentId and a specific groupId.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assignments in body.
     */
    @GetMapping("/assignments/studentAndGroupId/{studentId}/{classGroupId}/{month}")
    public List<AssignmentFullDto> getAllAssignmentsFromAStudentIdAndMonth(@PathVariable Long studentId,@PathVariable Long classGroupId,@PathVariable String month) {
        log.debug("REST request to get all Assignments of one Student");
        return assignmentService.findAllWithClassAndStudentIdAndMonth(studentId,classGroupId,month);
    }


    /**
     * {@code GET  /assignments/:id} : get the "id" assignment.
     *
     * @param id the id of the assignmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assignmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/assignments/{id}")
    public ResponseEntity<AssignmentDTO> getAssignment(@PathVariable Long id) {
        log.debug("REST request to get Assignment : {}", id);
        Optional<AssignmentDTO> assignmentDTO = assignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assignmentDTO);
    }

    /**
     * {@code DELETE  /assignments/:id} : delete the "id" assignment.
     *
     * @param id the id of the assignmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/assignments/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        log.debug("REST request to delete Assignment : {}", id);
        assignmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
