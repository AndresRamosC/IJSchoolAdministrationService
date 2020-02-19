package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Guardian;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.GuardianRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GuardianDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.GuardianMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public GuardianService(GuardianRepository guardianRepository, GuardianMapper guardianMapper) {
        this.guardianRepository = guardianRepository;
        this.guardianMapper = guardianMapper;
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
     * Delete the guardian by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Guardian : {}", id);
        guardianRepository.deleteById(id);
    }
}
