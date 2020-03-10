package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Assignment;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.AssignmentRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AssignmentDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AssignmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
     * Get all the assignments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssignmentDTO> findAllWithClassGroupId(Long classGroupId) {
        log.debug("Request to get all Assignments");
        return assignmentRepository.findByClassGroupId(classGroupId).stream()
            .map(assignmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    /**
     * Get all the assignments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssignmentDTO> findAllWithClassGroupIdAndMonth(long classGroupId,String month) {
        log.debug("Request to get all Assignments");
        String[] formattedDate=month.split("-");

        ZonedDateTime zonedDateTimeRequest=ZonedDateTime.of(Integer.parseInt(formattedDate[0]),Integer.parseInt(formattedDate[1]),1,0,0,0,0, ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeAfter=zonedDateTimeRequest.plusMonths(1);
        return assignmentRepository.findByClassGroupIdAndDueDateBetween(classGroupId,zonedDateTimeRequest,zonedDateTimeAfter).stream()
            .map(assignmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    /**
     * Get all the assignments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssignmentDTO> findAllWithClassAndStudentId(long studentId, long classGroupId) {
        log.debug("Request to get all Assignments");
        return assignmentRepository.findByStudentIdAndClassGroupId(studentId,classGroupId).stream()
            .map(assignmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    /**
     * Get all the assignments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssignmentDTO> findAllWithClassAndStudentIdAndMonth( long studentId,long classGroupId,String month) {
        log.debug("Request to get all Assignments");
        String[] formattedDate=month.split("-");

        ZonedDateTime zonedDateTimeRequest=ZonedDateTime.of(Integer.parseInt(formattedDate[0]),Integer.parseInt(formattedDate[1]),1,0,0,0,0, ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeAfter=zonedDateTimeRequest.plusMonths(1);
        return assignmentRepository.findByStudentIdAndClassGroupIdAndDueDateBetween(studentId,classGroupId,zonedDateTimeRequest,zonedDateTimeAfter).stream()
            .map(assignmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    /**
     * Get all the assignments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssignmentDTO> findAllWithStudentId(Long studentId) {
        log.debug("Request to get all Assignments");
        return assignmentRepository.findByStudentId(studentId).stream()
            .map(assignmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    /**
     * Get all the assignments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssignmentDTO> findAllWithStudentIdAndMonth(long studentId,String month) {
        log.debug("Request to get all Assignments");
        String[] formattedDate=month.split("-");

        ZonedDateTime zonedDateTimeRequest=ZonedDateTime.of(Integer.parseInt(formattedDate[0]),Integer.parseInt(formattedDate[1]),1,0,0,0,0, ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeAfter=zonedDateTimeRequest.plusMonths(1);
        return assignmentRepository.findByStudentIdAndDueDateBetween(studentId,zonedDateTimeRequest,zonedDateTimeAfter).stream()
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
