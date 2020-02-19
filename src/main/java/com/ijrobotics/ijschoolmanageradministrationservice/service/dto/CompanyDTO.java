package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Company} entity.
 */
public class CompanyDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    private String name;

    private String bussinessField;

    private String webPage;

    private String description;


    private Set<ContactDTO> contacts = new HashSet<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBussinessField() {
        return bussinessField;
    }

    public void setBussinessField(String bussinessField) {
        this.bussinessField = bussinessField;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(Set<ContactDTO> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;
        if (companyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", name='" + getName() + "'" +
            ", bussinessField='" + getBussinessField() + "'" +
            ", webPage='" + getWebPage() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
