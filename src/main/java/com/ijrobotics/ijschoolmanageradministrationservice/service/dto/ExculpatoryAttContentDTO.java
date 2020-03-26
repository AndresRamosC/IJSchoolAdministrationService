package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.ExculpatoryAttContent} entity.
 */
public class ExculpatoryAttContentDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    @Lob
    private byte[] data;

    private String dataContentType;

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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExculpatoryAttContentDTO exculpatoryAttContentDTO = (ExculpatoryAttContentDTO) o;
        if (exculpatoryAttContentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exculpatoryAttContentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExculpatoryAttContentDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
