package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.ExculpatoryAttContentService;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ExculpatoryAttContentDTO;

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
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.ExculpatoryAttContent}.
 */
@RestController
@RequestMapping("/api")
public class ExculpatoryAttContentResource {

    private final Logger log = LoggerFactory.getLogger(ExculpatoryAttContentResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceExculpatoryAttContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExculpatoryAttContentService exculpatoryAttContentService;

    public ExculpatoryAttContentResource(ExculpatoryAttContentService exculpatoryAttContentService) {
        this.exculpatoryAttContentService = exculpatoryAttContentService;
    }

    /**
     * {@code POST  /exculpatory-att-contents} : Create a new exculpatoryAttContent.
     *
     * @param exculpatoryAttContentDTO the exculpatoryAttContentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exculpatoryAttContentDTO, or with status {@code 400 (Bad Request)} if the exculpatoryAttContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exculpatory-att-contents")
    public ResponseEntity<ExculpatoryAttContentDTO> createExculpatoryAttContent(@RequestBody ExculpatoryAttContentDTO exculpatoryAttContentDTO) throws URISyntaxException {
        log.debug("REST request to save ExculpatoryAttContent : {}", exculpatoryAttContentDTO);
        if (exculpatoryAttContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new exculpatoryAttContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        exculpatoryAttContentDTO.setCreationDate(ZonedDateTime.now());
        ExculpatoryAttContentDTO result = exculpatoryAttContentService.save(exculpatoryAttContentDTO);
        return ResponseEntity.created(new URI("/api/exculpatory-att-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exculpatory-att-contents} : Updates an existing exculpatoryAttContent.
     *
     * @param exculpatoryAttContentDTO the exculpatoryAttContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exculpatoryAttContentDTO,
     * or with status {@code 400 (Bad Request)} if the exculpatoryAttContentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exculpatoryAttContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exculpatory-att-contents")
    public ResponseEntity<ExculpatoryAttContentDTO> updateExculpatoryAttContent(@RequestBody ExculpatoryAttContentDTO exculpatoryAttContentDTO) throws URISyntaxException {
        log.debug("REST request to update ExculpatoryAttContent : {}", exculpatoryAttContentDTO);
        if (exculpatoryAttContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExculpatoryAttContentDTO result = exculpatoryAttContentService.save(exculpatoryAttContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exculpatoryAttContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /exculpatory-att-contents} : get all the exculpatoryAttContents.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exculpatoryAttContents in body.
     */
    @GetMapping("/exculpatory-att-contents")
    public List<ExculpatoryAttContentDTO> getAllExculpatoryAttContents(@RequestParam(required = false) String filter) {
        if ("exculpatoryattachments-is-null".equals(filter)) {
            log.debug("REST request to get all ExculpatoryAttContents where exculpatoryAttachments is null");
            return exculpatoryAttContentService.findAllWhereExculpatoryAttachmentsIsNull();
        }
        log.debug("REST request to get all ExculpatoryAttContents");
        return exculpatoryAttContentService.findAll();
    }

    /**
     * {@code GET  /exculpatory-att-contents/:id} : get the "id" exculpatoryAttContent.
     *
     * @param id the id of the exculpatoryAttContentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exculpatoryAttContentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exculpatory-att-contents/{id}")
    public ResponseEntity<ExculpatoryAttContentDTO> getExculpatoryAttContent(@PathVariable Long id) {
        log.debug("REST request to get ExculpatoryAttContent : {}", id);
        Optional<ExculpatoryAttContentDTO> exculpatoryAttContentDTO = exculpatoryAttContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(exculpatoryAttContentDTO);
    }

    /**
     * {@code DELETE  /exculpatory-att-contents/:id} : delete the "id" exculpatoryAttContent.
     *
     * @param id the id of the exculpatoryAttContentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exculpatory-att-contents/{id}")
    public ResponseEntity<Void> deleteExculpatoryAttContent(@PathVariable Long id) {
        log.debug("REST request to delete ExculpatoryAttContent : {}", id);
        exculpatoryAttContentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
