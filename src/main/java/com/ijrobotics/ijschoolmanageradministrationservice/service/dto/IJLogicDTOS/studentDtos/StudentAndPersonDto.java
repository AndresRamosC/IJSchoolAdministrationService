package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.studentDtos;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.enumeration.BlodTypes;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.enumeration.Genders;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ContactDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.classGroupsAndSubjectsDtos.ClassGroupAndSubjectDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.StudentDTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class StudentAndPersonDto implements Serializable {
    private List<ClassGroupAndSubjectDto> classGroupAndSubjectDtoList;
    private Long studentId;
    private Integer academicYear;
    private Long controlNumber;
    private Long personId;
    private String firstName;
    private String lastName;
    private Genders gender;
    private BlodTypes bloodGroup;
    private LocalDate dateOfBirth;
    private byte[] photograph;
    private String photographContentType;
    private Set<ContactDTO> contacts;

        public StudentAndPersonDto(StudentDTO studentDTO,PersonDTO personDTO,List<ClassGroupAndSubjectDto> classGroupAndSubjectDtoList) {
        this.classGroupAndSubjectDtoList = classGroupAndSubjectDtoList;
        this.studentId = studentDTO.getId() ;
        this.academicYear = studentDTO.getAcademicYear();
        this.controlNumber = studentDTO.getControlNumber();
        this.personId = studentDTO.getPersonId();
        this.firstName = personDTO.getFirstName();
        this.lastName = personDTO.getLastName();
        this.gender = personDTO.getGender();
        this.bloodGroup = personDTO.getBloodGroup();
        this.dateOfBirth = personDTO.getDateOfBirth();
        this.photograph = personDTO.getPhotograph();
        this.photographContentType = personDTO.getPhotographContentType();
        this.contacts=personDTO.getContacts();
    }

    public List<ClassGroupAndSubjectDto> getClassGroupAndSubjectDtoList() {
        return classGroupAndSubjectDtoList;
    }

    public void setClassGroupAndSubjectDtoList(List<ClassGroupAndSubjectDto> classGroupAndSubjectDtoList) {
        this.classGroupAndSubjectDtoList = classGroupAndSubjectDtoList;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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

    public Set<ContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(Set<ContactDTO> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "StudentAndPersonDto{" +
            "classGroupAndSubjectDtoList=" + classGroupAndSubjectDtoList +
            ", studentId=" + studentId +
            ", academicYear=" + academicYear +
            ", controlNumber=" + controlNumber +
            ", personId=" + personId +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", gender=" + gender +
            ", bloodGroup=" + bloodGroup +
            ", dateOfBirth=" + dateOfBirth +
            ", photograph=" + Arrays.toString(photograph) +
            ", photographContentType='" + photographContentType + '\'' +
            ", contacts=" + contacts +
            '}';
    }
}
