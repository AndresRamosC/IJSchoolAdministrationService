package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AttendanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attendance} and its DTO {@link AttendanceDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudentMapper.class, ClassGroupMapper.class})
public interface AttendanceMapper extends EntityMapper<AttendanceDTO, Attendance> {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "classGroup.id", target = "classGroupId")
    AttendanceDTO toDto(Attendance attendance);

    @Mapping(source = "studentId", target = "student")
    @Mapping(source = "classGroupId", target = "classGroup")
    Attendance toEntity(AttendanceDTO attendanceDTO);

    default Attendance fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attendance attendance = new Attendance();
        attendance.setId(id);
        return attendance;
    }
}
