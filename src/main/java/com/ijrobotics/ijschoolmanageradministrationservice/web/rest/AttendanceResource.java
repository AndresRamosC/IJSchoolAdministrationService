package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;

import com.ijrobotics.ijschoolmanageradministrationservice.service.AttendanceService;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AttendanceDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Attendance}.
 */
@RestController
@RequestMapping("/api")
public class AttendanceResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceResource.class);

    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceAttendance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendanceService attendanceService;

    public AttendanceResource(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     * {@code POST  /attendances} : Create a new attendance.
     *
     * @param attendanceDTO the attendanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendanceDTO, or with status {@code 400 (Bad Request)} if the attendance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendances")
    public ResponseEntity<AttendanceDTO> createAttendance(@RequestBody AttendanceDTO attendanceDTO) throws URISyntaxException {
        log.debug("REST request to save Attendance : {}", attendanceDTO);
        if (attendanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        attendanceDTO.setCreationDate(ZonedDateTime.now());
        ZonedDateTime time=ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS);
        Optional<AttendanceDTO> attendanceDTOOptional = attendanceService.findOneByStudentIdAndClassGroupIdOnADate(attendanceDTO.getStudentId(),attendanceDTO.getClassGroupId(),time,time.plusHours(23));
        if (!attendanceDTOOptional.isPresent()){
            AttendanceDTO result = attendanceService.save(attendanceDTO);
            return ResponseEntity.created(new URI("/api/attendances/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        }else{
            throw new BadRequestAlertException("A new attendance cannot be created", ENTITY_NAME, "attendance for that class already exist");
        }
    }

    /**
     * {@code PUT  /attendances} : Updates an existing attendance.
     *
     * @param attendanceDTO the attendanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendanceDTO,
     * or with status {@code 400 (Bad Request)} if the attendanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attendances/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(@RequestBody AttendanceDTO attendanceDTO,@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update Attendance : {}", attendanceDTO);
        Optional<AttendanceDTO> attendanceDTOUpdate = attendanceService.findOne(id);
        if (!attendanceDTOUpdate.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (attendanceDTO.getStudentId()!=null){
            attendanceDTOUpdate.get().setStudentId(attendanceDTO.getStudentId());
        }
        if (attendanceDTO.getClassGroupId()!=null){
            attendanceDTOUpdate.get().setClassGroupId(attendanceDTO.getClassGroupId());
        }

        AttendanceDTO result = attendanceService.save(attendanceDTOUpdate.get());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attendanceDTOUpdate.get().getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attendances} : get all the attendances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendances in body.
     */
    @GetMapping("/attendances")
    public List<AttendanceDTO> getAllAttendances() {
        log.debug("REST request to get all Attendances");
        return attendanceService.findAll();
    }

    /**
     * {@code GET  /attendances/:id} : get the "id" attendance.
     *
     * @param id the id of the attendanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attendances/{id}")
    public ResponseEntity<AttendanceDTO> getAttendance(@PathVariable Long id) {
        log.debug("REST request to get Attendance : {}", id);
        Optional<AttendanceDTO> attendanceDTO = attendanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendanceDTO);
    }

    /**
     * {@code DELETE  /attendances/:id} : delete the "id" attendance.
     *
     * @param id the id of the attendanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attendances/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        log.debug("REST request to delete Attendance : {}", id);
        attendanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /attendances/student/byDay/} : get all the attendances of a given day.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendances in body.
     */
    @GetMapping("/attendances/student/byDay/{studentId}/{day}")
    public List<AttendanceDTO> getAllAttendancesOfStudentByDay(@PathVariable long studentId,@PathVariable String day) {
        log.debug("REST request to get all Attendances of a given day");
        return attendanceService.findAllAttendanceFromStudentByDay(studentId,day);
    }
    /**
     * {@code GET  /attendances/byMonth} : get all the attendances of a given month.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendances in body.
     */
    @GetMapping("/attendances/student/classGroup/byMonth/{studentId}/{classGroupId}/{month}")
    public List<AttendanceDTO> getAllAttendancesOfStudentInClassByMonth(@PathVariable long studentId,@PathVariable long classGroupId,@PathVariable String month) {
        log.debug("REST request to get all Attendances of a given month ordered by day");
        return attendanceService.findAllAttendanceFromStudentInClassByMonth(studentId,classGroupId,month);
    }
}
