package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Subject;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.ClassGroupRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.SubjectRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.classGroupsAndSubjectsDtos.SubjectAdminDashBoardDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.SubjectDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.SubjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Subject}.
 */
@Service
@Transactional
public class SubjectService {

    private final Logger log = LoggerFactory.getLogger(SubjectService.class);

    private final SubjectRepository subjectRepository;

    private final SubjectMapper subjectMapper;

    private final ClassGroupRepository classGroupRepository;

    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper,ClassGroupRepository classGroupRepository) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
        this.classGroupRepository=classGroupRepository;
    }

    /**
     * Save a subject.
     *
     * @param subjectDTO the entity to save.
     * @return the persisted entity.
     */
    public SubjectDTO save(SubjectDTO subjectDTO) {
        log.debug("Request to save Subject : {}", subjectDTO);
        subjectDTO.setCreationDate(ZonedDateTime.now());
        Subject subject = subjectMapper.toEntity(subjectDTO);
        subject = subjectRepository.save(subject);
        return subjectMapper.toDto(subject);
    }

    /**
     * Get all the subjects.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubjectAdminDashBoardDto> findAll() {
        log.debug("Request to get all Subjects");
        List<SubjectAdminDashBoardDto> subjectAdminDashBoardDtos=new ArrayList<>();
        subjectRepository.findAll().forEach(subject -> {
            subjectAdminDashBoardDtos.add(new SubjectAdminDashBoardDto(subjectMapper.toDto(subject),classGroupRepository.countBySubjectId(subject.getId())));
        });
        return subjectAdminDashBoardDtos;
    }

    /**
     * Get one subject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubjectDTO> findOne(Long id) {
        log.debug("Request to get Subject : {}", id);
        return subjectRepository.findById(id)
            .map(subjectMapper::toDto);
    }

    /**
     * Delete the subject by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Subject : {}", id);
        subjectRepository.deleteById(id);
    }
}
