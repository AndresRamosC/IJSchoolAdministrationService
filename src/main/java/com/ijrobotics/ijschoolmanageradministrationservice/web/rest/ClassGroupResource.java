package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.ClassGroupService;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassGroupDTO;

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
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.ClassGroup}.
 */
@RestController
@RequestMapping("/api")
public class ClassGroupResource {

    private final Logger log = LoggerFactory.getLogger(ClassGroupResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceClassGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassGroupService classGroupService;

    public ClassGroupResource(ClassGroupService classGroupService) {
        this.classGroupService = classGroupService;
    }

    /**
     * {@code POST  /class-groups} : Create a new classGroup.
     *
     * @param classGroupDTO the classGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classGroupDTO, or with status {@code 400 (Bad Request)} if the classGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/class-groups")
    public ResponseEntity<ClassGroupDTO> createClassGroup(@RequestBody ClassGroupDTO classGroupDTO) throws URISyntaxException {
        log.debug("REST request to save ClassGroup : {}", classGroupDTO);
        if (classGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new classGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassGroupDTO result = classGroupService.save(classGroupDTO);
        return ResponseEntity.created(new URI("/api/class-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /class-groups} : Updates an existing classGroup.
     *
     * @param classGroupDTO the classGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classGroupDTO,
     * or with status {@code 400 (Bad Request)} if the classGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/class-groups/{id}")
    public ResponseEntity<ClassGroupDTO> updateClassGroup(@RequestBody ClassGroupDTO classGroupDTO,@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update ClassGroup : {}", classGroupDTO);
        Optional<ClassGroupDTO> classGroupDTOUpdate = classGroupService.findOne(id);
        if ( !classGroupDTOUpdate.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (classGroupDTO.getGroupCode() !=null){
            classGroupDTOUpdate.get().setGroupCode(classGroupDTO.getGroupCode());
        }
        if (classGroupDTO.getStartHour() !=null){
            classGroupDTOUpdate.get().setStartHour(classGroupDTO.getStartHour());
        }
        if (classGroupDTO.getEndHour() !=null){
            classGroupDTOUpdate.get().setEndHour(classGroupDTO.getEndHour());
        }
        if (classGroupDTO.getClassRoom() !=null){
            classGroupDTOUpdate.get().setClassRoom(classGroupDTO.getClassRoom());
        }
        if (classGroupDTO.getSize() !=null){
            classGroupDTOUpdate.get().setSize(classGroupDTO.getSize());
        }
        if (classGroupDTO.getSubjectId() !=null){
            classGroupDTOUpdate.get().setSubjectId(classGroupDTO.getSubjectId());
        }
        if (classGroupDTO.getTeacherId() !=null){
            classGroupDTOUpdate.get().setTeacherId(classGroupDTO.getTeacherId());
        }
        ClassGroupDTO result = classGroupService.save(classGroupDTOUpdate.get());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classGroupDTOUpdate.get().getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /class-groups} : get all the classGroups.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classGroups in body.
     */
    @GetMapping("/class-groups")
    public List<ClassGroupDTO> getAllClassGroups(@RequestParam(required = false) String filter) {
        if ("grade-is-null".equals(filter)) {
            log.debug("REST request to get all ClassGroups where grade is null");
            return classGroupService.findAllWhereGradeIsNull();
        }
        if ("assignment-is-null".equals(filter)) {
            log.debug("REST request to get all ClassGroups where assignment is null");
            return classGroupService.findAllWhereAssignmentIsNull();
        }
        log.debug("REST request to get all ClassGroups");
        return classGroupService.findAll();
    }

    /**
     * {@code GET  /class-groups/:id} : get the "id" classGroup.
     *
     * @param id the id of the classGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-groups/{id}")
    public ResponseEntity<ClassGroupDTO> getClassGroup(@PathVariable Long id) {
        log.debug("REST request to get ClassGroup : {}", id);
        Optional<ClassGroupDTO> classGroupDTO = classGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classGroupDTO);
    }

    /**
     * {@code DELETE  /class-groups/:id} : delete the "id" classGroup.
     *
     * @param id the id of the classGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/class-groups/{id}")
    public ResponseEntity<Void> deleteClassGroup(@PathVariable Long id) {
        log.debug("REST request to delete ClassGroup : {}", id);
        classGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
