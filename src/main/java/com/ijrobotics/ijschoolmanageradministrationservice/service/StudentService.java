package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Student;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.StudentRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ContactDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GuardianDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.guardianDtos.GuardianPhotoAndName;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.studentDtos.NewStudentDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.studentDtos.StudentInfoWithGuardianPhotoAndName;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.studentDtos.StudentPhotoAndName;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.StudentDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.GuardianMapper;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.PersonMapper;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.StudentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Student}.
 */
@Service
@Transactional
public class StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final PersonService personService;

    private final ContactService contactService;

    private final GuardianService guardianService;

    private final GuardianMapper guardianMapper;

    private final PersonMapper personMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper,PersonService personService,ContactService contactService,GuardianService guardianService,GuardianMapper guardianMapper,PersonMapper personMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.personService=personService;
        this.contactService=contactService;
        this.guardianService=guardianService;
        this.guardianMapper=guardianMapper;
        this.personMapper=personMapper;
    }

    /**
     * Save a student.
     *
     * @param studentDTO the entity to save.
     * @return the persisted entity.
     */
    public StudentDTO save(StudentDTO studentDTO) {
        log.debug("Request to save Student : {}", studentDTO);
        Student student = studentMapper.toEntity(studentDTO);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }
    /**
     * Save a guardian.
     *
     * @param studentDTO the entity to save.
     * @return the persisted entity.
     */
    public StudentDTO saveFullStudent(NewStudentDto studentDTO,long guardianId) {
        log.debug("Request to save student : {}", studentDTO.getStudentDTO());
        Optional<GuardianDTO> guardianDTO=guardianService.findOne(guardianId);

        studentDTO.getContactDTO().setCreationDate(ZonedDateTime.now());
        ContactDTO contactSaved=contactService.save(studentDTO.getContactDTO());

        studentDTO.getPersonDTO().setCreationDate(ZonedDateTime.now());
        studentDTO.getPersonDTO().addContacts(contactSaved);
        PersonDTO personSaved=personService.save(studentDTO.getPersonDTO());

        studentDTO.getStudentDTO().setCreationDate(ZonedDateTime.now());
        studentDTO.getStudentDTO().setPersonId(personSaved.getId());
        guardianDTO.ifPresent(guardianDTO1 -> {
            studentDTO.getStudentDTO().addGuardian(guardianDTO1);
        });
        Student student = studentMapper.toEntity(studentDTO.getStudentDTO());
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    /**
     * Get all the students.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StudentInfoWithGuardianPhotoAndName> findAll() {
        List<StudentInfoWithGuardianPhotoAndName> studentInfoWithGuardianPhotoAndNames=new ArrayList<>();
        studentRepository.findAllWithEagerRelationships().forEach(student -> {
            List<GuardianPhotoAndName> guardianPhotoAndNameList= new ArrayList<>();
            student.getGuardians().forEach(guardian->{
                guardianPhotoAndNameList.add(new GuardianPhotoAndName(guardianMapper.toDto(guardian),personMapper.toDto(guardian.getPerson()),new ArrayList<>()));
            });
            studentInfoWithGuardianPhotoAndNames.add(new StudentInfoWithGuardianPhotoAndName(guardianPhotoAndNameList,studentMapper.toDto(student),personMapper.toDto(student.getPerson())));
        });
        log.debug("Request to get all Students");
        return studentInfoWithGuardianPhotoAndNames;
    }

    /**
     * Get all the students with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<StudentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return studentRepository.findAllWithEagerRelationships(pageable).map(studentMapper::toDto);
    }

    /**
     * Get one student by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StudentDTO> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findOneWithEagerRelationships(id)
            .map(studentMapper::toDto);
    }
    /**
     * Get students by Guardian id.
     *
     * @param id the id of the Guardian.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public List<StudentDTO> findStudentsWithGuardian(Long id) {
        log.debug("Request to get Student with guardian: {}", id);
        return studentRepository.findByGuardiansId(id).stream()
            .map(studentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }
    /**
     * Get students by Guardian id.
     *
     * @param id the id of the Guardian.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public List<StudentPhotoAndName> findStudentsBasicInfoWithGuardian(Long id) {
        log.debug("Request to get Student with guardian: {}", id);
        List<Student> studentList=studentRepository.findByGuardiansId(id);
        List<StudentPhotoAndName> studentBasicInfo = new ArrayList<>();
        studentList.forEach(student -> {
            studentBasicInfo.add(new StudentPhotoAndName(studentMapper.toDto(student),personMapper.toDto(student.getPerson())));
        });
        return studentBasicInfo;
    }
    /**
     * Get one guardian by person id.
     *
     * @param id the id of the person.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StudentDTO> findOneByPersonId(Long id) {
        log.debug("Request to get Guardian : {}", id);
        return studentRepository.findByPersonId(id)
            .map(studentMapper::toDto);
    }
    /**
     * Get one guardian by person id.
     *
     * @param controlNumber the id of the person.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Boolean verifyControlNumber(Long controlNumber) {
        log.debug("Request to verify the control number : {}", controlNumber);
        return studentRepository.findByControlNumber(controlNumber).isPresent();
    }

    /**
     * Delete the student by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.deleteById(id);
    }
}
