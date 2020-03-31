package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.ContactService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.GuardianService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.PersonService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ContactDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.guardianDtos.GuardianDashBoardInfoDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.guardianDtos.NewGuardianDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GuardianDTO;

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
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Guardian}.
 */
@RestController
@RequestMapping("/api")
public class GuardianResource {

    private final Logger log = LoggerFactory.getLogger(GuardianResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceGuardian";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GuardianService guardianService;

    public GuardianResource(GuardianService guardianService) {
        this.guardianService = guardianService;
    }

    /**
     * {@code POST  /guardians} : Create a new guardian.
     *
     * @param guardianDTO the guardianDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new guardianDTO, or with status {@code 400 (Bad Request)} if the guardian has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/guardians")
    public ResponseEntity<GuardianDTO> createGuardian(@RequestBody NewGuardianDto newGuardianDto) throws URISyntaxException {
        GuardianDTO result = guardianService.saveFullGuardian(newGuardianDto);
        return ResponseEntity.created(new URI("/api/guardians/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /guardians} : Updates an existing guardian.
     *
     * @param guardianDTO the guardianDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guardianDTO,
     * or with status {@code 400 (Bad Request)} if the guardianDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the guardianDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/guardians/{id}")
    public ResponseEntity<GuardianDTO> updateGuardian(@RequestBody GuardianDTO guardianDTO,@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update Guardian : {}", guardianDTO);
        Optional<GuardianDTO> guardianDTOUpdate = guardianService.findOne(id);
        if ( !guardianDTOUpdate.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (guardianDTO.getEducationLevel()!=null){
            guardianDTOUpdate.get().setEducationLevel(guardianDTO.getEducationLevel());
        }
        if (guardianDTO.getOccupation()!=null){
            guardianDTOUpdate.get().setOccupation(guardianDTO.getOccupation());
        }
        if (guardianDTO.getWorkAddress()!=null){
            guardianDTOUpdate.get().setWorkAddress(guardianDTO.getWorkAddress());
        }
        if (guardianDTO.getPersonId()!=null){
            guardianDTOUpdate.get().setPersonId(guardianDTO.getPersonId());
        }

        GuardianDTO result = guardianService.save(guardianDTOUpdate.get());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guardianDTOUpdate.get().getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /guardians} : get all the guardians.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of guardians in body.
     */
    @GetMapping("/guardians")
    public List<GuardianDTO> getAllGuardians() {
        log.debug("REST request to get all Guardians");
        return guardianService.findAll();
    }

    /**
     * {@code GET  /guardians/:id} : get the "id" guardian.
     *
     * @param id the id of the guardianDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the guardianDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/guardians/{id}")
    public ResponseEntity<GuardianDTO> getGuardian(@PathVariable Long id) {
        log.debug("REST request to get Guardian : {}", id);
        Optional<GuardianDTO> guardianDTO = guardianService.findOne(id);
        return ResponseUtil.wrapOrNotFound(guardianDTO);
    }

    /**
     * {@code DELETE  /guardians/:id} : delete the "id" guardian.
     *
     * @param id the id of the guardianDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/guardians/{id}")
    public ResponseEntity<Void> deleteGuardian(@PathVariable Long id) {
        log.debug("REST request to delete Guardian : {}", id);
        guardianService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
