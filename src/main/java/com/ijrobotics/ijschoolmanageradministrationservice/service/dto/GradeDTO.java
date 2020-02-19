package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Grade} entity.
 */
public class GradeDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    private Integer semester;

    private Float grade;


    private Long classGroupId;

    private Long studentId;

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

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public Long getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(Long classGroupId) {
        this.classGroupId = classGroupId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GradeDTO gradeDTO = (GradeDTO) o;
        if (gradeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gradeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GradeDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", semester=" + getSemester() +
            ", grade=" + getGrade() +
            ", classGroupId=" + getClassGroupId() +
            ", studentId=" + getStudentId() +
            "}";
    }
}
