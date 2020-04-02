package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.ClassGroup;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.ClassGroupRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassGroupDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassScheduleDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.classGroupsAndSubjectsDtos.ClassGroupAndSubjectDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.classGroupsAndSubjectsDtos.NewClassGroupDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.classGroupsAndSubjectsDtos.SubjectDashBoardInfo;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.teacherDtos.TeacherFullInfoDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.StudentDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.SubjectDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    private ClassScheduleService classScheduleService;

    private StudentService studentService;
    private SubjectService subjectService;
    private ClassScheduleMapper classScheduleMapper;

    private TeacherMapper teacherMapper;
    private EmployeeMapper employeeMapper;
    private PersonMapper personMapper;
    private SubjectMapper subjectMapper;

    public ClassGroupService(ClassGroupRepository classGroupRepository, ClassGroupMapper classGroupMapper,ClassScheduleService classScheduleService,StudentService studentService,
                             SubjectService subjectService,ClassScheduleMapper classScheduleMapper,TeacherMapper teacherMapper,EmployeeMapper employeeMapper,PersonMapper personMapper,SubjectMapper subjectMapper) {
        this.classGroupRepository = classGroupRepository;
        this.classGroupMapper = classGroupMapper;
        this.classScheduleService = classScheduleService;
        this.studentService=studentService;
        this.subjectService=subjectService;
        this.classScheduleMapper=classScheduleMapper;
        this.teacherMapper=teacherMapper;
        this.employeeMapper=employeeMapper;
        this.personMapper=personMapper;
        this.subjectMapper=subjectMapper;
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
     * Save a classGroup.
     *
     * @param newClassGroupDto the entity to save.
     * @return the persisted entity.
     */
    public ClassGroupDTO saveNewClassRoom(NewClassGroupDto newClassGroupDto) {
        log.debug("Request to save ClassGroup : {}", newClassGroupDto.getClassGroupDTO());
        //receive a list with all the class schedules for this classGroup

        List<ClassScheduleDTO> classScheduleDTOList=classScheduleService.findAll(); //get all the classSchedules Already Created
        List<ClassScheduleDTO> classScheduleDTOListToInsert=new ArrayList<>(); //get all the classSchedules Already Created
        newClassGroupDto.getClassScheduleDTOS().forEach(classScheduleDTO -> {
            //verify each group if it already exist
            boolean exist=false;
            for (ClassScheduleDTO classScheduleDTO1:classScheduleDTOList) {
                if (classScheduleDTO1.getWeekDays().equals(classScheduleDTO.getWeekDays())){
                    if (classScheduleDTO1.getStartHour().equals(classScheduleDTO.getStartHour())){
                        if (classScheduleDTO1.getEndHour().equals(classScheduleDTO.getEndHour())){
                            exist=true;
                            classScheduleDTOListToInsert.add(classScheduleDTO1);
                            break;
                        }
                    }
                }
            }
            if (!exist){
               classScheduleDTOListToInsert.add(classScheduleService.save(classScheduleDTO));
            }
        });
        //Get all the students that will be added
        List<StudentDTO> studentDTOListToInsert=new ArrayList<>();
        newClassGroupDto.getStudentsIds().forEach(studentId->{
            studentService.findOne(studentId).ifPresent(studentDTOListToInsert::add);
        });
        newClassGroupDto.getClassGroupDTO().setSize(studentDTOListToInsert.size());
        newClassGroupDto.getClassGroupDTO().addSchedules(classScheduleDTOListToInsert);
        newClassGroupDto.getClassGroupDTO().addStudents(studentDTOListToInsert);
        newClassGroupDto.getClassGroupDTO().setSubjectId(newClassGroupDto.getSubjectId());
        newClassGroupDto.getClassGroupDTO().setTeacherId(newClassGroupDto.getTeacherId());
        ClassGroup classGroup = classGroupMapper.toEntity(newClassGroupDto.getClassGroupDTO());
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
        return classGroupRepository.findAllWithEagerRelationships().stream()
            .map(classGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the classGroups with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ClassGroupDTO> findAllWithEagerRelationships(Pageable pageable) {
        return classGroupRepository.findAllWithEagerRelationships(pageable).map(classGroupMapper::toDto);
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
        return classGroupRepository.findByTeacherIdOrderBySubjectIdAsc(id).stream()
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
        return classGroupRepository.findByTeacherId(id).stream()
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
        return classGroupRepository.findByStudentsId(id).stream()
            .map(classGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    /**
     *  Get all the classGroups where subject ID.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubjectDashBoardInfo> findAllClassGroupWhereSubjectId(Long id) {
        log.debug("Request to get all classGroups where Teacher id ");
        List<SubjectDashBoardInfo> subjectDashBoardInfoList=new ArrayList<>();
        List<ClassGroup> classGroupDTOList= classGroupRepository.findBySubjectId(id);
        classGroupDTOList.forEach(classGroup -> {
            subjectDashBoardInfoList.add(new SubjectDashBoardInfo(new ClassGroupAndSubjectDto(classGroupMapper.toDto(classGroup),subjectMapper.toDto(classGroup.getSubject())),
                new TeacherFullInfoDto(teacherMapper.toDto(classGroup.getTeacher()),employeeMapper.toDto(classGroup.getTeacher().getEmployee()),personMapper.toDto(classGroup.getTeacher().getEmployee().getPerson()),0,0)));
        });
        return subjectDashBoardInfoList;
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
        return classGroupRepository.findOneWithEagerRelationships(id)
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
