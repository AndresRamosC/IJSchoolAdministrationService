package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.ClassGroupService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.StudentService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.TeacherService;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.StudentDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.TeacherDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassGroupDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

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
    public ResponseEntity<ClassGroupDTO> createClassGroup(@RequestBody ClassGroupDTO classGroupDTO) throws URISyntaxException {
        log.debug("REST request to save ClassGroup : {}", classGroupDTO);
        if (classGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new classGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassGroupDTO result = classGroupService.save(classGroupDTO);
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
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classGroups in body.
     */
    @GetMapping("/class-groups")
    public List<ClassGroupDTO> getAllClassGroups(@RequestParam(required = false) String filter) {
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
    public List<ClassGroupDTO> getClassGroupFromStudentIdAndDate(@PathVariable Long studentId,@PathVariable String date) {
        List<ClassGroupDTO> classGroupDTOList=new ArrayList<>();

        Optional<StudentDTO> studentDTO=studentService.findOne(studentId);

        String[] formattedDate=date.split("-");
        LocalDate localDate=LocalDate.of(Integer.parseInt(formattedDate[0]),Integer.parseInt(formattedDate[1]),Integer.parseInt(formattedDate[2]));
        //Monday 1, sunday 7
        int weekDay=localDate.getDayOfWeek().getValue()-1;
        studentDTO.get().getClassGroups().forEach(classGroupDTO -> {
            boolean[] bits = new boolean[7];
            for (int i = 6; i >= 0; i--) {
                bits[i] = (classGroupDTO.getWeekDays() & (1 << i)) != 0;
            }
            if (bits[weekDay]){
                classGroupDTOList.add(classGroupDTO);
            }
        });
        return classGroupDTOList;
    }
    /**
     * {@code GET  /class-groups/:TeacherId/:Date} : get the classGroup of a teacher on a day.
     *
     * @param teacherId the id of the teacher to retrieve the classes.
     * @param date the day to get the classes
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-groups/fromTeacher/{teacherId}/{date}")
    public List<ClassGroupDTO> getClassGroupFromTeacherIdAndDate(@PathVariable Long teacherId,@PathVariable String date) {
        List<ClassGroupDTO> classGroupDTOList=new ArrayList<>();

        Optional<TeacherDTO> teacherDTO=teacherService.findOne(teacherId);
        if (teacherDTO.isPresent()){
            List<ClassGroupDTO> classGroupDTOList1 =  classGroupService.findAllWhereTeacherIdOrderedByStartHour(teacherDTO.get().getId());

            String[] formattedDate=date.split("-");
            LocalDate localDate=LocalDate.of(Integer.parseInt(formattedDate[0]),Integer.parseInt(formattedDate[1]),Integer.parseInt(formattedDate[2]));
            //Monday 1, sunday 7
            int weekDay=localDate.getDayOfWeek().getValue()-1;
            classGroupDTOList1.forEach(classGroupDTO -> {
                boolean[] bits = new boolean[7];
                for (int i = 6; i >= 0; i--) {
                    bits[i] = (classGroupDTO.getWeekDays() & (1 << i)) != 0;
                }
                if (bits[weekDay]){
                    classGroupDTOList.add(classGroupDTO);
                }
            });
            return classGroupDTOList;
        }else{
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, " ");
        }

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
