package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.enumeration.EducationLevel;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Guardian} entity.
 */
public class GuardianDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    private EducationLevel educationLevel;

    private String occupation;

    private String workAddress;


    private Long personId;

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

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(EducationLevel educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GuardianDTO guardianDTO = (GuardianDTO) o;
        if (guardianDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), guardianDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GuardianDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", educationLevel='" + getEducationLevel() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", workAddress='" + getWorkAddress() + "'" +
            ", personId=" + getPersonId() +
            "}";
    }
}
