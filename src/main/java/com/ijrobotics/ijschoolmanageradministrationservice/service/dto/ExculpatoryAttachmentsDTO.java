package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.ExculpatoryAttachments} entity.
 */
public class ExculpatoryAttachmentsDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    private String title;

    private Long size;

    private String mimeType;


    private Long exculpatoryAttContentId;

    private Long exculpatoryId;

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

    public Long getExculpatoryAttContentId() {
        return exculpatoryAttContentId;
    }

    public void setExculpatoryAttContentId(Long exculpatoryAttContentId) {
        this.exculpatoryAttContentId = exculpatoryAttContentId;
    }

    public Long getExculpatoryId() {
        return exculpatoryId;
    }

    public void setExculpatoryId(Long exculpatoryId) {
        this.exculpatoryId = exculpatoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExculpatoryAttachmentsDTO exculpatoryAttachmentsDTO = (ExculpatoryAttachmentsDTO) o;
        if (exculpatoryAttachmentsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exculpatoryAttachmentsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExculpatoryAttachmentsDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", title='" + getTitle() + "'" +
            ", size=" + getSize() +
            ", mimeType='" + getMimeType() + "'" +
            ", exculpatoryAttContentId=" + getExculpatoryAttContentId() +
            ", exculpatoryId=" + getExculpatoryId() +
            "}";
    }
}
