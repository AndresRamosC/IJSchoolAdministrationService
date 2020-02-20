package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.GradeService;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GradeDTO;

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

/**
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Grade}.
 */
@RestController
@RequestMapping("/api")
public class GradeResource {

    private final Logger log = LoggerFactory.getLogger(GradeResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceGrade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GradeService gradeService;

    public GradeResource(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    /**
     * {@code POST  /grades} : Create a new grade.
     *
     * @param gradeDTO the gradeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gradeDTO, or with status {@code 400 (Bad Request)} if the grade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grades")
    public ResponseEntity<GradeDTO> createGrade(@RequestBody GradeDTO gradeDTO) throws URISyntaxException {
        log.debug("REST request to save Grade : {}", gradeDTO);
        if (gradeDTO.getId() != null) {
            throw new BadRequestAlertException("A new grade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GradeDTO result = gradeService.save(gradeDTO);
        return ResponseEntity.created(new URI("/api/grades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grades} : Updates an existing grade.
     *
     * @param gradeDTO the gradeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gradeDTO,
     * or with status {@code 400 (Bad Request)} if the gradeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gradeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grades/{id}")
    public ResponseEntity<GradeDTO> updateGrade(@RequestBody GradeDTO gradeDTO,@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update Grade : {}", gradeDTO);
        Optional<GradeDTO> gradeDTOUpdate = gradeService.findOne(id);
        if ( !gradeDTOUpdate.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (gradeDTO.getSemester()!=null){
            gradeDTOUpdate.get().setSemester(gradeDTO.getSemester());
        }
        if (gradeDTO.getGrade()!=null){
            gradeDTOUpdate.get().setGrade(gradeDTO.getGrade());
        }
        if (gradeDTO.getClassGroupId()!=null){
            gradeDTOUpdate.get().setClassGroupId(gradeDTO.getClassGroupId());
        }
        if (gradeDTO.getStudentId()!=null){
            gradeDTOUpdate.get().setStudentId(gradeDTO.getStudentId());
        }
        GradeDTO result = gradeService.save(gradeDTOUpdate.get());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gradeDTOUpdate.get().getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grades} : get all the grades.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grades in body.
     */
    @GetMapping("/grades")
    public List<GradeDTO> getAllGrades() {
        log.debug("REST request to get all Grades");
        return gradeService.findAll();
    }

    /**
     * {@code GET  /grades/:id} : get the "id" grade.
     *
     * @param id the id of the gradeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gradeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grades/{id}")
    public ResponseEntity<GradeDTO> getGrade(@PathVariable Long id) {
        log.debug("REST request to get Grade : {}", id);
        Optional<GradeDTO> gradeDTO = gradeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gradeDTO);
    }

    /**
     * {@code DELETE  /grades/:id} : delete the "id" grade.
     *
     * @param id the id of the gradeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grades/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        log.debug("REST request to delete Grade : {}", id);
        gradeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
