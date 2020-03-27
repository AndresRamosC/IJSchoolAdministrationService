package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.ClassGroup;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.Subject;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.Teacher;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.TeacherRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassGroupDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.classGroupsAndSubjectsDtos.SubjectAmountDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.teacherDtos.TeacherFullInfoDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.TeacherDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.EmployeeMapper;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.PersonMapper;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.TeacherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Teacher}.
 */
@Service
@Transactional
public class TeacherService {

    private final Logger log = LoggerFactory.getLogger(TeacherService.class);

    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;
    private  PersonMapper personMapper;
    private EmployeeMapper employeeMapper;

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper,PersonMapper personMapper,EmployeeMapper employeeMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        this.personMapper=personMapper;
        this.employeeMapper=employeeMapper;
    }

    /**
     * Save a teacher.
     *
     * @param teacherDTO the entity to save.
     * @return the persisted entity.
     */
    public TeacherDTO save(TeacherDTO teacherDTO) {
        log.debug("Request to save Teacher : {}", teacherDTO);
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        teacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    /**
     * Get all the teachers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TeacherFullInfoDto> findAll() {
        log.debug("Request to get all Teachers");
        List<TeacherFullInfoDto> teacherFullInfoDtosList=new ArrayList<>();
        teacherRepository.findAll().forEach(teacher -> {
            teacherFullInfoDtosList.add(new TeacherFullInfoDto(teacherMapper.toDto(teacher),employeeMapper.toDto(teacher.getEmployee()),personMapper.toDto(teacher.getEmployee().getPerson()),getAmountOfSubjects(teacher.getClassGroups()),teacher.getClassGroups().size()));
        });

        return teacherFullInfoDtosList;
    }

    /**
     * Get one teacher by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TeacherDTO> findOne(Long id) {
        log.debug("Request to get Teacher : {}", id);
        return teacherRepository.findById(id)
            .map(teacherMapper::toDto);
    }
    /**
     * Get one Teacher by Employee id.
     *
     * @param id the id of the person.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TeacherDTO> findOneByEmployeeId(Long id) {
        log.debug("Request to get Teacher : {}", id);
        return teacherRepository.findByEmployeeId(id)
            .map(teacherMapper::toDto);
    }

    /**
     * Delete the teacher by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Teacher : {}", id);
        teacherRepository.deleteById(id);
    }
    static long getAmountOfSubjects(Set<ClassGroup> classGroupDTOList){
        List<Subject> subjectAmountDtoList=new ArrayList<>();
        classGroupDTOList.forEach(classGroup -> {
            if (!subjectAmountDtoList.contains(classGroup.getSubject())){
                subjectAmountDtoList.add(classGroup.getSubject());
            }
        });
        return subjectAmountDtoList.size();
    }
}
