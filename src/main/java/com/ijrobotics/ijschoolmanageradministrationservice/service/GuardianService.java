package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Guardian;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.GuardianRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ContactDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GuardianDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.guardianDtos.NewGuardianDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.GuardianMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Guardian}.
 */
@Service
@Transactional
public class GuardianService {

    private final Logger log = LoggerFactory.getLogger(GuardianService.class);

    private final GuardianRepository guardianRepository;

    private final GuardianMapper guardianMapper;

    private final PersonService personService;

    private final ContactService contactService;

    public GuardianService(GuardianRepository guardianRepository, GuardianMapper guardianMapper,ContactService contactService,PersonService personService) {
        this.guardianRepository = guardianRepository;
        this.guardianMapper = guardianMapper;
        this.contactService=contactService;
        this.personService=personService;
    }

    /**
     * Save a guardian.
     *
     * @param guardianDTO the entity to save.
     * @return the persisted entity.
     */
    public GuardianDTO save(GuardianDTO guardianDTO) {
        log.debug("Request to save Guardian : {}", guardianDTO);
        Guardian guardian = guardianMapper.toEntity(guardianDTO);
        guardian = guardianRepository.save(guardian);
        return guardianMapper.toDto(guardian);
    }
    /**
     * Save a guardian.
     *
     * @param guardianDTO the entity to save.
     * @return the persisted entity.
     */
    public GuardianDTO saveFullGuardian(NewGuardianDto guardianDTO) {
        log.debug("Request to save Guardian : {}", guardianDTO.getGuardianDTO());
        guardianDTO.getContactDTO().setCreationDate(ZonedDateTime.now());
        ContactDTO contactSaved=contactService.save(guardianDTO.getContactDTO());
        guardianDTO.getPersonDTO().setCreationDate(ZonedDateTime.now());
        guardianDTO.getPersonDTO().addContacts(contactSaved);
        PersonDTO personSaved=personService.save(guardianDTO.getPersonDTO());
        guardianDTO.getGuardianDTO().setCreationDate(ZonedDateTime.now());
        guardianDTO.getGuardianDTO().setPersonId(personSaved.getId());
        Guardian guardian = guardianMapper.toEntity(guardianDTO.getGuardianDTO());
        guardian = guardianRepository.save(guardian);
        return guardianMapper.toDto(guardian);
    }

    /**
     * Get all the guardians.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GuardianDTO> findAll() {
        log.debug("Request to get all Guardians");
        return guardianRepository.findAll().stream()
            .map(guardianMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one guardian by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GuardianDTO> findOne(Long id) {
        log.debug("Request to get Guardian : {}", id);
        return guardianRepository.findById(id)
            .map(guardianMapper::toDto);
    }
    /**
     * Get one guardian by person id.
     *
     * @param id the id of the person.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GuardianDTO> findOneByPersonId(Long id) {
        log.debug("Request to get Guardian : {}", id);
        return guardianRepository.findByPersonId(id)
            .map(guardianMapper::toDto);
    }

    /**
     * Delete the guardian by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Guardian : {}", id);
        guardianRepository.deleteById(id);
    }
}
