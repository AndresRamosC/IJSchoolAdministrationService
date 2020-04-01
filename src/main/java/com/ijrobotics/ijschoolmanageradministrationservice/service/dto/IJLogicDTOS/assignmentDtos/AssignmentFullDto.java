package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.assignmentDtos;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AssignmentAssignedDTO;

import java.io.Serializable;

public class AssignmentFullDto implements Serializable {

    private AssignmentAndAttachmentsDto assignmentAndAttachmentsDto;
    private Boolean done;
    private Float grade;

    public AssignmentFullDto(AssignmentAssignedDTO assignmentAssignedDTO, AssignmentAndAttachmentsDto assignmentAndAttachmentsDto) {
        this.done = assignmentAssignedDTO.isDone();
        this.grade = assignmentAssignedDTO.getGrade();
        this.assignmentAndAttachmentsDto=assignmentAndAttachmentsDto;
    }

    public AssignmentAndAttachmentsDto getAssignmentAndAttachmentsDto() {
        return assignmentAndAttachmentsDto;
    }

    public void setAssignmentAndAttachmentsDto(AssignmentAndAttachmentsDto assignmentAndAttachmentsDto) {
        this.assignmentAndAttachmentsDto = assignmentAndAttachmentsDto;
    }

    public Boolean getDone() {
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
}
