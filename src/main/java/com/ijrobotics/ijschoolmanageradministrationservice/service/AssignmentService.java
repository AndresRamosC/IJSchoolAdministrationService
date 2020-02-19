package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Assignment;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.AssignmentRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AssignmentDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AssignmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Assignment}.
 */
@Service
@Transactional
public class AssignmentService {

    private final Logger log = LoggerFactory.getLogger(AssignmentService.class);

    private final AssignmentRepository assignmentRepository;

    private final AssignmentMapper assignmentMapper;

    public AssignmentService(AssignmentRepository assignmentRepository, AssignmentMapper assignmentMapper) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
    }

    /**
     * Save a assignment.
     *
     * @param assignmentDTO the entity to save.
     * @return the persisted entity.
     */
    public AssignmentDTO save(AssignmentDTO assignmentDTO) {
        log.debug("Request to save Assignment : {}", assignmentDTO);
        Assignment assignment = assignmentMapper.toEntity(assignmentDTO);
        assignment = assignmentRepository.save(assignment);
        return assignmentMapper.toDto(assignment);
    }

    /**
     * Get all the assignments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssignmentDTO> findAll() {
        log.debug("Request to get all Assignments");
        return assignmentRepository.findAll().stream()
            .map(assignmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one assignment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssignmentDTO> findOne(Long id) {
        log.debug("Request to get Assignment : {}", id);
        return assignmentRepository.findById(id)
            .map(assignmentMapper::toDto);
    }

    /**
     * Delete the assignment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Assignment : {}", id);
        assignmentRepository.deleteById(id);
    }
}
