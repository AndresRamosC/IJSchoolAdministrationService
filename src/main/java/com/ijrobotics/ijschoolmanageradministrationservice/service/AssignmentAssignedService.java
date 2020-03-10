package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.AssignmentAssigned;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.AssignmentAssignedRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AssignmentAssignedDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AssignmentAssignedMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AssignmentAssigned}.
 */
@Service
@Transactional
public class AssignmentAssignedService {

    private final Logger log = LoggerFactory.getLogger(AssignmentAssignedService.class);

    private final AssignmentAssignedRepository assignmentAssignedRepository;

    private final AssignmentAssignedMapper assignmentAssignedMapper;

    public AssignmentAssignedService(AssignmentAssignedRepository assignmentAssignedRepository, AssignmentAssignedMapper assignmentAssignedMapper) {
        this.assignmentAssignedRepository = assignmentAssignedRepository;
        this.assignmentAssignedMapper = assignmentAssignedMapper;
    }

    /**
     * Save a assignmentAssigned.
     *
     * @param assignmentAssignedDTO the entity to save.
     * @return the persisted entity.
     */
    public AssignmentAssignedDTO save(AssignmentAssignedDTO assignmentAssignedDTO) {
        log.debug("Request to save AssignmentAssigned : {}", assignmentAssignedDTO);
        AssignmentAssigned assignmentAssigned = assignmentAssignedMapper.toEntity(assignmentAssignedDTO);
        assignmentAssigned = assignmentAssignedRepository.save(assignmentAssigned);
        return assignmentAssignedMapper.toDto(assignmentAssigned);
    }

    /**
     * Get all the assignmentAssigneds.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssignmentAssignedDTO> findAll() {
        log.debug("Request to get all AssignmentAssigneds");
        return assignmentAssignedRepository.findAll().stream()
            .map(assignmentAssignedMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one assignmentAssigned by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssignmentAssignedDTO> findOne(Long id) {
        log.debug("Request to get AssignmentAssigned : {}", id);
        return assignmentAssignedRepository.findById(id)
            .map(assignmentAssignedMapper::toDto);
    }

    /**
     * Delete the assignmentAssigned by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AssignmentAssigned : {}", id);
        assignmentAssignedRepository.deleteById(id);
    }
}
