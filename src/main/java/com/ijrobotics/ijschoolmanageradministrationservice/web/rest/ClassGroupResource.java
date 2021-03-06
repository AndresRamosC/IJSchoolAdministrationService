package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.classGroupsAndSubjectsDtos.ClassGroupAndSubjectDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.classGroupsAndSubjectsDtos.NewClassGroupDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.classGroupsAndSubjectsDtos.SubjectDashBoardInfo;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.studentDtos.StudentAndPersonDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.studentDtos.StudentsInClassRoomDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassGroupDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.ClassGroup}.
 */
@RestController
@RequestMapping("/api")
public class ClassGroupResource {

    private final Logger log = LoggerFactory.getLogger(ClassGroupResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceClassGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassGroupService classGroupService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private PersonService personService;

    public ClassGroupResource(ClassGroupService classGroupService) {
        this.classGroupService = classGroupService;
    }

    /**
     * {@code POST  /class-groups} : Create a new classGroup.
     *
     * @param classGroupDTO the classGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classGroupDTO, or with status {@code 400 (Bad Request)} if the classGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/class-groups")
    public ResponseEntity<ClassGroupDTO> createClassGroup(@RequestBody NewClassGroupDto classGroupDTO) throws URISyntaxException {
        log.debug("REST request to save ClassGroup : {}", classGroupDTO.getClassGroupDTO());
        ClassGroupDTO result = classGroupService.saveNewClassRoom(classGroupDTO);
        return ResponseEntity.created(new URI("/api/class-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /class-groups} : Updates an existing classGroup.
     *
     * @param classGroupDTO the classGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classGroupDTO,
     * or with status {@code 400 (Bad Request)} if the classGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/class-groups")
    public ResponseEntity<ClassGroupDTO> updateClassGroup(@RequestBody ClassGroupDTO classGroupDTO) throws URISyntaxException {
        log.debug("REST request to update ClassGroup : {}", classGroupDTO);
        if (classGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassGroupDTO result = classGroupService.save(classGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /class-groups} : get all the classGroups.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classGroups in body.
     */
    @GetMapping("/class-groups")
    public List<ClassGroupDTO> getAllClassGroups(@RequestParam(required = false) String filter,@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("grade-is-null".equals(filter)) {
            log.debug("REST request to get all ClassGroups where grade is null");
            return classGroupService.findAllWhereGradeIsNull();
        }
        log.debug("REST request to get all ClassGroups");
        return classGroupService.findAll();
    }

    /**
     * {@code GET  /class-groups/:id} : get the "id" classGroup.
     *
     * @param id the id of the classGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-groups/{id}")
    public ResponseEntity<ClassGroupDTO> getClassGroup(@PathVariable Long id) {
        log.debug("REST request to get ClassGroup : {}", id);
        Optional<ClassGroupDTO> classGroupDTO = classGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classGroupDTO);
    }

    /**
     * {@code GET  /class-groups/:StudentId/:Date} : get the classGroup of a student on a day.
     *
     * @param studentId the id of the Student to retrieve the classes.
     * @param date the day to get the classes
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-groups/{studentId}/{date}")
    public List<ClassGroupAndSubjectDto> getClassGroupFromStudentIdAndDate(@PathVariable Long studentId,@PathVariable String date) {
        List<ClassGroupAndSubjectDto> classGroupAndSubjectDtoList=new ArrayList<>();


        String[] formattedDate=date.split("-");
        LocalDate localDate=LocalDate.of(Integer.parseInt(formattedDate[0]),Integer.parseInt(formattedDate[1]),Integer.parseInt(formattedDate[2]));
        //Monday 1, sunday 7
        int weekDay=localDate.getDayOfWeek().getValue()-1;

        classGroupService.findAllWhereStudentIdOrderedByStartHour(studentId).forEach(classGroupDTO -> {
            classGroupDTO.getClassSchedules().forEach(classScheduleDTO -> {
                boolean[] bits = new boolean[7];
                for (int i = 6; i >= 0; i--) {
                    bits[i] = (classScheduleDTO.getWeekDays() & (1 << i)) != 0;
                }
                if (bits[weekDay]){
                    classGroupAndSubjectDtoList.add(new ClassGroupAndSubjectDto(classGroupDTO,subjectService.findOne(classGroupDTO.getSubjectId()).get()));
                }
            });
        });
        return classGroupAndSubjectDtoList;
    }
    /**
     * {@code GET  /class-groups/:TeacherId/:Date} : get the classGroup of a teacher on a day.
     *
     * @param teacherId the id of the teacher to retrieve the classes.
     * @param date the day to get the classes
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-groups/fromTeacher/{teacherId}/{date}")
    public List<ClassGroupAndSubjectDto> getClassGroupFromTeacherIdAndDate(@PathVariable Long teacherId,@PathVariable String date) {
        List<ClassGroupDTO> classGroupDTOList=new ArrayList<>();

        Optional<TeacherDTO> teacherDTO=teacherService.findOne(teacherId);
        if (teacherDTO.isPresent()){
            List<ClassGroupDTO> classGroupDTOList1 =  classGroupService.findAllWhereTeacherIdOrderedByStartHour(teacherDTO.get().getId());

            String[] formattedDate=date.split("-");
            LocalDate localDate=LocalDate.of(Integer.parseInt(formattedDate[0]),Integer.parseInt(formattedDate[1]),Integer.parseInt(formattedDate[2]));
            //Monday 1, sunday 7
            int weekDay=localDate.getDayOfWeek().getValue()-1;
            classGroupDTOList1.forEach(classGroupDTO -> {
                classGroupDTO.getClassSchedules().forEach(classScheduleDTO -> {
                    boolean[] bits = new boolean[7];
                    for (int i = 6; i >= 0; i--) {
                        bits[i] = (classScheduleDTO.getWeekDays() & (1 << i)) != 0;
                    }
                    if (bits[weekDay]){
                        classGroupDTOList.add(classGroupDTO);
                    }
                });
            });
            List<ClassGroupAndSubjectDto> classGroupAndSubjectDtoList=new ArrayList<>();
            classGroupDTOList.forEach(classGroupDTO -> {
                classGroupAndSubjectDtoList.add(new ClassGroupAndSubjectDto(classGroupDTO,subjectService.findOne(classGroupDTO.getSubjectId()).get()));
            });
            return classGroupAndSubjectDtoList;
        }else{
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, " ");
        }

    }
    /**
     * {@code GET  /class-groups/Students/Attendance/:ClassGroupID} : get the students of a classgroup and the attendance.
     *
     * @param classGroupID the id of the group to retrieve the students.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the List of Students, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-groups/Students/Attendance/{classGroupID}/{date}")
    @Transactional
    public List<StudentsInClassRoomDTO> getStudentsAttendanceFromClassGroup(@PathVariable Long classGroupID, @PathVariable String date ) {
        ClassGroupDTO classGroup=classGroupService.findClassToGetStudents(classGroupID).get();
        List<StudentsInClassRoomDTO> studentsInClassRoomDTOList=new ArrayList<>();
        String[] formattedDate=date.split("-");
        ZonedDateTime zonedDateTimeRequest=ZonedDateTime.of(Integer.parseInt(formattedDate[0]),Integer.parseInt(formattedDate[1]),Integer.parseInt(formattedDate[2]),0,0,0,0, ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeAfter=zonedDateTimeRequest.plusHours(23).plusMinutes(59);
        classGroup.getStudents().forEach(student -> {
            Optional<AttendanceDTO> attendanceDTO=attendanceService.findOneByStudentIdAndClassGroupIdOnADate(student.getId(),classGroup.getId(),zonedDateTimeRequest.truncatedTo(ChronoUnit.MINUTES),zonedDateTimeAfter.truncatedTo(ChronoUnit.MINUTES));
            Optional<PersonDTO> personDTO =personService.findOne(student.getPersonId());
            if (personDTO.isPresent()){
                if (attendanceDTO.isPresent()){

                    studentsInClassRoomDTOList.add(new StudentsInClassRoomDTO(student.getId(),personDTO.get().getLastName()+" "+personDTO.get().getFirstName(),attendanceDTO.get().isOnTime()));
                }else {
                    studentsInClassRoomDTOList.add(0,new StudentsInClassRoomDTO(student.getId(),personDTO.get().getLastName()+" "+personDTO.get().getFirstName()));
                }
            }
        });
        return studentsInClassRoomDTOList;
    }
    /**
     * {@code GET  /class-groups/Students/:ClassGroupID} : get the students of a classgroup.
     *
     * @param classGroupID the id of the group to retrieve the students.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the List of Students, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-groups/Students/{classGroupID}")
    @Transactional
    public List<StudentAndPersonDto> getStudentsFromClassGroup(@PathVariable Long classGroupID) {
        ClassGroupDTO classGroup=classGroupService.findClassToGetStudents(classGroupID).get();
        List<StudentAndPersonDto> studentsInClassRoomDTOList=new ArrayList<>();
        classGroup.getStudents().forEach(student -> {
            Optional<PersonDTO> personDTO =personService.findOne(student.getPersonId());
            if (personDTO.isPresent()){
                studentsInClassRoomDTOList.add(new StudentAndPersonDto(student,personDTO.get(),new ArrayList<>()));
            }
        });
        return studentsInClassRoomDTOList;
    }
    /**
     * {@code GET  /class-groups/subjectId/:ClassGroupID} : get the ClassGroups of a subjectId.
     *
     * @param subjectId the id of the subject to retrieve the students.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the List of Students, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-groups/whereSubject/{subjectId}")
    @Transactional
    public List<SubjectDashBoardInfo> getClassGroupFromSubject(@PathVariable Long subjectId) {
        return classGroupService.findAllClassGroupWhereSubjectId(subjectId);
    }

    /**
     * {@code DELETE  /class-groups/:id} : delete the "id" classGroup.
     *
     * @param id the id of the classGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/class-groups/{id}")
    public ResponseEntity<Void> deleteClassGroup(@PathVariable Long id) {
        log.debug("REST request to delete ClassGroup : {}", id);
        classGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}
