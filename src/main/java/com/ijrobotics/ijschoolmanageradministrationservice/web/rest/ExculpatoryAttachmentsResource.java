package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.ExculpatoryAttachments;
import com.ijrobotics.ijschoolmanageradministrationservice.service.ExculpatoryAttContentService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.ExculpatoryAttachmentsService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ExculpatoryAttContentDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.ExculpatoryAttContentMapper;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.ExculpatoryAttachmentsMapper;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ExculpatoryAttachmentsDTO;

import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.DocumentNotFoundException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.ExculpatoryAttachments}.
 */
@RestController
@RequestMapping("/api")
public class ExculpatoryAttachmentsResource {

    private final Logger log = LoggerFactory.getLogger(ExculpatoryAttachmentsResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceExculpatoryAttachments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
    private
    ExculpatoryAttContentService attachmentsContentService;
    @Autowired
    private
    ExculpatoryAttachmentsMapper attachmentsContentMapper;
    private final ExculpatoryAttachmentsService exculpatoryAttachmentsService;

    public ExculpatoryAttachmentsResource(ExculpatoryAttachmentsService exculpatoryAttachmentsService) {
        this.exculpatoryAttachmentsService = exculpatoryAttachmentsService;
    }

    /**
     * {@code POST  /exculpatory-attachments} : Create a new exculpatoryAttachments.
     *
     * @param exculpatoryAttachmentsDTO the exculpatoryAttachmentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exculpatoryAttachmentsDTO, or with status {@code 400 (Bad Request)} if the exculpatoryAttachments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exculpatory-attachments")
    public ResponseEntity<ExculpatoryAttachmentsDTO> createExculpatoryAttachments(@RequestBody ExculpatoryAttachmentsDTO exculpatoryAttachmentsDTO) throws URISyntaxException {
        log.debug("REST request to save ExculpatoryAttachments : {}", exculpatoryAttachmentsDTO);
        if (exculpatoryAttachmentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new exculpatoryAttachments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExculpatoryAttachmentsDTO result = exculpatoryAttachmentsService.save(exculpatoryAttachmentsDTO);
        return ResponseEntity.created(new URI("/api/exculpatory-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exculpatory-attachments} : Updates an existing exculpatoryAttachments.
     *
     * @param exculpatoryAttachmentsDTO the exculpatoryAttachmentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exculpatoryAttachmentsDTO,
     * or with status {@code 400 (Bad Request)} if the exculpatoryAttachmentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exculpatoryAttachmentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exculpatory-attachments/{id}")
    public ResponseEntity<ExculpatoryAttachmentsDTO> updateExculpatoryAttachments(@RequestBody ExculpatoryAttachmentsDTO exculpatoryAttachmentsDTO,@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update ExculpatoryAttachments : {}", exculpatoryAttachmentsDTO);
        Optional<ExculpatoryAttachmentsDTO> attachmentsDTOUpdate = exculpatoryAttachmentsService.findOne(id);
        if (!attachmentsDTOUpdate.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (exculpatoryAttachmentsDTO.getExculpatoryId()!=null){
            attachmentsDTOUpdate.get().setExculpatoryId(exculpatoryAttachmentsDTO.getExculpatoryId());
        }
        if (exculpatoryAttachmentsDTO.getSize()!=null){
            attachmentsDTOUpdate.get().setSize(exculpatoryAttachmentsDTO.getSize());
        }
        if (exculpatoryAttachmentsDTO.getTitle()!=null){
            attachmentsDTOUpdate.get().setTitle(exculpatoryAttachmentsDTO.getTitle());
        }
        if (exculpatoryAttachmentsDTO.getMimeType()!=null){
            attachmentsDTOUpdate.get().setMimeType(exculpatoryAttachmentsDTO.getMimeType());
        }
        if (exculpatoryAttachmentsDTO.getExculpatoryAttContentId()!=null){
            attachmentsDTOUpdate.get().setExculpatoryAttContentId(exculpatoryAttachmentsDTO.getExculpatoryAttContentId());
        }
        ExculpatoryAttachmentsDTO result = exculpatoryAttachmentsService.save(exculpatoryAttachmentsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exculpatoryAttachmentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /exculpatory-attachments} : get all the exculpatoryAttachments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exculpatoryAttachments in body.
     */
    @GetMapping("/exculpatory-attachments")
    public List<ExculpatoryAttachmentsDTO> getAllExculpatoryAttachments() {
        log.debug("REST request to get all ExculpatoryAttachments");
        return exculpatoryAttachmentsService.findAll();
    }

    /**
     * {@code GET  /exculpatory-attachments/:id} : get the "id" exculpatoryAttachments.
     *
     * @param id the id of the exculpatoryAttachmentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exculpatoryAttachmentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exculpatory-attachments/{id}")
    public ResponseEntity<ExculpatoryAttachmentsDTO> getExculpatoryAttachments(@PathVariable Long id) {
        log.debug("REST request to get ExculpatoryAttachments : {}", id);
        Optional<ExculpatoryAttachmentsDTO> exculpatoryAttachmentsDTO = exculpatoryAttachmentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(exculpatoryAttachmentsDTO);
    }

    /**
     * {@code DELETE  /exculpatory-attachments/:id} : delete the "id" exculpatoryAttachments.
     *
     * @param id the id of the exculpatoryAttachmentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exculpatory-attachments/{id}")
    public ResponseEntity<Void> deleteExculpatoryAttachments(@PathVariable Long id) {
        log.debug("REST request to delete ExculpatoryAttachments : {}", id);
        exculpatoryAttachmentsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/exculpatory-attachments/{id}/$content")
    @Timed
    public ResponseEntity<byte[]> getDocumentContent(@PathVariable Long id) {
        ExculpatoryAttachments attachment = attachmentsContentMapper.toEntity(exculpatoryAttachmentsService.findOne(id)
            .orElseThrow(DocumentNotFoundException::new));

        Optional<ExculpatoryAttContentDTO> attachmentsContent = attachmentsContentService.findOne(attachment.getExculpatoryAttContent().getId());
        if (attachmentsContent.isPresent()){
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getMimeType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getTitle() + "\"")
                .body(attachmentsContent.get().getData());
        }else {
            return    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
        }
    }
}
