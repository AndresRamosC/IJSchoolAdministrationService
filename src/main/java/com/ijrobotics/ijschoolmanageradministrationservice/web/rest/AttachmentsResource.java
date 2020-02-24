package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.AttachmentsService;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AttachmentsDTO;

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
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Attachments}.
 */
@RestController
@RequestMapping("/api")
public class AttachmentsResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentsResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceAttachments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachmentsService attachmentsService;

    public AttachmentsResource(AttachmentsService attachmentsService) {
        this.attachmentsService = attachmentsService;
    }

    /**
     * {@code POST  /attachments} : Create a new attachments.
     *
     * @param attachmentsDTO the attachmentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachmentsDTO, or with status {@code 400 (Bad Request)} if the attachments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachments")
    public ResponseEntity<AttachmentsDTO> createAttachments(@RequestBody AttachmentsDTO attachmentsDTO) throws URISyntaxException {
        log.debug("REST request to save Attachments : {}", attachmentsDTO);
        if (attachmentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new attachments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttachmentsDTO result = attachmentsService.save(attachmentsDTO);
        return ResponseEntity.created(new URI("/api/attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attachments} : Updates an existing attachments.
     *
     * @param attachmentsDTO the attachmentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentsDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachmentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachments")
    public ResponseEntity<AttachmentsDTO> updateAttachments(@RequestBody AttachmentsDTO attachmentsDTO,@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update Attachments : {}", attachmentsDTO);
        Optional<AttachmentsDTO> attachmentsDTOUpdate = attachmentsService.findOne(id);
        if (!attachmentsDTOUpdate.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (attachmentsDTO.getAssignmentId()!=null){
            attachmentsDTOUpdate.get().setAssignmentId(attachmentsDTO.getAssignmentId());
        }
        if (attachmentsDTO.getAttachment()!=null){
            attachmentsDTOUpdate.get().setAttachment(attachmentsDTO.getAttachment());
        }
        if (attachmentsDTO.getAttachmentContentType()!=null){
            attachmentsDTOUpdate.get().setAttachmentContentType(attachmentsDTO.getAttachmentContentType());
        }
        AttachmentsDTO result = attachmentsService.save(attachmentsDTOUpdate.get());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attachmentsDTOUpdate.get().getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attachments} : get all the attachments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachments in body.
     */
    @GetMapping("/attachments")
    public List<AttachmentsDTO> getAllAttachments() {
        log.debug("REST request to get all Attachments");
        return attachmentsService.findAll();
    }

    /**
     * {@code GET  /attachments/:id} : get the "id" attachments.
     *
     * @param id the id of the attachmentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachmentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachments/{id}")
    public ResponseEntity<AttachmentsDTO> getAttachments(@PathVariable Long id) {
        log.debug("REST request to get Attachments : {}", id);
        Optional<AttachmentsDTO> attachmentsDTO = attachmentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachmentsDTO);
    }

    /**
     * {@code DELETE  /attachments/:id} : delete the "id" attachments.
     *
     * @param id the id of the attachmentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachments/{id}")
    public ResponseEntity<Void> deleteAttachments(@PathVariable Long id) {
        log.debug("REST request to delete Attachments : {}", id);
        attachmentsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
