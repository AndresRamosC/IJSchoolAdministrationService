package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.AttachmentsContentService;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AttachmentsContentDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.AttachmentsContent}.
 */
@RestController
@RequestMapping("/api")
public class AttachmentsContentResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentsContentResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceAttachmentsContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachmentsContentService attachmentsContentService;

    public AttachmentsContentResource(AttachmentsContentService attachmentsContentService) {
        this.attachmentsContentService = attachmentsContentService;
    }

    /**
     * {@code POST  /attachments-contents} : Create a new attachmentsContent.
     *
     * @param attachmentsContentDTO the attachmentsContentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachmentsContentDTO, or with status {@code 400 (Bad Request)} if the attachmentsContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachments-contents")
    public ResponseEntity<AttachmentsContentDTO> createAttachmentsContent(@RequestBody AttachmentsContentDTO attachmentsContentDTO) throws URISyntaxException {
        log.debug("REST request to save AttachmentsContent : {}", attachmentsContentDTO);
        if (attachmentsContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new attachmentsContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        attachmentsContentDTO.setCreationDate(ZonedDateTime.now());
        AttachmentsContentDTO result = attachmentsContentService.save(attachmentsContentDTO);
        return ResponseEntity.created(new URI("/api/attachments-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attachments-contents} : Updates an existing attachmentsContent.
     *
     * @param attachmentsContentDTO the attachmentsContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentsContentDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentsContentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachmentsContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachments-contents")
    public ResponseEntity<AttachmentsContentDTO> updateAttachmentsContent(@RequestBody AttachmentsContentDTO attachmentsContentDTO) throws URISyntaxException {
        log.debug("REST request to update AttachmentsContent : {}", attachmentsContentDTO);
        if (attachmentsContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttachmentsContentDTO result = attachmentsContentService.save(attachmentsContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attachmentsContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attachments-contents} : get all the attachmentsContents.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachmentsContents in body.
     */
    @GetMapping("/attachments-contents")
    public List<AttachmentsContentDTO> getAllAttachmentsContents(@RequestParam(required = false) String filter) {
        if ("attachments-is-null".equals(filter)) {
            log.debug("REST request to get all AttachmentsContents where attachments is null");
            return attachmentsContentService.findAllWhereAttachmentsIsNull();
        }
        log.debug("REST request to get all AttachmentsContents");
        return attachmentsContentService.findAll();
    }

    /**
     * {@code GET  /attachments-contents/:id} : get the "id" attachmentsContent.
     *
     * @param id the id of the attachmentsContentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachmentsContentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachments-contents/{id}")
    public ResponseEntity<AttachmentsContentDTO> getAttachmentsContent(@PathVariable Long id) {
        log.debug("REST request to get AttachmentsContent : {}", id);
        Optional<AttachmentsContentDTO> attachmentsContentDTO = attachmentsContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachmentsContentDTO);
    }

    /**
     * {@code DELETE  /attachments-contents/:id} : delete the "id" attachmentsContent.
     *
     * @param id the id of the attachmentsContentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachments-contents/{id}")
    public ResponseEntity<Void> deleteAttachmentsContent(@PathVariable Long id) {
        log.debug("REST request to delete AttachmentsContent : {}", id);
        attachmentsContentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
