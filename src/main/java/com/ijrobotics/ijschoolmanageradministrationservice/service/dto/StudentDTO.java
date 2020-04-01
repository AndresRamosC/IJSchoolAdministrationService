package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.*;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Student} entity.
 */
public class StudentDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    private LocalDate admissionDate;

    private Integer academicYear;

    private Long controlNumber;


    private Long personId;

    private Set<GuardianDTO> guardians = new HashSet<>();

    private Set<ClassGroupDTO> classGroups = new HashSet<>();

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

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public Integer getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(Integer academicYear) {
        this.academicYear = academicYear;
    }

    public Long getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(Long controlNumber) {
        this.controlNumber = controlNumber;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Set<GuardianDTO> getGuardians() {
        return guardians;
    }

    public void setGuardians(Set<GuardianDTO> guardians) {
        this.guardians = guardians;
    }
    public void addGuardian(GuardianDTO guardian) {
        this.guardians.add(guardian) ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudentDTO studentDTO = (StudentDTO) o;
        if (studentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", admissionDate='" + getAdmissionDate() + "'" +
            ", academicYear=" + getAcademicYear() +
            ", controlNumber=" + getControlNumber() +
            ", personId=" + getPersonId() +
            "}";
    }
}
