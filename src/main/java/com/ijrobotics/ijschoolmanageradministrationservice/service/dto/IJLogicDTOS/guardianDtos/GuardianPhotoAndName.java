package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.guardianDtos;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GuardianDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.studentDtos.StudentPhotoAndName;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;

import java.io.Serializable;
import java.util.List;

public class GuardianPhotoAndName implements Serializable {
    private List<StudentPhotoAndName> studentPhotoAndNameList;
    private Long guardianId;
    private String name;
    private String lastName;
    private byte[] photograph;
    private String photographContentType;

    public GuardianPhotoAndName(GuardianDTO guardianDTO, PersonDTO personDTO,List<StudentPhotoAndName> studentPhotoAndNameList) {
        this.studentPhotoAndNameList = studentPhotoAndNameList;
        this.guardianId = guardianDTO.getId();
        this.name = personDTO.getFirstName();
        this.lastName = personDTO.getLastName();
        this.photograph = personDTO.getPhotograph();
        this.photographContentType = personDTO.getPhotographContentType();
    }

    public List<StudentPhotoAndName> getStudentPhotoAndNameList() {
        return studentPhotoAndNameList;
    }

    public void setStudentPhotoAndNameList(List<StudentPhotoAndName> studentPhotoAndNameList) {
        this.studentPhotoAndNameList = studentPhotoAndNameList;
    }

    public Long getGuardianId() {
        return guardianId;
    }

    public void setGuardianId(Long guardianId) {
        this.guardianId = guardianId;
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
}
