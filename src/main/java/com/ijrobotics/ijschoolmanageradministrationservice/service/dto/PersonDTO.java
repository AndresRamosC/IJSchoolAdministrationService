package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.enumeration.Genders;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.enumeration.BlodTypes;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Person} entity.
 */
public class PersonDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    private String firstName;

    private String lastName;

    private Genders gender;

    private BlodTypes bloodGroup;

    private LocalDate dateOfBirth;

    @Lob
    private byte[] photograph;

    private String photographContentType;
    private Boolean assigned;


    private Long userExtendId;

    private Set<ContactDTO> contacts = new HashSet<>();

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Genders getGender() {
        return gender;
    }

    public void setGender(Genders gender) {
        this.gender = gender;
    }

    public BlodTypes getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BlodTypes bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public byte[] getPhotograph() {
        return photograph;
    }

    public void setPhotograph(byte[] photograph) {
        this.photograph = photograph;
    }

    public String getPhotographContentType() {
        return photographContentType;
    }

    public void setPhotographContentType(String photographContentType) {
        this.photographContentType = photographContentType;
    }

    public Boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }

    public Long getUserExtendId() {
        return userExtendId;
    }

    public void setUserExtendId(Long userExtendId) {
        this.userExtendId = userExtendId;
    }

    public Set<ContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(Set<ContactDTO> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonDTO personDTO = (PersonDTO) o;
        if (personDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender='" + getGender() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", photograph='" + getPhotograph() + "'" +
            ", assigned='" + isAssigned() + "'" +
            ", userExtendId=" + getUserExtendId() +
            "}";
    }
}
