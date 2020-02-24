package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.ExculpatoryService;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ExculpatoryDTO;

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
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Exculpatory}.
 */
@RestController
@RequestMapping("/api")
public class ExculpatoryResource {

    private final Logger log = LoggerFactory.getLogger(ExculpatoryResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceExculpatory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExculpatoryService exculpatoryService;

    public ExculpatoryResource(ExculpatoryService exculpatoryService) {
        this.exculpatoryService = exculpatoryService;
    }

    /**
     * {@code POST  /exculpatories} : Create a new exculpatory.
     *
     * @param exculpatoryDTO the exculpatoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exculpatoryDTO, or with status {@code 400 (Bad Request)} if the exculpatory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exculpatories")
    public ResponseEntity<ExculpatoryDTO> createExculpatory(@RequestBody ExculpatoryDTO exculpatoryDTO) throws URISyntaxException {
        log.debug("REST request to save Exculpatory : {}", exculpatoryDTO);
        if (exculpatoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new exculpatory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExculpatoryDTO result = exculpatoryService.save(exculpatoryDTO);
        return ResponseEntity.created(new URI("/api/exculpatories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exculpatories} : Updates an existing exculpatory.
     *
     * @param exculpatoryDTO the exculpatoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exculpatoryDTO,
     * or with status {@code 400 (Bad Request)} if the exculpatoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exculpatoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exculpatories")
    public ResponseEntity<ExculpatoryDTO> updateExculpatory(@RequestBody ExculpatoryDTO exculpatoryDTO,@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update Exculpatory : {}", exculpatoryDTO);
        Optional<ExculpatoryDTO> exculpatoryDTOUpdate = exculpatoryService.findOne(id);
        if (!exculpatoryDTOUpdate.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (exculpatoryDTO.getDescription()!=null){
            exculpatoryDTOUpdate.get().setDescription(exculpatoryDTO.getDescription());
        }
        if (exculpatoryDTO.getGuardianId()!=null){
            exculpatoryDTOUpdate.get().setGuardianId(exculpatoryDTO.getGuardianId());
        }
        if (exculpatoryDTO.getLeaveDateAndHour()!=null){
            exculpatoryDTOUpdate.get().setLeaveDateAndHour(exculpatoryDTO.getLeaveDateAndHour());
        }
        if (exculpatoryDTO.getStudentId()!=null){
            exculpatoryDTOUpdate.get().setStudentId(exculpatoryDTO.getStudentId());
        }
        ExculpatoryDTO result = exculpatoryService.save(exculpatoryDTOUpdate.get());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exculpatoryDTOUpdate.get().getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /exculpatories} : get all the exculpatories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exculpatories in body.
     */
    @GetMapping("/exculpatories")
    public List<ExculpatoryDTO> getAllExculpatories() {
        log.debug("REST request to get all Exculpatories");
        return exculpatoryService.findAll();
    }

    /**
     * {@code GET  /exculpatories/:id} : get the "id" exculpatory.
     *
     * @param id the id of the exculpatoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exculpatoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exculpatories/{id}")
    public ResponseEntity<ExculpatoryDTO> getExculpatory(@PathVariable Long id) {
        log.debug("REST request to get Exculpatory : {}", id);
        Optional<ExculpatoryDTO> exculpatoryDTO = exculpatoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(exculpatoryDTO);
    }

    /**
     * {@code DELETE  /exculpatories/:id} : delete the "id" exculpatory.
     *
     * @param id the id of the exculpatoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exculpatories/{id}")
    public ResponseEntity<Void> deleteExculpatory(@PathVariable Long id) {
        log.debug("REST request to delete Exculpatory : {}", id);
        exculpatoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
