package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.ClassGroup;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.ClassGroupRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassGroupDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.ClassGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link ClassGroup}.
 */
@Service
@Transactional
public class ClassGroupService {

    private final Logger log = LoggerFactory.getLogger(ClassGroupService.class);

    private final ClassGroupRepository classGroupRepository;

    private final ClassGroupMapper classGroupMapper;

    public ClassGroupService(ClassGroupRepository classGroupRepository, ClassGroupMapper classGroupMapper) {
        this.classGroupRepository = classGroupRepository;
        this.classGroupMapper = classGroupMapper;
    }

    /**
     * Save a classGroup.
     *
     * @param classGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public ClassGroupDTO save(ClassGroupDTO classGroupDTO) {
        log.debug("Request to save ClassGroup : {}", classGroupDTO);
        ClassGroup classGroup = classGroupMapper.toEntity(classGroupDTO);
        classGroup = classGroupRepository.save(classGroup);
        return classGroupMapper.toDto(classGroup);
    }

    /**
     * Get all the classGroups.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClassGroupDTO> findAll() {
        log.debug("Request to get all ClassGroups");
        return classGroupRepository.findAll().stream()
            .map(classGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the classGroups where Grade is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClassGroupDTO> findAllWhereGradeIsNull() {
        log.debug("Request to get all classGroups where Grade is null");
        return StreamSupport
            .stream(classGroupRepository.findAll().spliterator(), false)
            .filter(classGroup -> classGroup.getGrade() == null)
            .map(classGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    /**
     *  Get all the classGroups where Teacher ID is {@code null} ordered by subject ID and StarHour.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClassGroupDTO> findAllWhereTeacherIdOrderedBySubjectIdAndStartHour(Long id) {
        log.debug("Request to get all classGroups where Teacher id ");
        return classGroupRepository.findByTeacherIdOrderBySubjectIdAscStartHourAsc(id).stream()
            .map(classGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    /**
     *  Get all the classGroups where Teacher ID is {@code null} ordered by  StarHour.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClassGroupDTO> findAllWhereTeacherIdOrderedByStartHour(Long id) {
        log.debug("Request to get all classGroups where Teacher id ");
        return classGroupRepository.findByTeacherIdOrderByStartHourAsc(id).stream()
            .map(classGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    /**
     *  Get all the classGroups where Student ID appears is {@code null} ordered by StarHour.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClassGroupDTO> findAllWhereStudentIdOrderedByStartHour(Long id) {
        log.debug("Request to get all classGroups where student id ");
        return classGroupRepository.findByStudentsIdOrderByStartHourAsc(id).stream()
            .map(classGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one classGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClassGroupDTO> findOne(Long id) {
        log.debug("Request to get ClassGroup : {}", id);
        return classGroupRepository.findById(id)
            .map(classGroupMapper::toDto);
    }
    /**
     * Get one classGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClassGroupDTO> findClassToGetStudents(Long id) {
        log.debug("Request to get ClassGroup : {}", id);
        return classGroupRepository.findById(id).map(classGroupMapper::toDto);
    }

    /**
     * Delete the classGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClassGroup : {}", id);
        classGroupRepository.deleteById(id);
    }
}
