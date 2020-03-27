package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.classGroupsAndSubjectsDtos;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Subject;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.SubjectDTO;

import java.io.Serializable;

public class SubjectAdminDashBoardDto implements Serializable {
    private SubjectDTO subjectDTO;
    private long amount;

    @Override
    public String toString() {
        return "SubjectAdminDashBoardDto{" +
            "subjectDTO=" + subjectDTO +
            ", amount=" + amount +
            '}';
    }

    public SubjectDTO getSubjectDTO() {
        return subjectDTO;
    }

    public void setSubjectDTO(SubjectDTO subjectDTO) {
        this.subjectDTO = subjectDTO;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public SubjectAdminDashBoardDto(SubjectDTO subjectDTO, long amount) {
        this.subjectDTO = subjectDTO;
        this.amount = amount;
    }
}
