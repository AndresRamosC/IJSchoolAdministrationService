package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.GroupNoticesService;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GroupNoticesDTO;

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
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.GroupNotices}.
 */
@RestController
@RequestMapping("/api")
public class GroupNoticesResource {

    private final Logger log = LoggerFactory.getLogger(GroupNoticesResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceGroupNotices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupNoticesService groupNoticesService;

    public GroupNoticesResource(GroupNoticesService groupNoticesService) {
        this.groupNoticesService = groupNoticesService;
    }

    /**
     * {@code POST  /group-notices} : Create a new groupNotices.
     *
     * @param groupNoticesDTO the groupNoticesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupNoticesDTO, or with status {@code 400 (Bad Request)} if the groupNotices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-notices")
    public ResponseEntity<GroupNoticesDTO> createGroupNotices(@RequestBody GroupNoticesDTO groupNoticesDTO) throws URISyntaxException {
        log.debug("REST request to save GroupNotices : {}", groupNoticesDTO);
        if (groupNoticesDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupNotices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupNoticesDTO result = groupNoticesService.save(groupNoticesDTO);
        return ResponseEntity.created(new URI("/api/group-notices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-notices} : Updates an existing groupNotices.
     *
     * @param groupNoticesDTO the groupNoticesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupNoticesDTO,
     * or with status {@code 400 (Bad Request)} if the groupNoticesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupNoticesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-notices")
    public ResponseEntity<GroupNoticesDTO> updateGroupNotices(@RequestBody GroupNoticesDTO groupNoticesDTO,@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update GroupNotices : {}", groupNoticesDTO);
        Optional<GroupNoticesDTO> groupNoticesDTOUpdate= groupNoticesService.findOne(id);
        if (!groupNoticesDTOUpdate.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (groupNoticesDTO.getClassGroupId()!=null){
            groupNoticesDTOUpdate.get().setClassGroupId(groupNoticesDTO.getClassGroupId());
        }
        if (groupNoticesDTO.getDescription()!=null){
            groupNoticesDTOUpdate.get().setDescription(groupNoticesDTO.getDescription());
        }
        if (groupNoticesDTO.getTitle()!=null){
            groupNoticesDTOUpdate.get().setTitle(groupNoticesDTO.getTitle());
        }

        GroupNoticesDTO result = groupNoticesService.save(groupNoticesDTOUpdate.get());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupNoticesDTOUpdate.get().getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /group-notices} : get all the groupNotices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupNotices in body.
     */
    @GetMapping("/group-notices")
    public List<GroupNoticesDTO> getAllGroupNotices() {
        log.debug("REST request to get all GroupNotices");
        return groupNoticesService.findAll();
    }

    /**
     * {@code GET  /group-notices/:id} : get the "id" groupNotices.
     *
     * @param id the id of the groupNoticesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupNoticesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-notices/{id}")
    public ResponseEntity<GroupNoticesDTO> getGroupNotices(@PathVariable Long id) {
        log.debug("REST request to get GroupNotices : {}", id);
        Optional<GroupNoticesDTO> groupNoticesDTO = groupNoticesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupNoticesDTO);
    }

    /**
     * {@code DELETE  /group-notices/:id} : delete the "id" groupNotices.
     *
     * @param id the id of the groupNoticesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-notices/{id}")
    public ResponseEntity<Void> deleteGroupNotices(@PathVariable Long id) {
        log.debug("REST request to delete GroupNotices : {}", id);
        groupNoticesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
