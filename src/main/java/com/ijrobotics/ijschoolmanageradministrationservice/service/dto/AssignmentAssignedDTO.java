package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.AssignmentAssigned} entity.
 */
public class AssignmentAssignedDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    private Boolean done;

    private Float grade;


    private Long studentId;

    private Long assignmentId;

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

    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssignmentAssignedDTO assignmentAssignedDTO = (AssignmentAssignedDTO) o;
        if (assignmentAssignedDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assignmentAssignedDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssignmentAssignedDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", done='" + isDone() + "'" +
            ", grade=" + getGrade() +
            ", studentId=" + getStudentId() +
            ", assignmentId=" + getAssignmentId() +
            "}";
    }
}
