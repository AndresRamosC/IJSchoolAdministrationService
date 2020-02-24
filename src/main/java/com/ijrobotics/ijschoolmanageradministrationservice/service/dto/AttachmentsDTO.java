package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Attachments} entity.
 */
public class AttachmentsDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    @Lob
    private byte[] attachment;

    private String attachmentContentType;

    private Long assignmentId;

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

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttachmentsDTO attachmentsDTO = (AttachmentsDTO) o;
        if (attachmentsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attachmentsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AttachmentsDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", attachment='" + getAttachment() + "'" +
            ", assignmentId=" + getAssignmentId() +
            "}";
    }
}
