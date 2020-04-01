package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.studentDtos;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.StudentDTO;

import java.io.Serializable;
import java.util.Arrays;

public class StudentPhotoAndName implements Serializable {
    private Long studentId;
    private String name;
    private String lastName;
    private byte[] photograph;
    private String photographContentType;
    private Integer academicYear;

    public StudentPhotoAndName(StudentDTO studentDTO, PersonDTO personDTO) {
        this.studentId=studentDTO.getId();
        this.name = personDTO.getFirstName();
        this.lastName = personDTO.getLastName();
        this.photograph = personDTO.getPhotograph();
        this.photographContentType = personDTO.getPhotographContentType();
        this.academicYear=studentDTO.getAcademicYear();
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Integer getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(Integer academicYear) {
        this.academicYear = academicYear;
    }

    @Override
    public String toString() {
        return "StudentPhotoAndName{" +
            "studentId=" + studentId +
            ", name='" + name + '\'' +
            ", lastName='" + lastName + '\'' +
            ", photograph=" + Arrays.toString(photograph) +
            ", photographContentType='" + photographContentType + '\'' +
            ", academicYear=" + academicYear +
            '}';
    }
}
