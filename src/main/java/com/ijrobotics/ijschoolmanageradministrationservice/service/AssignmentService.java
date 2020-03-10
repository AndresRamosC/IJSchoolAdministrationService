package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Assignment;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.AssignmentAssignedRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.AssignmentRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.AttachmentsRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AssignmentAssignedDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AssignmentDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.AssignmentAndAttachmentsDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.AssignmentFullDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AssignmentAssignedMapper;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AssignmentMapper;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AttachmentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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


    private final AssignmentAssignedRepository assignmentAssignedRepository;

    private final AssignmentAssignedMapper assignmentAssignedMapper;
    private final AttachmentsRepository attachmentsRepository;
    private final AttachmentsMapper attachmentsMapper;

    public AssignmentService(AssignmentRepository assignmentRepository, AssignmentMapper assignmentMapper,AssignmentAssignedRepository assignmentAssignedRepository,
                             AssignmentAssignedMapper assignmentAssignedMapper,AttachmentsRepository attachmentsRepository,AttachmentsMapper attachmentsMapper) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
        this.assignmentAssignedRepository=assignmentAssignedRepository;
        this.assignmentAssignedMapper=assignmentAssignedMapper;
        this.attachmentsRepository=attachmentsRepository;
        this.attachmentsMapper=attachmentsMapper;
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
    public List<AssignmentAndAttachmentsDto> findAllWithClassGroupId(Long classGroupId) {
        log.debug("Request to get all Assignments");
        List<AssignmentDTO> assignmentDTOS=assignmentRepository.findByClassGroupId(classGroupId).stream()
            .map(assignmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        List<AssignmentAndAttachmentsDto> assignmentAndAttachmentsDtos=new ArrayList<>();
        assignmentDTOS.forEach(assignmentDTO -> {
            assignmentAndAttachmentsDtos.add(new AssignmentAndAttachmentsDto(assignmentDTO,attachmentsMapper.toDto(attachmentsRepository.findByAssignmentId(assignmentDTO.getId()))));
        });
        return assignmentAndAttachmentsDtos;
    }
    /**
     * Get all the assignments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssignmentAndAttachmentsDto> findAllWithClassGroupIdAndMonth(long classGroupId,String month) {
        log.debug("Request to get all Assignments");
        String[] formattedDate=month.split("-");

        ZonedDateTime zonedDateTimeRequest=ZonedDateTime.of(Integer.parseInt(formattedDate[0]),Integer.parseInt(formattedDate[1]),1,0,0,0,0, ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeAfter=zonedDateTimeRequest.plusMonths(1);

        List<AssignmentDTO> assignmentDTOS=assignmentRepository.findByClassGroupIdAndDueDateBetween(classGroupId,zonedDateTimeRequest,zonedDateTimeAfter).stream()
            .map(assignmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        List<AssignmentAndAttachmentsDto> assignmentAndAttachmentsDtos=new ArrayList<>();
        assignmentDTOS.forEach(assignmentDTO -> {
            assignmentAndAttachmentsDtos.add(new AssignmentAndAttachmentsDto(assignmentDTO,attachmentsMapper.toDto(attachmentsRepository.findByAssignmentId(assignmentDTO.getId()))));
        });
        return assignmentAndAttachmentsDtos;
    }
    /**
     * Get all the assignments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssignmentFullDto> findAllWithClassAndStudentId(long studentId, long classGroupId) {
        log.debug("Request to get all Assignments");
        List<AssignmentAssignedDTO> assignmentAssignedList= assignmentAssignedRepository.findByStudentId(studentId).stream()
            .map(assignmentAssignedMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        List<AssignmentFullDto> assignmentFullDtos=new ArrayList<>();
        assignmentAssignedList.forEach(assignmentAssignedDTO -> {
            Optional<Assignment> assignmentOptional=assignmentRepository.findByIdAndClassGroupId(assignmentAssignedDTO.getAssignmentId(),classGroupId);
            assignmentOptional.ifPresent(assignment -> {
                assignmentFullDtos.add(new AssignmentFullDto(assignmentAssignedDTO, new AssignmentAndAttachmentsDto(assignmentMapper.toDto(assignment),attachmentsMapper.toDto(attachmentsRepository.findByAssignmentId(assignment.getId()))) ));
            });
        });
        return assignmentFullDtos;
    }
    /**
     * Get all the assignments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssignmentFullDto> findAllWithClassAndStudentIdAndMonth( long studentId,long classGroupId,String month) {
        log.debug("Request to get all Assignments");
        String[] formattedDate=month.split("-");
        List<AssignmentAssignedDTO> assignmentAssignedList= assignmentAssignedRepository.findByStudentId(studentId).stream()
            .map(assignmentAssignedMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        ZonedDateTime zonedDateTimeRequest=ZonedDateTime.of(Integer.parseInt(formattedDate[0]),Integer.parseInt(formattedDate[1]),1,0,0,0,0, ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeAfter=zonedDateTimeRequest.plusMonths(1);
        List<AssignmentFullDto> assignmentsFullDtoList=new ArrayList<>();
        assignmentAssignedList.forEach(assignmentAssignedDTO -> {
            Optional<Assignment> assignmentOptional=assignmentRepository.findByIdAndClassGroupIdAndDueDateBetween(assignmentAssignedDTO.getAssignmentId(),classGroupId,zonedDateTimeRequest,zonedDateTimeAfter);
            assignmentOptional.ifPresent(assignment -> assignmentsFullDtoList.add(new AssignmentFullDto(assignmentAssignedDTO,new AssignmentAndAttachmentsDto(assignmentMapper.toDto(assignment),attachmentsMapper.toDto(attachmentsRepository.findByAssignmentId(assignment.getId()))))));
        });
        return assignmentsFullDtoList;
    }
    /**
     * Get all the assignments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssignmentFullDto> findAllWithStudentId(Long studentId) {
        log.debug("Request to get all Assignments");
       List<AssignmentAssignedDTO> assignmentAssignedList= assignmentAssignedRepository.findByStudentId(studentId).stream()
            .map(assignmentAssignedMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

       List<AssignmentFullDto> assignmentsFullDtoList=new ArrayList<>();
       assignmentAssignedList.forEach(assignmentAssignedDTO -> {
           Optional<Assignment> assignmentOptional=assignmentRepository.findById(assignmentAssignedDTO.getAssignmentId());
           assignmentOptional.ifPresent(assignment -> assignmentsFullDtoList.add(new AssignmentFullDto(assignmentAssignedDTO,new AssignmentAndAttachmentsDto(assignmentMapper.toDto(assignment),attachmentsMapper.toDto(attachmentsRepository.findByAssignmentId(assignment.getId()))))));
       });
        return assignmentsFullDtoList;
    }
    /**
     * Get all the assignments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssignmentFullDto> findAllWithStudentIdAndMonth(long studentId, String month) {
        log.debug("Request to get all Assignments");
        String[] formattedDate=month.split("-");

        ZonedDateTime zonedDateTimeRequest=ZonedDateTime.of(Integer.parseInt(formattedDate[0]),Integer.parseInt(formattedDate[1]),1,0,0,0,0, ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeAfter=zonedDateTimeRequest.plusMonths(1);
        List<AssignmentAssignedDTO> assignmentAssignedList= assignmentAssignedRepository.findByStudentId(studentId).stream()
            .map(assignmentAssignedMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        List<AssignmentFullDto> assignmentsFullDtoList=new ArrayList<>();
        assignmentAssignedList.forEach(assignmentAssignedDTO -> {
            Optional<Assignment> assignmentOptional=assignmentRepository.findByIdAndDueDateBetween(assignmentAssignedDTO.getAssignmentId(),zonedDateTimeRequest,zonedDateTimeAfter);
            assignmentOptional.ifPresent(assignment -> assignmentsFullDtoList.add(new AssignmentFullDto(assignmentAssignedDTO,new AssignmentAndAttachmentsDto(assignmentMapper.toDto(assignment),attachmentsMapper.toDto(attachmentsRepository.findByAssignmentId(assignment.getId()))))));
        });
        return assignmentsFullDtoList;
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
