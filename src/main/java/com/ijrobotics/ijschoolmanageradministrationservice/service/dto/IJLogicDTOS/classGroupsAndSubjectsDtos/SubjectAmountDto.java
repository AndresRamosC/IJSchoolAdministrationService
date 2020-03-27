package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.classGroupsAndSubjectsDtos;

import java.io.Serializable;

public class SubjectAmountDto implements Serializable {
    private Long subjectId;
    private Long amount;

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public SubjectAmountDto(Long subjectId, Long amount)  {
        this.subjectId = subjectId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "SubjectAmountDto{" +
            "subjectId=" + subjectId +
            ", amount=" + amount +
            '}';
    }
}
