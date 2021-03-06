package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.client.NotificationProducer;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.Attendance;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.ClassGroup;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.enumeration.NotificationType;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.AttendanceRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AttendanceDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassGroupDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.StudentDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AttendanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
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
 * Service Implementation for managing {@link Attendance}.
 */
@Service
@Transactional
public class AttendanceService {

    private final Logger log = LoggerFactory.getLogger(AttendanceService.class);

    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;

    private final ClassGroupService classGroupService;

    private final StudentService studentService;

    private final PersonService personService;
    private final SubjectService subjectService;
    @Autowired
    private NotificationProducer notificationProducer;

    public AttendanceService(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper,ClassGroupService classGroupService,StudentService studentService,PersonService personService,SubjectService subjectService) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
        this.classGroupService=classGroupService;
        this.studentService=studentService;
        this.personService=personService;
        this.subjectService=subjectService;
    }

    /**
     * Save a attendance.
     *
     * @param attendanceDTO the entity to save.
     * @return the persisted entity.
     */
    public AttendanceDTO save(AttendanceDTO attendanceDTO) {
        log.debug("Request to save Attendance : {}", attendanceDTO);
        Attendance attendance = attendanceMapper.toEntity(attendanceDTO);
        attendance = attendanceRepository.save(attendance);
        Optional<StudentDTO> studentDTO=studentService.findOne(attendanceDTO.getStudentId());
        Optional<ClassGroupDTO> classGroupDTO=classGroupService.findOne(attendanceDTO.getClassGroupId());
        //Generate the message Body
        List<String> bodyMessage=new ArrayList<>();
        bodyMessage.add("Guardians");
        studentDTO.ifPresent(studentDTO1 -> {
            studentDTO1.getGuardians().forEach(guardianDTO -> {
                personService.findOne(guardianDTO.getPersonId()).ifPresent(personDTO -> {
                    bodyMessage.add(personDTO.getKeycloakUserId());
                });
            });
            bodyMessage.add("StudentName");
            personService.findOne(studentDTO1.getPersonId()).ifPresent(personDTO -> {
                bodyMessage.add(personDTO.getFirstName()+" " +personDTO.getLastName());
            });
        });
        bodyMessage.add("SubjectName");
        classGroupDTO.ifPresent(classGroupDTO1 -> {
            subjectService.findOne(classGroupDTO1.getSubjectId()).ifPresent(subjectDTO -> {
                bodyMessage.add(subjectDTO.getCourseName());
                });

        });
        //Send Message to Producer and Create Push Notification

        notificationProducer.SendNotification(bodyMessage.toString(), NotificationType.ATTENDANCE);
        return attendanceMapper.toDto(attendance);
    }

    /**
     * Get all the attendances.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AttendanceDTO> findAll() {
        log.debug("Request to get all Attendances");
        return attendanceRepository.findAll().stream()
            .map(attendanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one attendance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttendanceDTO> findOne(Long id) {
        log.debug("Request to get Attendance : {}", id);
        return attendanceRepository.findById(id)
            .map(attendanceMapper::toDto);
    }
    /**
     * Get one attendance by classGroupID and studentID.
     *
     * @param classGroupId the id of the classGroup.
     * @param studentId the id of the student.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttendanceDTO> findOneByStudentIdAndClassGroupIdOnADate(Long studentId, Long classGroupId, ZonedDateTime date, ZonedDateTime dateAfter) {
        log.debug("Request to get Attendance from student: {} and class: {}", studentId,classGroupId);
        return attendanceRepository.findByStudentIdAndClassGroupIdAndCreationDateBetween(studentId,classGroupId,date,dateAfter)
            .map(attendanceMapper::toDto);
    }

    /**
     * Delete the attendance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Attendance : {}", id);
        attendanceRepository.deleteById(id);
    }

    /**
     * Get all the attendances of a given Day.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AttendanceDTO> findAllAttendanceFromStudentByDay( long studentId, String day) {
        log.debug("Request to get all Attendances");
        String[] formattedDate=day.split("-");
        ZonedDateTime zonedDateTimeRequest=ZonedDateTime.of(Integer.parseInt(formattedDate[0]),Integer.parseInt(formattedDate[1]),Integer.parseInt(formattedDate[2]),0,0,0,0, ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeAfter=zonedDateTimeRequest.plusHours(23).plusMinutes(59);
        System.out.println("zonedDateTimeRequest = " + zonedDateTimeRequest.toString());
        System.out.println("zonedDateTimeAfter.toString() = " + zonedDateTimeAfter.toString());
        return attendanceRepository.findByStudentIdAndCreationDateBetween(studentId,zonedDateTimeRequest,zonedDateTimeAfter).stream()
            .map(attendanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    /**
     * Get all the attendances of a given month.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AttendanceDTO> findAllAttendanceFromStudentInClassByMonth( long studentId, long classGroupId,String month) {
        log.debug("Request to get all Attendances");
        String[] formattedDate=month.split("-");

        ZonedDateTime zonedDateTimeRequest=ZonedDateTime.of(Integer.parseInt(formattedDate[0]),Integer.parseInt(formattedDate[1]),1,0,0,0,0, ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeAfter=zonedDateTimeRequest.plusMonths(1);
        System.out.println("zonedDateTimeRequest = " + zonedDateTimeRequest.toString());
        System.out.println("zonedDateTimeAfter.toString() = " + zonedDateTimeAfter.toString());
        return attendanceRepository.findByStudentIdAndClassGroupIdAndCreationDateBetweenOrderByCreationDate(studentId,classGroupId,zonedDateTimeRequest,zonedDateTimeAfter).stream()
            .map(attendanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

}
