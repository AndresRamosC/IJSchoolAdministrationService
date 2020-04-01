package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.assignmentDtos;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AssignmentDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AttachmentsDTO;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

public class AssignmentAndAttachmentsDto implements Serializable {
    private String title;
    private String description;
    private ZonedDateTime dueDate;
    private List<AttachmentsDTO> attachmentsDTOList;
    private Long classGroupId;


    public AssignmentAndAttachmentsDto(AssignmentDTO assignmentDTO, List<AttachmentsDTO> attachmentsDTOList) {
        this.title = assignmentDTO.getTitle();
        this.description = assignmentDTO.getDescription();
        this.dueDate = assignmentDTO.getDueDate();
        this.attachmentsDTOList = attachmentsDTOList;
        this.classGroupId=assignmentDTO.getClassGroupId();
    }

    public Long getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(Long classGroupId) {
        this.classGroupId = classGroupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public List<AttachmentsDTO> getAttachmentsDTOList() {
        return attachmentsDTOList;
    }

    public void setAttachmentsDTOList(List<AttachmentsDTO> attachmentsDTOList) {
        this.attachmentsDTOList = attachmentsDTOList;
    }
}
