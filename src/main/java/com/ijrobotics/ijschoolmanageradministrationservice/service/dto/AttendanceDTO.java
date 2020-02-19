package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Attendance} entity.
 */
public class AttendanceDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;


    private Long studentId;

    private Long classGroupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(Long classGroupId) {
        this.classGroupId = classGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttendanceDTO attendanceDTO = (AttendanceDTO) o;
        if (attendanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attendanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AttendanceDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", studentId=" + getStudentId() +
            ", classGroupId=" + getClassGroupId() +
            "}";
    }
}
