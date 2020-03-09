package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.PersonService;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;

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
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Person}.
 */
@RestController
@RequestMapping("/api")
public class PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServicePerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonService personService;

    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    /**
     * {@code POST  /people} : Create a new person.
     *
     * @param personDTO the personDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personDTO, or with status {@code 400 (Bad Request)} if the person has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/people")
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO) throws URISyntaxException {
        log.debug("REST request to save Person : {}", personDTO);
        if (personDTO.getId() != null) {
            throw new BadRequestAlertException("A new person cannot already have an ID", ENTITY_NAME, "idexists");
        }
        personDTO.setCreationDate(ZonedDateTime.now());
        PersonDTO result = personService.save(personDTO);
        return ResponseEntity.created(new URI("/api/people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /people} : Updates an existing person.
     *
     * @param personDTO the personDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personDTO,
     * or with status {@code 400 (Bad Request)} if the personDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/people/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO personDTO,@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update Person : {}", personDTO);
        Optional<PersonDTO> personDTOUpdate = personService.findOne(id);
        if ( !personDTOUpdate.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (personDTO.getFirstName()!=null){
            personDTOUpdate.get().setFirstName(personDTO.getFirstName());
        }
        if (personDTO.getLastName()!=null){
            personDTOUpdate.get().setLastName(personDTO.getLastName());
        }
        if (personDTO.getGender()!=null){
            personDTOUpdate.get().setGender(personDTO.getGender());
        }
        if (personDTO.getBloodGroup()!=null){
            personDTOUpdate.get().setBloodGroup(personDTO.getBloodGroup());
        }
        if (personDTO.getDateOfBirth()!=null){
            personDTOUpdate.get().setDateOfBirth(personDTO.getDateOfBirth());
        }
        if (personDTO.getPhotograph()!=null){
            personDTOUpdate.get().setPhotograph(personDTO.getPhotograph());
        }
        if (personDTO.getPhotographContentType()!=null){
            personDTOUpdate.get().setPhotographContentType(personDTO.getPhotographContentType());
        }
        if (personDTO.isEnabled()!=null){
            personDTOUpdate.get().setEnabled(personDTO.isEnabled());
        }
        if (personDTO.getKeycloakUserId()!=null){
            personDTOUpdate.get().setKeycloakUserId(personDTO.getKeycloakUserId());
        }
        if (personDTO.getContacts()!=null){
            personDTOUpdate.get().setContacts(personDTO.getContacts());
        }
        PersonDTO result = personService.save(personDTOUpdate.get());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personDTOUpdate.get().getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /people} : get all the people.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of people in body.
     */
    @GetMapping("/people")
    public List<PersonDTO> getAllPeople(@RequestParam(required = false) String filter,@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("guardian-is-null".equals(filter)) {
            log.debug("REST request to get all Persons where guardian is null");
            return personService.findAllWhereGuardianIsNull();
        }
        if ("student-is-null".equals(filter)) {
            log.debug("REST request to get all Persons where student is null");
            return personService.findAllWhereStudentIsNull();
        }
        if ("employee-is-null".equals(filter)) {
            log.debug("REST request to get all Persons where employee is null");
            return personService.findAllWhereEmployeeIsNull();
        }
        log.debug("REST request to get all People");
        return personService.findAll();
    }

    /**
     * {@code GET  /people/:id} : get the "id" person.
     *
     * @param id the id of the personDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/people/{id}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable Long id) {
        log.debug("REST request to get Person : {}", id);
        Optional<PersonDTO> personDTO = personService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personDTO);
    }

    /**
     * {@code GET  /people/:id} : get the person from Keycloak User ID.
     *
     * @param id the id of the KeycloakUserId to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/people/byUserID/{id}")
    public ResponseEntity<PersonDTO> getPersonFromUserExtendedId(@PathVariable String id) {
        log.debug("REST request to get Person : {}", id);
        Optional<PersonDTO> personDTO = personService.findOneWithUserId(id);
        return ResponseUtil.wrapOrNotFound(personDTO);
    }

    /**
     * {@code DELETE  /people/:id} : delete the "id" person.
     *
     * @param id the id of the personDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        log.debug("REST request to delete Person : {}", id);
        personService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
