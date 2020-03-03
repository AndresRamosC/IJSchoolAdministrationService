package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.UserExtendService;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.UserExtendDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.UserExtend}.
 */
@RestController
@RequestMapping("/api")
public class UserExtendResource {

    private final Logger log = LoggerFactory.getLogger(UserExtendResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceUserExtend";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserExtendService userExtendService;

    public UserExtendResource(UserExtendService userExtendService) {
        this.userExtendService = userExtendService;
    }

    /**
     * {@code POST  /user-extends} : Create a new userExtend.
     *
     * @param userExtendDTO the userExtendDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userExtendDTO, or with status {@code 400 (Bad Request)} if the userExtend has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-extends")
    public ResponseEntity<UserExtendDTO> createUserExtend(@RequestBody UserExtendDTO userExtendDTO) throws URISyntaxException {
        log.debug("REST request to save UserExtend : {}", userExtendDTO);
        if (userExtendDTO.getId() != null) {
            throw new BadRequestAlertException("A new userExtend cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserExtendDTO result = userExtendService.save(userExtendDTO);
        return ResponseEntity.created(new URI("/api/user-extends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-extends} : Updates an existing userExtend.
     *
     * @param userExtendDTO the userExtendDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userExtendDTO,
     * or with status {@code 400 (Bad Request)} if the userExtendDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userExtendDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-extends")
    public ResponseEntity<UserExtendDTO> updateUserExtend(@RequestBody UserExtendDTO userExtendDTO) throws URISyntaxException {
        log.debug("REST request to update UserExtend : {}", userExtendDTO);
        if (userExtendDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserExtendDTO result = userExtendService.save(userExtendDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userExtendDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-extends} : get all the userExtends.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userExtends in body.
     */
    @GetMapping("/user-extends")
    public ResponseEntity<List<UserExtendDTO>> getAllUserExtends(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("person-is-null".equals(filter)) {
            log.debug("REST request to get all UserExtends where person is null");
            return new ResponseEntity<>(userExtendService.findAllWherePersonIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of UserExtends");
        Page<UserExtendDTO> page = userExtendService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-extends/:id} : get the "id" userExtend.
     *
     * @param id the id of the userExtendDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userExtendDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-extends/{id}")
    public ResponseEntity<UserExtendDTO> getUserExtend(@PathVariable Long id) {
        log.debug("REST request to get UserExtend : {}", id);
        Optional<UserExtendDTO> userExtendDTO = userExtendService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userExtendDTO);
    }

    /**
     * {@code DELETE  /user-extends/:id} : delete the "id" userExtend.
     *
     * @param id the id of the userExtendDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-extends/{id}")
    public ResponseEntity<Void> deleteUserExtend(@PathVariable Long id) {
        log.debug("REST request to delete UserExtend : {}", id);
        userExtendService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
