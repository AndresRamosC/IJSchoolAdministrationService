package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Exculpatory} entity.
 */
public class ExculpatoryDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    private ZonedDateTime leaveDateAndHour;

    private Boolean fullDay;

    private String description;


    private Long guardianId;

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

    public ZonedDateTime getLeaveDateAndHour() {
        return leaveDateAndHour;
    }

    public void setLeaveDateAndHour(ZonedDateTime leaveDateAndHour) {
        this.leaveDateAndHour = leaveDateAndHour;
    }

    public Boolean isFullDay() {
        return fullDay;
    }

    public void setFullDay(Boolean fullDay) {
        this.fullDay = fullDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGuardianId() {
        return guardianId;
    }

    public void setGuardianId(Long guardianId) {
        this.guardianId = guardianId;
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

        ExculpatoryDTO exculpatoryDTO = (ExculpatoryDTO) o;
        if (exculpatoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exculpatoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExculpatoryDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", leaveDateAndHour='" + getLeaveDateAndHour() + "'" +
            ", fullDay='" + isFullDay() + "'" +
            ", description='" + getDescription() + "'" +
            ", guardianId=" + getGuardianId() +
            ", studentId=" + getStudentId() +
            "}";
    }
}
