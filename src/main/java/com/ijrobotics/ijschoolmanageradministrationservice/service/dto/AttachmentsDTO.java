package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Attachments} entity.
 */
public class AttachmentsDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    private String title;

    private Long size;

    private String mimeType;


    private Long attachmentsContentId;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getAttachmentsContentId() {
        return attachmentsContentId;
    }

    public void setAttachmentsContentId(Long attachmentsContentId) {
        this.attachmentsContentId = attachmentsContentId;
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
            ", title='" + getTitle() + "'" +
            ", size=" + getSize() +
            ", mimeType='" + getMimeType() + "'" +
            ", attachmentsContentId=" + getAttachmentsContentId() +
            ", assignmentId=" + getAssignmentId() +
            "}";
    }
}
